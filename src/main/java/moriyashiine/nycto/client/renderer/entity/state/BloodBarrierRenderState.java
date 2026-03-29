/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.state;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class BloodBarrierRenderState {
	public static final RenderStateDataKey<BloodBarrierRenderState> KEY = RenderStateDataKey.create(() -> "blood barrier");

	public int bloodBarriers = 0;

	public static <E extends LivingEntity, S extends LivingEntityRenderState> void extractRenderState(E entity, S state) {
		BloodBarrierRenderState bloodBarrierRenderState = new BloodBarrierRenderState();
		Entity realEntity = entity;
		if (entity instanceof Player) {
			for (AbstractClientPlayer player : Minecraft.getInstance().level.players()) {
				if (entity == SLibUtils.getModelReplacement(player)) {
					realEntity = player;
				}
			}
		}
		bloodBarrierRenderState.bloodBarriers = ModEntityComponents.BLOOD_BARRIER.get(realEntity).getBarriers();
		state.setData(KEY, bloodBarrierRenderState);
	}
}
