/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.network.CarnageClientState;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public final class CarnageAuraLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(NyctoNeoForge.id("player"), "carnage_aura");
	public static final ModelLayerLocation LAYER_SLIM = new ModelLayerLocation(NyctoNeoForge.id("player_slim"), "carnage_aura");
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/carnage/carnage_aura.png");

	private final PlayerModel<AbstractClientPlayer> model;

	public CarnageAuraLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer, net.minecraft.client.model.geom.EntityModelSet modelSet, boolean slim) {
		super(renderer);
		model = new PlayerModel<>(modelSet.bakeLayer(slim ? LAYER_SLIM : LAYER), slim);
	}

	public static LayerDefinition createBodyLayer() {
		return LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1), false), 64, 64);
	}

	public static LayerDefinition createSlimBodyLayer() {
		return LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1), true), 64, 64);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		float opacity = CarnageClientState.opacity(player, 0.5F);
		if (opacity <= 0 || player.isInvisible()) {
			return;
		}
		model.prepareMobModel(player, limbSwing, limbSwingAmount, partialTick);
		getParentModel().copyPropertiesTo(model);
		model.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		VertexConsumer consumer = bufferSource.getBuffer(RenderType.breezeWind(TEXTURE, 0, (player.tickCount + partialTick) * -0.01F % 1.0F));
		int alpha = Math.max(0, Math.min(255, (int) (opacity * 255)));
		int color = alpha << 24 | 0xFFFFFF;
		model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, color);
	}
}
