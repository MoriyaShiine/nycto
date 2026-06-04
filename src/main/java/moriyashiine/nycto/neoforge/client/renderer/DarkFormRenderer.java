/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.model.DarkFormModel;
import moriyashiine.nycto.neoforge.client.renderer.layer.BloodBarrierLayer;
import moriyashiine.nycto.neoforge.client.renderer.layer.DarkFormCarnageAuraLayer;
import moriyashiine.nycto.neoforge.entity.DarkForm;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public final class DarkFormRenderer extends MobRenderer<DarkForm, DarkFormModel<DarkForm>> {
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/dark_form/dark_form.png");

	public DarkFormRenderer(EntityRendererProvider.Context context) {
		super(context, new DarkFormModel<>(context.bakeLayer(DarkFormModel.LAYER)), 0.6F);
		addLayer(new BloodBarrierLayer<>(this, context.getModelSet()));
		addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
		addLayer(new DarkFormCarnageAuraLayer(this, context.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(DarkForm entity) {
		return TEXTURE;
	}
}
