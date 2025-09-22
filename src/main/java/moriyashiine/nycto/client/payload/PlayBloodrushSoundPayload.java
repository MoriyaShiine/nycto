/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.strawberrylib.impl.client.sound.AnchoredSoundInstance;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public record PlayBloodrushSoundPayload(int entityId) implements CustomPayload {
	public static final Id<PlayBloodrushSoundPayload> ID = new Id<>(Nycto.id("play_bloodrush_sound"));
	public static final PacketCodec<PacketByteBuf, PlayBloodrushSoundPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, PlayBloodrushSoundPayload::entityId,
			PlayBloodrushSoundPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(ServerPlayerEntity player, Entity entity) {
		ServerPlayNetworking.send(player, new PlayBloodrushSoundPayload(entity.getId()));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<PlayBloodrushSoundPayload> {
		@SuppressWarnings("DataFlowIssue")
		@Override
		public void receive(PlayBloodrushSoundPayload payload, ClientPlayNetworking.Context context) {
			if (context.player().getWorld().getEntityById(payload.entityId()) instanceof PlayerEntity player) {
				MinecraftClient.getInstance().getSoundManager().play(new AnchoredSoundInstance(player, ModPowers.BLOODRUSH.getUseSound(player), currentEntity -> !ModEntityComponents.BLOODRUSH.get(currentEntity).isActive(false)));
			}
		}
	}
}
