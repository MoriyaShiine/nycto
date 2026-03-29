/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific.client;

import moriyashiine.nycto.client.renderer.entity.armor.model.ThralledHorseHornsModel;
import moriyashiine.nycto.client.renderer.entity.layers.ThralledHorseHornsLayer;
import net.minecraft.client.model.animal.equine.HorseModel;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.world.entity.animal.equine.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseRenderer.class)
public abstract class HorseRendererMixin extends AbstractHorseRenderer<Horse, HorseRenderState, HorseModel> {
	public HorseRendererMixin(EntityRendererProvider.Context context, HorseModel model, HorseModel babyModel) {
		super(context, model, babyModel);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void nycto$vampiricThrall(EntityRendererProvider.Context context, CallbackInfo ci) {
		addLayer(new ThralledHorseHornsLayer(this, context.bakeLayer(ThralledHorseHornsModel.MODEL_LAYER)));
	}
}
