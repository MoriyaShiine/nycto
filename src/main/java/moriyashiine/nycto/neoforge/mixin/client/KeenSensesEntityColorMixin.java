/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.mixin.client;

import moriyashiine.nycto.neoforge.client.event.KeenSensesClientRenderEvents;
import moriyashiine.nycto.neoforge.client.event.VampiricThrallClientEvents;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class KeenSensesEntityColorMixin {
	@Inject(method = "getTeamColor", at = @At("HEAD"), cancellable = true)
	private void nycto$keenSensesOutlineColor(CallbackInfoReturnable<Integer> cir) {
		int color = KeenSensesClientRenderEvents.outlineColor((Entity) (Object) this);
		if (color != -1) {
			cir.setReturnValue(color);
			return;
		}
		color = VampiricThrallClientEvents.outlineColor((Entity) (Object) this);
		if (color != -1) {
			cir.setReturnValue(color);
		}
	}
}
