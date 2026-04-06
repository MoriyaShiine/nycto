/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModBannerPatterns;
import moriyashiine.nycto.common.tag.ModBannerPatternTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.concurrent.CompletableFuture;

public class ModBannerPatternTagsProvider extends FabricTagsProvider<BannerPattern> {
	public ModBannerPatternTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.BANNER_PATTERN, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(ModBannerPatternTags.PATTERN_ITEM_VAMPIRE_BAT)
				.add(ModBannerPatterns.VAMPIRE_BAT);
		builder(ModBannerPatternTags.PATTERN_ITEM_WOLF_SKULL)
				.add(ModBannerPatterns.WOLF_SKULL);
		builder(ModBannerPatternTags.PATTERN_ITEM_HUNTERS_MARK)
				.add(ModBannerPatterns.HUNTERS_MARK);
	}
}
