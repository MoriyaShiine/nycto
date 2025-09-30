/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event.power;

import moriyashiine.nycto.common.component.entity.power.NightVisionComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.client.ModifyNightVisionStrengthEvent;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class NightVisionEvent implements ModifyNightVisionStrengthEvent {
	@Override
	public float modify(float strength, LivingEntity entity) {
		@Nullable NightVisionComponent nightVisionComponent = ModEntityComponents.NIGHT_VISION.getNullable(entity);
		if (nightVisionComponent != null) {
			return nightVisionComponent.getStrengthPercentage();
		}
		return 0;
	}
}
