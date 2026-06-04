/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.hunter.goal;

import moriyashiine.nycto.neoforge.entity.hunter.HunterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public final class PathToContractPosGoal extends Goal {
	private final HunterEntity hunter;

	public PathToContractPosGoal(HunterEntity hunter) {
		this.hunter = hunter;
	}

	@Override
	public boolean canUse() {
		return hunter.getContractPos() != null && hunter.getLastHurtByMob() == null;
	}

	@Override
	public void tick() {
		BlockPos contractPos = hunter.getContractPos();
		if (contractPos == null) {
			return;
		}
		if (contractPos.closerToCenterThan(hunter.position(), 5)) {
			hunter.getNavigation().stop();
			hunter.setContractPos(null);
		} else if (hunter.getNavigation().isDone()) {
			hunter.getNavigation().moveTo(contractPos.getX(), contractPos.getY(), contractPos.getZ(), 1);
		}
	}
}
