/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.power.Power;
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

public record ModifyPowerPayload(int entityId, Power power, boolean add) implements CustomPacketPayload {
	public static final Type<ModifyPowerPayload> TYPE = new Type<>(Nycto.id("modify_power"));
	public static final StreamCodec<FriendlyByteBuf, ModifyPowerPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, ModifyPowerPayload::entityId,
			Identifier.STREAM_CODEC, payload -> NyctoRegistries.POWER.getKey(payload.power()),
			ByteBufCodecs.BOOL, ModifyPowerPayload::add,
			(entityId, identifier, add) -> new ModifyPowerPayload(entityId, NyctoRegistries.POWER.getValue(identifier), add));

	@Override
	public Type<ModifyPowerPayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer player, Entity entity, Power power, boolean add) {
		ServerPlayNetworking.send(player, new ModifyPowerPayload(entity.getId(), power, add));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ModifyPowerPayload> {
		@Override
		public void receive(ModifyPowerPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().level().getEntity(payload.entityId());
			if (entity instanceof Player player) {
				if (payload.add()) {
					NyctoAPIImpl.addPower(player, payload.power());
				} else {
					NyctoAPIImpl.removePower(player, payload.power());
				}
			}
		}
	}
}
