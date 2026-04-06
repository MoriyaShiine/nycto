/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.coffin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.nycto.client.renderer.entity.state.CoffinRenderState;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntityRenderer.class, priority = 500)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {
	@Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At("HEAD"), cancellable = true)
	private void nycto$coffin(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera, CallbackInfo ci) {
		CoffinRenderState coffinRenderState = state.getData(CoffinRenderState.KEY);
		if (coffinRenderState != null && coffinRenderState.sleepingInCoffin) {
			ci.cancel();
		}
	}

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("HEAD"))
	private void nycto$coffin(T entity, S state, float partialTicks, CallbackInfo ci) {
		CoffinRenderState coffinRenderState = new CoffinRenderState();
		entity.getSleepingPos().ifPresent(pos -> {
			if (entity.level().getBlockState(pos).is(ModBlockTags.COFFINS)) {
				state.isInvisible = true;
				coffinRenderState.sleepingInCoffin = true;
			}
		});
		state.setData(CoffinRenderState.KEY, coffinRenderState);
	}
}
