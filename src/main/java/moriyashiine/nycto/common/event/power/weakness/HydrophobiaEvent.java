/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power.weakness;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.power.vampire.HydrophobiaPower;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.event.ModifyMovementEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class HydrophobiaEvent implements ModifyMovementEvents.MovementVelocity {
	@Override
	public Vec3d modify(Vec3d velocity, LivingEntity entity) {
		if (NyctoUtil.isSurvival(entity) && entity instanceof PlayerEntity player && NyctoAPI.hasPower(player, ModPowers.HYDROPHOBIA) && player.isTouchingWaterOrRain()) {
			return velocity.multiply(HydrophobiaPower.MULTIPLIER);
		}
		return velocity;
	}
}
