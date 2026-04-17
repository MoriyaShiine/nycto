/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.nycto.client.renderer.entity.armor.model.HunterArmorModel;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public record HunterArmorRenderer(HunterArmorModel<HumanoidRenderState> armorModel, Identifier texture) implements ArmorRenderer {
	public HunterArmorRenderer(EntityRendererProvider.Context context, EquipmentSlot slot, Identifier texture) {
		this(new HunterArmorModel<>(context.bakeLayer(HunterArmorModel.MODEL_LAYERS.get(slot))), texture);
	}

	@Override
	public void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack stack, HumanoidRenderState humanoidRenderState, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
		OrderedSubmitNodeCollector queue = submitNodeCollector.order(0);
		ArmorRenderer.submitTransformCopyingModel(contextModel, humanoidRenderState, armorModel, humanoidRenderState, true, queue, poseStack, RenderTypes.armorCutoutNoCull(texture), light, OverlayTexture.NO_OVERLAY, humanoidRenderState.outlineColor, null);
		if (stack.hasFoil()) {
			ArmorRenderer.submitTransformCopyingModel(contextModel, humanoidRenderState, armorModel, humanoidRenderState, true, queue, poseStack, RenderTypes.armorEntityGlint(), light, OverlayTexture.NO_OVERLAY, humanoidRenderState.outlineColor, null);
		}
	}
}
