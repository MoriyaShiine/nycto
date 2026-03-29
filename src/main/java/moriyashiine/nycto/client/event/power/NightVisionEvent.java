/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.common.component.entity.power.NightVisionComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.client.AddNightVisionScaleEvent;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;

public class NightVisionEvent implements AddNightVisionScaleEvent {
	@Override
	public float addScale(LivingEntity entity) {
		@Nullable NightVisionComponent nightVisionComponent = ModEntityComponents.NIGHT_VISION.getNullable(entity);
		if (nightVisionComponent != null) {
			return nightVisionComponent.getStrengthPercentage();
		}
		return 0;
	}
}
