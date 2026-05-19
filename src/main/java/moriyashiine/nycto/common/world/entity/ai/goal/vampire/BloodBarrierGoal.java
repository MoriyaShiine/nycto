/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.vampire;

import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.nycto.common.world.entity.monster.Vampire;
import moriyashiine.nycto.common.world.power.vampire.BloodBarrierPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.world.entity.ai.goal.Goal;

public class BloodBarrierGoal extends Goal {
	private final Vampire mob;

	public BloodBarrierGoal(Vampire mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		return mob.canUsePower(NyctoPowers.BLOOD_BARRIER) && mob.getTarget() != null;
	}

	@Override
	public void start() {
		mob.useAbility(NyctoPowers.BLOOD_BARRIER);
		SLibUtils.playAnchoredSound(mob, NyctoSoundEvents.BLOOD_BARRIER_USE);
		BloodBarrierPower.activate(mob);
	}
}
