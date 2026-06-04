/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.ai;

import moriyashiine.nycto.neoforge.entity.Vampire;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public final class BloodFlechettesGoal extends Goal {
	private final Vampire vampire;

	public BloodFlechettesGoal(Vampire vampire) {
		this.vampire = vampire;
	}

	@Override
	public boolean canUse() {
		LivingEntity target = vampire.getTarget();
		return target != null && vampire.canUsePower(NyctoPowers.BLOOD_FLECHETTES) && vampire.hasLineOfSight(target);
	}

	@Override
	public void start() {
		LivingEntity target = vampire.getTarget();
		if (target != null) {
			vampire.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
		}
		vampire.startBloodFlechettes();
	}
}
