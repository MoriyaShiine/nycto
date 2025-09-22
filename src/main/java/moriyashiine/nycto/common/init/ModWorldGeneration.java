/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.tag.ModBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ModWorldGeneration {
	public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_GARLIC_CONFIGURED = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Nycto.id("patch_wild_garlic"));
	public static final RegistryKey<PlacedFeature> PATCH_WILD_GARLIC = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Nycto.id("patch_wild_garlic"));

	public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_ACONITE_CONFIGURED = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Nycto.id("patch_wild_aconite"));
	public static final RegistryKey<PlacedFeature> PATCH_WILD_ACONITE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Nycto.id("patch_wild_aconite"));

	public static void init() {
		BiomeModifications.addFeature(BiomeSelectors.tag(ModBiomeTags.GENERATES_GARLIC).and(ctx -> ctx.getBiomeKey() != BiomeKeys.PALE_GARDEN), GenerationStep.Feature.VEGETAL_DECORATION, PATCH_WILD_GARLIC);
		BiomeModifications.addFeature(BiomeSelectors.tag(ModBiomeTags.GENERATES_ACONITE).and(ctx -> ctx.getBiomeKey() != BiomeKeys.PALE_GARDEN), GenerationStep.Feature.VEGETAL_DECORATION, PATCH_WILD_ACONITE);
	}

	public static void bootstrapConfigured(Registerable<ConfiguredFeature<?, ?>> registerable) {
		ConfiguredFeatures.register(registerable, PATCH_WILD_GARLIC_CONFIGURED,
				Feature.RANDOM_PATCH,
				new RandomPatchFeatureConfig(64, 2, 3,
						PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
								new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.WILD_GARLIC)),
								ConfiguredFeatures.createBlockPredicate(List.of(Blocks.GRASS_BLOCK)))));

		ConfiguredFeatures.register(registerable, PATCH_WILD_ACONITE_CONFIGURED,
				Feature.RANDOM_PATCH,
				new RandomPatchFeatureConfig(64, 2, 3,
						PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
								new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.WILD_ACONITE)),
								ConfiguredFeatures.createBlockPredicate(List.of(Blocks.GRASS_BLOCK)))));
	}

	public static void bootstrapPlaced(Registerable<PlacedFeature> registerable) {
		RegistryEntryLookup<ConfiguredFeature<?, ?>> featureLookup = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
		PlacedFeatures.register(registerable, PATCH_WILD_GARLIC,
				featureLookup.getOrThrow(PATCH_WILD_GARLIC_CONFIGURED),
				RarityFilterPlacementModifier.of(64),
				SquarePlacementModifier.of(),
				PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
				BiomePlacementModifier.of());
		PlacedFeatures.register(registerable, PATCH_WILD_ACONITE,
				featureLookup.getOrThrow(PATCH_WILD_ACONITE_CONFIGURED),
				RarityFilterPlacementModifier.of(64),
				SquarePlacementModifier.of(),
				PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
				BiomePlacementModifier.of());
	}
}
