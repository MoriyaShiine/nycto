/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.inventory;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.api.world.power.PowerInstance;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
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
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AltarMenu extends AbstractContainerMenu {
	public final List<Power> selectablePowers = new ArrayList<>(), playerPowers = new ArrayList<>();

	private final Container altarSlots = new SimpleContainer(2) {
		@Override
		public void setChanged() {
			super.setChanged();
			slotsChanged(this);
		}
	};
	protected final ContainerLevelAccess access;

	public ItemStack itemCost;

	public AltarMenu(MenuType<?> type, int containerId, Inventory inventory, ContainerLevelAccess access, TagKey<Power> allowedPowers) {
		super(type, containerId);
		this.access = access;
		addSlot(new Slot(altarSlots, 0, 122, 94) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return ItemStack.isSameItem(stack, itemCost);
			}
		});
		addSlot(new Slot(altarSlots, 1, 142, 94) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return isAlternateMaterial(stack);
			}
		});
		addStandardInventorySlots(inventory, 8, 141);
		for (Power power : NyctoRegistries.POWER) {
			if (power.is(allowedPowers) && !NyctoAPI.hasPower(inventory.player, power)) {
				selectablePowers.add(power);
			}
		}
		for (PowerInstance instance : NyctoAPI.getPowers(inventory.player)) {
			if (instance.is(allowedPowers)) {
				playerPowers.add(instance.getPower());
			}
		}
		sort(selectablePowers);
		sort(playerPowers);

		itemCost = refreshItemCost(inventory.player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		ItemStack clicked = ItemStack.EMPTY;
		Slot slot = slots.get(slotIndex);
		if (slot.hasItem()) {
			ItemStack slotStack = slot.getItem();
			clicked = slotStack.copy();
			if (slotIndex == 0 || slotIndex == 1) {
				if (!moveItemStackTo(slotStack, 2, 38, false)) {
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
			} else if (!slots.getFirst().hasItem() && slots.getFirst().mayPlace(slotStack)) {
				slots.getFirst().setByPlayer(slotStack.split(1));
			} else {
				return ItemStack.EMPTY;
			}
			if (slotStack.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (slotStack.getCount() == clicked.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, slotStack);
		}
		return clicked;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		access.execute((_, _) -> clearContainer(player, altarSlots));
	}

	@Override
	public boolean clickMenuButton(Player player, int buttonId) {
		if (canUpgrade(player)) {
			access.execute((level, pos) -> {
				if (!player.isCreative()) {
					player.giveExperienceLevels(-getExpCost());
					altarSlots.getItem(0).shrink(getPrimaryMaterialCost());
					altarSlots.getItem(1).shrink(getAlternateMaterialCost());
				}
				clearContainer(player, altarSlots);
				altarSlots.setChanged();
				slotsChanged(altarSlots);
				level.playSound(null, pos, ModSoundEvents.BLOCK_ALTAR_USE, SoundSource.BLOCKS, 1, level.getRandom().nextFloat() * 0.1F + 0.9F);
			});
			apply(player, buttonId);
			return true;
		}
		return false;
	}

	public int getPrimaryMaterialCost() {
		return switch (getPlayerPowers() / 2 + 1) {
			case 1 -> 1;
			case 2 -> 3;
			default -> 5;
		};
	}

	public int getAlternateMaterialCost() {
		return getPlayerPowers() / 2 + 1;
	}

	public int getExpCost() {
		return (getPlayerPowers() / 2 + 1) * 5;
	}

	protected ItemStack refreshItemCost(Player player) {
		TagKey<Item> tag = switch (getPlayerPowers() / 2 + 1) {
			case 1 -> getWeakMaterials();
			case 2 -> getAverageMaterials();
			default -> getStrongMaterials();
		};
		List<Holder<Item>> ingredient = Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(tag)).items().toList();
		if (ingredient.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			return ingredient.get(ModEntityComponents.TRANSFORMATION.get(player).getRandomUpgradeCostIndex(ingredient)).value().getDefaultInstance();
		}
	}

	protected abstract TagKey<Item> getWeakMaterials();

	protected abstract TagKey<Item> getAverageMaterials();

	protected abstract TagKey<Item> getStrongMaterials();

	protected abstract boolean isAlternateMaterial(ItemStack stack);

	public boolean canUpgrade(Player player) {
		if (player.isCreative()) {
			return true;
		}
		return player.experienceLevel >= getExpCost() && altarSlots.getItem(0).getCount() >= getPrimaryMaterialCost() && altarSlots.getItem(1).getCount() >= getAlternateMaterialCost();
	}

	public int getPlayerPowers() {
		return playerPowers.stream().filter(power -> !power.isWeakness()).collect(Collectors.toSet()).size();
	}

	public void sort(List<Power> powers) {
		if (powers == selectablePowers) {
			powers.sort(Comparator.comparing(p -> p.getHolder().unwrapKey().orElseThrow().identifier()));
		}
		powers.sort(Comparator.comparing(Power::isWeakness));
	}

	public static void apply(Player player, int id) {
		if (player.containerMenu instanceof AltarMenu menu) {
			for (int i = menu.selectablePowers.size() - 1; i >= 0; i--) {
				Power power = menu.selectablePowers.get(i);
				if (NyctoRegistries.POWER.getId(power) == id) {
					menu.selectablePowers.remove(i);
					menu.playerPowers.add(power);
					menu.sort(menu.playerPowers);
					ModEntityComponents.TRANSFORMATION.get(player).updateUpgradeCostSeed();
					menu.itemCost = menu.refreshItemCost(player);
					if (player instanceof ServerPlayer serverPlayer) {
						NyctoAPI.addPower(serverPlayer, power);
					}
					return;
				}
			}
		}
	}
}
