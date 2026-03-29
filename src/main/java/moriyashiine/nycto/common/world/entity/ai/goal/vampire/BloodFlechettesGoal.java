/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.vampire;

import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.world.entity.monster.Vampire;
import moriyashiine.nycto.common.world.power.vampire.BloodFlechettesPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.ai.goal.Goal;

public class BloodFlechettesGoal extends Goal {
	private final Vampire mob;

	public BloodFlechettesGoal(Vampire mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		return mob.canUsePower(ModPowers.BLOOD_FLECHETTES) && mob.getTarget() != null && mob.getTarget().isAlive() && mob.hasLineOfSight(mob.getTarget());
	}

	@Override
	public void start() {
		mob.useAbility(ModPowers.BLOOD_FLECHETTES);
		SLibUtils.playAnchoredSound(mob, ModSoundEvents.POWER_BLOOD_FLECHETTES_USE);
		mob.lookAt(EntityAnchorArgument.Anchor.EYES, mob.getTarget().getEyePosition());
		BloodFlechettesPower.spawnProjectiles(mob.level(), mob);
	}
}
