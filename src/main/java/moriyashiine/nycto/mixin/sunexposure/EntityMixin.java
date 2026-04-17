/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.sunexposure;

import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(method = "clearFire", at = @At("HEAD"), cancellable = true)
	private void nycto$sunExposure(CallbackInfo ci) {
		if (NyctoAPI.isSunExposed((Entity) (Object) this)) {
			ci.cancel();
		}
	}

	@Inject(method = "playEntityOnFireExtinguishedSound", at = @At("HEAD"), cancellable = true)
	private void nycto$sunExposureSound(CallbackInfo ci) {
		if (NyctoAPI.isSunExposed((Entity) (Object) this)) {
			ci.cancel();
		}
	}
}
