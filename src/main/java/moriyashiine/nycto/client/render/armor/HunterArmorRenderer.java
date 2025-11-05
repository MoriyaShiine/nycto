/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.armor;

import moriyashiine.nycto.client.render.armor.model.HunterArmorModel;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.command.RenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public record HunterArmorRenderer(HunterArmorModel<BipedEntityRenderState> armorModel,
								  Identifier texture) implements ArmorRenderer {
	public HunterArmorRenderer(EntityRendererFactory.Context context, EquipmentSlot slot, Identifier texture) {
		this(new HunterArmorModel<>(context.getPart(HunterArmorModel.MODEL_LAYERS.getModelData(slot))), texture);
	}

	@Override
	public void render(MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, ItemStack stack, BipedEntityRenderState state, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> model) {
		RenderCommandQueue queue = orderedRenderCommandQueue.getBatchingQueue(0);
		ArmorRenderer.submitTransformCopyingModel(model, state, armorModel, state, true, queue, matrices, RenderLayer.getArmorCutoutNoCull(texture), light, OverlayTexture.DEFAULT_UV, state.outlineColor, null);
		if (stack.hasGlint()) {
			ArmorRenderer.submitTransformCopyingModel(model, state, armorModel, state, true, queue, matrices, RenderLayer.getArmorEntityGlint(), light, OverlayTexture.DEFAULT_UV, state.outlineColor, null);
		}
	}
}
