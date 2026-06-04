/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.api.world.inventory.AltarMenu;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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
		PacketDistributor.sendToServer(new ApplyPowerFromAltarPayload(id));
	}

	public static void handle(ApplyPowerFromAltarPayload payload, IPayloadContext context) {
		AltarMenu.apply(context.player(), payload.id());
	}
}
