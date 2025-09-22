/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.hud;

import moriyashiine.nycto.api.power.ActivePower;
import moriyashiine.nycto.api.power.PowerInstance;
import moriyashiine.nycto.client.event.PowerClientEvent;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class PowerHotbarHudElement implements HudElement {
	@Override
	public void render(DrawContext context, RenderTickCounter tickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(client.player);
		if (PowerClientEvent.isActive(client.player, transformationComponent)) {
			renderPowers(context, transformationComponent);
			Identifier hotbarOverlayTexture = transformationComponent.getTransformation().getPowerHotbarTextureSet().powerHotbarOverlayTexture();
			if (hotbarOverlayTexture != null) {
				context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, hotbarOverlayTexture, context.getScaledWindowWidth() / 2 - 91, context.getScaledWindowHeight() - 22, 182, 22);
			}
			Identifier selectionOverlayTexture = transformationComponent.getTransformation().getPowerHotbarTextureSet().powerHotbarSelectionOverlayTexture();
			if (selectionOverlayTexture != null) {
				context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, selectionOverlayTexture, context.getScaledWindowWidth() / 2 - 92 + PowerClientEvent.getActivePowersIndex(transformationComponent) * 20, context.getScaledWindowHeight() - 23, 24, 23);
			}
		}
	}

	private static void renderPowers(DrawContext context, TransformationComponent transformationComponent) {
		int xOffset = 0;
		for (PowerInstance powerInstance : transformationComponent.getPowers()) {
			if (powerInstance.getPower() instanceof ActivePower power) {
				int x = context.getScaledWindowWidth() / 2 - 88 + xOffset;
				int y = context.getScaledWindowHeight() - 19;

				context.drawTexture(RenderPipelines.GUI_TEXTURED, powerInstance.getPower().getTextureLocation(), x, y, 0, 0, 16, 16, 16, 16);
				float cooldownProgress = powerInstance.getCooldown() / (float) power.getCooldown();
				if (cooldownProgress > 0) {
					int y1 = y + MathHelper.floor(16 * (1 - cooldownProgress));
					int y2 = y1 + MathHelper.ceil(16 * cooldownProgress);
					context.fill(RenderPipelines.GUI, x, y1, x + 16, y2, Integer.MAX_VALUE);
				}

				xOffset += 20;
			}
		}
	}
}
