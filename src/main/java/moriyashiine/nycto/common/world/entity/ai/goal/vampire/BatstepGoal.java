/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.vampire;

import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.nycto.common.world.entity.monster.Vampire;
import moriyashiine.nycto.common.world.power.vampire.BatstepPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.ai.goal.Goal;

public class BatstepGoal extends Goal {
	private final Vampire mob;

	public BatstepGoal(Vampire mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		return mob.canUsePower(NyctoPowers.BATSTEP) && mob.getTarget() != null && mob.distanceTo(mob.getTarget()) > 6 && mob.hasLineOfSight(mob.getTarget());
	}

	@Override
	public void start() {
		mob.useAbility(NyctoPowers.BATSTEP);
		SLibUtils.playAnchoredSound(mob, NyctoSoundEvents.BATSTEP_USE);
		mob.lookAt(EntityAnchorArgument.Anchor.EYES, mob.getTarget().getEyePosition());
		BatstepPower.teleport(mob.level(), mob);
	}
}
