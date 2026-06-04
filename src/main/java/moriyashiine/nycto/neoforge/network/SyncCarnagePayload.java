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

public record SyncCarnagePayload(int entityId, int ticks) implements CustomPacketPayload {
	public static final Type<SyncCarnagePayload> TYPE = new Type<>(NyctoNeoForge.id("sync_carnage"));
	public static final StreamCodec<FriendlyByteBuf, SyncCarnagePayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SyncCarnagePayload::entityId,
			ByteBufCodecs.VAR_INT, SyncCarnagePayload::ticks,
			SyncCarnagePayload::new);

	@Override
	public Type<SyncCarnagePayload> type() {
		return TYPE;
	}

	public static void send(Entity entity, int ticks) {
		if (!entity.level().isClientSide()) {
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncCarnagePayload(entity.getId(), ticks));
		}
	}

	public static void handle(SyncCarnagePayload payload, IPayloadContext context) {
		Entity entity = context.player().level().getEntity(payload.entityId());
		if (entity != null) {
			CarnageClientState.setActive(entity, payload.ticks());
		}
	}
}
