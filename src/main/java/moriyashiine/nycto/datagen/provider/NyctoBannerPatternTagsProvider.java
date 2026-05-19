/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.init.NyctoBannerPatterns;
import moriyashiine.nycto.common.tag.NyctoBannerPatternTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.concurrent.CompletableFuture;

public class NyctoBannerPatternTagsProvider extends FabricTagsProvider<BannerPattern> {
	public NyctoBannerPatternTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.BANNER_PATTERN, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(NyctoBannerPatternTags.PATTERN_ITEM_VAMPIRE_BAT)
				.add(NyctoBannerPatterns.VAMPIRE_BAT);
		builder(NyctoBannerPatternTags.PATTERN_ITEM_WOLF_SKULL)
				.add(NyctoBannerPatterns.WOLF_SKULL);
		builder(NyctoBannerPatternTags.PATTERN_ITEM_HUNTERS_MARK)
				.add(NyctoBannerPatterns.HUNTERS_MARK);
	}
}
