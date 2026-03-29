/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.world.item.crafting.BloodExtractionRecipe;
import moriyashiine.nycto.common.world.item.crafting.FoodPoisoningRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerRecipeSerializer;

public class ModRecipeSerializers {
	public static final RecipeSerializer<BloodExtractionRecipe> BLOOD_EXTRACTION = registerRecipeSerializer("blood_extraction", new RecipeSerializer<>(BloodExtractionRecipe.MAP_CODEC, BloodExtractionRecipe.STREAM_CODEC));
	public static final RecipeSerializer<FoodPoisoningRecipe> FOOD_POISONING = registerRecipeSerializer("food_poisoning", new RecipeSerializer<>(FoodPoisoningRecipe.MAP_CODEC, FoodPoisoningRecipe.STREAM_CODEC));

	public static void init() {
	}
}
