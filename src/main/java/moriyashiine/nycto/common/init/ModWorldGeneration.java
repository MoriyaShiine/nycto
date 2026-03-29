/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.tag.ModBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;

public class ModWorldGeneration {
	public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_GARLIC = ResourceKey.create(Registries.CONFIGURED_FEATURE, Nycto.id("wild_garlic"));
	public static final ResourceKey<PlacedFeature> PATCH_WILD_GARLIC = ResourceKey.create(Registries.PLACED_FEATURE, Nycto.id("patch_wild_garlic"));

	public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_ACONITE = ResourceKey.create(Registries.CONFIGURED_FEATURE, Nycto.id("wild_aconite"));
	public static final ResourceKey<PlacedFeature> PATCH_WILD_ACONITE = ResourceKey.create(Registries.PLACED_FEATURE, Nycto.id("patch_wild_aconite"));

	public static void init() {
		BiomeModifications.addFeature(BiomeSelectors.tag(ModBiomeTags.GENERATES_GARLIC).and(ctx -> ctx.getBiomeKey() != Biomes.PALE_GARDEN), GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_WILD_GARLIC);
		BiomeModifications.addFeature(BiomeSelectors.tag(ModBiomeTags.GENERATES_ACONITE).and(ctx -> ctx.getBiomeKey() != Biomes.PALE_GARDEN), GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_WILD_ACONITE);
	}

	public static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> registry) {
		FeatureUtils.register(registry, WILD_GARLIC, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.WILD_GARLIC)));
		FeatureUtils.register(registry, WILD_ACONITE, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.WILD_ACONITE)));
	}

	public static void bootstrapPlaced(BootstrapContext<PlacedFeature> registry) {
		HolderGetter<ConfiguredFeature<?, ?>> registries = registry.lookup(Registries.CONFIGURED_FEATURE);
		PlacementUtils.register(registry, PATCH_WILD_GARLIC,
				registries.getOrThrow(WILD_GARLIC),
				RarityFilter.onAverageOnceEvery(64),
				InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome(),
				CountPlacement.of(64),
				RandomOffsetPlacement.ofTriangle(2, 3),
				BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK))
				));
		PlacementUtils.register(registry, PATCH_WILD_ACONITE,
				registries.getOrThrow(WILD_ACONITE),
				RarityFilter.onAverageOnceEvery(64),
				InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome(),
				CountPlacement.of(64),
				RandomOffsetPlacement.ofTriangle(2, 3),
				BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK))
				));
	}
}
