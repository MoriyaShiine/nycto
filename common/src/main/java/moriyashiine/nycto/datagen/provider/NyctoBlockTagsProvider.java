package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.references.NyctoBlockItemIds;
import moriyashiine.nycto.common.tag.NyctoBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class NyctoBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
	public NyctoBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(NyctoBlockTags.BEAST_MINEABLE)
				.forceAddTag(BlockTags.MINEABLE_WITH_PICKAXE)
				.forceAddTag(BlockTags.MINEABLE_WITH_AXE)
				.forceAddTag(BlockTags.MINEABLE_WITH_SHOVEL)
				.forceAddTag(BlockTags.MINEABLE_WITH_HOE);
		builder(NyctoBlockTags.COFFINS)
				.add(NyctoBlockItemIds.OAK_COFFIN)
				.add(NyctoBlockItemIds.SPRUCE_COFFIN)
				.add(NyctoBlockItemIds.BIRCH_COFFIN)
				.add(NyctoBlockItemIds.JUNGLE_COFFIN)
				.add(NyctoBlockItemIds.ACACIA_COFFIN)
				.add(NyctoBlockItemIds.DARK_OAK_COFFIN)
				.add(NyctoBlockItemIds.PALE_OAK_COFFIN)
				.add(NyctoBlockItemIds.MANGROVE_COFFIN)
				.add(NyctoBlockItemIds.CHERRY_COFFIN)
				.add(NyctoBlockItemIds.BAMBOO_COFFIN)
				.add(NyctoBlockItemIds.CRIMSON_COFFIN)
				.add(NyctoBlockItemIds.WARPED_COFFIN);
		builder(NyctoBlockTags.HURTS_VAMPIRES)
				.add(NyctoBlockItemIds.GARLIC_WREATH)
				.add(NyctoBlockItemIds.WILD_GARLIC)
				.add(NyctoBlockItemIds.GARLIC);
		builder(NyctoBlockTags.MIST_FORM_UNPASSABLE)
				.add(BlockItemIds.CALIBRATED_SCULK_SENSOR)
				.add(BlockItemIds.DIRT_PATH)
				.add(BlockItemIds.SCULK_SENSOR)
				.add(BlockItemIds.SCULK_SHRIEKER)
				.forceAddTag(BlockTags.CLIMBABLE)
				.forceAddTag(BlockTags.DOORS)
				.forceAddTag(BlockTags.SLABS)
				.forceAddTag(BlockTags.SNOW)
				.forceAddTag(BlockTags.STAIRS)
				.forceAddTag(BlockTags.SUPPORT_OVERRIDE_SNOW_LAYER)
				.forceAddTag(BlockTags.SUPPORTS_DRY_VEGETATION)
				.forceAddTag(BlockTags.TRAPDOORS)
				.forceAddTag(BlockTags.WITHER_IMMUNE);

		builder(BlockTags.MINEABLE_WITH_AXE)
				.addTag(NyctoBlockTags.COFFINS)
				.add(NyctoBlockItemIds.VAMPIRE_ALTAR)
				.add(NyctoBlockItemIds.GARLIC)
				.add(NyctoBlockItemIds.ACONITE)
				.add(NyctoBlockItemIds.WOODEN_STAKE);
		builder(BlockTags.MINEABLE_WITH_PICKAXE)
				.add(NyctoBlockItemIds.WEREWOLF_ALTAR)
				.add(NyctoBlockItemIds.BLOOD_FOUNTAIN);
		builder(BlockTags.SHEARS_MAJOR_BREAKING_SPEED)
				.add(NyctoBlockItemIds.GARLIC_WREATH)
				.add(NyctoBlockItemIds.ACONITE_GARLAND);
		builder(BlockTags.BEDS)
				.addTag(NyctoBlockTags.COFFINS);
		builder(BlockTags.CROPS)
				.add(NyctoBlockItemIds.GARLIC)
				.add(NyctoBlockItemIds.ACONITE);
		builder(BlockTags.MAINTAINS_FARMLAND)
				.add(NyctoBlockItemIds.GARLIC)
				.add(NyctoBlockItemIds.ACONITE);
		builder(BlockTags.FIRE)
				.add(NyctoBlockItemIds.FIREBOMB);
		builder(BlockTags.HAPPY_GHAST_AVOIDS)
				.add(NyctoBlockItemIds.WOODEN_STAKE);
	}
}
