/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.mixin.client;

import moriyashiine.nycto.neoforge.network.ThrallClientState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class ThrallLivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
	@Shadow
	protected M model;

	@Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
	private void nycto$thrallRenderType(T entity, boolean bodyVisible, boolean translucent, boolean glowing, CallbackInfoReturnable<RenderType> cir) {
		ResourceLocation texture = ThrallClientState.textureFor(entity);
		if (texture == null) {
			return;
		}
		if (translucent) {
			cir.setReturnValue(RenderType.itemEntityTranslucentCull(texture));
		} else if (bodyVisible) {
			cir.setReturnValue(model.renderType(texture));
		} else {
			cir.setReturnValue(glowing ? RenderType.outline(texture) : null);
		}
	}
}
