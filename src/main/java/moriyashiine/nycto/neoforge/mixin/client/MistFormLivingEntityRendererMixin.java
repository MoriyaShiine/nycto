/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.nycto.neoforge.network.BatFormClientState;
import moriyashiine.nycto.neoforge.network.DarkFormClientState;
import moriyashiine.nycto.neoforge.network.MistFormClientState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Mixin(LivingEntityRenderer.class)
public abstract class MistFormLivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
	@Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
	private void nycto$formRenderType(T entity, boolean bodyVisible, boolean translucent, boolean glowing, CallbackInfoReturnable<RenderType> cir) {
		if (MistFormClientState.isActive(entity) || BatFormClientState.isActive(entity) || DarkFormClientState.isActive(entity)) {
			cir.setReturnValue(null);
		}
	}

	@Inject(method = "isBodyVisible", at = @At("HEAD"), cancellable = true)
	private void nycto$mistFormBodyInvisible(T entity, CallbackInfoReturnable<Boolean> cir) {
		if (MistFormClientState.isActive(entity) || BatFormClientState.isActive(entity) || DarkFormClientState.isActive(entity)) {
			cir.setReturnValue(false);
		}
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"))
	private Iterator<RenderLayer<T, M>> nycto$hideMistFormLayers(List<RenderLayer<T, M>> layers, T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		if (MistFormClientState.isActive(entity) || BatFormClientState.isActive(entity) || DarkFormClientState.isActive(entity)) {
			return Collections.emptyIterator();
		}
		return layers.iterator();
	}
}
