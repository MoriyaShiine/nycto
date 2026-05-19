/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.healblock;

import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "heal", at = @At("HEAD"), cancellable = true)
	private void nycto$healBlock(float heal, CallbackInfo ci) {
		if (NyctoAPI.isHealingBlocked((LivingEntity) (Object) this)) {
			ci.cancel();
		}
	}
}
