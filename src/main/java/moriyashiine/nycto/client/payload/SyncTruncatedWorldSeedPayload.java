/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public record SyncTruncatedWorldSeedPayload(int seed) implements CustomPayload {
	public static final Id<SyncTruncatedWorldSeedPayload> ID = new Id<>(Nycto.id("sync_truncated_world_seed"));
	public static final PacketCodec<PacketByteBuf, SyncTruncatedWorldSeedPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, SyncTruncatedWorldSeedPayload::seed,
			SyncTruncatedWorldSeedPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(ServerPlayerEntity player, int seed) {
		ServerPlayNetworking.send(player, new SyncTruncatedWorldSeedPayload(seed));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<SyncTruncatedWorldSeedPayload> {
		@Override
		public void receive(SyncTruncatedWorldSeedPayload payload, ClientPlayNetworking.Context context) {
			NyctoUtil.truncatedWorldSeed = payload.seed();
		}
	}
}
