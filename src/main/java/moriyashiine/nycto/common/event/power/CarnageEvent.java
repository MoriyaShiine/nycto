/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power;

import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.MathHelper;

public class CarnageEvent implements AfterDamageIncludingDeathEvent {
	@Override
	public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
		if (!blocked && SLibUtils.isAttackingPlayerCooldownWithinThreshold(0.7F) && source.getSource() instanceof LivingEntity attacker && ModEntityComponents.CARNAGE.get(attacker).isActive()) {
			BloodComponent bloodComponent = ModEntityComponents.BLOOD.get(entity);
			bloodComponent.drainAttack(MathHelper.floor(Math.min(5, damageTaken)));
			bloodComponent.setBleedTicks(80);
			SLibUtils.playSound(entity, ModSoundEvents.POWER_CARNAGE_HIT, 1, MathHelper.nextFloat(entity.getRandom(), 0.8F, 1.2F));
		}
	}
}
