/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncBloodBarrierPayload(int entityId, int layers) implements CustomPacketPayload {
	public static final Type<SyncBloodBarrierPayload> TYPE = new Type<>(NyctoNeoForge.id("sync_blood_barrier"));
	public static final StreamCodec<FriendlyByteBuf, SyncBloodBarrierPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SyncBloodBarrierPayload::entityId,
			ByteBufCodecs.VAR_INT, SyncBloodBarrierPayload::layers,
			SyncBloodBarrierPayload::new);

	@Override
	public Type<SyncBloodBarrierPayload> type() {
		return TYPE;
	}

	public static void send(Entity entity, int layers) {
		if (!entity.level().isClientSide()) {
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncBloodBarrierPayload(entity.getId(), Mth.clamp(layers, 0, 3)));
		}
	}

	public static void handle(SyncBloodBarrierPayload payload, IPayloadContext context) {
		Entity entity = context.player().level().getEntity(payload.entityId());
		if (entity != null) {
			entity.getPersistentData().putInt(NyctoPowers.TAG_BLOOD_BARRIER_LAYERS, Mth.clamp(payload.layers(), 0, 3));
		}
	}
}
