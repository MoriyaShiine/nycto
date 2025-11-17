/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power.util;

import moriyashiine.nycto.common.component.entity.power.util.HasOwnerComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.predicate.entity.EntityPredicates;

public class HasOwnerEvent implements ServerLivingEntityEvents.AfterDamage {
	private static final RevengeFunction REVENGE = new RevengeFunction() {
		@Override
		public boolean shouldHelp(MobEntity mob, LivingEntity attacker, LivingEntity victim) {
			if (SLibUtils.shouldHurt(attacker, victim) && !NyctoUtil.isSurvival(mob.getTarget())) {
				if (ModEntityComponents.VAMPIRIC_THRALL.get(mob).getFollowMode() == VampiricThrallComponent.FollowMode.STAY) {
					return false;
				}
				return HasOwnerComponent.isOwner(mob, victim);
			}
			return false;
		}

		@Override
		public boolean targetAttacker() {
			return true;
		}
	};

	@Override
	public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
		revenge(entity, source, REVENGE);
	}

	public static void revenge(LivingEntity victim, DamageSource source, RevengeFunction revengeFunction) {
		if (source.getAttacker() instanceof LivingEntity attacker) {
			victim.getEntityWorld().getEntitiesByClass(MobEntity.class, victim.getBoundingBox().expand(16), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(
							entity -> entity instanceof MobEntity mob && revengeFunction.shouldHelp(mob, attacker, victim)))
					.forEach(foundEntity -> {
						LivingEntity target = revengeFunction.targetAttacker() ? attacker : victim;
						setTarget(foundEntity, target);
					});
		}
	}

	public static void setTarget(MobEntity attacker, LivingEntity target) {
		attacker.setTarget(target);
		attacker.getBrain().remember(MemoryModuleType.ANGRY_AT, target.getUuid());
		attacker.getBrain().remember(MemoryModuleType.ATTACK_TARGET, target);
	}

	public interface RevengeFunction {
		boolean shouldHelp(MobEntity mob, LivingEntity attacker, LivingEntity victim);

		boolean targetAttacker();
	}
}
