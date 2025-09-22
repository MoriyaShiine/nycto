/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.thrall;

import moriyashiine.nycto.common.entity.ai.goal.vampire.VampireEscapeSunlightGoal;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.mob.PathAwareEntity;

public class ThralledEscapeSunlightGoal extends VampireEscapeSunlightGoal {
	public ThralledEscapeSunlightGoal(PathAwareEntity mob, double speed) {
		super(mob, speed);
	}

	@Override
	public boolean canStart() {
		return ModEntityComponents.VAMPIRIC_THRALL.get(mob).isThralled() && super.canStart();
	}
}
