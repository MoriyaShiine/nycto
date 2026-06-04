/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.network.BloodrushClientState;
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
import net.minecraft.util.Mth;

public final class BloodrushAuraLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(NyctoNeoForge.id("player"), "bloodrush_aura");
	public static final ModelLayerLocation LAYER_SLIM = new ModelLayerLocation(NyctoNeoForge.id("player_slim"), "bloodrush_aura");
	private static final ResourceLocation TEXTURE = NyctoNeoForge.id("textures/entity/bloodrush/bloodrush_aura.png");

	private final PlayerModel<AbstractClientPlayer> model;

	public BloodrushAuraLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer, net.minecraft.client.model.geom.EntityModelSet modelSet, boolean slim) {
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
		if (!BloodrushClientState.isActive(player) || player.isInvisible()) {
			return;
		}
		float offset = Mth.cos((player.tickCount + partialTick) * 0.02F) / 2;
		model.prepareMobModel(player, limbSwing, limbSwingAmount, partialTick);
		getParentModel().copyPropertiesTo(model);
		model.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		VertexConsumer consumer = bufferSource.getBuffer(RenderType.energySwirl(TEXTURE, offset % 1.0F, (player.tickCount + partialTick) * 0.01F % 1.0F));
		model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, -8355712);
	}
}
