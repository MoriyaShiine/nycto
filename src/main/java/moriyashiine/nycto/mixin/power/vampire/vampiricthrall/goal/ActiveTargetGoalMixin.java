/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.goal;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ActiveTargetGoal.class)
public abstract class ActiveTargetGoalMixin extends TrackTargetGoal {
	public ActiveTargetGoalMixin(MobEntity mob, boolean checkVisibility) {
		super(mob, checkVisibility);
	}

	@Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
	private void nycto$vampiricThrall(CallbackInfoReturnable<Boolean> cir) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(mob).isThralled()) {
			cir.setReturnValue(false);
		}
	}
}
