/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class CarnageEvent implements AfterDamageIncludingDeathEvent {
	public static void init() {
		AfterDamageIncludingDeathEvent.EVENT.register(new CarnageEvent());
	}

	@Override
	public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
		if (!blocked && SLibUtils.isAttackingPlayerCooldownWithinThreshold(0.7F) && source.getDirectEntity() instanceof LivingEntity attacker && NyctoEntityComponents.CARNAGE.get(attacker).isActive()) {
			BloodComponent blood = NyctoEntityComponents.BLOOD.get(victim);
			blood.drainAttack(Mth.floor(Math.min(5, modifiedDamage)));
			blood.setBleedTicks(80);
			SLibUtils.playSound(victim, NyctoSoundEvents.CARNAGE_HIT, 1, Mth.nextFloat(victim.getRandom(), 0.8F, 1.2F));
		}
	}
}
