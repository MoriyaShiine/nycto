/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.strawberrylib.impl.client.sound.AnchoredSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PlayBloodrushSoundPayload(int entityId) implements CustomPacketPayload {
	public static final Type<PlayBloodrushSoundPayload> TYPE = new Type<>(Nycto.id("play_bloodrush_sound"));
	public static final StreamCodec<FriendlyByteBuf, PlayBloodrushSoundPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, PlayBloodrushSoundPayload::entityId,
			PlayBloodrushSoundPayload::new);

	@Override
	public Type<PlayBloodrushSoundPayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer player, Entity entity) {
		PacketDistributor.sendToPlayer(player, new PlayBloodrushSoundPayload(entity.getId()));
	}

	@SuppressWarnings("DataFlowIssue")
	public static void handle(PlayBloodrushSoundPayload payload, IPayloadContext context) {
		Entity entity = context.player().level().getEntity(payload.entityId());
		if (entity instanceof Player player) {
			Minecraft.getInstance().getSoundManager().play(new AnchoredSoundInstance(player, ModPowers.BLOODRUSH.getUseSound(player), currentEntity -> !ModEntityComponents.BLOODRUSH.get(currentEntity).isActive(false)));
		}
	}
}
