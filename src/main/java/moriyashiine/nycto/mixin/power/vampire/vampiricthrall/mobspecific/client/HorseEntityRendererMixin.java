/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific.client;

import moriyashiine.nycto.client.render.armor.ThralledHorseHornsRenderer;
import moriyashiine.nycto.client.render.armor.model.ThralledHorseHornsModel;
import net.minecraft.client.render.entity.AbstractHorseEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.HorseEntityRenderer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.render.entity.state.HorseEntityRenderState;
import net.minecraft.entity.passive.HorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseEntityRenderer.class)
public abstract class HorseEntityRendererMixin extends AbstractHorseEntityRenderer<HorseEntity, HorseEntityRenderState, HorseEntityModel> {
	public HorseEntityRendererMixin(EntityRendererFactory.Context context, HorseEntityModel model, HorseEntityModel babyModel) {
		super(context, model, babyModel);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void nycto$vampiricThrall(EntityRendererFactory.Context context, CallbackInfo ci) {
		addFeature(new ThralledHorseHornsRenderer(this, context.getPart(ThralledHorseHornsModel.MODEL_LAYER)));
	}
}
