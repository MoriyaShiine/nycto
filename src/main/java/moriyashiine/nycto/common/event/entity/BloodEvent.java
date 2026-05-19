/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.entity.event.v1.effect.EffectEventContext;
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class BloodEvent {
	public static void init() {
		ServerPlayerEvents.COPY_FROM.register(new Copy());
		ServerMobEffectEvents.ALLOW_ADD.register(new EffectImmunity());
	}

	private static class Copy implements ServerPlayerEvents.CopyFrom {
		@Override
		public void copyFromPlayer(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
			BloodComponent blood = NyctoEntityComponents.BLOOD.get(newPlayer);
			blood.setRegeneratesNaturally(NyctoEntityComponents.BLOOD.get(oldPlayer).regeneratesNaturally());
			blood.fill(BloodComponent.MAX_BLOOD);
		}
	}

	private static class EffectImmunity implements ServerMobEffectEvents.AllowAdd {
		@Override
		public boolean allowAdd(MobEffectInstance effectInstance, LivingEntity entity, EffectEventContext ctx) {
			return !(effectInstance.getEffect() == MobEffects.REGENERATION && NyctoEntityComponents.BLOOD.get(entity).criticalBlood());
		}
	}
}
