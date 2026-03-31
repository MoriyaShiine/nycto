/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.batform.client;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.nycto.client.event.FormChangeClientEvent;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, AvatarRenderState, PlayerModel> {
	@Unique
	private static final Identifier BAT_TEXTURE = Identifier.withDefaultNamespace("textures/entity/bat/bat.png");

	public AvatarRendererMixin(EntityRendererProvider.Context context, PlayerModel model, float shadow) {
		super(context, model, shadow);
	}

	@Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
	private void nycto$batForm(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, Identifier skinTexture, ModelPart arm, boolean hasSleeve, CallbackInfo ci) {
		if (FormChangeClientEvent.batModel != null) {
			boolean left = arm == model.leftArm;
			ModelPart body = FormChangeClientEvent.batModel.root().getChild(PartNames.BODY);
			ModelPart batFormArm = left ? body.getChild(PartNames.LEFT_WING) : body.getChild(PartNames.RIGHT_WING);
			batFormArm.resetPose();
			batFormArm.visible = true;
			batFormArm.y += 5.5F;
			submitNodeCollector.submitModelPart(batFormArm, poseStack, RenderTypes.entityTranslucent(BAT_TEXTURE), lightCoords, OverlayTexture.NO_OVERLAY, null);
			ci.cancel();
		}
	}
}
