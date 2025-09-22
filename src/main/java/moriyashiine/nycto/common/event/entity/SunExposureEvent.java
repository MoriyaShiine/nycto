/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.common.component.entity.SunExposureComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class SunExposureEvent implements ServerPlayerEvents.AfterRespawn {
	@Override
	public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
		if (!alive) {
			SunExposureComponent newSunExposureComponent = ModEntityComponents.SUN_EXPOSURE.get(newPlayer);
			newSunExposureComponent.setShouldTick(ModEntityComponents.SUN_EXPOSURE.get(oldPlayer).shouldTick());
			newSunExposureComponent.sync();
		}
	}
}
