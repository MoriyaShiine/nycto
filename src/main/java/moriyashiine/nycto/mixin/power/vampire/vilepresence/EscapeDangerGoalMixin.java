/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.power.vampire.VilePresencePower;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EscapeDangerGoal.class)
public class EscapeDangerGoalMixin {
	@Shadow
	@Final
	protected PathAwareEntity mob;

	@ModifyReturnValue(method = "isInDanger", at = @At("RETURN"))
	private boolean nycto$vilePresence(boolean original) {
		return original || (mob.age % 5 == 0 && !(mob instanceof Angerable) && VilePresencePower.isAffected(mob, mob.getAttributeValue(EntityAttributes.FOLLOW_RANGE)));
	}
}
