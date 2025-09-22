/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.hud.power;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.vampire.CarnageComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class CarnageHudElement implements HudElement {
	private static final Identifier CARNAGE_OVERLAY = Nycto.id("textures/misc/carnage_overlay.png");

	@Override
	public void render(DrawContext context, RenderTickCounter tickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();
		PlayerEntity player = client.player;
		if (player != null && !player.isSpectator()) {
			CarnageComponent carnageComponent = ModEntityComponents.CARNAGE.get(player);
			if (carnageComponent.isActive()) {
				client.inGameHud.renderOverlay(context, CARNAGE_OVERLAY, carnageComponent.getOverlayOpacity(2 / 3F));
			}
		}
	}
}
