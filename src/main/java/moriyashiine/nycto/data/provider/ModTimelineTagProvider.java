/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModTimelines;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TimelineTags;
import net.minecraft.world.attribute.timeline.Timeline;

import java.util.concurrent.CompletableFuture;

public class ModTimelineTagProvider extends FabricTagProvider<Timeline> {
	public ModTimelineTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.TIMELINE, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		builder(TimelineTags.UNIVERSAL)
				.add(ModTimelines.VAMPIRE_VILLAGER_SCHEDULE);
	}
}
