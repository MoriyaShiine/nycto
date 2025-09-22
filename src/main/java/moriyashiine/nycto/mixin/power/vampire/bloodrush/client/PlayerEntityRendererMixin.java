/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.bloodrush.client;

import moriyashiine.nycto.client.render.entity.feature.BloodrushAuraFeatureRenderer;
import moriyashiine.nycto.client.render.entity.state.BloodrushRenderStateAddition;
import moriyashiine.nycto.common.init.ModEntityComponents;
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
	private void nycto$bloodrush(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
		addFeature(new BloodrushAuraFeatureRenderer(this, ctx.getEntityModels(), slim));
	}

	@Inject(method = "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("TAIL"))
	private void nycto$bloodrush(AbstractClientPlayerEntity player, PlayerEntityRenderState state, float tickDelta, CallbackInfo ci) {
		((BloodrushRenderStateAddition) state).nycto$setUsingBloodrush(ModEntityComponents.BLOODRUSH.get(player).isActive(false));
		((BloodrushRenderStateAddition) state).nycto$setUsingBloodrushLenient(ModEntityComponents.BLOODRUSH.get(player).isActive(true));
	}
}
