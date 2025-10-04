/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.coffin.client;

import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntityRenderer.class, priority = 999)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {
	@Unique
	private boolean cancel = false;

	@Inject(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At("HEAD"), cancellable = true)
	private void nycto$coffin(S renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraRenderState, CallbackInfo ci) {
		if (cancel) {
			ci.cancel();
		}
	}

	@Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At("HEAD"))
	private void nycto$coffin(T entity, S state, float tickDelta, CallbackInfo ci) {
		cancel = false;
		entity.getSleepingPosition().ifPresent(pos -> {
			if (entity.getEntityWorld().getBlockState(pos).isIn(ModBlockTags.COFFINS)) {
				cancel = true;
			}
		});
	}
}
