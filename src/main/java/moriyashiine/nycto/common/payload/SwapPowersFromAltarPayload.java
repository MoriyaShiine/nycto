/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.inventory.AltarMenu;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SwapPowersFromAltarPayload(Power first, Power second) implements CustomPacketPayload {
	public static final Type<SwapPowersFromAltarPayload> TYPE = new Type<>(Nycto.id("swap_powers_from_altar"));
	public static final StreamCodec<FriendlyByteBuf, SwapPowersFromAltarPayload> CODEC = StreamCodec.composite(
			Identifier.STREAM_CODEC, payload -> NyctoRegistries.POWER.getKey(payload.first()),
			Identifier.STREAM_CODEC, payload -> NyctoRegistries.POWER.getKey(payload.second()),
			(first, second) -> new SwapPowersFromAltarPayload(NyctoRegistries.POWER.getValue(first), NyctoRegistries.POWER.getValue(second)));

	@Override
	public Type<SwapPowersFromAltarPayload> type() {
		return TYPE;
	}

	public static void send(Power first, Power second) {
		PacketDistributor.sendToServer(new SwapPowersFromAltarPayload(first, second));
	}

	public static void handle(SwapPowersFromAltarPayload payload, IPayloadContext context) {
		AltarMenu.swapPowers(context.player(), payload.first(), payload.second());
	}
}
