/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.vampire.DarkFormComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record DarkFormJumpPayload() implements CustomPayload {
	public static final Id<DarkFormJumpPayload> ID = new Id<>(Nycto.id("dark_form_jump"));
	public static final PacketCodec<PacketByteBuf, DarkFormJumpPayload> CODEC = PacketCodec.unit(new DarkFormJumpPayload());

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
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
