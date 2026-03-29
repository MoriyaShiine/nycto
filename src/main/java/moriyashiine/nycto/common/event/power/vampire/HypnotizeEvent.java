/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.HypnotizedComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModMobEffects;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;

public class HypnotizeEvent implements AfterDamageIncludingDeathEvent {
	@Override
	public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
		if (shouldRemoveEffect(victim, source)) {
			victim.removeEffect(ModMobEffects.HYPNOTIZED);
		}
	}

	private static boolean shouldRemoveEffect(LivingEntity entity, DamageSource source) {
		if (entity.slib$isPlayer()) {
			return source.getEntity() != null;
		}
		@Nullable HypnotizedComponent hypnotizedComponent = ModEntityComponents.HYPNOTIZED.getNullable(entity);
		return hypnotizedComponent != null && hypnotizedComponent.isOwner(source.getEntity());
	}
}
