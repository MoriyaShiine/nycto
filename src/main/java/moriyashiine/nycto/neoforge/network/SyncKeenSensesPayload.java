/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import io.netty.buffer.ByteBuf;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;

public record SyncKeenSensesPayload(boolean active, int distance, int renderTicks) implements CustomPacketPayload {
	public static final Type<SyncKeenSensesPayload> TYPE = new Type<>(NyctoNeoForge.id("sync_keen_senses"));
	public static final StreamCodec<ByteBuf, SyncKeenSensesPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL, SyncKeenSensesPayload::active,
			ByteBufCodecs.VAR_INT, SyncKeenSensesPayload::distance,
			ByteBufCodecs.VAR_INT, SyncKeenSensesPayload::renderTicks,
			SyncKeenSensesPayload::new);

	@Override
	public Type<SyncKeenSensesPayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer player) {
		boolean active = NyctoPowers.isKeenSensesActive(player);
		int distance = player.getPersistentData().getInt(NyctoPowers.TAG_KEEN_SENSES_DISTANCE);
		int renderTicks = player.getPersistentData().getInt(NyctoPowers.TAG_KEEN_SENSES_RENDER_TICKS);
		PacketDistributor.sendToPlayer(player, new SyncKeenSensesPayload(active, distance, renderTicks));
	}

	public static void handle(SyncKeenSensesPayload payload, IPayloadContext context) {
		context.enqueueWork(() -> setClientState(payload.active(), payload.distance(), payload.renderTicks()));
	}

	private static void setClientState(boolean active, int distance, int renderTicks) {
		if (!FMLEnvironment.dist.isClient()) {
			return;
		}
		try {
			Class<?> stateClass = Class.forName("moriyashiine.nycto.neoforge.client.KeenSensesClientState");
			stateClass.getMethod("set", boolean.class, int.class, int.class).invoke(null, active, distance, renderTicks);
		} catch (ReflectiveOperationException ignored) {
		}
	}
}
