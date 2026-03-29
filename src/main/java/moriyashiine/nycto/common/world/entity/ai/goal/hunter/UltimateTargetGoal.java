/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.hunter;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.entity.monster.Hunter;
import net.minecraft.world.entity.ai.goal.Goal;

public class UltimateTargetGoal extends Goal {
	private final Hunter mob;

	public UltimateTargetGoal(Hunter mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		if (!NyctoUtil.isSurvivalNullable(mob.getUltimateTarget()) || NyctoAPI.hasRespawnLeniency(mob.getUltimateTarget())) {
			return false;
		}
		return !NyctoUtil.isSurvivalNullable(mob.getLastHurtByMob());
	}

	@Override
	public void tick() {
		mob.setTarget(mob.getUltimateTarget());
	}
}
