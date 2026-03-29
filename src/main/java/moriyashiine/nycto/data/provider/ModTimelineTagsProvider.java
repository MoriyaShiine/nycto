/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModTimelines;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TimelineTags;
import net.minecraft.world.timeline.Timeline;

import java.util.concurrent.CompletableFuture;

public class ModTimelineTagsProvider extends FabricTagsProvider<Timeline> {
	public ModTimelineTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.TIMELINE, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(TimelineTags.UNIVERSAL)
				.add(ModTimelines.VAMPIRE_VILLAGER_SCHEDULE);
	}
}
