/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.model.DarkFormModel;
import moriyashiine.nycto.neoforge.entity.DarkForm;
import moriyashiine.nycto.neoforge.network.CarnageClientState;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public final class DarkFormCarnageAuraLayer extends RenderLayer<DarkForm, DarkFormModel<DarkForm>> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(NyctoNeoForge.id("dark_form"), "carnage_aura");
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/carnage/carnage_aura.png");

	private final DarkFormModel<DarkForm> model;

	public DarkFormCarnageAuraLayer(RenderLayerParent<DarkForm, DarkFormModel<DarkForm>> renderer, EntityModelSet modelSet) {
		super(renderer);
		model = new DarkFormModel<>(modelSet.bakeLayer(LAYER));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, DarkForm entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		float opacity = CarnageClientState.opacity(entity, 0.5F);
		if (opacity <= 0 || entity.isInvisible()) {
			return;
		}
		getParentModel().copyPropertiesTo(model);
		model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		VertexConsumer consumer = bufferSource.getBuffer(RenderType.breezeWind(TEXTURE, 0, (entity.tickCount + partialTick) * -0.01F % 1.0F));
		int alpha = Math.max(0, Math.min(255, (int) (opacity * 255)));
		model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, alpha << 24 | 0xFFFFFF);
	}
}
