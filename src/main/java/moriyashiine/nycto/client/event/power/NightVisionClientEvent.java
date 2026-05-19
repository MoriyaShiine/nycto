/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.common.component.entity.power.NightVisionComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.strawberrylib.api.event.client.AddNightVisionScaleEvent;
import net.minecraft.world.entity.LivingEntity;

public class NightVisionClientEvent implements AddNightVisionScaleEvent {
	public static void init() {
		AddNightVisionScaleEvent.EVENT.register(new NightVisionClientEvent());
	}

	@Override
	public float addScale(LivingEntity entity) {
		NightVisionComponent nightVision = NyctoEntityComponents.NIGHT_VISION.getNullable(entity);
		if (nightVision != null) {
			return nightVision.getStrengthPercentage();
		}
		return 0;
	}
}
