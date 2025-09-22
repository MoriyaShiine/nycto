/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.internal;

import moriyashiine.nycto.client.payload.SyncTruncatedWorldSeedPayload;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class SyncTruncatedWorldSeedEvent implements ServerPlayerEvents.Join {
	@Override
	public void onJoin(ServerPlayerEntity player) {
		NyctoUtil.truncatedWorldSeed = (int) player.getWorld().getSeed();
		SyncTruncatedWorldSeedPayload.send(player, NyctoUtil.truncatedWorldSeed);
	}
}
