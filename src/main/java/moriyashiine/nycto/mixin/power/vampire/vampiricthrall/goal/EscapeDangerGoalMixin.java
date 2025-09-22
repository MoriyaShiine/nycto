/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.goal;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
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
	private boolean nycto$vampiricThrall(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(mob).isThralled();
	}
}
