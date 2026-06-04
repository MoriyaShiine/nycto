/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.hunter.goal;

import moriyashiine.nycto.neoforge.entity.hunter.HunterEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

public final class UltimateTargetGoal extends Goal {
	private final HunterEntity hunter;

	public UltimateTargetGoal(HunterEntity hunter) {
		this.hunter = hunter;
	}

	@Override
	public boolean canUse() {
		Player target = hunter.getUltimateTarget();
		return target != null && target.isAlive() && !target.isCreative() && !target.isSpectator() && hunter.getLastHurtByMob() == null;
	}

	@Override
	public void tick() {
		Player target = hunter.getUltimateTarget();
		if (target != null) {
			hunter.setTarget(target);
		}
	}
}
