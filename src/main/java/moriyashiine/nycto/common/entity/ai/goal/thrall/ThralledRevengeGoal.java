/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.thrall;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class ThralledRevengeGoal extends RevengeGoal {
	public ThralledRevengeGoal(PathAwareEntity mob, Class<?>... noRevengeTypes) {
		super(mob, noRevengeTypes);
	}

	@Override
	public boolean canStart() {
		return ModEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner() && super.canStart();
	}
}
