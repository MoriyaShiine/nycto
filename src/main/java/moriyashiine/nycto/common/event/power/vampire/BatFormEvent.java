/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import moriyashiine.strawberrylib.api.event.ModifyMovementEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class BatFormEvent {
	public static class ReduceDamage implements ModifyDamageTakenEvent {
		@Override
		public float modify(Phase phase, LivingEntity victim, ServerLevel level, DamageSource source) {
			return phase == Phase.FINAL && !source.is(DamageTypeTags.BYPASSES_ARMOR) && source.getEntity() instanceof Player player && ModEntityComponents.BAT_FORM.get(player).isEnabled() ? 0.1F : 1;
		}
	}

	public static class ReduceFlightSpeed implements ModifyMovementEvents.MovementDelta {
		@Override
		public Vec3 modify(Vec3 delta, LivingEntity entity) {
			if (entity instanceof Player player && ModEntityComponents.BAT_FORM.get(player).isEnabled()) {
				return delta.scale(0.75F);
			}
			return delta;
		}
	}
}
