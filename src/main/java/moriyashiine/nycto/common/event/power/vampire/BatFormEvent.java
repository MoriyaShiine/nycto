/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import moriyashiine.strawberrylib.api.event.ModifyMovementEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class BatFormEvent {
	public static class ReduceDamage implements ModifyDamageTakenEvent {
		@Override
		public float modify(Phase phase, float amount, ServerWorld world, DamageSource source, LivingEntity victim) {
			return phase == Phase.FINAL && !source.isIn(DamageTypeTags.BYPASSES_ARMOR) && source.getAttacker() instanceof PlayerEntity player && ModEntityComponents.BAT_FORM.get(player).isEnabled() ? 0.1F : 1;
		}
	}

	public static class ReduceFlightSpeed implements ModifyMovementEvents.MovementVelocity {
		@Override
		public Vec3d modify(Vec3d velocity, LivingEntity entity) {
			if (entity instanceof PlayerEntity player && ModEntityComponents.BAT_FORM.get(player).isEnabled()) {
				return velocity.multiply(0.75F);
			}
			return velocity;
		}
	}
}
