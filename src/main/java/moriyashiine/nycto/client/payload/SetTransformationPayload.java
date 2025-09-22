/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.transformation.Transformation;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.NyctoAPIImpl;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record SetTransformationPayload(int entityId, Transformation transformation) implements CustomPayload {
	public static final Id<SetTransformationPayload> ID = new Id<>(Nycto.id("set_transformation"));
	public static final PacketCodec<PacketByteBuf, SetTransformationPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, SetTransformationPayload::entityId,
			Identifier.PACKET_CODEC, setTransformationPayload -> NyctoRegistries.TRANSFORMATION.getId(setTransformationPayload.transformation()),
			(entityId, identifier) -> new SetTransformationPayload(entityId, NyctoRegistries.TRANSFORMATION.get(identifier)));

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(ServerPlayerEntity player, Entity entity, Transformation transformation) {
		ServerPlayNetworking.send(player, new SetTransformationPayload(entity.getId(), transformation));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<SetTransformationPayload> {
		@Override
		public void receive(SetTransformationPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().getWorld().getEntityById(payload.entityId());
			if (entity instanceof PlayerEntity player) {
				NyctoAPIImpl.setTransformation(player, payload.transformation());
			}
		}
	}
}
