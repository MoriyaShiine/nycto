/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event;

import moriyashiine.nycto.common.init.ModComponentTypes;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

public class ShowCapeEvent implements LivingEntityFeatureRenderEvents.AllowCapeRender {
	@Override
	public boolean allowCapeRender(AvatarRenderState state) {
		return !state.chestEquipment.getOrDefault(ModComponentTypes.SHOW_CAPE, false);
	}
}
