/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.inventory;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.inventory.AltarMenu;
import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModMenuTypes;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.nycto.common.tag.ModPowerTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class VampireAltarMenu extends AltarMenu {
	public VampireAltarMenu(int syncId, Inventory playerInventory) {
		this(syncId, playerInventory, ContainerLevelAccess.NULL);
	}

	public VampireAltarMenu(int syncId, Inventory playerInventory, ContainerLevelAccess context) {
		super(ModMenuTypes.VAMPIRE_ALTAR, syncId, playerInventory, context, ModPowerTags.VAMPIRE_CHOOSABLE);
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(access, player, ModBlocks.VAMPIRE_ALTAR) && NyctoAPI.isVampire(player);
	}

	@Override
	public int getMaterialCost() {
		return (getPlayerPowers() / 2 + 1);
	}

	@Override
	public int getExpCost() {
		return (getPlayerPowers() / 2 + 1) * 5;
	}

	@Override
	protected ItemStack refreshItemCost(Player player) {
		TagKey<Item> tag = switch (getPlayerPowers()) {
			case 0, 1 -> ModItemTags.WEAK_VAMPIRE_ALTAR_UPGRADES;
			case 2, 3 -> ModItemTags.AVERAGE_VAMPIRE_ALTAR_UPGRADES;
			default -> ModItemTags.STRONG_VAMPIRE_ALTAR_UPGRADES;
		};
		List<Holder<Item>> ingredient = Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(tag)).items().toList();
		if (ingredient.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			return ingredient.get(ModEntityComponents.TRANSFORMATION.get(player).getRandomUpgradeCostIndex(ingredient)).value().getDefaultInstance();
		}
	}

	@Override
	protected boolean isStackValidForSecondSlot(ItemStack stack) {
		return stack.is(ModItemTags.USABLE_BLOOD_BOTTLES);
	}
}
