/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.BloodBarrierComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.MathHelper;

public class BloodBarrierEvent implements ServerLivingEntityEvents.AllowDamage {
	@Override
	public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
		if (!source.isIn(DamageTypeTags.BYPASSES_ARMOR) && SLibUtils.isAttackingPlayerCooldownWithinThreshold(0.7F)) {
			BloodBarrierComponent bloodBarrierComponent = ModEntityComponents.BLOOD_BARRIER.get(entity);
			if (bloodBarrierComponent.getBarriers() > 0) {
				entity.hurtTime = entity.maxHurtTime = 10;
				if (amount >= 3) {
					bloodBarrierComponent.breakBarrier();
				} else {
					SLibUtils.playSound(entity, ModSoundEvents.POWER_BLOOD_BARRIER_HIT, 1, MathHelper.nextFloat(entity.getRandom(), 0.8F, 1.2F));
				}
				return false;
			}
		}
		return true;
	}
}
