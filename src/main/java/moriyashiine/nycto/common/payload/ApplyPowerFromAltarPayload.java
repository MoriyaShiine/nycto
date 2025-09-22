/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.api.screenhandler.AltarScreenHandler;
import moriyashiine.nycto.common.Nycto;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ApplyPowerFromAltarPayload(int rawId) implements CustomPayload {
	public static final Id<ApplyPowerFromAltarPayload> ID = new Id<>(Nycto.id("apply_power_from_altar"));
	public static final PacketCodec<PacketByteBuf, ApplyPowerFromAltarPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, ApplyPowerFromAltarPayload::rawId,
			ApplyPowerFromAltarPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(int rawId) {
		ClientPlayNetworking.send(new ApplyPowerFromAltarPayload(rawId));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<ApplyPowerFromAltarPayload> {
		@Override
		public void receive(ApplyPowerFromAltarPayload payload, ServerPlayNetworking.Context context) {
			AltarScreenHandler.apply(context.player(), payload.rawId());
		}
	}
}
