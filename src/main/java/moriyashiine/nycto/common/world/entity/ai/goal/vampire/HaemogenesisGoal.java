/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.vampire;

import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.world.entity.monster.Vampire;
import moriyashiine.nycto.common.world.power.vampire.HaemogenesisPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.world.entity.ai.goal.Goal;

public class HaemogenesisGoal extends Goal {
	private final Vampire mob;

	public HaemogenesisGoal(Vampire mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		return mob.canUsePower(ModPowers.HAEMOGENESIS) && mob.getHealth() <= mob.getMaxHealth() * 0.7F;
	}

	@Override
	public void start() {
		mob.useAbility(ModPowers.HAEMOGENESIS);
		SLibUtils.playAnchoredSound(mob, ModSoundEvents.POWER_HAEMOGENESIS_USE);
		HaemogenesisPower.startHealing(mob);
	}
}
