/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.screenhandler;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.api.power.PowerInstance;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AltarScreenHandler extends ScreenHandler {
	public final List<Power> selectablePowers = new ArrayList<>(), playerPowers = new ArrayList<>();

	private final Inventory inventory = new SimpleInventory(2) {
		@Override
		public void markDirty() {
			super.markDirty();
			onContentChanged(this);
		}
	};
	protected final ScreenHandlerContext context;

	public ItemStack itemCost;

	public AltarScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, TagKey<Power> allowedPowers) {
		super(type, syncId);
		this.context = context;
		addSlot(new Slot(inventory, 0, 122, 94) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return ItemStack.areItemsEqual(stack, itemCost);
			}
		});
		addSlot(new Slot(inventory, 1, 142, 94) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return isStackValidForSecondSlot(stack);
			}
		});
		addPlayerSlots(playerInventory, 8, 141);
		for (Power power : NyctoRegistries.POWER) {
			if (power.isIn(allowedPowers) && !NyctoAPI.hasPower(playerInventory.player, power)) {
				selectablePowers.add(power);
			}
		}
		for (PowerInstance instance : NyctoAPI.getPowers(playerInventory.player)) {
			if (instance.getPower().isIn(allowedPowers)) {
				playerPowers.add(instance.getPower());
			}
		}
		sort(selectablePowers);
		sort(playerPowers);

		itemCost = refreshItemCost(playerInventory.player);
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slotIndex) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = slots.get(slotIndex);
		if (slot.hasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			if (slotIndex == 0 || slotIndex == 1) {
				if (!insertItem(stackInSlot, 2, 38, false)) {
					return ItemStack.EMPTY;
				}
			} else if (getSlot(0).canInsert(stackInSlot)) {
				if (!insertItem(stackInSlot, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (getSlot(1).canInsert(stackInSlot)) {
				if (!insertItem(stackInSlot, 1, 2, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!slots.getFirst().hasStack() && slots.getFirst().canInsert(stackInSlot)) {
				slots.getFirst().setStack(stackInSlot.split(1));
			} else {
				return ItemStack.EMPTY;
			}
			if (stackInSlot.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
			if (stackInSlot.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTakeItem(player, stackInSlot);
		}
		return stack;
	}

	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		context.run((world, pos) -> dropInventory(player, inventory));
	}

	@Override
	public boolean onButtonClick(PlayerEntity player, int id) {
		if (canUpgrade(player)) {
			apply(player, id);
			context.run((world, pos) -> {
				if (!player.isCreative()) {
					player.addExperienceLevels(-getExpCost());
					inventory.getStack(0).decrement(getMaterialCost());
					inventory.getStack(1).decrement(getMaterialCost());
				}
				dropInventory(player, inventory);
				inventory.markDirty();
				onContentChanged(inventory);
				world.playSound(null, pos, ModSoundEvents.BLOCK_ALTAR_USE, SoundCategory.BLOCKS, 1, world.random.nextFloat() * 0.1F + 0.9F);
			});
			return true;
		}
		return false;
	}

	public abstract int getMaterialCost();

	public abstract int getExpCost();

	protected abstract ItemStack refreshItemCost(PlayerEntity player);

	protected abstract boolean isStackValidForSecondSlot(ItemStack stack);

	public boolean canUpgrade(PlayerEntity player) {
		if (player.isCreative()) {
			return true;
		}
		return player.experienceLevel >= getExpCost() && inventory.getStack(0).getCount() >= getMaterialCost() && inventory.getStack(1).getCount() >= getMaterialCost();
	}

	public int getPositivePowers() {
		return playerPowers.stream().filter(power -> !power.isNegative()).collect(Collectors.toSet()).size();
	}

	public void sort(List<Power> powers) {
		if (powers == selectablePowers) {
			powers.sort(Comparator.comparing(p -> p.getEntry().getKey().orElseThrow().getValue()));
		}
		powers.sort(Comparator.comparing(Power::isNegative));
	}

	public static void apply(PlayerEntity player, int rawId) {
		if (player.currentScreenHandler instanceof AltarScreenHandler handler) {
			for (int i = handler.selectablePowers.size() - 1; i >= 0; i--) {
				Power power = handler.selectablePowers.get(i);
				if (NyctoRegistries.POWER.getRawId(power) == rawId) {
					handler.selectablePowers.remove(i);
					handler.playerPowers.add(power);
					handler.sort(handler.playerPowers);
					ModEntityComponents.TRANSFORMATION.get(player).updateUpgradeCostSeed();
					handler.itemCost = handler.refreshItemCost(player);
					if (player instanceof ServerPlayerEntity serverPlayer) {
						NyctoAPI.addPower(serverPlayer, power);
					}
					return;
				}
			}
		}
	}
}
