/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.recipe;

import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.init.ModRecipeSerializers;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.stream.Collectors;

public class FoodPoisoningRecipe extends SpecialCraftingRecipe {
	public FoodPoisoningRecipe(CraftingRecipeCategory category) {
		super(category);
	}

	@Override
	public boolean matches(CraftingRecipeInput input, World world) {
		boolean foundFood = false, foundAconite = false;
		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getStackInSlot(i);
			if (stack.contains(DataComponentTypes.FOOD) && !stack.getOrDefault(ModComponentTypes.POISONED, false)) {
				foundFood = true;
			}
			if (stack.isOf(ModItems.ACONITE)) {
				foundAconite = true;
			}
		}
		return foundFood && foundAconite && input.getStacks().stream().filter(stack -> !stack.isEmpty()).collect(Collectors.toSet()).size() == 2;
	}

	@Override
	public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
		for (ItemStack stack : input.getStacks()) {
			if (stack.contains(DataComponentTypes.FOOD)) {
				ItemStack copy = stack.copyWithCount(1);
				copy.set(ModComponentTypes.POISONED, true);
				return copy;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public RecipeSerializer<FoodPoisoningRecipe> getSerializer() {
		return ModRecipeSerializers.FOOD_POISONING;
	}
}
