/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.gui.hud;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.SunExposureComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class SunExposureHudElement implements HudElement {
	private static final Identifier SUN_EXPOSURE_GRADIENT = Nycto.id("textures/misc/sun_exposure_gradient.png");
	private static final Identifier SUN_EXPOSURE_RAYS = Nycto.id("textures/misc/sun_exposure_rays.png");
	private static final Identifier SUN_EXPOSURE_VEINS = Nycto.id("textures/misc/sun_exposure_veins.png");

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		Minecraft client = Minecraft.getInstance();
		if (!client.options.hideGui) {
			Player player = client.player;
			if (player != null) {
				SunExposureComponent sunExposureComponent = ModEntityComponents.SUN_EXPOSURE.get(player);
				if (sunExposureComponent.shouldTick()) {
					float progress = sunExposureComponent.getExposureTime() / (float) SunExposureComponent.MAX_EXPOSURE_TIME;
					float alpha = Math.min(1, progress * 2);
					if (alpha > 0) {
						client.gui.extractTextureOverlay(graphics, SUN_EXPOSURE_GRADIENT, alpha);
						client.gui.extractTextureOverlay(graphics, SUN_EXPOSURE_RAYS, alpha);
						float veinAlpha = Mth.lerp(progress, -0.5F, 0.5F);
						if (veinAlpha > 0) {
							client.gui.extractTextureOverlay(graphics, SUN_EXPOSURE_VEINS, veinAlpha);
						}
					}
				}
			}
		}
	}
}
