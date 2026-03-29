/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.gui.hud;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.VampireChargeJumpComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public class VampireChargeJumpHudElement implements HudElement {
	private static final Identifier JUMP_BAR_BACKGROUND_TEXTURE = Nycto.id("hud/vampire_charge_jump/background");
	private static final Identifier JUMP_BAR_PROGRESS_TEXTURE = Nycto.id("hud/vampire_charge_jump/progress");

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		Minecraft client = Minecraft.getInstance();
		if (!client.options.hideGui) {
			Player player = client.player;
			if (player != null) {
				VampireChargeJumpComponent vampireChargeJumpComponent = ModEntityComponents.VAMPIRE_CHARGE_JUMP.get(player);
				if (vampireChargeJumpComponent.isEnabled()) {
					float boostProgress = vampireChargeJumpComponent.getBoostProgress();
					if (boostProgress > 0) {
						int x = graphics.guiWidth() / 2 - 91, y = graphics.guiHeight() - 29;
						graphics.blitSprite(RenderPipelines.GUI_TEXTURED, JUMP_BAR_BACKGROUND_TEXTURE, x, y, 182, 5);
						graphics.blitSprite(RenderPipelines.GUI_TEXTURED, JUMP_BAR_PROGRESS_TEXTURE, 182, 5, 0, 0, x, y, (int) (182 * boostProgress), 5);
					}
				}
			}
		}
	}
}
