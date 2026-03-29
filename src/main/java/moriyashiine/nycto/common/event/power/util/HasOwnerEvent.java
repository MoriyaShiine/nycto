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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class HasOwnerEvent implements ServerLivingEntityEvents.AfterDamage {
	private static final RevengeFunction REVENGE = new RevengeFunction() {
		@Override
		public boolean shouldHelp(Mob mob, LivingEntity attacker, LivingEntity victim) {
			if (SLibUtils.shouldHurt(attacker, victim) && !NyctoUtil.isSurvivalNullable(mob.getTarget())) {
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
		if (source.getEntity() instanceof LivingEntity attacker) {
			victim.level().getEntitiesOfClass(Mob.class, victim.getBoundingBox().inflate(16), EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(
							entity -> entity instanceof Mob mob && revengeFunction.shouldHelp(mob, attacker, victim)))
					.forEach(foundEntity -> {
						LivingEntity target = revengeFunction.targetAttacker() ? attacker : victim;
						setTarget(foundEntity, target);
					});
		}
	}

	public static void setTarget(Mob attacker, LivingEntity target) {
		attacker.setTarget(target);
		if (target == null) {
			attacker.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
			attacker.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
		} else {
			attacker.getBrain().setMemory(MemoryModuleType.ANGRY_AT, target.getUUID());
			attacker.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
		}
	}

	public interface RevengeFunction {
		boolean shouldHelp(Mob mob, LivingEntity attacker, LivingEntity victim);

		boolean targetAttacker();
	}
}
