/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.vampire.client;

import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
	@Inject(method = "updateShadow(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/render/entity/state/EntityRenderState;)V", at = @At("TAIL"))
	private void nycto$vampire(T entity, S state, CallbackInfo ci) {
		if (NyctoAPI.isVampire(entity)) {
			state.shadowPieces.clear();
		}
	}
}
