/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.event.VampirePowerEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DarkFormJumpPayload() implements CustomPacketPayload {
	public static final Type<DarkFormJumpPayload> TYPE = new Type<>(NyctoNeoForge.id("dark_form_jump"));
	public static final StreamCodec<FriendlyByteBuf, DarkFormJumpPayload> CODEC = StreamCodec.unit(new DarkFormJumpPayload());

	@Override
	public Type<DarkFormJumpPayload> type() {
		return TYPE;
	}

	public static void send() {
		PacketDistributor.sendToServer(new DarkFormJumpPayload());
	}

	public static void handle(DarkFormJumpPayload payload, IPayloadContext context) {
		context.enqueueWork(() -> VampirePowerEvents.tryDarkFormJump(context.player()));
	}
}
