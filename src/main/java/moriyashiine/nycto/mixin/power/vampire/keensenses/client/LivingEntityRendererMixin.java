/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.keensenses.client;

import moriyashiine.nycto.client.event.power.KeenSensesClientEvent;
import moriyashiine.nycto.client.renderer.entity.state.KeenSensesRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
	private void nycto$keenSenses(T entity, S state, float partialTicks, CallbackInfo ci) {
		KeenSensesRenderState keenSensesRenderState = new KeenSensesRenderState();
		if (KeenSensesClientEvent.Outline.shouldApply(entity)) {
			keenSensesRenderState.position = entity.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, entity.getYRot(partialTicks));
			keenSensesRenderState.healthPercentage = entity.getHealth() / entity.getMaxHealth();
		}
		state.setData(KeenSensesRenderState.KEY, keenSensesRenderState);
	}
}
