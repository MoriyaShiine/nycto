/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.vampire;

import moriyashiine.nycto.common.entity.mob.VampireEntity;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.power.vampire.CarnagePower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.ai.goal.Goal;

public class CarnageGoal extends Goal {
	private final VampireEntity mob;

	public CarnageGoal(VampireEntity mob) {
		this.mob = mob;
	}

	@Override
	public boolean canStart() {
		return mob.canUsePower(ModPowers.CARNAGE) && mob.getTarget() != null && mob.getTarget().isAlive() && mob.getHealth() <= mob.getMaxHealth() * 0.3F;
	}

	@Override
	public void start() {
		mob.useAbility(ModPowers.CARNAGE);
		SLibUtils.playAnchoredSound(mob, ModSoundEvents.POWER_CARNAGE_USE);
		CarnagePower.activate(mob);
	}
}
