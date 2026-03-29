/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.internal;

import moriyashiine.nycto.client.payload.SyncTruncatedWorldSeedPayload;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;

public class SyncTruncatedWorldSeedEvent implements ServerPlayerEvents.Join {
	@Override
	public void onJoin(ServerPlayer player) {
		NyctoUtil.truncatedWorldSeed = (int) player.level().getSeed();
		SyncTruncatedWorldSeedPayload.send(player, NyctoUtil.truncatedWorldSeed);
	}
}
