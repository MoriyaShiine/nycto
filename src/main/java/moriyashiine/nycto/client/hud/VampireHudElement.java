/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.hud;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;

public class VampireHudElement implements HudElement {
	private static final Identifier[] BLOOD = new Identifier[16];

	static {
		for (int i = 0; i < BLOOD.length; i++) {
			boolean hunger = i >= 8;
			int index = hunger ? i - 8 : i;
			BLOOD[i] = Nycto.id("hud/blood/" + (hunger ? "hunger/" : "") + "blood_" + index);
		}
	}

	@Override
	public void render(DrawContext context, RenderTickCounter tickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player.isSneaking() && client.player.isPartOfGame() && NyctoAPI.isVampire(client.player)) {
			if (client.targetedEntity instanceof LivingEntity living && !living.getType().isIn(ModEntityTypeTags.HAS_NO_BLOOD)) {
				drawBlood(context, living, (int) (context.getScaledWindowWidth() / 2F + 12), (int) (context.getScaledWindowHeight() / 2F + 9), 5);
			}
		}
	}

	public static void drawBlood(DrawContext context, LivingEntity living, int xPos, int yPos, int droplets) {
		boolean hunger = living.hasStatusEffect(StatusEffects.HUNGER) || !living.getType().isIn(ModEntityTypeTags.HAS_QUALITY_BLOOD);
		float blood = ((float) ModEntityComponents.BLOOD.get(living).getBlood() / BloodComponent.MAX_BLOOD * droplets);
		int full = (int) blood;
		for (int i = 0; i < full; i++) {
			context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getTexture(1, hunger), xPos - i * 8, yPos, 9, 9);
		}
		if (full < droplets) {
			float remaining = blood - full;
			context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getTexture(remaining, hunger), xPos - full * 8, yPos, 9, 9);
		}
		for (int i = (full + 1); i < droplets; i++) {
			context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getTexture(0, hunger), xPos - i * 8, yPos, 9, 9);
		}
	}

	private static Identifier getTexture(float remaining, boolean hunger) {
		int i = 7;
		if (remaining != 1) {
			if (remaining > 5 / 6F) {
				i = 6;
			} else if (remaining > 4 / 6F) {
				i = 5;
			} else if (remaining > 3 / 6F) {
				i = 4;
			} else if (remaining > 2 / 6F) {
				i = 3;
			} else if (remaining > 1 / 6F) {
				i = 2;
			} else if (remaining > 0) {
				i = 1;
			} else {
				i = 0;
			}
		}
		return BLOOD[hunger ? i + 8 : i];
	}
}
