/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.level.AuraComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModEntityTypes;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.entity.monster.Hunter;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HunterEvent {
	public static class Heat implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
			if (source.getEntity() instanceof Player player) {
				if (victim.getType() == ModEntityTypes.HUNTER) {
					if (victim.isDeadOrDying()) {
						NyctoUtil.notifyNearbyVillagers(victim, player, GossipType.MAJOR_NEGATIVE, 25);
					} else {
						NyctoUtil.notifyNearbyVillagers(victim, player, GossipType.MINOR_NEGATIVE, 5);
					}
				}
				if (NyctoAPI.isVampire(player)) {
					if (originalDamage > 1 || victim.getRandom().nextBoolean() || victim.isDeadOrDying()) {
						ModEntityComponents.HUNTER_HEAT.get(player).maybeIncreaseHeat(victim, victim.isDeadOrDying());
					}
				}
			}
		}
	}

	public static class Aura implements TickEntityEvent {
		@Override
		public void tick(Level level, Entity entity) {
			if (!level.isClientSide() && entity.tickCount % 10 == 0 && entity instanceof LivingEntity living && NyctoUtil.hasGarlicAura(living)) {
				AuraComponent.applyAura(level, living.blockPosition(), 2, false, NyctoAPI::isVampire);
			}
		}
	}

	public static class CriticalImmunity implements ModifyCriticalStatusEvent {
		@Override
		public TriState isCritical(Player attacker, Entity target, float attackCooldownProgress) {
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
			if (!(attacker instanceof Enemy) && target instanceof Hunter) {
				return TriState.TRUE;
			}
			return TriState.DEFAULT;
		}
	}
}
