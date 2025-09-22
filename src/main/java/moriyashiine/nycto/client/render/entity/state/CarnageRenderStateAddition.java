/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.state;

import moriyashiine.nycto.common.component.entity.power.vampire.CarnageComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public interface CarnageRenderStateAddition {
	float nycto$getCarnageOpacity();

	void nycto$setCarnageOpacity(float opacity);

	static <E extends LivingEntity, S extends LivingEntityRenderState> void updateRenderState(E entity, S state) {
		if (state instanceof CarnageRenderStateAddition carnageState) {
			Entity realEntity = entity;
			if (!realEntity.isPlayer()) {
				for (AbstractClientPlayerEntity player : MinecraftClient.getInstance().world.getPlayers()) {
					if (entity == SLibUtils.getModelReplacement(player)) {
						realEntity = player;
					}
				}
			}
			CarnageComponent carnageComponent = ModEntityComponents.CARNAGE.get(realEntity);
			carnageState.nycto$setCarnageOpacity(carnageComponent.isActive() ? carnageComponent.getOverlayOpacity(0.5F) : 0);
		}
	}
}
