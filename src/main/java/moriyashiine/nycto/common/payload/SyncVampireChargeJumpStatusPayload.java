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

public record SyncVampireChargeJumpStatusPayload(boolean enabled) implements CustomPayload {
	public static final Id<SyncVampireChargeJumpStatusPayload> ID = new Id<>(Nycto.id("sync_vampire_charge_jump_status"));
	public static final PacketCodec<PacketByteBuf, SyncVampireChargeJumpStatusPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.BOOLEAN, SyncVampireChargeJumpStatusPayload::enabled,
			SyncVampireChargeJumpStatusPayload::new
	);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(boolean enabled) {
		ClientPlayNetworking.send(new SyncVampireChargeJumpStatusPayload(enabled));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SyncVampireChargeJumpStatusPayload> {
		@Override
		public void receive(SyncVampireChargeJumpStatusPayload payload, ServerPlayNetworking.Context context) {
			ModEntityComponents.SYNCED_CONFIG_VALUES.get(context.player()).setVampireChargeJump(payload.enabled());
		}
	}
}
