/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record UsePowerPayload(int powerIndex) implements CustomPacketPayload {
	public static final Type<UsePowerPayload> TYPE = new Type<>(Nycto.id("use_power"));
	public static final StreamCodec<FriendlyByteBuf, UsePowerPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, UsePowerPayload::powerIndex,
			UsePowerPayload::new);

	@Override
	public Type<UsePowerPayload> type() {
		return TYPE;
	}

	public static void send(int powerIndex) {
		ClientPlayNetworking.send(new UsePowerPayload(powerIndex));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<UsePowerPayload> {
		@Override
		public void receive(UsePowerPayload payload, ServerPlayNetworking.Context context) {
			NyctoUtil.usePower(context.player().level(), context.player(), NyctoAPI.getPowers(context.player()).get(payload.powerIndex()));
		}
	}
}
