/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.sunexposure;

import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantValue")
@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	private Level level;

	@Shadow
	public abstract boolean isAlive();

	@Inject(method = "clearFire", at = @At("HEAD"), cancellable = true)
	private void nycto$sunExposure(CallbackInfo ci) {
		if (!level.isClientSide() && isAlive() && NyctoAPI.isSunExposed((Entity) (Object) this)) {
			ci.cancel();
		}
	}

	@Inject(method = "playEntityOnFireExtinguishedSound", at = @At("HEAD"), cancellable = true)
	private void nycto$sunExposureSound(CallbackInfo ci) {
		if (isAlive() && NyctoAPI.isSunExposed((Entity) (Object) this)) {
			ci.cancel();
		}
	}
}
