/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.AgeableMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AgeableMob.class)
public class AgeableMobMixin {
	@Inject(method = "setAge", at = @At("HEAD"), cancellable = true)
	private void nycto$vampiricThrall(int newAge, CallbackInfo ci) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner()) {
			ci.cancel();
		}
	}
}
