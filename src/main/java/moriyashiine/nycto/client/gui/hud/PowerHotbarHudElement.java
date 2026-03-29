/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.gui.hud;

import moriyashiine.nycto.api.world.power.ActivePower;
import moriyashiine.nycto.api.world.power.PowerInstance;
import moriyashiine.nycto.client.event.PowerClientEvent;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class PowerHotbarHudElement implements HudElement {
	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		Minecraft client = Minecraft.getInstance();
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(client.player);
		if (PowerClientEvent.isActive(client.player, transformationComponent)) {
			extractPowers(graphics, transformationComponent);
			Identifier hotbarOverlayTexture = transformationComponent.getTransformation().getPowerHotbarTextureSet().powerHotbarOverlayTexture();
			if (hotbarOverlayTexture != null) {
				graphics.blitSprite(RenderPipelines.GUI_TEXTURED, hotbarOverlayTexture, graphics.guiWidth() / 2 - 91, graphics.guiHeight() - 22, 182, 22);
			}
			Identifier selectionOverlayTexture = transformationComponent.getTransformation().getPowerHotbarTextureSet().powerHotbarSelectionOverlayTexture();
			if (selectionOverlayTexture != null) {
				graphics.blitSprite(RenderPipelines.GUI_TEXTURED, selectionOverlayTexture, graphics.guiWidth() / 2 - 92 + PowerClientEvent.getActivePowersIndex(transformationComponent) * 20, graphics.guiHeight() - 23, 24, 23);
			}
		}
	}

	private static void extractPowers(GuiGraphicsExtractor graphics, TransformationComponent transformationComponent) {
		int xOffset = 0;
		for (PowerInstance powerInstance : transformationComponent.getPowers()) {
			if (powerInstance.getPower() instanceof ActivePower power) {
				int x = graphics.guiWidth() / 2 - 88 + xOffset;
				int y = graphics.guiHeight() - 19;

				graphics.blit(RenderPipelines.GUI_TEXTURED, powerInstance.getPower().getOrCreateTextureLocation(), x, y, 0, 0, 16, 16, 16, 16);
				float cooldownProgress = powerInstance.getCooldown() / (float) power.getCooldown();
				if (cooldownProgress > 0) {
					int y1 = y + Mth.floor(16 * (1 - cooldownProgress));
					int y2 = y1 + Mth.ceil(16 * cooldownProgress);
					graphics.fill(RenderPipelines.GUI, x, y1, x + 16, y2, Integer.MAX_VALUE);
				}

				xOffset += 20;
			}
		}
	}
}
