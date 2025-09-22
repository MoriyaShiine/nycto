/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.batform.client;

import moriyashiine.nycto.client.event.FormChangeClientEvent;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {
	@Unique
	private static final Identifier BAT_TEXTURE = Identifier.ofVanilla("textures/entity/bat.png");

	public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(method = "renderArm", at = @At("HEAD"), cancellable = true)
	private void nycto$batForm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Identifier skinTexture, ModelPart arm, boolean sleeveVisible, CallbackInfo ci) {
		if (FormChangeClientEvent.batModel != null) {
			boolean left = arm == model.leftArm;
			ModelPart body = FormChangeClientEvent.batModel.getRootPart().getChild(EntityModelPartNames.BODY);
			ModelPart batFormArm = left ? body.getChild(EntityModelPartNames.LEFT_WING) : body.getChild(EntityModelPartNames.RIGHT_WING);
			batFormArm.resetTransform();
			batFormArm.visible = true;
			batFormArm.originY += 5.5F;
			batFormArm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(BAT_TEXTURE)), light, OverlayTexture.DEFAULT_UV);
			ci.cancel();
		}
	}
}
