/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.screenhandler;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.screenhandler.AltarScreenHandler;
import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModScreenHandlerTypes;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.nycto.common.tag.ModPowerTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenHandlerContext;

import java.util.List;

public class VampireAltarScreenHandler extends AltarScreenHandler {
	public VampireAltarScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

	public VampireAltarScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(ModScreenHandlerTypes.VAMPIRE_ALTAR, syncId, playerInventory, context, ModPowerTags.VAMPIRE_CHOOSABLE);
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return canUse(context, player, ModBlocks.VAMPIRE_ALTAR) && NyctoAPI.isVampire(player);
	}

	@Override
	public int getMaterialCost() {
		return (getPositivePowers() / 2 + 1);
	}

	@Override
	public int getExpCost() {
		return (getPositivePowers() / 2 + 1) * 5;
	}

	@Override
	protected ItemStack refreshItemCost(PlayerEntity player) {
		TagKey<Item> tag = switch (getPositivePowers()) {
			case 0, 1 -> ModItemTags.WEAK_VAMPIRE_ALTAR_UPGRADES;
			case 2, 3 -> ModItemTags.AVERAGE_VAMPIRE_ALTAR_UPGRADES;
			default -> ModItemTags.STRONG_VAMPIRE_ALTAR_UPGRADES;
		};
		List<RegistryEntry<Item>> ingredient = Ingredient.ofTag(Registries.ITEM.getOrThrow(tag)).getMatchingItems().toList();
		if (ingredient.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			return ingredient.get(ModEntityComponents.TRANSFORMATION.get(player).getRandomUpgradeCostIndex(ingredient)).value().getDefaultStack();
		}
	}

	@Override
	protected boolean isStackValidForSecondSlot(ItemStack stack) {
		return stack.isIn(ModItemTags.USABLE_BLOOD_BOTTLES);
	}
}
