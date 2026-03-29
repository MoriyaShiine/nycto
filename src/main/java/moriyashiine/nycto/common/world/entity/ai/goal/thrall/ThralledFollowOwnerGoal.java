/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.thrall;

import moriyashiine.nycto.common.component.entity.power.util.HasOwnerComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModMobEffects;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;

public class ThralledFollowOwnerGoal extends TemptGoal {
	public ThralledFollowOwnerGoal(PathfinderMob mob, double speedModifier) {
		super(mob, speedModifier, _ -> false, false);
		targetingConditions = TemptGoal.TEMPT_TARGETING.copy().selector((target, _) -> !NyctoUtil.isSurvivalNullable(mob.getTarget()) && HasOwnerComponent.isOwner(mob, target));
	}

	@Override
	public boolean canUse() {
		if (mob.hasEffect(ModMobEffects.HYPNOTIZED) || ModEntityComponents.VAMPIRIC_THRALL.get(mob).getFollowMode() == VampiricThrallComponent.FollowMode.FOLLOW) {
			player = getServerLevel(mob).getNearestPlayer(targetingConditions.range(mob.getAttributeValue(Attributes.FOLLOW_RANGE)), mob);
			return player != null;
		}
		return false;
	}
}
