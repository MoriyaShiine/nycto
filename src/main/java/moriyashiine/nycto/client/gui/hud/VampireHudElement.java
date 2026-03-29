/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.gui.hud;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

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
	public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		Minecraft client = Minecraft.getInstance();
		if (!client.options.hideGui && client.player.isShiftKeyDown() && client.player.slib$exists() && NyctoAPI.isVampire(client.player)) {
			if (client.crosshairPickEntity instanceof LivingEntity living && !living.is(ModEntityTypeTags.HAS_NO_BLOOD)) {
				extractBlood(graphics, living, (int) (graphics.guiWidth() / 2F + 12), (int) (graphics.guiHeight() / 2F + 9), 5);
			}
		}
	}

	public static void extractBlood(GuiGraphicsExtractor graphics, LivingEntity living, int x, int y, int droplets) {
		boolean badBlood = living.hasEffect(MobEffects.HUNGER) || !living.is(ModEntityTypeTags.HAS_QUALITY_BLOOD);
		float blood = ((float) ModEntityComponents.BLOOD.get(living).getBlood() / BloodComponent.MAX_BLOOD * droplets);
		int full = (int) blood;
		for (int i = 0; i < full; i++) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getTexture(1, badBlood), x - i * 8, y, 9, 9);
		}
		if (full < droplets) {
			float remaining = blood - full;
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getTexture(remaining, badBlood), x - full * 8, y, 9, 9);
		}
		for (int i = (full + 1); i < droplets; i++) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getTexture(0, badBlood), x - i * 8, y, 9, 9);
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
