/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.state;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

public class BloodrushRenderState {
	public static final RenderStateDataKey<BloodrushRenderState> KEY = RenderStateDataKey.create(() -> "bloodrush");

	public boolean usingBloodrush = false;
	public boolean usingBloodrushLenient = false;
}
