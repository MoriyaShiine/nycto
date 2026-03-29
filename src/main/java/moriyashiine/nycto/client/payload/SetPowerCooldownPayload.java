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

public record SetPowerCooldownPayload(Power power, int cooldown) implements CustomPacketPayload {
	public static final Type<SetPowerCooldownPayload> TYPE = new Type<>(Nycto.id("set_power_cooldown"));
	public static final StreamCodec<FriendlyByteBuf, SetPowerCooldownPayload> CODEC = StreamCodec.composite(
			Identifier.STREAM_CODEC, payload -> NyctoRegistries.POWER.getKey(payload.power()),
			ByteBufCodecs.VAR_INT, SetPowerCooldownPayload::cooldown,
			(identifier, cooldown) -> new SetPowerCooldownPayload(NyctoRegistries.POWER.getValue(identifier), cooldown));

	@Override
	public Type<SetPowerCooldownPayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer player, Power power, int cooldown) {
		ServerPlayNetworking.send(player, new SetPowerCooldownPayload(power, cooldown));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<SetPowerCooldownPayload> {
		@Override
		public void receive(SetPowerCooldownPayload payload, ClientPlayNetworking.Context context) {
			NyctoAPIImpl.setPowerCooldown(context.player(), payload.power(), payload.cooldown());
		}
	}
}
