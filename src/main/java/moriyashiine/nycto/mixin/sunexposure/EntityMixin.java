/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.sunexposure;

import moriyashiine.nycto.common.component.entity.SunExposureComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(method = "clearFire", at = @At("HEAD"), cancellable = true)
	private void nycto$sunExposure(CallbackInfo ci) {
		SunExposureComponent sunExposureComponent = ModEntityComponents.SUN_EXPOSURE.getNullable(this);
		if (sunExposureComponent != null && sunExposureComponent.isExposed()) {
			ci.cancel();
		}
	}

	@Inject(method = "playEntityOnFireExtinguishedSound", at = @At("HEAD"), cancellable = true)
	private void nycto$sunExposureSound(CallbackInfo ci) {
		SunExposureComponent sunExposureComponent = ModEntityComponents.SUN_EXPOSURE.getNullable(this);
		if (sunExposureComponent != null && sunExposureComponent.isExposed()) {
			ci.cancel();
		}
	}
}
