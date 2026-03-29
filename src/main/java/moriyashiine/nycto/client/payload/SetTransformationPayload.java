/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.transformation.Transformation;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.NyctoAPIImpl;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record SetTransformationPayload(int entityId, Transformation transformation) implements CustomPacketPayload {
	public static final Type<SetTransformationPayload> TYPE = new Type<>(Nycto.id("set_transformation"));
	public static final StreamCodec<FriendlyByteBuf, SetTransformationPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SetTransformationPayload::entityId,
			Identifier.STREAM_CODEC, payload -> NyctoRegistries.TRANSFORMATION.getKey(payload.transformation()),
			(entityId, identifier) -> new SetTransformationPayload(entityId, NyctoRegistries.TRANSFORMATION.getValue(identifier)));

	@Override
	public Type<SetTransformationPayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer player, Entity entity, Transformation transformation) {
		ServerPlayNetworking.send(player, new SetTransformationPayload(entity.getId(), transformation));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<SetTransformationPayload> {
		@Override
		public void receive(SetTransformationPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().level().getEntity(payload.entityId());
			if (entity instanceof Player player) {
				NyctoAPIImpl.setTransformation(player, payload.transformation());
			}
		}
	}
}
