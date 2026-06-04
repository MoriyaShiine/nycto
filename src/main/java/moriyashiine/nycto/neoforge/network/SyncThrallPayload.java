/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncThrallPayload(int entityId, boolean active) implements CustomPacketPayload {
	public static final Type<SyncThrallPayload> TYPE = new Type<>(NyctoNeoForge.id("sync_thrall"));
	public static final StreamCodec<FriendlyByteBuf, SyncThrallPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SyncThrallPayload::entityId,
			ByteBufCodecs.BOOL, SyncThrallPayload::active,
			SyncThrallPayload::new);

	@Override
	public Type<SyncThrallPayload> type() {
		return TYPE;
	}

	public static void send(Entity entity, boolean active) {
		if (!entity.level().isClientSide()) {
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncThrallPayload(entity.getId(), active));
		}
	}

	public static void handle(SyncThrallPayload payload, IPayloadContext context) {
		ThrallClientState.setThrall(payload.entityId(), payload.active());
	}
}
