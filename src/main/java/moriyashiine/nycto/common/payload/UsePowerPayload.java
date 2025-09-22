/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record UsePowerPayload(int powerIndex) implements CustomPayload {
	public static final Id<UsePowerPayload> ID = new Id<>(Nycto.id("use_power"));
	public static final PacketCodec<PacketByteBuf, UsePowerPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, UsePowerPayload::powerIndex,
			UsePowerPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(int powerIndex) {
		ClientPlayNetworking.send(new UsePowerPayload(powerIndex));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<UsePowerPayload> {
		@Override
		public void receive(UsePowerPayload payload, ServerPlayNetworking.Context context) {
			NyctoUtil.usePower(context.player().getWorld(), context.player(), NyctoAPI.getPowers(context.player()).get(payload.powerIndex()));
		}
	}
}
