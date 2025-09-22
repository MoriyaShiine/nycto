/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.vampire;

import moriyashiine.nycto.common.entity.mob.VampireEntity;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.power.vampire.BloodBarrierPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.ai.goal.Goal;

public class BloodBarrierGoal extends Goal {
	private final VampireEntity mob;

	public BloodBarrierGoal(VampireEntity mob) {
		this.mob = mob;
	}

	@Override
	public boolean canStart() {
		return mob.canUsePower(ModPowers.BLOOD_BARRIER) && mob.getTarget() != null && mob.getTarget().isAlive();
	}

	@Override
	public void start() {
		mob.useAbility(ModPowers.BLOOD_BARRIER);
		SLibUtils.playAnchoredSound(mob, ModSoundEvents.POWER_BLOOD_BARRIER_USE);
		BloodBarrierPower.activate(mob);
	}
}
