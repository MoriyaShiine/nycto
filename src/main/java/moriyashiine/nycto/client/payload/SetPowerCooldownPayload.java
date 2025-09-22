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
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record SetPowerCooldownPayload(Power power, int cooldown) implements CustomPayload {
	public static final Id<SetPowerCooldownPayload> ID = new Id<>(Nycto.id("set_power_cooldown"));
	public static final PacketCodec<PacketByteBuf, SetPowerCooldownPayload> CODEC = PacketCodec.tuple(
			Identifier.PACKET_CODEC, setPowerCooldownPayload -> NyctoRegistries.POWER.getId(setPowerCooldownPayload.power()),
			PacketCodecs.VAR_INT, SetPowerCooldownPayload::cooldown,
			(identifier, cooldown) -> new SetPowerCooldownPayload(NyctoRegistries.POWER.get(identifier), cooldown));

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(ServerPlayerEntity player, Power power, int cooldown) {
		ServerPlayNetworking.send(player, new SetPowerCooldownPayload(power, cooldown));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<SetPowerCooldownPayload> {
		@Override
		public void receive(SetPowerCooldownPayload payload, ClientPlayNetworking.Context context) {
			NyctoAPIImpl.setPowerCooldown(context.player(), payload.power(), payload.cooldown());
		}
	}
}
