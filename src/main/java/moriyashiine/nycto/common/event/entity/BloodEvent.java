/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class BloodEvent {
	public static class Load implements ServerEntityEvents.Load {
		@Override
		public void onLoad(Entity entity, ServerWorld world) {
			ModEntityComponents.BLOOD.maybeGet(entity).ifPresent(bloodComponent -> {
				if (bloodComponent.getLastLoadTime() > 0) {
					long difference = world.getTime() - bloodComponent.getLastLoadTime();
					bloodComponent.setLastLoadTime(world.getTime());
					if (!NyctoAPI.isVampire(entity)) {
						if (bloodComponent.getRegenerationBlockTicks() > 0) {
							bloodComponent.setRegenerationBlockTicks((int) Math.max(0, bloodComponent.getRegenerationBlockTicks() - difference));
						}
						bloodComponent.fill((int) (difference / BloodComponent.REGEN_TIME));
					}
				}
			});
		}
	}

	public static class Unload implements ServerEntityEvents.Unload {
		@Override
		public void onUnload(Entity entity, ServerWorld world) {
			ModEntityComponents.BLOOD.maybeGet(entity).ifPresent(bloodComponent -> bloodComponent.setLastLoadTime(world.getTime()));
		}
	}

	public static class Copy implements ServerPlayerEvents.CopyFrom {
		@Override
		public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
			BloodComponent newBloodComponent = ModEntityComponents.BLOOD.get(newPlayer);
			BloodComponent oldBloodComponent = ModEntityComponents.BLOOD.get(oldPlayer);
			newBloodComponent.setShouldRegenerateNaturally(oldBloodComponent.shouldRegenerateNaturally());
			newBloodComponent.setBlood(BloodComponent.MAX_BLOOD);
			newBloodComponent.sync();
		}
	}
}
