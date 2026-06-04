/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.ai;

import moriyashiine.nycto.neoforge.entity.Vampire;
import net.minecraft.world.entity.ai.goal.Goal;

public final class CarnageGoal extends Goal {
	private final Vampire vampire;

	public CarnageGoal(Vampire vampire) {
		this.vampire = vampire;
	}

	@Override
	public boolean canUse() {
		return vampire.getTarget() != null && vampire.getHealth() <= vampire.getMaxHealth() * 0.3F && vampire.canUseCarnage();
	}

	@Override
	public void start() {
		vampire.startCarnage();
	}
}
