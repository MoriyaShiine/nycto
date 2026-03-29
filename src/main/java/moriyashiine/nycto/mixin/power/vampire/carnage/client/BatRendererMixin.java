/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.carnage.client;

import moriyashiine.nycto.client.renderer.entity.layers.carnage.BatCarnageAuraLayer;
import moriyashiine.nycto.client.renderer.entity.state.CarnageRenderState;
import net.minecraft.client.model.ambient.BatModel;
import net.minecraft.client.renderer.entity.BatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.BatRenderState;
import net.minecraft.world.entity.ambient.Bat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BatRenderer.class)
public abstract class BatRendererMixin extends MobRenderer<Bat, BatRenderState, BatModel> {
	public BatRendererMixin(EntityRendererProvider.Context context, BatModel model, float shadow) {
		super(context, model, shadow);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void nycto$carnage(EntityRendererProvider.Context context, CallbackInfo ci) {
		addLayer(new BatCarnageAuraLayer(this, context.getModelSet()));
	}

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/ambient/Bat;Lnet/minecraft/client/renderer/entity/state/BatRenderState;F)V", at = @At("TAIL"))
	private void nycto$carnage(Bat entity, BatRenderState state, float partialTicks, CallbackInfo ci) {
		CarnageRenderState.extractRenderState(entity, state);
	}
}
