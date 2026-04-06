/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.nycto.client.renderer.entity.armor.model.ThralledHorseHornsModel;
import moriyashiine.nycto.client.renderer.entity.state.VampiricThrallRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.animal.equine.HorseModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Map;

public class ThralledHorseHornsLayer extends RenderLayer<HorseRenderState, HorseModel> {
	private static final Map<Item, Identifier> TEXTURE_MAP = Map.of(
			Items.LEATHER_HORSE_ARMOR, Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_horns/leather.png"),
			Items.COPPER_HORSE_ARMOR, Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_horns/copper.png"),
			Items.IRON_HORSE_ARMOR, Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_horns/iron.png"),
			Items.GOLDEN_HORSE_ARMOR, Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_horns/golden.png"),
			Items.DIAMOND_HORSE_ARMOR, Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_horns/diamond.png"),
			Items.NETHERITE_HORSE_ARMOR, Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_horns/netherite.png")
	);

	private final ThralledHorseHornsModel hornsModel;

	public ThralledHorseHornsLayer(RenderLayerParent<HorseRenderState, HorseModel> renderer, ModelPart root) {
		super(renderer);
		hornsModel = new ThralledHorseHornsModel(root);
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, HorseRenderState state, float yRot, float xRot) {
		VampiricThrallRenderState vampiricThrallRenderState = state.getData(VampiricThrallRenderState.KEY);
		if (vampiricThrallRenderState != null && vampiricThrallRenderState.thrallTexture != null && TEXTURE_MAP.containsKey(state.bodyArmorItem.getItem())) {
			int color = 0xFFFFFFFF;
			if (state.bodyArmorItem.has(DataComponents.DYED_COLOR)) {
				color = state.bodyArmorItem.get(DataComponents.DYED_COLOR).rgb();
			} else if (state.bodyArmorItem.is(Items.LEATHER_HORSE_ARMOR)) {
				color = 0xA06540;
			}
			submitNodeCollector.submitModel(hornsModel, state, poseStack, RenderTypes.entityCutout(TEXTURE_MAP.get(state.bodyArmorItem.getItem())), lightCoords, OverlayTexture.NO_OVERLAY, color, null, state.outlineColor, null);
		}
	}
}
