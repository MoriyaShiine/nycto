/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.vampire;

import moriyashiine.nycto.common.entity.mob.VampireEntity;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.power.vampire.BloodFlechettesPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.ai.goal.Goal;

public class BloodFlechettesGoal extends Goal {
	private final VampireEntity mob;

	public BloodFlechettesGoal(VampireEntity mob) {
		this.mob = mob;
	}

	@Override
	public boolean canStart() {
		return mob.canUsePower(ModPowers.BLOOD_FLECHETTES) && mob.getTarget() != null && mob.getTarget().isAlive() && mob.canSee(mob.getTarget());
	}

	@Override
	public void start() {
		mob.useAbility(ModPowers.BLOOD_FLECHETTES);
		SLibUtils.playAnchoredSound(mob, ModSoundEvents.POWER_BLOOD_FLECHETTES_USE);
		mob.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, mob.getTarget().getEyePos());
		BloodFlechettesPower.spawnProjectiles(mob.getEntityWorld(), mob);
	}
}
