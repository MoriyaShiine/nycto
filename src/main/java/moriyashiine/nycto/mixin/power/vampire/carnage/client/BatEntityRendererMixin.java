/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.carnage.client;

import moriyashiine.nycto.client.render.entity.feature.carnage.BatCarnageAuraFeatureRenderer;
import moriyashiine.nycto.client.render.entity.state.CarnageRenderState;
import net.minecraft.client.render.entity.BatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.client.render.entity.state.BatEntityRenderState;
import net.minecraft.entity.passive.BatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BatEntityRenderer.class)
public abstract class BatEntityRendererMixin extends MobEntityRenderer<BatEntity, BatEntityRenderState, BatEntityModel> {
	public BatEntityRendererMixin(EntityRendererFactory.Context context, BatEntityModel model, float shadowRadius) {
		super(context, model, shadowRadius);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void nycto$carnage(EntityRendererFactory.Context context, CallbackInfo ci) {
		addFeature(new BatCarnageAuraFeatureRenderer(this, context.getEntityModels()));
	}

	@Inject(method = "updateRenderState(Lnet/minecraft/entity/passive/BatEntity;Lnet/minecraft/client/render/entity/state/BatEntityRenderState;F)V", at = @At("TAIL"))
	private void nycto$carnage(BatEntity entity, BatEntityRenderState state, float tickProgress, CallbackInfo ci) {
		CarnageRenderState.updateRenderState(entity, state);
	}
}
