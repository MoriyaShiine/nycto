/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event;

import moriyashiine.nycto.common.init.ModComponentTypes;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;

public class ShowCapeEvent implements LivingEntityFeatureRenderEvents.AllowCapeRender {
	@Override
	public boolean allowCapeRender(PlayerEntityRenderState state) {
		return !state.equippedChestStack.getOrDefault(ModComponentTypes.SHOW_CAPE, false);
	}
}
