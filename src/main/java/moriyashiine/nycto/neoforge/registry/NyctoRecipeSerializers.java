/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.recipe.BloodExtractionRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NyctoRecipeSerializers {
	private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, NyctoNeoForge.MOD_ID);

	public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BloodExtractionRecipe>> BLOOD_EXTRACTION = RECIPE_SERIALIZERS.register("blood_extraction", () -> new SimpleCraftingRecipeSerializer<>(BloodExtractionRecipe::new));

	private NyctoRecipeSerializers() {
	}

	public static void register(IEventBus bus) {
		RECIPE_SERIALIZERS.register(bus);
	}
}
