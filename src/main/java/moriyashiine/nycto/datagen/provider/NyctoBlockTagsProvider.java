package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.init.NyctoBlocks;
import moriyashiine.nycto.common.tag.NyctoBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class NyctoBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
	public NyctoBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		valueLookupBuilder(NyctoBlockTags.BEAST_MINEABLE)
				.forceAddTag(BlockTags.MINEABLE_WITH_PICKAXE)
				.forceAddTag(BlockTags.MINEABLE_WITH_AXE)
				.forceAddTag(BlockTags.MINEABLE_WITH_SHOVEL)
				.forceAddTag(BlockTags.MINEABLE_WITH_HOE);
		valueLookupBuilder(NyctoBlockTags.COFFINS)
				.add(NyctoBlocks.OAK_COFFIN)
				.add(NyctoBlocks.SPRUCE_COFFIN)
				.add(NyctoBlocks.BIRCH_COFFIN)
				.add(NyctoBlocks.JUNGLE_COFFIN)
				.add(NyctoBlocks.ACACIA_COFFIN)
				.add(NyctoBlocks.DARK_OAK_COFFIN)
				.add(NyctoBlocks.PALE_OAK_COFFIN)
				.add(NyctoBlocks.MANGROVE_COFFIN)
				.add(NyctoBlocks.CHERRY_COFFIN)
				.add(NyctoBlocks.BAMBOO_COFFIN)
				.add(NyctoBlocks.CRIMSON_COFFIN)
				.add(NyctoBlocks.WARPED_COFFIN);
		valueLookupBuilder(NyctoBlockTags.HURTS_VAMPIRES)
				.add(NyctoBlocks.GARLIC_WREATH)
				.add(NyctoBlocks.WILD_GARLIC)
				.add(NyctoBlocks.GARLIC);
		valueLookupBuilder(NyctoBlockTags.MIST_FORM_UNPASSABLE)
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
				.addTag(NyctoBlockTags.COFFINS)
				.add(NyctoBlocks.VAMPIRE_ALTAR)
				.add(NyctoBlocks.GARLIC)
				.add(NyctoBlocks.ACONITE)
				.add(NyctoBlocks.WOODEN_STAKE);
		valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
				.add(NyctoBlocks.WEREWOLF_ALTAR)
				.add(NyctoBlocks.BLOOD_FOUNTAIN);
		valueLookupBuilder(BlockTags.BEDS)
				.addTag(NyctoBlockTags.COFFINS);
		valueLookupBuilder(BlockTags.CROPS)
				.add(NyctoBlocks.GARLIC)
				.add(NyctoBlocks.ACONITE);
		valueLookupBuilder(BlockTags.MAINTAINS_FARMLAND)
				.add(NyctoBlocks.GARLIC)
				.add(NyctoBlocks.ACONITE);
		valueLookupBuilder(BlockTags.FIRE)
				.add(NyctoBlocks.FIREBOMB);
		valueLookupBuilder(BlockTags.HAPPY_GHAST_AVOIDS)
				.add(NyctoBlocks.WOODEN_STAKE);
	}
}
