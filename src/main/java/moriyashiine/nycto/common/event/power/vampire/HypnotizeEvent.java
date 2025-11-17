/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.HypnotizedComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModStatusEffects;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.jetbrains.annotations.Nullable;

public class HypnotizeEvent implements AfterDamageIncludingDeathEvent {
	@Override
	public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
		if (shouldRemoveEffect(entity, source)) {
			entity.removeStatusEffect(ModStatusEffects.HYPNOTIZED);
		}
	}

	private static boolean shouldRemoveEffect(LivingEntity entity, DamageSource source) {
		if (entity.isPlayer()) {
			return source.getAttacker() != null;
		}
		@Nullable HypnotizedComponent hypnotizedComponent = ModEntityComponents.HYPNOTIZED.getNullable(entity);
		return hypnotizedComponent != null && hypnotizedComponent.isOwner(source.getAttacker());
	}
}
