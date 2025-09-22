/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.state;

import moriyashiine.nycto.common.entity.mob.HunterEntity;
import net.minecraft.client.render.entity.state.IllagerEntityRenderState;

public class HunterEntityRenderState extends IllagerEntityRenderState {
	public HunterEntity.HunterType hunterType = HunterEntity.HunterType.VAMPIRE;
}
