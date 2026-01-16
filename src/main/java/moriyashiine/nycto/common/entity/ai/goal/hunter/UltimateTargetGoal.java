/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.hunter;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.entity.mob.HunterEntity;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.ai.goal.Goal;

public class UltimateTargetGoal extends Goal {
	private final HunterEntity mob;

	public UltimateTargetGoal(HunterEntity mob) {
		this.mob = mob;
	}

	@Override
	public boolean canStart() {
		if (!NyctoUtil.isTargetable(mob.getUltimateTarget()) || NyctoAPI.hasRespawnLeniency(mob.getUltimateTarget())) {
			return false;
		}
		return !NyctoUtil.isTargetable(mob.getAttacker());
	}

	@Override
	public void tick() {
		mob.setTarget(mob.getUltimateTarget());
	}
}
