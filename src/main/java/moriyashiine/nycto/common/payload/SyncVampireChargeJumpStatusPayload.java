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

public record SyncVampireChargeJumpStatusPayload(boolean enabled) implements CustomPacketPayload {
	public static final Type<SyncVampireChargeJumpStatusPayload> TYPE = new Type<>(Nycto.id("sync_vampire_charge_jump_status"));
	public static final StreamCodec<FriendlyByteBuf, SyncVampireChargeJumpStatusPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL, SyncVampireChargeJumpStatusPayload::enabled,
			SyncVampireChargeJumpStatusPayload::new
	);

	@Override
	public Type<SyncVampireChargeJumpStatusPayload> type() {
		return TYPE;
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
