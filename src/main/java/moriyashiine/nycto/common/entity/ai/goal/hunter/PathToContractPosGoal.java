/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.hunter;

import moriyashiine.nycto.common.entity.mob.HunterEntity;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PathToContractPosGoal extends Goal {
	private final HunterEntity mob;

	public PathToContractPosGoal(HunterEntity mob) {
		this.mob = mob;
	}

	@Override
	public boolean canStart() {
		return mob.getContractPos() != null && !NyctoUtil.isTargetable(mob.getAttacker());
	}

	@Override
	public void tick() {
		@Nullable BlockPos contractPos = mob.getContractPos();
		if (contractPos != null) {
			if (contractPos.isWithinDistance(mob.getEntityPos(), 5)) {
				mob.getNavigation().stop();
				mob.setContractPos(null);
			} else if (mob.getNavigation().isIdle()) {
				mob.getNavigation().startMovingTo(contractPos.getX(), contractPos.getY(), contractPos.getZ(), 1);
			}
		}
	}
}
