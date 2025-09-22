/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.brain.task;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.HideWhenBellRingsTask;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HideWhenBellRingsTask.class)
public class HideWhenBellsRingTaskMixin {
	@Inject(method = "method_47034", at = @At("HEAD"), cancellable = true)
	private static void nycto$vampiricThrall(CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) LivingEntity entity) {
		@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
		if (vampiricThrallComponent != null && vampiricThrallComponent.cannotWanderIfThralled()) {
			cir.setReturnValue(false);
		}
	}
}
