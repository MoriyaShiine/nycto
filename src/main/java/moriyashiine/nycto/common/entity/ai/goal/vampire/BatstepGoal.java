/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.vampire;

import moriyashiine.nycto.common.entity.mob.VampireEntity;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.power.vampire.BatstepPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.ai.goal.Goal;

public class BatstepGoal extends Goal {
	private final VampireEntity mob;

	public BatstepGoal(VampireEntity mob) {
		this.mob = mob;
	}

	@Override
	public boolean canStart() {
		return mob.canUsePower(ModPowers.BATSTEP) && mob.getTarget() != null && mob.getTarget().isAlive() && mob.distanceTo(mob.getTarget()) > 6 && mob.canSee(mob.getTarget());
	}

	@Override
	public void start() {
		mob.useAbility(ModPowers.BATSTEP);
		SLibUtils.playAnchoredSound(mob, ModSoundEvents.POWER_BATSTEP_USE);
		mob.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, mob.getTarget().getEyePos());
		BatstepPower.teleport(mob.getEntityWorld(), mob);
	}
}
