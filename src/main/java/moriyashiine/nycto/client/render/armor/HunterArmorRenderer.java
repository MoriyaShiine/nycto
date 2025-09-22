/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.armor;

import moriyashiine.nycto.client.render.armor.model.HunterArmorModel;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public record HunterArmorRenderer(Identifier texture) implements ArmorRenderer {
	private static HunterArmorModel<BipedEntityRenderState> armorModel;

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, BipedEntityRenderState state, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> model) {
		if (armorModel == null) {
			armorModel = new HunterArmorModel<>(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(HunterArmorModel.MODEL_LAYER));
		}

		model.copyTransforms(armorModel);

		armorModel.coatFlap.pitch = Math.max(model.leftLeg.pitch, model.rightLeg.pitch);
		if (state.sneaking) {
			armorModel.coatFlap.pitch /= 3;
		}

		armorModel.head.visible = slot == EquipmentSlot.HEAD;
		armorModel.body.visible = armorModel.leftArm.visible = armorModel.rightArm.visible = slot == EquipmentSlot.CHEST;
		armorModel.leftLegReal.visible = armorModel.rightLegReal.visible = slot == EquipmentSlot.LEGS;
		armorModel.leftFoot.visible = armorModel.rightFoot.visible = slot == EquipmentSlot.FEET;

		ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, texture);
	}
}
