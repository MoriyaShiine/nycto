/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event;

import moriyashiine.nycto.common.init.NyctoDataComponents;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

public class ShowCapeClientEvent implements LivingEntityFeatureRenderEvents.AllowCapeRender {
	public static void init() {
		LivingEntityFeatureRenderEvents.ALLOW_CAPE_RENDER.register(new ShowCapeClientEvent());
	}

	@Override
	public boolean allowCapeRender(AvatarRenderState state) {
		return !state.chestEquipment.getOrDefault(NyctoDataComponents.SHOW_CAPE, false);
	}
}
