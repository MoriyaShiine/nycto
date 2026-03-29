/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.vampire;

import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.world.entity.monster.Vampire;
import moriyashiine.nycto.common.world.power.vampire.CarnagePower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.world.entity.ai.goal.Goal;

public class CarnageGoal extends Goal {
	private final Vampire mob;

	public CarnageGoal(Vampire mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		return mob.canUsePower(ModPowers.CARNAGE) && mob.getTarget() != null && mob.getTarget().isAlive() && mob.getHealth() <= mob.getMaxHealth() * 0.3F;
	}

	@Override
	public void start() {
		mob.useAbility(ModPowers.CARNAGE);
		SLibUtils.playAnchoredSound(mob, ModSoundEvents.POWER_CARNAGE_USE);
		CarnagePower.activate(mob);
	}
}
