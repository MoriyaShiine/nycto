/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.passive.PassiveEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PassiveEntity.class)
public class PassiveEntityMixin {
	@Inject(method = "setBreedingAge", at = @At("HEAD"), cancellable = true)
	private void nycto$vampiricThrall(int age, CallbackInfo ci) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(this).isThralled()) {
			ci.cancel();
		}
	}
}
