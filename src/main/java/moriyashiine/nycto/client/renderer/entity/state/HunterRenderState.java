/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.state;

import moriyashiine.nycto.common.world.entity.monster.Hunter;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;

public class HunterRenderState extends IllagerRenderState {
	public Hunter.HunterType hunterType = Hunter.HunterType.VAMPIRE;
}
