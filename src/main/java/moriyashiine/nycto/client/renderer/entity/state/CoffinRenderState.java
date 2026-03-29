/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.state;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

public class CoffinRenderState {
	public static final RenderStateDataKey<CoffinRenderState> KEY = RenderStateDataKey.create(() -> "coffin");

	public boolean sleepingInCoffin = false;
}
