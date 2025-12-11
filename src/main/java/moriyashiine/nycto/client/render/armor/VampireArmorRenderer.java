/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.armor;

import moriyashiine.nycto.client.render.armor.model.VampireArmorModel;
import moriyashiine.nycto.common.Nycto;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.command.RenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public record VampireArmorRenderer(VampireArmorModel<BipedEntityRenderState> armorModel) implements ArmorRenderer {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/equipment/vampire_armor.png");

	public VampireArmorRenderer(EntityRendererFactory.Context context, EquipmentSlot slot) {
		this(new VampireArmorModel<>(context.getPart(VampireArmorModel.MODEL_LAYERS.getModelData(slot))));
	}

	@Override
	public void render(MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, ItemStack stack, BipedEntityRenderState state, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> model) {
		RenderCommandQueue queue = orderedRenderCommandQueue.getBatchingQueue(0);
		ArmorRenderer.submitTransformCopyingModel(model, state, armorModel, state, true, queue, matrices, RenderLayers.armorCutoutNoCull(TEXTURE), light, OverlayTexture.DEFAULT_UV, state.outlineColor, null);
		if (stack.hasGlint()) {
			ArmorRenderer.submitTransformCopyingModel(model, state, armorModel, state, true, queue, matrices, RenderLayers.armorEntityGlint(), light, OverlayTexture.DEFAULT_UV, state.outlineColor, null);
		}
	}
}
