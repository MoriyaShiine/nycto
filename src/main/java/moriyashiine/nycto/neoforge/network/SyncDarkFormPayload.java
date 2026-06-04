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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncDarkFormPayload(int entityId, int ticks, int jumpTicks) implements CustomPacketPayload {
	public static final Type<SyncDarkFormPayload> TYPE = new Type<>(NyctoNeoForge.id("sync_dark_form"));
	public static final StreamCodec<FriendlyByteBuf, SyncDarkFormPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SyncDarkFormPayload::entityId,
			ByteBufCodecs.VAR_INT, SyncDarkFormPayload::ticks,
			ByteBufCodecs.VAR_INT, SyncDarkFormPayload::jumpTicks,
			SyncDarkFormPayload::new);

	public SyncDarkFormPayload(int entityId, int ticks) {
		this(entityId, ticks, 0);
	}

	@Override
	public Type<SyncDarkFormPayload> type() {
		return TYPE;
	}

	public static void send(Entity entity, int ticks) {
		if (!entity.level().isClientSide()) {
			int jumpTicks = entity instanceof ServerPlayer player ? player.getPersistentData().getInt(NyctoPowers.TAG_DARK_FORM_JUMP_COOLDOWN) : 0;
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncDarkFormPayload(entity.getId(), ticks, jumpTicks));
		}
	}

	public static void handle(SyncDarkFormPayload payload, IPayloadContext context) {
		Entity entity = context.player().level().getEntity(payload.entityId());
		if (entity != null) {
			DarkFormClientState.setActive(entity, payload.ticks(), payload.jumpTicks());
		}
	}
}
