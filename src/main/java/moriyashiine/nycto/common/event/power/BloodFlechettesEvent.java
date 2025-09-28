/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power;

import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.MathHelper;

public class BloodFlechettesEvent implements AfterDamageIncludingDeathEvent {
	@Override
	public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
		if (!blocked && !entity.getType().isIn(ModEntityTypeTags.HAS_NO_BLOOD)
				&& source.getSource() instanceof LivingEntity attacker && attacker.getHealth() < attacker.getMaxHealth() && ModEntityComponents.HEAL_BLOCK.get(entity).canStealLife(attacker)) {
			BloodComponent bloodComponent = ModEntityComponents.BLOOD.get(entity);
			int drainAmount = MathHelper.floor(Math.min(damageTaken * 0.2, bloodComponent.getBlood()));
			bloodComponent.drainAttack(drainAmount);
			attacker.heal(drainAmount);
			SLibUtils.playSound(attacker, ModSoundEvents.POWER_BLOOD_FLECHETTES_LIFE_DRAIN);
		}
	}
}
