/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
	public ModBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		valueLookupBuilder(ModBlockTags.BEAST_MINEABLE)
				.forceAddTag(BlockTags.MINEABLE_WITH_PICKAXE)
				.forceAddTag(BlockTags.MINEABLE_WITH_AXE)
				.forceAddTag(BlockTags.MINEABLE_WITH_SHOVEL)
				.forceAddTag(BlockTags.MINEABLE_WITH_HOE);
		valueLookupBuilder(ModBlockTags.COFFINS)
				.add(ModBlocks.OAK_COFFIN)
				.add(ModBlocks.SPRUCE_COFFIN)
				.add(ModBlocks.BIRCH_COFFIN)
				.add(ModBlocks.JUNGLE_COFFIN)
				.add(ModBlocks.ACACIA_COFFIN)
				.add(ModBlocks.DARK_OAK_COFFIN)
				.add(ModBlocks.PALE_OAK_COFFIN)
				.add(ModBlocks.MANGROVE_COFFIN)
				.add(ModBlocks.CHERRY_COFFIN)
				.add(ModBlocks.BAMBOO_COFFIN)
				.add(ModBlocks.CRIMSON_COFFIN)
				.add(ModBlocks.WARPED_COFFIN);
		valueLookupBuilder(ModBlockTags.HURTS_VAMPIRES)
				.add(ModBlocks.GARLIC_WREATH)
				.add(ModBlocks.WILD_GARLIC)
				.add(ModBlocks.GARLIC);
		valueLookupBuilder(ModBlockTags.MIST_FORM_UNPASSABLE)
				.forceAddTag(BlockTags.CLIMBABLE)
				.forceAddTag(BlockTags.DOORS)
				.forceAddTag(BlockTags.SLABS)
				.forceAddTag(BlockTags.SNOW)
				.forceAddTag(BlockTags.STAIRS)
				.forceAddTag(BlockTags.SUPPORT_OVERRIDE_SNOW_LAYER)
				.forceAddTag(BlockTags.SUPPORTS_DRY_VEGETATION)
				.forceAddTag(BlockTags.TRAPDOORS)
				.forceAddTag(BlockTags.WITHER_IMMUNE)
				.add(Blocks.CALIBRATED_SCULK_SENSOR)
				.add(Blocks.DIRT_PATH)
				.add(Blocks.SCULK_SENSOR)
				.add(Blocks.SCULK_SHRIEKER);

		valueLookupBuilder(BlockTags.MINEABLE_WITH_AXE)
				.addTag(ModBlockTags.COFFINS)
				.add(ModBlocks.VAMPIRE_ALTAR)
				.add(ModBlocks.GARLIC)
				.add(ModBlocks.ACONITE);
		valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
				.add(ModBlocks.WEREWOLF_ALTAR);
		valueLookupBuilder(BlockTags.BEDS)
				.addTag(ModBlockTags.COFFINS);
		valueLookupBuilder(BlockTags.CROPS)
				.add(ModBlocks.GARLIC)
				.add(ModBlocks.ACONITE);
		valueLookupBuilder(BlockTags.MAINTAINS_FARMLAND)
				.add(ModBlocks.GARLIC)
				.add(ModBlocks.ACONITE);
		valueLookupBuilder(BlockTags.FIRE)
				.add(ModBlocks.FIREBOMB);
	}
}
