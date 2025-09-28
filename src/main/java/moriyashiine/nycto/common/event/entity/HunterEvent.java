/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.world.AuraComponent;
import moriyashiine.nycto.common.entity.mob.HunterEntity;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class HunterEvent {
	public static class Heat implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
			if (source.getAttacker() instanceof PlayerEntity player && NyctoAPI.isVampire(player)) {
				if (entity.isDead()) {
					NyctoAPI.maximizeHunterHeat(player, entity);
				} else if (baseDamageTaken > 1 || entity.getRandom().nextBoolean()) {
					NyctoAPI.increaseHunterHeat(player, entity);
				}
			}
		}
	}

	public static class Aura implements TickEntityEvent {
		@Override
		public void tick(ServerWorld world, Entity entity) {
			if (entity.age % 10 == 0 && entity instanceof LivingEntity living && NyctoUtil.hasGarlicAura(living)) {
				AuraComponent.applyAura(world, living.getBlockPos(), 2, NyctoAPI::isVampire);
			}
		}
	}

	public static class CriticalImmunity implements ModifyCriticalStatusEvent {
		@Override
		public TriState isCritical(PlayerEntity attacker, Entity target, float attackCooldownProgress) {
			if (target instanceof LivingEntity living && NyctoUtil.hasVampireCriticalHitImmunity(living) && NyctoAPI.isVampire(attacker)) {
				return TriState.FALSE;
			}
			return TriState.DEFAULT;
		}

		@Override
		public int getPriority() {
			return 900;
		}
	}

	public static class PreventTargeting implements PreventHostileTargetingEvent {
		@Override
		public TriState preventsTargeting(LivingEntity attacker, LivingEntity target) {
			if (!(attacker instanceof Monster) && target instanceof HunterEntity) {
				return TriState.TRUE;
			}
			return TriState.DEFAULT;
		}
	}
}
