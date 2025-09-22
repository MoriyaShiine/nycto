/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.payload;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.vampire.BloodBarrierComponent;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;

public record AddBloodBarrierParticlesPayload(int entityId, int barrier) implements CustomPayload {
	public static final Id<AddBloodBarrierParticlesPayload> ID = new Id<>(Nycto.id("add_blood_barrier_particles"));
	public static final PacketCodec<PacketByteBuf, AddBloodBarrierParticlesPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, AddBloodBarrierParticlesPayload::entityId,
			PacketCodecs.VAR_INT, AddBloodBarrierParticlesPayload::barrier,
			AddBloodBarrierParticlesPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(ServerPlayerEntity player, Entity entity, int barrier) {
		ServerPlayNetworking.send(player, new AddBloodBarrierParticlesPayload(entity.getId(), barrier));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<AddBloodBarrierParticlesPayload> {
		@Override
		public void receive(AddBloodBarrierParticlesPayload payload, ClientPlayNetworking.Context context) {
			if (context.player().getWorld().getEntityById(payload.entityId()) instanceof LivingEntity entity && SLibClientUtils.shouldAddParticles(entity)) {
				for (int i = 0; i < 24; i++) {
					entity.getWorld().addParticleClient(ParticleTypes.SMOKE, entity.getParticleX(1), entity.getY() + BloodBarrierComponent.getHeightOffset(payload.barrier(), entity.getHeight()), entity.getParticleZ(1), entity.getRandom().nextGaussian() / 16, entity.getRandom().nextGaussian() / 32, entity.getRandom().nextGaussian() / 16);
				}
			}
		}
	}
}
