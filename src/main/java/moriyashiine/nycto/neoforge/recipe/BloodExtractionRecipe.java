/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.recipe;

import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.item.VampiricDaggerItem;
import moriyashiine.nycto.neoforge.registry.NyctoRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class BloodExtractionRecipe extends CustomRecipe {
	public BloodExtractionRecipe(CraftingBookCategory category) {
		super(category);
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		boolean foundFullDagger = false;
		boolean foundBottle = false;
		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getItem(i);
			if (stack.isEmpty()) {
				continue;
			}
			if (stack.is(NyctoItems.VAMPIRIC_DAGGER.get()) && VampiricDaggerItem.isFull(stack) && !foundFullDagger) {
				foundFullDagger = true;
			} else if (stack.is(Items.GLASS_BOTTLE) && !foundBottle) {
				foundBottle = true;
			} else {
				return false;
			}
		}
		return foundFullDagger && foundBottle;
	}

	@Override
	public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getItem(i);
			if (stack.is(NyctoItems.VAMPIRIC_DAGGER.get())) {
				return new ItemStack(VampiricDaggerItem.hasVampireBlood(stack) ? NyctoItems.VAMPIRE_BLOOD_BOTTLE.get() : NyctoItems.BLOOD_BOTTLE.get());
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
		NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getItem(i);
			if (stack.is(NyctoItems.VAMPIRIC_DAGGER.get())) {
				ItemStack dagger = stack.copy();
				VampiricDaggerItem.resetBlood(dagger);
				remaining.set(i, dagger);
			}
		}
		return remaining;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return NyctoRecipeSerializers.BLOOD_EXTRACTION.get();
	}
}
