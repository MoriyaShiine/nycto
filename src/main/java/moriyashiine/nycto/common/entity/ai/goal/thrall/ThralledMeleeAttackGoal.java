/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.thrall;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class ThralledMeleeAttackGoal extends MeleeAttackGoal {
	public ThralledMeleeAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
		super(mob, speed, pauseWhenMobIdle);
	}

	@Override
	public boolean canStart() {
		return ModEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner() && super.canStart();
	}
}
