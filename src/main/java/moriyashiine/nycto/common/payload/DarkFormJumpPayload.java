/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.vampire.DarkFormComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record DarkFormJumpPayload() implements CustomPacketPayload {
	public static final Type<DarkFormJumpPayload> TYPE = new Type<>(Nycto.id("dark_form_jump"));
	public static final StreamCodec<FriendlyByteBuf, DarkFormJumpPayload> CODEC = StreamCodec.unit(new DarkFormJumpPayload());

	@Override
	public Type<DarkFormJumpPayload> type() {
		return TYPE;
	}

	public static void send() {
		ClientPlayNetworking.send(new DarkFormJumpPayload());
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<DarkFormJumpPayload> {
		@Override
		public void receive(DarkFormJumpPayload payload, ServerPlayNetworking.Context context) {
			DarkFormComponent darkFormComponent = ModEntityComponents.DARK_FORM.get(context.player());
			if (darkFormComponent.canJump()) {
				darkFormComponent.jump();
			}
		}
	}
}
