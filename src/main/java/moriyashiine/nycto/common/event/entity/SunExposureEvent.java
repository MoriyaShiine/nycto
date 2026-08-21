package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.common.component.entity.SunExposureComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;

public class SunExposureEvent implements ServerPlayerEvents.AfterRespawn {
	public static void init() {
		ServerPlayerEvents.AFTER_RESPAWN.register(new SunExposureEvent());
	}

	@Override
	public void afterRespawn(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
		if (!alive) {
			SunExposureComponent sunExposure = NyctoEntityComponents.SUN_EXPOSURE.get(newPlayer);
			sunExposure.setShouldTick(NyctoEntityComponents.SUN_EXPOSURE.get(oldPlayer).shouldTick());
			sunExposure.sync();
		}
	}
}
