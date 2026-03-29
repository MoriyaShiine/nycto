/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SyncPowerIndexPayload(int index) implements CustomPacketPayload {
	public static final Type<SyncPowerIndexPayload> TYPE = new Type<>(Nycto.id("sync_power_index"));
	public static final StreamCodec<FriendlyByteBuf, SyncPowerIndexPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SyncPowerIndexPayload::index,
			SyncPowerIndexPayload::new);

	@Override
	public Type<SyncPowerIndexPayload> type() {
		return TYPE;
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
