/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power.vampire.weakness;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.power.vampire.weakness.HydrophobiaPower;
import moriyashiine.strawberrylib.api.event.ModifyMovementEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class HydrophobiaEvent implements ModifyMovementEvents.MovementVelocity {
	@Override
	public Vec3d modify(Vec3d velocity, LivingEntity entity) {
		if (entity instanceof PlayerEntity player && player.getGameMode().isSurvivalLike() && NyctoAPI.hasPower(player, ModPowers.HYDROPHOBIA) && player.isTouchingWaterOrRain()) {
			return velocity.multiply(HydrophobiaPower.MULTIPLIER);
		}
		return velocity;
	}
}
