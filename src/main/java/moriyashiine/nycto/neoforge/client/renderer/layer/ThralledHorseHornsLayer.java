/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.model.ThralledHorseHornsModel;
import moriyashiine.nycto.neoforge.network.ThrallClientState;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Map;

public final class ThralledHorseHornsLayer extends RenderLayer<Horse, HorseModel<Horse>> {
	private static final Map<Item, ResourceLocation> HORNS_BY_ARMOR = Map.of(
			Items.LEATHER_HORSE_ARMOR, NyctoNeoForge.id("textures/entity/vampiric_thrall/minecraft/horse_horns/leather.png"),
			Items.IRON_HORSE_ARMOR, NyctoNeoForge.id("textures/entity/vampiric_thrall/minecraft/horse_horns/iron.png"),
			Items.GOLDEN_HORSE_ARMOR, NyctoNeoForge.id("textures/entity/vampiric_thrall/minecraft/horse_horns/golden.png"),
			Items.DIAMOND_HORSE_ARMOR, NyctoNeoForge.id("textures/entity/vampiric_thrall/minecraft/horse_horns/diamond.png"));
	private static final ResourceLocation DEFAULT_HORNS = NyctoNeoForge.id("textures/entity/vampiric_thrall/minecraft/horse_horns/leather.png");
	private final ThralledHorseHornsModel model;

	public ThralledHorseHornsLayer(RenderLayerParent<Horse, HorseModel<Horse>> parent, EntityModelSet models) {
		super(parent);
		this.model = new ThralledHorseHornsModel(models.bakeLayer(ThralledHorseHornsModel.LAYER));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Horse horse, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!ThrallClientState.isThrall(horse)) {
			return;
		}
		getParentModel().copyPropertiesTo(model);
		model.prepareMobModel(horse, limbSwing, limbSwingAmount, partialTick);
		model.setupAnim(horse, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(hornsTexture(horse.getBodyArmorItem())));
		model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, -1);
	}

	private static ResourceLocation hornsTexture(ItemStack armor) {
		return HORNS_BY_ARMOR.getOrDefault(armor.getItem(), DEFAULT_HORNS);
	}
}
