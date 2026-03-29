/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModDamageTypes;
import moriyashiine.nycto.common.init.ModMobEffects;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class StatusEffectEvent {
	public static class VampireWard implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
			if (!blocked && victim.hasEffect(ModMobEffects.VAMPIRE_WARD) && !source.is(ModDamageTypes.TOXIC_TOUCH) && source.getDirectEntity() instanceof LivingEntity attacker && victim != attacker && NyctoAPI.isVampire(attacker)) {
				attacker.hurt(attacker.damageSources().source(ModDamageTypes.TOXIC_TOUCH, victim), Mth.nextFloat(victim.getRandom(), 1, 3));
			}
		}
	}

	public static class Stunned implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
			if (!blocked) {
				victim.removeEffect(ModMobEffects.STUNNED);
			}
		}
	}
}
