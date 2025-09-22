/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.bloodbarrier.client;

import moriyashiine.nycto.client.render.entity.feature.BloodBarrierFeatureRenderer;
import moriyashiine.nycto.client.render.entity.state.BloodBarrierRenderStateAddition;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {
	public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void nycto$bloodBarrier(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
		addFeature(new BloodBarrierFeatureRenderer<>(this, ctx.getEntityModels()));
	}

	@Inject(method = "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("TAIL"))
	private void nycto$bloodBarrier(AbstractClientPlayerEntity entity, PlayerEntityRenderState state, float tickProgress, CallbackInfo ci) {
		BloodBarrierRenderStateAddition.updateRenderState(entity, state);
	}
}
