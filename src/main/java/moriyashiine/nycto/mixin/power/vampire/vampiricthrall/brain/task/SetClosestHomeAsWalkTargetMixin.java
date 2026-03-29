/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.brain.task;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.SetClosestHomeAsWalkTarget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SetClosestHomeAsWalkTarget.class)
public class SetClosestHomeAsWalkTargetMixin {
	@Inject(method = "lambda$create$2", at = @At("HEAD"), cancellable = true)
	private static void nycto$vampiricThrall(CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) PathfinderMob body) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(body).cannotWanderIfThralled()) {
			cir.setReturnValue(false);
		}
	}
}
