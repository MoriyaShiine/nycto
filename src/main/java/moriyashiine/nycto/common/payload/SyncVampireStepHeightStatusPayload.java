/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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
		PacketDistributor.sendToServer(new SyncVampireStepHeightStatusPayload(enabled));
	}

	public static void handle(SyncVampireStepHeightStatusPayload payload, IPayloadContext context) {
		ModEntityComponents.SYNCED_CONFIG_VALUES.get(context.player()).setVampireStepHeight(payload.enabled());
	}
}
