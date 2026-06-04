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

public record SyncBatFormPayload(int entityId, int ticks) implements CustomPacketPayload {
	public static final Type<SyncBatFormPayload> TYPE = new Type<>(NyctoNeoForge.id("sync_bat_form"));
	public static final StreamCodec<FriendlyByteBuf, SyncBatFormPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SyncBatFormPayload::entityId,
			ByteBufCodecs.VAR_INT, SyncBatFormPayload::ticks,
			SyncBatFormPayload::new);

	@Override
	public Type<SyncBatFormPayload> type() {
		return TYPE;
	}

	public static void send(Entity entity, int ticks) {
		if (!entity.level().isClientSide()) {
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncBatFormPayload(entity.getId(), ticks));
		}
	}

	public static void handle(SyncBatFormPayload payload, IPayloadContext context) {
		Entity entity = context.player().level().getEntity(payload.entityId());
		if (entity != null) {
			BatFormClientState.setActive(entity, payload.ticks());
		}
	}
}
