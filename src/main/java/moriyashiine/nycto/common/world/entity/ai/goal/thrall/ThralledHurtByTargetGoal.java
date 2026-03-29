/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.thrall;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class ThralledHurtByTargetGoal extends HurtByTargetGoal {
	public ThralledHurtByTargetGoal(PathfinderMob mob, Class<?>... ignoreDamageFromTheseTypes) {
		super(mob, ignoreDamageFromTheseTypes);
	}

	@Override
	public boolean canUse() {
		return ModEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner() && super.canUse();
	}
}
