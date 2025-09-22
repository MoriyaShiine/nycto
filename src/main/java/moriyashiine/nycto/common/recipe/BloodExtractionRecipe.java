/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.recipe;

import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.init.ModRecipeSerializers;
import moriyashiine.nycto.common.item.VampiricDaggerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.stream.Collectors;

public class BloodExtractionRecipe extends SpecialCraftingRecipe {
	public BloodExtractionRecipe(CraftingRecipeCategory category) {
		super(category);
	}

	@Override
	public boolean matches(CraftingRecipeInput input, World world) {
		boolean foundDagger = false, foundBottle = false;
		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getStackInSlot(i);
			if (stack.isOf(ModItems.VAMPIRIC_DAGGER) && VampiricDaggerItem.isFull(stack)) {
				foundDagger = true;
			}
			if (stack.isOf(Items.GLASS_BOTTLE)) {
				foundBottle = true;
			}
		}
		return foundDagger && foundBottle && input.getStacks().stream().filter(stack -> !stack.isEmpty()).collect(Collectors.toSet()).size() == 2;
	}

	@Override
	public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
		for (ItemStack stack : input.getStacks()) {
			if (stack.isOf(ModItems.VAMPIRIC_DAGGER)) {
				return getCraftingResult(stack);
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input) {
		DefaultedList<ItemStack> defaultedList = super.getRecipeRemainders(input);
		for (int i = 0; i < defaultedList.size(); i++) {
			ItemStack stack = input.getStackInSlot(i);
			if (stack.isOf(ModItems.VAMPIRIC_DAGGER)) {
				VampiricDaggerItem.setBloodTypes(stack, false, false);
				VampiricDaggerItem.setBloodCharge(stack, 0);
				defaultedList.set(i, stack.copy());
			}
		}
		return defaultedList;
	}

	@Override
	public RecipeSerializer<BloodExtractionRecipe> getSerializer() {
		return ModRecipeSerializers.BLOOD_EXTRACTION;
	}

	public static ItemStack getCraftingResult(ItemStack stack) {
		Item result = ModItems.BLOOD_BOTTLE;
		boolean vampire = stack.getOrDefault(ModComponentTypes.VAMPIRE_BLOOD, false);
		if (stack.getOrDefault(ModComponentTypes.PLAYER_BLOOD, false)) {
			if (vampire) {
				result = ModItems.PLAYER_VAMPIRE_BLOOD_BOTTLE;
			} else {
				result = ModItems.PLAYER_BLOOD_BOTTLE;
			}
		} else if (vampire) {
			result = ModItems.VAMPIRE_BLOOD_BOTTLE;
		}
		return result.getDefaultStack();
	}
}
