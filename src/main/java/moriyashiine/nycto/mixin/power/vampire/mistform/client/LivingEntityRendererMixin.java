/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.mistform.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.client.renderer.entity.state.MistFormRenderState;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {
	@ModifyExpressionValue(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;shouldRenderLayers(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)Z"))
	private boolean nycto$mistForm(boolean original, S state) {
		MistFormRenderState mistFormRenderState = state.getData(MistFormRenderState.KEY);
		if (mistFormRenderState != null && mistFormRenderState.mistForm) {
			return false;
		}
		return original;
	}

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
	private void nycto$mistForm(T entity, S state, float partialTicks, CallbackInfo ci) {
		MistFormRenderState mistFormRenderState = new MistFormRenderState();
		if (entity instanceof Player player && ModEntityComponents.MIST_FORM.get(player).isEnabled()) {
			state.isInvisible = true;
			mistFormRenderState.mistForm = true;
		}
		state.setData(MistFormRenderState.KEY, mistFormRenderState);
	}
}
