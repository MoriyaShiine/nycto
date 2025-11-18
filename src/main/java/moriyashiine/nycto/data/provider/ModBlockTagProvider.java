/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		valueLookupBuilder(ModBlockTags.BEAST_MINEABLE)
				.forceAddTag(BlockTags.PICKAXE_MINEABLE)
				.forceAddTag(BlockTags.AXE_MINEABLE)
				.forceAddTag(BlockTags.SHOVEL_MINEABLE)
				.forceAddTag(BlockTags.HOE_MINEABLE);
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
				.forceAddTag(BlockTags.DRY_VEGETATION_MAY_PLACE_ON)
				.forceAddTag(BlockTags.SLABS)
				.forceAddTag(BlockTags.SNOW)
				.forceAddTag(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)
				.forceAddTag(BlockTags.STAIRS)
				.forceAddTag(BlockTags.TRAPDOORS)
				.forceAddTag(BlockTags.WITHER_IMMUNE)
				.add(Blocks.CALIBRATED_SCULK_SENSOR)
				.add(Blocks.DIRT_PATH)
				.add(Blocks.SCULK_SENSOR)
				.add(Blocks.SCULK_SHRIEKER);

		valueLookupBuilder(BlockTags.AXE_MINEABLE)
				.addTag(ModBlockTags.COFFINS)
				.add(ModBlocks.VAMPIRE_ALTAR)
				.add(ModBlocks.GARLIC)
				.add(ModBlocks.ACONITE);
		valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
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
