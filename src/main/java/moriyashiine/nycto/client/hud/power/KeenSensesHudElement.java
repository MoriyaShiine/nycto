/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.hud.power;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class KeenSensesHudElement implements HudElement {
	@Override
	public void render(DrawContext context, RenderTickCounter tickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();
		PlayerEntity player = client.player;
		if (player != null && !player.isSpectator()) {
			int fade = MathHelper.lerp(ModEntityComponents.KEEN_SENSES.get(player).getRenderTicks() / 20F, 0, 255);
			if (fade > 0) {
				context.fill(0, 0, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), fade << 24);
			}
		}
	}
}
