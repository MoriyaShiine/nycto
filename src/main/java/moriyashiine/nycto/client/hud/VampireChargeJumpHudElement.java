/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.hud;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.VampireChargeJumpComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class VampireChargeJumpHudElement implements HudElement {
	private static final Identifier JUMP_BAR_BACKGROUND_TEXTURE = Nycto.id("hud/vampire_charge_jump/background");
	private static final Identifier JUMP_BAR_PROGRESS_TEXTURE = Nycto.id("hud/vampire_charge_jump/progress");

	@Override
	public void render(DrawContext context, RenderTickCounter tickCounter) {
		PlayerEntity player = MinecraftClient.getInstance().player;
		if (player != null && !player.isSpectator()) {
			VampireChargeJumpComponent vampireChargeJumpComponent = ModEntityComponents.VAMPIRE_CHARGE_JUMP.get(player);
			if (vampireChargeJumpComponent.isEnabled()) {
				float boostProgress = vampireChargeJumpComponent.getBoostProgress();
				if (boostProgress > 0) {
					int x = context.getScaledWindowWidth() / 2 - 91, y = context.getScaledWindowHeight() - 29;
					context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, JUMP_BAR_BACKGROUND_TEXTURE, x, y, 182, 5);
					context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, JUMP_BAR_PROGRESS_TEXTURE, 182, 5, 0, 0, x, y, (int) (182 * boostProgress), 5);
				}
			}
		}
	}
}
