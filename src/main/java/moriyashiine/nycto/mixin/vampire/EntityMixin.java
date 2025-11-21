/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = 1)
public class EntityMixin {
	@Inject(method = "isFireImmune", at = @At("HEAD"), cancellable = true)
	private void nycto$vampire(CallbackInfoReturnable<Boolean> cir) {
		if (NyctoAPI.isVampire((Entity) (Object) this)) {
			cir.setReturnValue(false);
		}
	}
}
