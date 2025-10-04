/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.state;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.util.Identifier;

public class VampiricThrallRenderState {
	public static final RenderStateDataKey<VampiricThrallRenderState> KEY = RenderStateDataKey.create(() -> "vampiric thrall");

	public Identifier thrallTexture = null;
}
