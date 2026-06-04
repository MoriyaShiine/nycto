/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetActivePowerPayload(int index) implements CustomPacketPayload {
	public static final Type<SetActivePowerPayload> TYPE = new Type<>(NyctoNeoForge.id("set_active_power"));
	public static final StreamCodec<FriendlyByteBuf, SetActivePowerPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SetActivePowerPayload::index,
			SetActivePowerPayload::new);

	@Override
	public Type<SetActivePowerPayload> type() {
		return TYPE;
	}

	public static void send(int index) {
		PacketDistributor.sendToServer(new SetActivePowerPayload(index));
	}

	public static void handle(SetActivePowerPayload payload, IPayloadContext context) {
		if (context.player() instanceof ServerPlayer player) {
			NyctoData.setActivePowerIndex(player, payload.index());
		}
	}
}
