/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.keensenses.client;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.nycto.client.renderer.entity.state.KeenSensesRenderState;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<S extends EntityRenderState> {
	@Inject(method = "submitNameDisplay(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;I)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"))
	private void nycto$keenSenses(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera, int offset, CallbackInfo ci) {
		KeenSensesRenderState keenSensesRenderState = state.getData(KeenSensesRenderState.KEY);
		if (keenSensesRenderState != null && keenSensesRenderState.position != null) {
			Vec3 position = keenSensesRenderState.position;
			if (state.nameTag != null) {
				position = position.add(0, 0.3, 0);
			}
			MutableComponent health = Component.empty();
			for (int i = 0; i < 8; i++) {
				health.append(Component.literal("❤").withStyle(i / 8F < keenSensesRenderState.healthPercentage ? ChatFormatting.GRAY : ChatFormatting.DARK_GRAY));
			}
			submitNodeCollector.submitNameTag(poseStack, position, offset, health, true, state.lightCoords, state.distanceToCameraSq, camera);
		}
	}
}
