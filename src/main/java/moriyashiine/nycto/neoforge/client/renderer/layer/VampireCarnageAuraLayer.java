/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.model.VampireModel;
import moriyashiine.nycto.neoforge.entity.Vampire;
import moriyashiine.nycto.neoforge.network.CarnageClientState;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public final class VampireCarnageAuraLayer extends RenderLayer<Vampire, VampireModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(NyctoNeoForge.id("vampire"), "carnage_aura");
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/carnage/carnage_aura.png");

	private final VampireModel model;

	public VampireCarnageAuraLayer(RenderLayerParent<Vampire, VampireModel> renderer, EntityModelSet modelSet) {
		super(renderer);
		model = new VampireModel(modelSet.bakeLayer(LAYER));
	}

	public static LayerDefinition createBodyLayer() {
		return VampireModel.createBodyLayer();
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Vampire vampire, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		float opacity = CarnageClientState.opacity(vampire, 0.5F);
		if (opacity <= 0 || vampire.isInvisible()) {
			return;
		}
		model.prepareMobModel(vampire, limbSwing, limbSwingAmount, partialTick);
		getParentModel().copyPropertiesTo(model);
		model.setupAnim(vampire, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		VertexConsumer consumer = bufferSource.getBuffer(RenderType.breezeWind(TEXTURE, 0, (vampire.tickCount + partialTick) * -0.01F % 1.0F));
		int alpha = Math.max(0, Math.min(255, (int) (opacity * 255)));
		int color = alpha << 24 | 0xFFFFFF;
		model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, color);
	}
}
