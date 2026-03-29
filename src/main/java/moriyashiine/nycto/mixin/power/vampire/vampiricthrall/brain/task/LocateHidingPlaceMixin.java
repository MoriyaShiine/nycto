/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.brain.task;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.LocateHidingPlace;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocateHidingPlace.class)
public class LocateHidingPlaceMixin {
	@Inject(method = "lambda$create$2", at = @At("HEAD"), cancellable = true)
	private static void nycto$vampiricThrall(CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) LivingEntity body) {
		@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(body);
		if (vampiricThrallComponent != null && vampiricThrallComponent.cannotWanderIfThralled()) {
			cir.setReturnValue(false);
		}
	}
}
