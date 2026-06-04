/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.util.NyctoBloodUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncEntityBloodPayload(int entityId, int blood) implements CustomPacketPayload {
	public static final Type<SyncEntityBloodPayload> TYPE = new Type<>(NyctoNeoForge.id("sync_entity_blood"));
	public static final StreamCodec<FriendlyByteBuf, SyncEntityBloodPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SyncEntityBloodPayload::entityId,
			ByteBufCodecs.VAR_INT, SyncEntityBloodPayload::blood,
			SyncEntityBloodPayload::new);

	@Override
	public Type<SyncEntityBloodPayload> type() {
		return TYPE;
	}

	public static void send(LivingEntity entity, int blood) {
		if (!entity.level().isClientSide()) {
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncEntityBloodPayload(entity.getId(), Mth.clamp(blood, 0, NyctoBloodUtil.MAX_ENTITY_BLOOD)));
		}
	}

	public static void handle(SyncEntityBloodPayload payload, IPayloadContext context) {
		Entity entity = context.player().level().getEntity(payload.entityId());
		if (entity instanceof LivingEntity living) {
			NyctoBloodUtil.applySyncedBlood(living, payload.blood());
		}
	}
}
