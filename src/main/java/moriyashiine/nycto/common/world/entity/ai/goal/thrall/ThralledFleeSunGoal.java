/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.thrall;

import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.world.entity.ai.goal.vampire.VampireFleeSunGoal;
import net.minecraft.world.entity.PathfinderMob;

public class ThralledFleeSunGoal extends VampireFleeSunGoal {
	public ThralledFleeSunGoal(PathfinderMob mob, double speedModifier) {
		super(mob, speedModifier);
	}

	@Override
	public boolean canUse() {
		return NyctoEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner() && super.canUse();
	}
}
