/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.hunter;

import moriyashiine.nycto.common.world.entity.monster.Hunter;
import net.minecraft.world.entity.ai.goal.Goal;

public class UseCustomItemGoal extends Goal {
	private final Hunter mob;
	private int cooldown = 0;

	public UseCustomItemGoal(Hunter mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		return mob.getHunterType().getCustomItemCooldown(mob) > 0;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		if (cooldown > 0) {
			cooldown--;
		} else if (mob.getHunterType().shouldUseCustomItem(mob)) {
			mob.getHunterType().useCustomItem(mob);
			cooldown = mob.getHunterType().getCustomItemCooldown(mob);
		}
	}
}
