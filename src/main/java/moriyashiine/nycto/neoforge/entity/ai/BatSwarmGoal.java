/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.ai;

import moriyashiine.nycto.neoforge.entity.Vampire;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import net.minecraft.world.entity.ai.goal.Goal;

public final class BatSwarmGoal extends Goal {
	private final Vampire vampire;

	public BatSwarmGoal(Vampire vampire) {
		this.vampire = vampire;
	}

	@Override
	public boolean canUse() {
		return vampire.canUsePower(NyctoPowers.BAT_SWARM) && vampire.getTarget() != null && vampire.getHealth() <= vampire.getMaxHealth() / 2;
	}

	@Override
	public void start() {
		vampire.startBatSwarm();
	}
}
