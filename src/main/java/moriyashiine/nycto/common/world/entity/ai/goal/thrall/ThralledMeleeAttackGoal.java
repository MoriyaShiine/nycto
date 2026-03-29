/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.thrall;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class ThralledMeleeAttackGoal extends MeleeAttackGoal {
	public ThralledMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
		super(mob, speedModifier, followingTargetEvenIfNotSeen);
	}

	@Override
	public boolean canUse() {
		return ModEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner() && super.canUse();
	}
}
