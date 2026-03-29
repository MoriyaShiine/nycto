/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.timeline.Timeline;

public class ModTimelines {
	public static final ResourceKey<Timeline> VAMPIRE_VILLAGER_SCHEDULE = ResourceKey.create(Registries.TIMELINE, Nycto.id("vampire_villager_schedule"));

	public static void bootstrap(BootstrapContext<Timeline> registry) {
		registry.register(
				VAMPIRE_VILLAGER_SCHEDULE,
				Timeline.builder(registry.lookup(Registries.WORLD_CLOCK).getOrThrow(WorldClocks.OVERWORLD))
						.setPeriodTicks(24000)
						.addTrack(
								ModEnvironmentAttributes.VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY,
								track -> track
										.addKeyframe(10, Activity.REST)
										.addKeyframe(12000, Activity.IDLE)
										.addKeyframe(14000, Activity.WORK)
										.addKeyframe(21000, Activity.MEET)
										.addKeyframe(23000, Activity.IDLE)
						)
						.addTrack(
								ModEnvironmentAttributes.BABY_VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY,
								track -> track
										.addKeyframe(10, Activity.REST)
										.addKeyframe(12000, Activity.IDLE)
										.addKeyframe(15000, Activity.PLAY)
										.addKeyframe(18000, Activity.IDLE)
										.addKeyframe(22000, Activity.PLAY)
						)
						.build()
		);
	}
}
