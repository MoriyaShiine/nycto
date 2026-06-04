/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.ai;

import moriyashiine.nycto.neoforge.entity.Vampire;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import net.minecraft.world.entity.ai.goal.Goal;

public final class HaemogenesisGoal extends Goal {
	private final Vampire vampire;

	public HaemogenesisGoal(Vampire vampire) {
		this.vampire = vampire;
	}

	@Override
	public boolean canUse() {
		return vampire.canUsePower(NyctoPowers.HAEMOGENESIS) && vampire.getHealth() <= vampire.getMaxHealth() * 0.7F;
	}

	@Override
	public void start() {
		vampire.startHaemogenesis();
	}
}
