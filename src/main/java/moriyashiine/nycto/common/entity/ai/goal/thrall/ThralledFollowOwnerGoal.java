/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.thrall;

import moriyashiine.nycto.common.component.entity.power.util.HasOwnerComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModStatusEffects;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;

public class ThralledFollowOwnerGoal extends TemptGoal {
	public ThralledFollowOwnerGoal(PathAwareEntity entity, double speed) {
		super(entity, speed, stack -> false, false);
		predicate = TemptGoal.TEMPTING_ENTITY_PREDICATE.copy().setPredicate((target, world) -> !NyctoUtil.isTargetable(entity.getTarget()) && HasOwnerComponent.isOwner(entity, target));
	}

	@Override
	public boolean canStart() {
		if (mob.hasStatusEffect(ModStatusEffects.HYPNOTIZED) || ModEntityComponents.VAMPIRIC_THRALL.get(mob).getFollowMode() == VampiricThrallComponent.FollowMode.FOLLOW) {
			closestPlayer = getServerWorld(mob).getClosestPlayer(predicate.setBaseMaxDistance(mob.getAttributeValue(EntityAttributes.FOLLOW_RANGE)), mob);
			return closestPlayer != null;
		}
		return false;
	}
}
