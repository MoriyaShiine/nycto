/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.menu.VampireAltarMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SwapAltarPowersPayload(String first, String second) implements CustomPacketPayload {
	public static final Type<SwapAltarPowersPayload> TYPE = new Type<>(NyctoNeoForge.id("swap_altar_powers"));
	public static final StreamCodec<FriendlyByteBuf, SwapAltarPowersPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8, SwapAltarPowersPayload::first,
			ByteBufCodecs.STRING_UTF8, SwapAltarPowersPayload::second,
			SwapAltarPowersPayload::new);

	@Override
	public Type<SwapAltarPowersPayload> type() {
		return TYPE;
	}

	public static void send(String first, String second) {
		PacketDistributor.sendToServer(new SwapAltarPowersPayload(first, second));
	}

	public static void handle(SwapAltarPowersPayload payload, IPayloadContext context) {
		context.enqueueWork(() -> {
			if (context.player().containerMenu instanceof VampireAltarMenu menu) {
				menu.swapPowers(payload.first(), payload.second());
			}
		});
	}
}
