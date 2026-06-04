/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.world.item.crafting.BloodExtractionRecipe;
import moriyashiine.nycto.common.world.item.crafting.FoodPoisoningRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializers {
	public static final RecipeSerializer<BloodExtractionRecipe> BLOOD_EXTRACTION = registerRecipeSerializer("blood_extraction", new RecipeSerializer<>(BloodExtractionRecipe.MAP_CODEC, BloodExtractionRecipe.STREAM_CODEC));
	public static final RecipeSerializer<FoodPoisoningRecipe> FOOD_POISONING = registerRecipeSerializer("food_poisoning", new RecipeSerializer<>(FoodPoisoningRecipe.MAP_CODEC, FoodPoisoningRecipe.STREAM_CODEC));

	private static <T extends RecipeSerializer<?>> T registerRecipeSerializer(String name, T serializer) {
		ModRegistration.register(ModRegistration.RECIPE_SERIALIZERS, name, serializer);
		return serializer;
	}

	public static void init() {
	}
}
