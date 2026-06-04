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

public record SyncMistFormPayload(int entityId, int ticks) implements CustomPacketPayload {
	public static final Type<SyncMistFormPayload> TYPE = new Type<>(NyctoNeoForge.id("sync_mist_form"));
	public static final StreamCodec<FriendlyByteBuf, SyncMistFormPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SyncMistFormPayload::entityId,
			ByteBufCodecs.VAR_INT, SyncMistFormPayload::ticks,
			SyncMistFormPayload::new);

	@Override
	public Type<SyncMistFormPayload> type() {
		return TYPE;
	}

	public static void send(Entity entity, int ticks) {
		if (!entity.level().isClientSide()) {
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncMistFormPayload(entity.getId(), ticks));
		}
	}

	public static void handle(SyncMistFormPayload payload, IPayloadContext context) {
		Entity entity = context.player().level().getEntity(payload.entityId());
		if (entity != null) {
			MistFormClientState.setActive(entity, payload.ticks());
		}
	}
}
