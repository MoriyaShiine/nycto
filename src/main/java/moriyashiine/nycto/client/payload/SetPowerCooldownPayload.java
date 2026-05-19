/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.NyctoAPIImpl;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record SetPowerCooldownPayload(Power power, int cooldown) implements CustomPacketPayload {
	public static final Type<SetPowerCooldownPayload> TYPE = new Type<>(Nycto.id("set_power_cooldown"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SetPowerCooldownPayload> CODEC = StreamCodec.composite(
			Power.STREAM_CODEC, SetPowerCooldownPayload::power,
			ByteBufCodecs.VAR_INT, SetPowerCooldownPayload::cooldown,
			SetPowerCooldownPayload::new
	);

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
