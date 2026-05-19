/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.vampire;

import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.nycto.common.world.entity.monster.Vampire;
import moriyashiine.nycto.common.world.power.vampire.BatSwarmPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.world.entity.ai.goal.Goal;

public class BatSwarmGoal extends Goal {
	private final Vampire mob;

	public BatSwarmGoal(Vampire mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		return mob.canUsePower(NyctoPowers.BAT_SWARM) && mob.getTarget() != null && mob.getHealth() <= mob.getMaxHealth() / 2;
	}

	@Override
	public void start() {
		mob.useAbility(NyctoPowers.BAT_SWARM);
		SLibUtils.playAnchoredSound(mob, NyctoSoundEvents.BAT_SWARM_USE);
		BatSwarmPower.spawnSwarm(mob.level(), mob);
	}
}
