/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.attribute.timeline.Timeline;

public class ModTimelines {
	public static final RegistryKey<Timeline> VAMPIRE_VILLAGER_SCHEDULE = RegistryKey.of(RegistryKeys.TIMELINE, Nycto.id("vampire_villager_schedule"));

	public static void bootstrap(Registerable<Timeline> registry) {
		registry.register(
				VAMPIRE_VILLAGER_SCHEDULE,
				Timeline.builder()
						.period(24000)
						.entry(
								ModEnvironmentAttributes.VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY,
								track -> track
										.keyframe(10, Activity.REST)
										.keyframe(12000, Activity.IDLE)
										.keyframe(14000, Activity.WORK)
										.keyframe(21000, Activity.MEET)
										.keyframe(23000, Activity.IDLE)
						)
						.entry(
								ModEnvironmentAttributes.BABY_VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY,
								track -> track
										.keyframe(10, Activity.REST)
										.keyframe(12000, Activity.IDLE)
										.keyframe(15000, Activity.PLAY)
										.keyframe(18000, Activity.IDLE)
										.keyframe(22000, Activity.PLAY)
						)
						.build()
		);
	}
}
