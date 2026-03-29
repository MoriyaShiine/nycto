/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire.weakness;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.world.power.vampire.weakness.HydrophobiaWeakness;
import moriyashiine.strawberrylib.api.event.ModifyMovementEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class HydrophobiaEvent implements ModifyMovementEvents.MovementDelta {
	@Override
	public Vec3 modify(Vec3 delta, LivingEntity entity) {
		if (entity instanceof Player player && player.slib$isSurvival() && NyctoAPI.hasPower(player, ModPowers.HYDROPHOBIA) && player.isInWaterOrRain()) {
			return delta.scale(HydrophobiaWeakness.MULTIPLIER);
		}
		return delta;
	}
}
