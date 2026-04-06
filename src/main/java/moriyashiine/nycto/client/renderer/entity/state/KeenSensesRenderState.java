/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.state;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.world.phys.Vec3;

public class KeenSensesRenderState {
	public static final RenderStateDataKey<KeenSensesRenderState> KEY = RenderStateDataKey.create(() -> "keen senses");

	public Vec3 position = null;
	public float healthPercentage = 0;
}
