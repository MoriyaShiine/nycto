/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.power.Power;
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

public record ModifyPowerPayload(int entityId, Power power, boolean add) implements CustomPayload {
	public static final Id<ModifyPowerPayload> ID = new Id<>(Nycto.id("modify_power"));
	public static final PacketCodec<PacketByteBuf, ModifyPowerPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, ModifyPowerPayload::entityId,
			Identifier.PACKET_CODEC, modifyPowerPayload -> NyctoRegistries.POWER.getId(modifyPowerPayload.power()),
			PacketCodecs.BOOLEAN, ModifyPowerPayload::add,
			(entityId, identifier, add) -> new ModifyPowerPayload(entityId, NyctoRegistries.POWER.get(identifier), add));

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(ServerPlayerEntity player, Entity entity, Power power, boolean add) {
		ServerPlayNetworking.send(player, new ModifyPowerPayload(entity.getId(), power, add));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ModifyPowerPayload> {
		@Override
		public void receive(ModifyPowerPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().getWorld().getEntityById(payload.entityId());
			if (entity instanceof PlayerEntity player) {
				if (payload.add()) {
					NyctoAPIImpl.addPower(player, payload.power());
				} else {
					NyctoAPIImpl.removePower(player, payload.power());
				}
			}
		}
	}
}
