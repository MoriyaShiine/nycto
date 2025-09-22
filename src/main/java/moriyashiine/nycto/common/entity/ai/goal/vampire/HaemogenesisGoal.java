/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.vampire;

import moriyashiine.nycto.common.entity.mob.VampireEntity;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.power.vampire.HaemogenesisPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.ai.goal.Goal;

public class HaemogenesisGoal extends Goal {
	private final VampireEntity mob;

	public HaemogenesisGoal(VampireEntity mob) {
		this.mob = mob;
	}

	@Override
	public boolean canStart() {
		return mob.canUsePower(ModPowers.HAEMOGENESIS) && mob.getHealth() <= mob.getMaxHealth() * 0.7F;
	}

	@Override
	public void start() {
		mob.useAbility(ModPowers.HAEMOGENESIS);
		SLibUtils.playAnchoredSound(mob, ModSoundEvents.POWER_HAEMOGENESIS_USE);
		HaemogenesisPower.startHealing(mob);
	}
}
