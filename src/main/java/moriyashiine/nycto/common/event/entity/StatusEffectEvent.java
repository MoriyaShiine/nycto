/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModDamageTypes;
import moriyashiine.nycto.common.init.ModStatusEffects;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.MathHelper;

public class StatusEffectEvent {
	public static class VampireWard implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
			if (!blocked && entity.hasStatusEffect(ModStatusEffects.VAMPIRE_WARD) && !source.isOf(ModDamageTypes.TOXIC_TOUCH) && source.getSource() instanceof LivingEntity attacker && entity != attacker && NyctoAPI.isVampire(attacker)) {
				attacker.serverDamage(attacker.getDamageSources().create(ModDamageTypes.TOXIC_TOUCH, entity), MathHelper.nextFloat(entity.getRandom(), 1, 3));
			}
		}
	}

	public static class Stunned implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
			if (!blocked) {
				entity.removeStatusEffect(ModStatusEffects.STUNNED);
			}
		}
	}
}
