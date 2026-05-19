/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.HypnotizedComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoMobEffects;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class HypnotizeEvent implements AfterDamageIncludingDeathEvent {
	public static void init() {
		AfterDamageIncludingDeathEvent.EVENT.register(new HypnotizeEvent());
	}

	@Override
	public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
		if (shouldRemoveEffect(victim, source)) {
			victim.removeEffect(NyctoMobEffects.HYPNOTIZED);
		}
	}

	private static boolean shouldRemoveEffect(LivingEntity entity, DamageSource source) {
		if (entity.slib$isPlayer()) {
			return source.getEntity() != null;
		}
		HypnotizedComponent hypnotized = NyctoEntityComponents.HYPNOTIZED.getNullable(entity);
		return hypnotized != null && hypnotized.isOwner(source.getEntity());
	}
}
