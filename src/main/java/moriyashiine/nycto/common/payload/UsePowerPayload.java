/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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
		PacketDistributor.sendToServer(new UsePowerPayload(powerIndex));
	}

	public static void handle(UsePowerPayload payload, IPayloadContext context) {
		NyctoUtil.usePower(context.player().level(), context.player(), NyctoAPI.getPowers(context.player()).get(payload.powerIndex()));
	}
}
