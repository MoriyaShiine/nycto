/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.vampire.BloodBarrierComponent;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public record AddBloodBarrierParticlesPayload(int entityId, int barrier) implements CustomPacketPayload {
	public static final Type<AddBloodBarrierParticlesPayload> TYPE = new Type<>(Nycto.id("add_blood_barrier_particles"));
	public static final StreamCodec<FriendlyByteBuf, AddBloodBarrierParticlesPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, AddBloodBarrierParticlesPayload::entityId,
			ByteBufCodecs.VAR_INT, AddBloodBarrierParticlesPayload::barrier,
			AddBloodBarrierParticlesPayload::new);

	@Override
	public Type<AddBloodBarrierParticlesPayload> type() {
		return TYPE;
	}

	public static void send(ServerPlayer player, Entity entity, int barrier) {
		ServerPlayNetworking.send(player, new AddBloodBarrierParticlesPayload(entity.getId(), barrier));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<AddBloodBarrierParticlesPayload> {
		@Override
		public void receive(AddBloodBarrierParticlesPayload payload, ClientPlayNetworking.Context context) {
			Entity entity = context.player().level().getEntity(payload.entityId());
			if (entity instanceof LivingEntity living && SLibClientUtils.shouldAddParticles(living)) {
				for (int i = 0; i < 24; i++) {
					living.level().addParticle(ParticleTypes.SMOKE, living.getRandomX(1), living.getY() + BloodBarrierComponent.getHeightOffset(payload.barrier(), living.getBbHeight()), living.getRandomZ(1), living.getRandom().nextGaussian() / 16, living.getRandom().nextGaussian() / 32, living.getRandom().nextGaussian() / 16);
				}
			}
		}
	}
}
