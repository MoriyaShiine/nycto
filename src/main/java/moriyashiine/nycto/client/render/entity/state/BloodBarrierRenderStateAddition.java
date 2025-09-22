/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.state;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public interface BloodBarrierRenderStateAddition {
	int nycto$getBloodBarriers();

	void nycto$setBloodBarriers(int barriers);

	static <E extends LivingEntity, S extends LivingEntityRenderState> void updateRenderState(E entity, S state) {
		if (state instanceof BloodBarrierRenderStateAddition bloodBarrierState) {
			Entity realEntity = entity;
			if (!realEntity.isPlayer()) {
				for (AbstractClientPlayerEntity player : MinecraftClient.getInstance().world.getPlayers()) {
					if (entity == SLibUtils.getModelReplacement(player)) {
						realEntity = player;
					}
				}
			}
			bloodBarrierState.nycto$setBloodBarriers(ModEntityComponents.BLOOD_BARRIER.get(realEntity).getBarriers());
		}
	}
}
