/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.entity.event.v1.effect.EffectEventContext;
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BloodEvent {
	public static class Load implements ServerEntityEvents.Load {
		@Override
		public void onLoad(Entity entity, ServerLevel level) {
			ModEntityComponents.BLOOD.maybeGet(entity).ifPresent(bloodComponent -> {
				if (bloodComponent.getLastLoadTime() > 0) {
					long difference = level.getGameTime() - bloodComponent.getLastLoadTime();
					bloodComponent.setLastLoadTime(level.getGameTime());
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
		public void onUnload(Entity entity, ServerLevel level) {
			ModEntityComponents.BLOOD.maybeGet(entity).ifPresent(bloodComponent -> bloodComponent.setLastLoadTime(level.getGameTime()));
		}
	}

	public static class Copy implements ServerPlayerEvents.CopyFrom {
		@Override
		public void copyFromPlayer(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
			BloodComponent newBloodComponent = ModEntityComponents.BLOOD.get(newPlayer);
			BloodComponent oldBloodComponent = ModEntityComponents.BLOOD.get(oldPlayer);
			newBloodComponent.setRegeneratesNaturally(oldBloodComponent.regeneratesNaturally());
			newBloodComponent.setBlood(BloodComponent.MAX_BLOOD);
			newBloodComponent.sync();
		}
	}

	public static class EffectImmunity implements ServerMobEffectEvents.AllowAdd {
		@Override
		public boolean allowAdd(MobEffectInstance effectInstance, LivingEntity entity, EffectEventContext ctx) {
			return !(effectInstance.getEffect() == MobEffects.REGENERATION && ModEntityComponents.BLOOD.get(entity).criticalBlood());
		}
	}
}
