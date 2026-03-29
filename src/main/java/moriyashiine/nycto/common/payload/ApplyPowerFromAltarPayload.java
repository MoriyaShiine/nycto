/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.api.world.inventory.AltarMenu;
import moriyashiine.nycto.common.Nycto;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ApplyPowerFromAltarPayload(int id) implements CustomPacketPayload {
	public static final Type<ApplyPowerFromAltarPayload> TYPE = new Type<>(Nycto.id("apply_power_from_altar"));
	public static final StreamCodec<FriendlyByteBuf, ApplyPowerFromAltarPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, ApplyPowerFromAltarPayload::id,
			ApplyPowerFromAltarPayload::new);

	@Override
	public Type<ApplyPowerFromAltarPayload> type() {
		return TYPE;
	}

	public static void send(int id) {
		ClientPlayNetworking.send(new ApplyPowerFromAltarPayload(id));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<ApplyPowerFromAltarPayload> {
		@Override
		public void receive(ApplyPowerFromAltarPayload payload, ServerPlayNetworking.Context context) {
			AltarMenu.apply(context.player(), payload.id());
		}
	}
}
