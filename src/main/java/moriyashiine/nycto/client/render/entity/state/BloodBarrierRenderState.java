/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.state;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class BloodBarrierRenderState {
	public static final RenderStateDataKey<BloodBarrierRenderState> KEY = RenderStateDataKey.create(() -> "blood barrier");

	public int bloodBarriers = 0;

	public static <E extends LivingEntity, S extends LivingEntityRenderState> void updateRenderState(E entity, S state) {
		BloodBarrierRenderState bloodBarrierRenderState = new BloodBarrierRenderState();
		Entity realEntity = entity;
		if (!realEntity.isPlayer()) {
			for (AbstractClientPlayerEntity player : MinecraftClient.getInstance().world.getPlayers()) {
				if (entity == SLibUtils.getModelReplacement(player)) {
					realEntity = player;
				}
			}
		}
		bloodBarrierRenderState.bloodBarriers = ModEntityComponents.BLOOD_BARRIER.get(realEntity).getBarriers();
		state.setData(KEY, bloodBarrierRenderState);
	}
}
