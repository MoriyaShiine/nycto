/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.menu;

import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.power.NyctoPowerRegistry;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import moriyashiine.nycto.neoforge.registry.NyctoBlocks;
import moriyashiine.nycto.neoforge.registry.NyctoMenuTypes;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import moriyashiine.nycto.neoforge.registry.NyctoTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class VampireAltarMenu extends AbstractContainerMenu {
	private static final List<String> ALTAR_CHOOSABLE_POWERS = List.of(
			NyctoPowers.BAT_FORM,
			NyctoPowers.BAT_SWARM,
			NyctoPowers.BATSTEP,
			NyctoPowers.BLOOD_BARRIER,
			NyctoPowers.BLOOD_FLECHETTES,
			NyctoPowers.BLOODRUSH,
			NyctoPowers.CARNAGE,
			NyctoPowers.DARK_FORM,
			NyctoPowers.HAEMOGENESIS,
			NyctoPowers.HYPNOTIZE,
			NyctoPowers.KEEN_SENSES,
			NyctoPowers.MIST_FORM,
			NyctoPowers.VAMPIRIC_THRALL);

	private final Container altarSlots = new SimpleContainer(2) {
		@Override
		public void setChanged() {
			super.setChanged();
			VampireAltarMenu.this.slotsChanged(this);
		}
	};
	private final ContainerLevelAccess access;
	private final Player player;

	public final List<String> selectablePowers = new ArrayList<>();
	public final List<String> playerPowers = new ArrayList<>();
	public ItemStack itemCost = ItemStack.EMPTY;

	public VampireAltarMenu(int containerId, Inventory inventory) {
		this(containerId, inventory, ContainerLevelAccess.NULL);
	}

	public VampireAltarMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
		super(NyctoMenuTypes.VAMPIRE_ALTAR.get(), containerId);
		this.access = access;
		player = inventory.player;
		addSlot(new Slot(altarSlots, 0, 122, 94) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return ItemStack.isSameItemSameComponents(stack, itemCost);
			}
		});
		addSlot(new Slot(altarSlots, 1, 142, 94) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return isAlternateMaterial(stack);
			}
		});
		addPlayerInventorySlots(inventory, 8, 141);
		refreshPowerLists();
		itemCost = refreshItemCost();
	}

	@Override
	public boolean stillValid(Player player) {
		return NyctoData.isVampire(player) && stillValid(access, player, NyctoBlocks.VAMPIRE_ALTAR.get());
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		ItemStack clicked = ItemStack.EMPTY;
		Slot slot = slots.get(slotIndex);
		if (!slot.hasItem()) {
			return clicked;
		}
		ItemStack slotStack = slot.getItem();
		clicked = slotStack.copy();
		if (slotIndex < 2) {
			if (!moveItemStackTo(slotStack, 2, slots.size(), false)) {
				return ItemStack.EMPTY;
			}
		} else if (getSlot(0).mayPlace(slotStack)) {
			if (!moveItemStackTo(slotStack, 0, 1, false)) {
				return ItemStack.EMPTY;
			}
		} else if (getSlot(1).mayPlace(slotStack)) {
			if (!moveItemStackTo(slotStack, 1, 2, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			return ItemStack.EMPTY;
		}
		if (slotStack.isEmpty()) {
			slot.setByPlayer(ItemStack.EMPTY);
		} else {
			slot.setChanged();
		}
		return clicked;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		access.execute((level, pos) -> clearContainer(player, altarSlots));
	}

	@Override
	public boolean clickMenuButton(Player player, int buttonId) {
		if (buttonId == Short.MAX_VALUE) {
			return true;
		}
		if (buttonId < 0 || buttonId >= selectablePowers.size() || !canUpgrade(player)) {
			return false;
		}
		String power = selectablePowers.get(buttonId);
		if (NyctoData.hasPower(player, power) || NyctoData.isRejectedWeakness(power)) {
			return false;
		}
		access.execute((level, pos) -> {
			if (!player.isCreative()) {
				player.giveExperienceLevels(-getExpCost());
				altarSlots.getItem(0).shrink(getPrimaryMaterialCost());
				altarSlots.getItem(1).shrink(getAlternateMaterialCost());
			}
			clearContainer(player, altarSlots);
			altarSlots.setChanged();
			level.playSound(null, pos, NyctoSoundEvents.ALTAR_USE.get(), SoundSource.BLOCKS, 1, level.getRandom().nextFloat() * 0.1F + 0.9F);
		});
		NyctoData.addPower(player, power);
		NyctoData.advanceUpgradeCostSeed(player);
		refreshPowerLists();
		itemCost = refreshItemCost();
		return true;
	}

	public boolean canUpgrade(Player player) {
		if (player.isCreative()) {
			return true;
		}
		return player.experienceLevel >= getExpCost()
				&& altarSlots.getItem(0).getCount() >= getPrimaryMaterialCost()
				&& altarSlots.getItem(1).getCount() >= getAlternateMaterialCost();
	}

	public int getPlayerPowers() {
		return playerPowers.size();
	}

	public int getPrimaryMaterialCost() {
		return switch (getUpgradeTier()) {
			case 1 -> 1;
			case 2 -> 3;
			default -> 5;
		};
	}

	public int getAlternateMaterialCost() {
		return getUpgradeTier();
	}

	public int getExpCost() {
		return getUpgradeTier() * 5;
	}

	public void swapPowers(String first, String second) {
		if (first.equals(second) || !playerPowers.contains(first) || !playerPowers.contains(second)) {
			return;
		}
		LinkedHashSet<String> reordered = new LinkedHashSet<>(NyctoData.getPowers(player));
		List<String> powers = new ArrayList<>(reordered);
		int firstIndex = powers.indexOf(first);
		int secondIndex = powers.indexOf(second);
		if (firstIndex < 0 || secondIndex < 0) {
			return;
		}
		powers.set(firstIndex, second);
		powers.set(secondIndex, first);
		reordered.clear();
		reordered.addAll(powers);
		for (String power : new ArrayList<>(NyctoData.getPowers(player))) {
			NyctoData.removePower(player, power);
		}
		for (String power : reordered) {
			NyctoData.addPower(player, power);
		}
		refreshPowerLists();
	}

	private void refreshPowerLists() {
		playerPowers.clear();
		playerPowers.addAll(NyctoData.getPowers(player).stream()
				.filter(NyctoPowerRegistry::isRegistered)
				.filter(ALTAR_CHOOSABLE_POWERS::contains)
				.filter(power -> !NyctoData.isRejectedWeakness(power))
				.toList());
		selectablePowers.clear();
		Set<String> current = NyctoData.getPowers(player);
		for (String power : ALTAR_CHOOSABLE_POWERS) {
			if (NyctoPowerRegistry.isRegistered(power) && !current.contains(power) && !NyctoData.isRejectedWeakness(power)) {
				selectablePowers.add(power);
			}
		}
		selectablePowers.sort(Comparator.naturalOrder());
	}

	private ItemStack refreshItemCost() {
		TagKey<Item> tag = switch (getUpgradeTier()) {
			case 1 -> NyctoTags.WEAK_VAMPIRE_ALTAR_UPGRADES;
			case 2 -> NyctoTags.AVERAGE_VAMPIRE_ALTAR_UPGRADES;
			default -> NyctoTags.STRONG_VAMPIRE_ALTAR_UPGRADES;
		};
		return BuiltInRegistries.ITEM.getTag(tag)
				.flatMap(named -> {
					List<Item> items = named.stream().map(Holder::value).toList();
					if (items.isEmpty()) {
						return java.util.Optional.empty();
					}
					return java.util.Optional.of(items.get(Math.floorMod(NyctoData.getUpgradeCostSeed(player), items.size())));
				})
				.map(Item::getDefaultInstance)
				.orElse(ItemStack.EMPTY);
	}

	private int getUpgradeTier() {
		return Math.min(getPlayerPowers() / 2 + 1, 3);
	}

	private static boolean isAlternateMaterial(ItemStack stack) {
		return stack.is(NyctoTags.USABLE_BLOOD_BOTTLES);
	}

	private void addPlayerInventorySlots(Inventory inventory, int left, int top) {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				addSlot(new Slot(inventory, column + row * 9 + 9, left + column * 18, top + row * 18));
			}
		}
		for (int column = 0; column < 9; column++) {
			addSlot(new Slot(inventory, column, left + column * 18, top + 58));
		}
	}
}
