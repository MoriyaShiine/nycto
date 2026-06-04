/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncBloodrushPayload(int entityId, int ticks) implements CustomPacketPayload {
	public static final Type<SyncBloodrushPayload> TYPE = new Type<>(NyctoNeoForge.id("sync_bloodrush"));
	public static final StreamCodec<FriendlyByteBuf, SyncBloodrushPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, SyncBloodrushPayload::entityId,
			ByteBufCodecs.VAR_INT, SyncBloodrushPayload::ticks,
			SyncBloodrushPayload::new);

	@Override
	public Type<SyncBloodrushPayload> type() {
		return TYPE;
	}

	public static void send(Entity entity, int ticks) {
		if (!entity.level().isClientSide()) {
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncBloodrushPayload(entity.getId(), ticks));
		}
	}

	public static void handle(SyncBloodrushPayload payload, IPayloadContext context) {
		Entity entity = context.player().level().getEntity(payload.entityId());
		if (entity != null) {
			BloodrushClientState.setActive(entity, payload.ticks());
			if (payload.ticks() > 0) {
				playClientSound(entity);
			}
		}
	}

	private static void playClientSound(Entity entity) {
		if (!FMLEnvironment.dist.isClient()) {
			return;
		}
		try {
			Class<?> soundClass = Class.forName("moriyashiine.nycto.neoforge.client.sound.BloodrushClientSound");
			soundClass.getMethod("play", Entity.class).invoke(null, entity);
		} catch (ReflectiveOperationException ignored) {
		}
	}
}
