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

public record SyncVampireStepHeightStatusPayload(boolean enabled) implements CustomPacketPayload {
	public static final Type<SyncVampireStepHeightStatusPayload> TYPE = new Type<>(Nycto.id("sync_vampire_step_height_status"));
	public static final StreamCodec<FriendlyByteBuf, SyncVampireStepHeightStatusPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL, SyncVampireStepHeightStatusPayload::enabled,
			SyncVampireStepHeightStatusPayload::new
	);

	@Override
	public Type<SyncVampireStepHeightStatusPayload> type() {
		return TYPE;
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
