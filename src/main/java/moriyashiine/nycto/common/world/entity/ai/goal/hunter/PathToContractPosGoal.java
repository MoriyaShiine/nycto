/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.hunter;

import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.entity.monster.Hunter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public class PathToContractPosGoal extends Goal {
	private final Hunter mob;

	public PathToContractPosGoal(Hunter mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		return mob.getContractPos() != null && !NyctoUtil.isSurvivalNullable(mob.getLastHurtByMob());
	}

	@Override
	public void tick() {
		BlockPos contractPos = mob.getContractPos();
		if (contractPos != null) {
			if (contractPos.closerToCenterThan(mob.position(), 5)) {
				mob.getNavigation().stop();
				mob.setContractPos(null);
			} else if (mob.getNavigation().isDone()) {
				mob.getNavigation().moveTo(contractPos.getX(), contractPos.getY(), contractPos.getZ(), 1);
			}
		}
	}
}
