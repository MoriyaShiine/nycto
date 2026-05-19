/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.vampire;

import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
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
		return mob.canUsePower(NyctoPowers.HAEMOGENESIS) && mob.getHealth() <= mob.getMaxHealth() * 0.7F;
	}

	@Override
	public void start() {
		mob.useAbility(NyctoPowers.HAEMOGENESIS);
		SLibUtils.playAnchoredSound(mob, NyctoSoundEvents.HAEMOGENESIS_USE);
		HaemogenesisPower.startHealing(mob);
	}
}
