/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.state;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

public class MistFormRenderState {
	public static final RenderStateDataKey<MistFormRenderState> KEY = RenderStateDataKey.create(() -> "mist form");

	public boolean mistForm = false;
}
