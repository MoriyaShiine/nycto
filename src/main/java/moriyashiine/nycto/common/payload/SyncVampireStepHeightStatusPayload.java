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

public record SyncVampireStepHeightStatusPayload(boolean enabled) implements CustomPayload {
	public static final Id<SyncVampireStepHeightStatusPayload> ID = new Id<>(Nycto.id("sync_vampire_step_height_status"));
	public static final PacketCodec<PacketByteBuf, SyncVampireStepHeightStatusPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.BOOLEAN, SyncVampireStepHeightStatusPayload::enabled,
			SyncVampireStepHeightStatusPayload::new
	);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(boolean enabled) {
		ClientPlayNetworking.send(new SyncVampireStepHeightStatusPayload(enabled));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SyncVampireStepHeightStatusPayload> {
		@Override
		public void receive(SyncVampireStepHeightStatusPayload payload, ServerPlayNetworking.Context context) {
			ModEntityComponents.SYNCED_CONFIG_VALUES.get(context.player()).setVampireStepHeight(payload.enabled());
		}
	}
}
