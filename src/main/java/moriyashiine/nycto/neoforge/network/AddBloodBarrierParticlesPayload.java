/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AddBloodBarrierParticlesPayload(int entityId, int barrier) implements CustomPacketPayload {
	public static final Type<AddBloodBarrierParticlesPayload> TYPE = new Type<>(NyctoNeoForge.id("add_blood_barrier_particles"));
	public static final StreamCodec<FriendlyByteBuf, AddBloodBarrierParticlesPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, AddBloodBarrierParticlesPayload::entityId,
			ByteBufCodecs.VAR_INT, AddBloodBarrierParticlesPayload::barrier,
			AddBloodBarrierParticlesPayload::new);

	@Override
	public Type<AddBloodBarrierParticlesPayload> type() {
		return TYPE;
	}

	public static void send(Entity entity, int barrier) {
		if (!entity.level().isClientSide()) {
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new AddBloodBarrierParticlesPayload(entity.getId(), barrier));
		}
	}

	public static void handle(AddBloodBarrierParticlesPayload payload, IPayloadContext context) {
		Entity entity = context.player().level().getEntity(payload.entityId());
		if (entity instanceof LivingEntity living && !living.isInvisible()) {
			for (int i = 0; i < 24; i++) {
				living.level().addParticle(ParticleTypes.SMOKE,
						living.getRandomX(1),
						living.getY() + heightOffset(payload.barrier(), living.getBbHeight()),
						living.getRandomZ(1),
						living.getRandom().nextGaussian() / 16,
						living.getRandom().nextGaussian() / 32,
						living.getRandom().nextGaussian() / 16);
			}
		}
	}

	private static float heightOffset(int barrier, float height) {
		return switch (barrier) {
			case 0 -> height * 0.5F;
			case 1 -> height * 0.2F;
			default -> height * 0.8F;
		};
	}
}
