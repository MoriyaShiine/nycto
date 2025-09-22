/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.brain.task;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.ai.brain.task.GoToRememberedPositionTask;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GoToRememberedPositionTask.class)
public class GoToRememberedPositionTaskMixin {
	@Inject(method = "method_47089", at = @At("HEAD"), cancellable = true)
	private static void nycto$vampiricThrall(CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) PathAwareEntity entity) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(entity).cannotWanderIfThralled()) {
			cir.setReturnValue(false);
		}
	}
}
