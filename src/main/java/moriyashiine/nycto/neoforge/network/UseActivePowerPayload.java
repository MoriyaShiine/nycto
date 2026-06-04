/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoGameplay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UseActivePowerPayload() implements CustomPacketPayload {
	public static final Type<UseActivePowerPayload> TYPE = new Type<>(NyctoNeoForge.id("use_active_power"));
	public static final StreamCodec<FriendlyByteBuf, UseActivePowerPayload> CODEC = StreamCodec.unit(new UseActivePowerPayload());

	@Override
	public Type<UseActivePowerPayload> type() {
		return TYPE;
	}

	public static void send() {
		PacketDistributor.sendToServer(new UseActivePowerPayload());
	}

	public static void handle(UseActivePowerPayload payload, IPayloadContext context) {
		if (context.player() instanceof ServerPlayer player) {
			NyctoGameplay.useActivePower(player);
		}
	}
}
