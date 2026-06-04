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
		PacketDistributor.sendToServer(new SyncVampireChargeJumpStatusPayload(enabled));
	}

	public static void handle(SyncVampireChargeJumpStatusPayload payload, IPayloadContext context) {
		ModEntityComponents.SYNCED_CONFIG_VALUES.get(context.player()).setVampireChargeJump(payload.enabled());
	}
}
