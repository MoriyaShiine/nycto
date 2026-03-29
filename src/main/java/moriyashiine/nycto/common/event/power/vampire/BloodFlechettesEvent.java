/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class BloodFlechettesEvent implements AfterDamageIncludingDeathEvent {
	@Override
	public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
		if (!blocked && !victim.is(ModEntityTypeTags.HAS_NO_BLOOD) && source.getDirectEntity() instanceof LivingEntity attacker && attacker.getHealth() < attacker.getMaxHealth() && ModEntityComponents.HEAL_BLOCK.get(victim).canStealLife(attacker)) {
			BloodComponent bloodComponent = ModEntityComponents.BLOOD.get(victim);
			int drainAmount = Mth.floor(Math.min(modifiedDamage * 0.2, bloodComponent.getBlood()));
			bloodComponent.drainAttack(drainAmount);
			attacker.heal(drainAmount);
			SLibUtils.playSound(attacker, ModSoundEvents.POWER_BLOOD_FLECHETTES_LIFE_DRAIN);
		}
	}
}
