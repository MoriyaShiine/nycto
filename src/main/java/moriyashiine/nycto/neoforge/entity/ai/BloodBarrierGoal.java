/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.ai;

import moriyashiine.nycto.neoforge.entity.Vampire;
import net.minecraft.world.entity.ai.goal.Goal;

public final class BloodBarrierGoal extends Goal {
	private final Vampire vampire;

	public BloodBarrierGoal(Vampire vampire) {
		this.vampire = vampire;
	}

	@Override
	public boolean canUse() {
		return vampire.getTarget() != null && vampire.canUseBloodBarrier();
	}

	@Override
	public void start() {
		vampire.startBloodBarrier();
	}
}
