/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.armor;

import moriyashiine.nycto.client.render.armor.model.VampireArmorModel;
import moriyashiine.nycto.common.Nycto;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class VampireArmorRenderer implements ArmorRenderer {
	private static final Identifier TEXTURE = Nycto.id("textures/entity/equipment/vampire_armor.png");

	private static VampireArmorModel<BipedEntityRenderState> armorModel;

	@Override
	public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, ItemStack stack, BipedEntityRenderState state, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> model) {
		if (armorModel == null) {
			armorModel = new VampireArmorModel<>(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(VampireArmorModel.MODEL_LAYER));
		}
		queue.getBatchingQueue(1).submitModel(armorModel, state, matrices, RenderLayer.getArmorCutoutNoCull(TEXTURE), light, OverlayTexture.DEFAULT_UV, state.outlineColor, null);
	}
}
