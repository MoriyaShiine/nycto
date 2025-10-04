/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.vampire;

import moriyashiine.nycto.common.entity.mob.VampireEntity;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.power.vampire.BatSwarmPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.ai.goal.Goal;

public class BatSwarmGoal extends Goal {
	private final VampireEntity mob;

	public BatSwarmGoal(VampireEntity mob) {
		this.mob = mob;
	}

	@Override
	public boolean canStart() {
		return mob.canUsePower(ModPowers.BAT_SWARM) && mob.getTarget() != null && mob.getTarget().isAlive() && mob.getHealth() <= mob.getMaxHealth() / 2;
	}

	@Override
	public void start() {
		mob.useAbility(ModPowers.BAT_SWARM);
		SLibUtils.playAnchoredSound(mob, ModSoundEvents.POWER_BAT_SWARM_USE);
		BatSwarmPower.spawnSwarm(mob.getEntityWorld(), mob);
	}
}
