package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.tag.NyctoBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;

public class NyctoWorldGeneration {
	public static final ResourceKey<Feature> WILD_GARLIC = ResourceKey.create(Registries.FEATURE, Nycto.id("wild_garlic"));
	public static final ResourceKey<PlacedFeature> PATCH_WILD_GARLIC = ResourceKey.create(Registries.PLACED_FEATURE, Nycto.id("patch_wild_garlic"));

	public static final ResourceKey<Feature> WILD_ACONITE = ResourceKey.create(Registries.FEATURE, Nycto.id("wild_aconite"));
	public static final ResourceKey<PlacedFeature> PATCH_WILD_ACONITE = ResourceKey.create(Registries.PLACED_FEATURE, Nycto.id("patch_wild_aconite"));

	public static void init() {
		BiomeModifications.addFeature(BiomeSelectors.tag(NyctoBiomeTags.GENERATES_GARLIC).and(ctx -> ctx.getBiomeKey() != Biomes.PALE_GARDEN), GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_WILD_GARLIC);
		BiomeModifications.addFeature(BiomeSelectors.tag(NyctoBiomeTags.GENERATES_ACONITE).and(ctx -> ctx.getBiomeKey() != Biomes.PALE_GARDEN), GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_WILD_ACONITE);
	}

	public static void bootstrapFeature(BootstrapContext<Feature> registry) {
		registry.register(WILD_GARLIC, new SimpleBlockFeature(BlockStateProvider.of(NyctoBlocks.WILD_GARLIC)));
		registry.register(WILD_ACONITE, new SimpleBlockFeature(BlockStateProvider.of(NyctoBlocks.WILD_ACONITE)));
	}

	public static void bootstrapPlacedFeature(BootstrapContext<PlacedFeature> registry) {
		HolderGetter<Feature> configuredFeatures = registry.lookup(Registries.FEATURE);
		PlacementUtils.register(registry, PATCH_WILD_GARLIC,
				configuredFeatures.getOrThrow(WILD_GARLIC),
				RarityFilter.onAverageOnceEvery(64),
				InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome(),
				CountPlacement.of(64),
				OffsetPlacement.ofTriangle(2, 3),
				BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK))
				));
		PlacementUtils.register(registry, PATCH_WILD_ACONITE,
				configuredFeatures.getOrThrow(WILD_ACONITE),
				RarityFilter.onAverageOnceEvery(64),
				InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome(),
				CountPlacement.of(64),
				OffsetPlacement.ofTriangle(2, 3),
				BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK))
				));
	}
}
