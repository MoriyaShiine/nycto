/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.misc.client;

import moriyashiine.nycto.common.tag.ModStatusEffectTags;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {
	@Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
	private void nycto$infection(T entity, S state, float tickProgress, CallbackInfo ci) {
		state.shaking |= entity.getStatusEffects().stream().anyMatch(instance -> instance.getEffectType().isIn(ModStatusEffectTags.INFECTION));
	}
}
