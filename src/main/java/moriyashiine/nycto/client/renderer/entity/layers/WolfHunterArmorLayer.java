/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.entity.huntertype.HunterType;
import moriyashiine.nycto.client.renderer.entity.model.WolfHunterArmorModel;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.animal.wolf.WolfModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class WolfHunterArmorLayer extends RenderLayer<WolfRenderState, WolfModel> {
	public static final Map<HunterType, ModelLayerLocation> MODEL_LAYERS = new HashMap<>();
	private static final Map<HunterType, WolfHunterArmorModel> MODELS = new HashMap<>();
	private static final Map<Crackiness.Level, Identifier> ARMOR_CRACK_LOCATIONS = Map.of(
			Crackiness.Level.LOW,
			Nycto.id("textures/entity/equipment/wolf_body/crackiness_low.png"),
			Crackiness.Level.MEDIUM,
			Nycto.id("textures/entity/equipment/wolf_body/crackiness_medium.png"),
			Crackiness.Level.HIGH,
			Nycto.id("textures/entity/equipment/wolf_body/crackiness_high.png")
	);

	private final EquipmentLayerRenderer equipmentRenderer;
	private final EntityModelSet modelSet;

	public WolfHunterArmorLayer(RenderLayerParent<WolfRenderState, WolfModel> renderer, EntityModelSet modelSet, EquipmentLayerRenderer equipmentRenderer) {
		super(renderer);
		this.equipmentRenderer = equipmentRenderer;
		this.modelSet = modelSet;
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, WolfRenderState state, float yRot, float xRot) {
		NyctoRegistries.HUNTER_TYPE.forEach(type -> {
			ItemStack armorItem = state.bodyArmorItem;
			if (!state.isBaby && armorItem.is(type.armorTagKey)) {
				WolfHunterArmorModel model = MODELS.computeIfAbsent(type, hunterType -> new WolfHunterArmorModel(modelSet.bakeLayer(MODEL_LAYERS.get(hunterType))));
				equipmentRenderer.renderLayers(
						EquipmentClientInfo.LayerType.WOLF_BODY,
						type.assetKey,
						model,
						state,
						armorItem,
						poseStack,
						submitNodeCollector,
						lightCoords,
						state.outlineColor);
				maybeRenderCracks(poseStack, submitNodeCollector, lightCoords, armorItem, model, state);
			}
		});
	}

	private void maybeRenderCracks(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, ItemStack armorItem, Model<WolfRenderState> model, WolfRenderState state) {
		Crackiness.Level crackiness = Crackiness.WOLF_ARMOR.byDamage(armorItem);
		if (crackiness != Crackiness.Level.NONE) {
			Identifier damageTexture = ARMOR_CRACK_LOCATIONS.get(crackiness);
			submitNodeCollector.submitModel(model, state, poseStack, RenderTypes.armorTranslucent(damageTexture), lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
		}
	}
}
