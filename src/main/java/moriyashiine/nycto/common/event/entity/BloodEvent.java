/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;

public class BloodEvent {
	public static void init() {
		ServerPlayerEvents.COPY_FROM.register(new Copy());
	}

	private static class Copy implements ServerPlayerEvents.CopyFrom {
		@Override
		public void copyFromPlayer(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
			BloodComponent blood = NyctoEntityComponents.BLOOD.get(newPlayer);
			blood.setRegeneratesNaturally(NyctoEntityComponents.BLOOD.get(oldPlayer).regeneratesNaturally());
			blood.fill(BloodComponent.MAX_BLOOD);
		}
	}
}
