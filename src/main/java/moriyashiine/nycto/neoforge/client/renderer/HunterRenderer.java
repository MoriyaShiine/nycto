/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.model.HunterModel;
import moriyashiine.nycto.neoforge.entity.hunter.HunterEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class HunterRenderer extends MobRenderer<HunterEntity, HunterModel> {
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/hunter/vampire_hunter.png");

	public HunterRenderer(EntityRendererProvider.Context context) {
		super(context, new HunterModel(context.bakeLayer(HunterModel.LAYER)), 0.5F);
		addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(HunterEntity entity) {
		return TEXTURE;
	}
}
