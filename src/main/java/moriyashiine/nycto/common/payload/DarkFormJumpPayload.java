/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.vampire.DarkFormComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DarkFormJumpPayload() implements CustomPacketPayload {
	public static final Type<DarkFormJumpPayload> TYPE = new Type<>(Nycto.id("dark_form_jump"));
	public static final StreamCodec<FriendlyByteBuf, DarkFormJumpPayload> CODEC = StreamCodec.unit(new DarkFormJumpPayload());

	@Override
	public Type<DarkFormJumpPayload> type() {
		return TYPE;
	}

	public static void send() {
		PacketDistributor.sendToServer(new DarkFormJumpPayload());
	}

	public static void handle(DarkFormJumpPayload payload, IPayloadContext context) {
		DarkFormComponent darkFormComponent = ModEntityComponents.DARK_FORM.get(context.player());
		if (darkFormComponent.canJump()) {
			darkFormComponent.jump();
		}
	}
}
