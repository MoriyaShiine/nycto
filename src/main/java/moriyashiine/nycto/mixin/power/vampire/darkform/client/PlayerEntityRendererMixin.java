/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.darkform.client;

import moriyashiine.nycto.client.event.FormChangeClientEvent;
import moriyashiine.nycto.client.render.entity.DarkFormEntityRenderer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {
	public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(method = "renderArm", at = @At("HEAD"), cancellable = true)
	private void nycto$darkForm(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, Identifier skinTexture, ModelPart arm, boolean sleeveVisible, CallbackInfo ci) {
		if (FormChangeClientEvent.darkFormModel != null) {
			boolean left = arm == model.leftArm;
			ModelPart darkFormArm = left ? FormChangeClientEvent.darkFormModel.leftArm : FormChangeClientEvent.darkFormModel.rightArm;
			darkFormArm.resetTransform();
			darkFormArm.visible = true;
			darkFormArm.originY += 1;
			queue.submitModelPart(darkFormArm, matrices, RenderLayers.entityTranslucent(DarkFormEntityRenderer.TEXTURE), light, OverlayTexture.DEFAULT_UV, null);
			ci.cancel();
		}
	}
}
