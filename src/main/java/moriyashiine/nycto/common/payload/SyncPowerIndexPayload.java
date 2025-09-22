/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record SyncPowerIndexPayload(int index) implements CustomPayload {
	public static final Id<SyncPowerIndexPayload> ID = new Id<>(Nycto.id("sync_power_index"));
	public static final PacketCodec<PacketByteBuf, SyncPowerIndexPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, SyncPowerIndexPayload::index,
			SyncPowerIndexPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(int index) {
		ClientPlayNetworking.send(new SyncPowerIndexPayload(index));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SyncPowerIndexPayload> {
		@Override
		public void receive(SyncPowerIndexPayload payload, ServerPlayNetworking.Context context) {
			ModEntityComponents.TRANSFORMATION.get(context.player()).setPowerIndex(payload.index());
		}
	}
}
