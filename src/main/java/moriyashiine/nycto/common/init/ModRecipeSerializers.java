/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.recipe.BloodExtractionRecipe;
import moriyashiine.nycto.common.recipe.FoodPoisoningRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerRecipeSerializer;

public class ModRecipeSerializers {
	public static final RecipeSerializer<BloodExtractionRecipe> BLOOD_EXTRACTION = registerRecipeSerializer("blood_extraction", new SpecialCraftingRecipe.SpecialRecipeSerializer<>(BloodExtractionRecipe::new));
	public static final RecipeSerializer<FoodPoisoningRecipe> FOOD_POISONING = registerRecipeSerializer("food_poisoning", new SpecialCraftingRecipe.SpecialRecipeSerializer<>(FoodPoisoningRecipe::new));

	public static void init() {
	}
}
