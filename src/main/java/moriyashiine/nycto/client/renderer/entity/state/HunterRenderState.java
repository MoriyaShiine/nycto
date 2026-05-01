/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.state;

import moriyashiine.nycto.api.world.entity.huntertype.HunterType;
import moriyashiine.nycto.common.init.ModHunterTypes;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;

public class HunterRenderState extends IllagerRenderState {
	public HunterType hunterType = ModHunterTypes.VAMPIRE;
}
