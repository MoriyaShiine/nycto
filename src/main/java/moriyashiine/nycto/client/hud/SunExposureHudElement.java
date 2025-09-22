/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.hud;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.SunExposureComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SunExposureHudElement implements HudElement {
	private static final Identifier SUN_EXPOSURE_GRADIENT = Nycto.id("textures/misc/sun_exposure_gradient.png");
	private static final Identifier SUN_EXPOSURE_RAYS = Nycto.id("textures/misc/sun_exposure_rays.png");
	private static final Identifier SUN_EXPOSURE_VEINS = Nycto.id("textures/misc/sun_exposure_veins.png");

	@Override
	public void render(DrawContext context, RenderTickCounter tickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();
		PlayerEntity player = client.player;
		if (player != null && !player.isSpectator()) {
			SunExposureComponent sunExposureComponent = ModEntityComponents.SUN_EXPOSURE.get(player);
			if (sunExposureComponent.shouldTick()) {
				float progress = sunExposureComponent.getExposureTime() / (float) SunExposureComponent.MAX_EXPOSURE_TIME;
				float opacity = Math.min(1, progress * 2);
				if (opacity > 0) {
					client.inGameHud.renderOverlay(context, SUN_EXPOSURE_GRADIENT, opacity);
					client.inGameHud.renderOverlay(context, SUN_EXPOSURE_RAYS, opacity);
					float veinOpacity = MathHelper.lerp(progress, -0.5F, 0.5F);
					if (veinOpacity > 0) {
						client.inGameHud.renderOverlay(context, SUN_EXPOSURE_VEINS, veinOpacity);
					}
				}
			}
		}
	}
}
