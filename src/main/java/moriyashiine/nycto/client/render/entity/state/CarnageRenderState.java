/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.state;

import moriyashiine.nycto.common.component.entity.power.vampire.CarnageComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class CarnageRenderState {
	public static final RenderStateDataKey<CarnageRenderState> KEY = RenderStateDataKey.create(() -> "carnage");

	public float carnageOpacity = 0;

	public static <E extends LivingEntity, S extends LivingEntityRenderState> void updateRenderState(E entity, S state) {
		CarnageRenderState carnageRenderState = new CarnageRenderState();
		Entity realEntity = entity;
		if (!realEntity.isPlayer()) {
			for (AbstractClientPlayerEntity player : MinecraftClient.getInstance().world.getPlayers()) {
				if (entity == SLibUtils.getModelReplacement(player)) {
					realEntity = player;
				}
			}
		}
		CarnageComponent carnageComponent = ModEntityComponents.CARNAGE.get(realEntity);
		carnageRenderState.carnageOpacity = carnageComponent.isActive() ? carnageComponent.getOverlayOpacity(0.5F) : 0;
		state.setData(KEY, carnageRenderState);
	}
}
