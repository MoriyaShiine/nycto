/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.vampire;

import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
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
		return mob.canUsePower(ModPowers.BATSTEP) && mob.getTarget() != null && mob.getTarget().isAlive() && mob.distanceTo(mob.getTarget()) > 6 && mob.hasLineOfSight(mob.getTarget());
	}

	@Override
	public void start() {
		mob.useAbility(ModPowers.BATSTEP);
		SLibUtils.playAnchoredSound(mob, ModSoundEvents.POWER_BATSTEP_USE);
		mob.lookAt(EntityAnchorArgument.Anchor.EYES, mob.getTarget().getEyePosition());
		BatstepPower.teleport(mob.level(), mob);
	}
}
