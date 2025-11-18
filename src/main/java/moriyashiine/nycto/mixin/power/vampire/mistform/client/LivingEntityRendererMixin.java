/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.mistform.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.client.render.entity.state.MistFormRenderState;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {
	@ModifyExpressionValue(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;shouldRenderFeatures(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;)Z"))
	private boolean nycto$mistForm(boolean original, S state) {
		@Nullable MistFormRenderState mistFormRenderState = state.getData(MistFormRenderState.KEY);
		if (mistFormRenderState != null && mistFormRenderState.mistForm) {
			return false;
		}
		return original;
	}

	@Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
	private void nycto$mistForm(T entity, S state, float tickDelta, CallbackInfo ci) {
		MistFormRenderState mistFormRenderState = new MistFormRenderState();
		if (entity instanceof PlayerEntity player && ModEntityComponents.MIST_FORM.get(player).isEnabled()) {
			state.invisible = true;
			mistFormRenderState.mistForm = true;
		}
		state.setData(MistFormRenderState.KEY, mistFormRenderState);
	}
}
