/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.client.ModifyNightVisionStrengthEvent;
import net.minecraft.entity.LivingEntity;

public class NightVisionEvent implements ModifyNightVisionStrengthEvent {
	@Override
	public float modify(float strength, LivingEntity player) {
		return ModEntityComponents.NIGHT_VISION.get(player).getStrengthPercentage();
	}
}
