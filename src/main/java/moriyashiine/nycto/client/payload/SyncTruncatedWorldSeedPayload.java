/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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
		PacketDistributor.sendToPlayer(player, new SyncTruncatedWorldSeedPayload(seed));
	}

	public static void handle(SyncTruncatedWorldSeedPayload payload, IPayloadContext context) {
		NyctoUtil.truncatedWorldSeed = payload.seed();
	}
}
