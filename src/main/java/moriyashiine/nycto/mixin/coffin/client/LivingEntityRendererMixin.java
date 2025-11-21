/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.coffin.client;

import moriyashiine.nycto.client.render.entity.state.CoffinRenderState;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntityRenderer.class, priority = 500)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {
	@Inject(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At("HEAD"), cancellable = true)
	private void nycto$coffin(S renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraRenderState, CallbackInfo ci) {
		@Nullable CoffinRenderState coffinRenderState = renderState.getData(CoffinRenderState.KEY);
		if (coffinRenderState != null && coffinRenderState.sleepingInCoffin) {
			ci.cancel();
		}
	}

	@Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At("HEAD"))
	private void nycto$coffin(T entity, S state, float tickDelta, CallbackInfo ci) {
		CoffinRenderState coffinRenderState = new CoffinRenderState();
		entity.getSleepingPosition().ifPresent(pos -> {
			if (entity.getEntityWorld().getBlockState(pos).isIn(ModBlockTags.COFFINS)) {
				state.invisible = true;
				coffinRenderState.sleepingInCoffin = true;
			}
		});
		state.setData(CoffinRenderState.KEY, coffinRenderState);
	}
}
