/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.entity.event.v1.effect.EffectEventContext;
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class BloodEvent {
	public static class Copy implements ServerPlayerEvents.CopyFrom {
		@Override
		public void copyFromPlayer(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
			BloodComponent bloodComponent = ModEntityComponents.BLOOD.get(newPlayer);
			bloodComponent.setRegeneratesNaturally(ModEntityComponents.BLOOD.get(oldPlayer).regeneratesNaturally());
			bloodComponent.fill(BloodComponent.MAX_BLOOD);
		}
	}

	public static class EffectImmunity implements ServerMobEffectEvents.AllowAdd {
		@Override
		public boolean allowAdd(MobEffectInstance effectInstance, LivingEntity entity, EffectEventContext ctx) {
			return !(effectInstance.getEffect() == MobEffects.REGENERATION && ModEntityComponents.BLOOD.get(entity).criticalBlood());
		}
	}
}
