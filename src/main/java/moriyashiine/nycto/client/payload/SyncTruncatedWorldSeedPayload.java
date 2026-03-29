/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record SyncTruncatedWorldSeedPayload(int seed) implements CustomPacketPayload {
	public static final Type<SyncTruncatedWorldSeedPayload> TYPE = new Type<>(Nycto.id("sync_truncated_world_seed"));
	public static final StreamCodec<FriendlyByteBuf, SyncTruncatedWorldSeedPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SyncTruncatedWorldSeedPayload::seed,
			SyncTruncatedWorldSeedPayload::new);

	@Override
	public Type<SyncTruncatedWorldSeedPayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer player, int seed) {
		ServerPlayNetworking.send(player, new SyncTruncatedWorldSeedPayload(seed));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<SyncTruncatedWorldSeedPayload> {
		@Override
		public void receive(SyncTruncatedWorldSeedPayload payload, ClientPlayNetworking.Context context) {
			NyctoUtil.truncatedWorldSeed = payload.seed();
		}
	}
}
