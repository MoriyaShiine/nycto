/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.armor;

import moriyashiine.nycto.client.render.armor.model.ThralledHorseHornsModel;
import moriyashiine.nycto.client.render.entity.state.VampiricThrallRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.render.entity.state.HorseEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ThralledHorseHornsRenderer extends FeatureRenderer<HorseEntityRenderState, HorseEntityModel> {
	private static final Map<Item, Identifier> TEXTURE_MAP = Map.of(
			Items.LEATHER_HORSE_ARMOR, Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_horns/leather.png"),
			Items.COPPER_HORSE_ARMOR, Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_horns/copper.png"),
			Items.IRON_HORSE_ARMOR, Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_horns/iron.png"),
			Items.GOLDEN_HORSE_ARMOR, Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_horns/golden.png"),
			Items.DIAMOND_HORSE_ARMOR, Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_horns/diamond.png")
	);

	private final ThralledHorseHornsModel hornsModel;

	public ThralledHorseHornsRenderer(FeatureRendererContext<HorseEntityRenderState, HorseEntityModel> context, ModelPart part) {
		super(context);
		hornsModel = new ThralledHorseHornsModel(part);
	}

	@Override
	public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, HorseEntityRenderState state, float limbAngle, float limbDistance) {
		@Nullable VampiricThrallRenderState vampiricThrallRenderState = state.getData(VampiricThrallRenderState.KEY);
		if (vampiricThrallRenderState != null && vampiricThrallRenderState.thrallTexture != null && TEXTURE_MAP.containsKey(state.armorStack.getItem())) {
			int color = 0xFFFFFFFF;
			if (state.armorStack.contains(DataComponentTypes.DYED_COLOR)) {
				color = state.armorStack.get(DataComponentTypes.DYED_COLOR).rgb();
			} else if (state.armorStack.isOf(Items.LEATHER_HORSE_ARMOR)) {
				color = 0xA06540;
			}
			queue.submitModel(hornsModel, state, matrices, RenderLayers.entityCutoutNoCull(TEXTURE_MAP.get(state.armorStack.getItem())), light, OverlayTexture.DEFAULT_UV, color, null, state.outlineColor, null);
		}
	}
}
