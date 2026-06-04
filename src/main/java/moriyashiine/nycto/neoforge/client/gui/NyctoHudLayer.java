/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.gui;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.client.KeenSensesClientState;
import moriyashiine.nycto.neoforge.item.VampiricDaggerItem;
import moriyashiine.nycto.neoforge.network.CarnageClientState;
import moriyashiine.nycto.neoforge.util.NyctoBloodUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class NyctoHudLayer {
	private static final int ICON_SIZE = 16;
	private static final int BLOOD_DROPLETS = 10;
	private static final int TARGET_BLOOD_DROPLETS = 5;
	private static final int CONTEXT_PROGRESS_WIDTH = 182;
	private static final int CONTEXT_PROGRESS_HEIGHT = 5;
	private static final int STATUS_Y_OFFSET = 39;
	private static final int CONTEXT_PROGRESS_Y_OFFSET = 29;
	private static final ResourceLocation CONTEXT_PROGRESS_BACKGROUND = NyctoNeoForge.id("hud/vampire_charge_jump/background");
	private static final ResourceLocation CONTEXT_PROGRESS_FILL = NyctoNeoForge.id("hud/vampire_charge_jump/progress");
	private static final ResourceLocation CARNAGE_OVERLAY = NyctoNeoForge.id("textures/misc/carnage_overlay.png");
	private static final ResourceLocation HEAL_BLOCK_FULL = NyctoNeoForge.id("hud/heal_block/full");
	private static final ResourceLocation HEAL_BLOCK_FULL_BLINKING = NyctoNeoForge.id("hud/heal_block/full_blinking");
	private static final ResourceLocation HEAL_BLOCK_HALF = NyctoNeoForge.id("hud/heal_block/half");
	private static final ResourceLocation HEAL_BLOCK_HALF_BLINKING = NyctoNeoForge.id("hud/heal_block/half_blinking");
	private static final ResourceLocation[] BLOOD = new ResourceLocation[8];
	private static final ResourceLocation[] TARGET_BLOOD = new ResourceLocation[8];
	private static int drainPreviewTicks = 0;
	private static int lastDrainPreviewTick = -1;

	static {
		for (int i = 0; i < BLOOD.length; i++) {
			BLOOD[i] = NyctoNeoForge.id("hud/blood/hunger/blood_" + i);
			TARGET_BLOOD[i] = NyctoNeoForge.id("hud/blood/blood_" + i);
		}
	}

	private NyctoHudLayer() {
	}

	public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		if (player == null || minecraft.options.hideGui || player.isSpectator()) {
			return;
		}

		renderCarnageOverlay(guiGraphics, player);
		renderKeenSensesOverlay(guiGraphics, player);

		renderHeldBloodToolProgress(guiGraphics, minecraft, player);

		HudState state = HudState.read(player);
		if (state == null) {
			return;
		}
		renderHealBlock(guiGraphics, player, state);
		renderBlood(guiGraphics, state);
	}

	private static void renderCarnageOverlay(GuiGraphics guiGraphics, Player player) {
		float opacity = CarnageClientState.opacity(player, 2 / 3F);
		if (opacity <= 0) {
			return;
		}
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.setColor(1.0F, 1.0F, 1.0F, opacity);
		guiGraphics.blit(CARNAGE_OVERLAY, 0, 0, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight());
		guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableBlend();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
	}

	private static void renderKeenSensesOverlay(GuiGraphics guiGraphics, Player player) {
		boolean active = KeenSensesClientState.isActive(player);
		int renderTicks = KeenSensesClientState.renderTicks();
		if (!active && renderTicks <= 0) {
			return;
		}
		float fade = active ? 0.28F : Mth.clamp(renderTicks / 20F, 0, 1) * 0.28F;
		if (renderTicks > 0) {
			fade = Math.max(fade, Mth.clamp(renderTicks / 20F, 0, 1) * 0.45F);
		}
		int alpha = Mth.clamp((int) (fade * 255), 0, 160);
		guiGraphics.fill(0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), alpha << 24);
		if (active) {
			int distance = KeenSensesClientState.distance();
			int width = Math.min(guiGraphics.guiWidth(), Math.max(24, distance * 5));
			int height = Math.min(guiGraphics.guiHeight(), Math.max(24, distance * 3));
			int x1 = (guiGraphics.guiWidth() - width) / 2;
			int y1 = (guiGraphics.guiHeight() - height) / 2;
			int x2 = x1 + width;
			int y2 = y1 + height;
			int color = 0x55B3131D;
			guiGraphics.hLine(x1, x2, y1, color);
			guiGraphics.hLine(x1, x2, y2, color);
			guiGraphics.vLine(x1, y1, y2, color);
			guiGraphics.vLine(x2, y1, y2, color);
		}
	}

	private static void renderBlood(GuiGraphics guiGraphics, HudState state) {
		int x = guiGraphics.guiWidth() / 2 + 82;
		int y = guiGraphics.guiHeight() - STATUS_Y_OFFSET;
		float blood = state.maxBlood() <= 0 ? 0 : state.blood() / (float) state.maxBlood() * BLOOD_DROPLETS;
		int full = (int) blood;
		for (int i = 0; i < BLOOD_DROPLETS; i++) {
			float remaining = i < full ? 1 : i == full ? blood - full : 0;
			guiGraphics.blitSprite(bloodTexture(remaining), x - i * 8, y, 9, 9);
		}
	}

	private static void renderHealBlock(GuiGraphics guiGraphics, Player player, HudState state) {
		if (state.healBlockTicks() <= 0) {
			return;
		}
		int hearts = Math.min(10, (int) Math.ceil(player.getMaxHealth() / 2.0F));
		float healthHearts = player.getHealth() / 2.0F;
		boolean blinking = player.tickCount / 6 % 2 == 0;
		int x = guiGraphics.guiWidth() / 2 - 91;
		int y = guiGraphics.guiHeight() - STATUS_Y_OFFSET;
		for (int i = 0; i < hearts; i++) {
			float remaining = healthHearts - i;
			if (remaining >= 1) {
				guiGraphics.blitSprite(blinking ? HEAL_BLOCK_FULL_BLINKING : HEAL_BLOCK_FULL, x + i * 8, y, 9, 9);
			} else if (remaining > 0) {
				guiGraphics.blitSprite(blinking ? HEAL_BLOCK_HALF_BLINKING : HEAL_BLOCK_HALF, x + i * 8, y, 9, 9);
			}
		}
	}

	private static ResourceLocation bloodTexture(float remaining) {
		return bloodTexture(BLOOD, remaining);
	}

	private static ResourceLocation targetBloodTexture(float remaining) {
		return bloodTexture(TARGET_BLOOD, remaining);
	}

	private static ResourceLocation bloodTexture(ResourceLocation[] textures, float remaining) {
		int index = 7;
		if (remaining != 1) {
			if (remaining > 5 / 6F) {
				index = 6;
			} else if (remaining > 4 / 6F) {
				index = 5;
			} else if (remaining > 3 / 6F) {
				index = 4;
			} else if (remaining > 2 / 6F) {
				index = 3;
			} else if (remaining > 1 / 6F) {
				index = 2;
			} else if (remaining > 0) {
				index = 1;
			} else {
				index = 0;
			}
		}
		return textures[index];
	}

	private static void renderHeldBloodToolProgress(GuiGraphics guiGraphics, Minecraft minecraft, Player player) {
		ItemStack dagger = heldDagger(player);
		if (!dagger.isEmpty()) {
			int charge = VampiricDaggerItem.getBloodCharge(dagger);
			if (charge > 0) {
				renderProgressBar(guiGraphics, charge / (float) VampiricDaggerItem.MAX_CHARGE);
			}
			resetDrainPreview(player);
			return;
		}
		if (!NyctoData.isVampire(player) || !player.isShiftKeyDown() || !player.getMainHandItem().isEmpty() || !(minecraft.crosshairPickEntity instanceof LivingEntity target) || !NyctoBloodUtil.hasDrainableBlood(target)) {
			resetDrainPreview(player);
			return;
		}
		if (lastDrainPreviewTick != player.tickCount) {
			lastDrainPreviewTick = player.tickCount;
			drainPreviewTicks = Math.min(20, drainPreviewTicks + 1);
		}
		renderProgressBar(guiGraphics, drainPreviewTicks / 20.0F);
		renderTargetBloodPreview(guiGraphics, target);
	}

	private static ItemStack heldDagger(Player player) {
		if (player.getMainHandItem().is(NyctoItems.VAMPIRIC_DAGGER.get())) {
			return player.getMainHandItem();
		}
		if (player.getOffhandItem().is(NyctoItems.VAMPIRIC_DAGGER.get())) {
			return player.getOffhandItem();
		}
		return ItemStack.EMPTY;
	}

	private static void resetDrainPreview(Player player) {
		lastDrainPreviewTick = player.tickCount;
		drainPreviewTicks = 0;
	}

	private static void renderProgressBar(GuiGraphics guiGraphics, float progress) {
		int x = guiGraphics.guiWidth() / 2 - CONTEXT_PROGRESS_WIDTH / 2;
		int y = guiGraphics.guiHeight() - CONTEXT_PROGRESS_Y_OFFSET;
		int fillWidth = Mth.clamp((int) (CONTEXT_PROGRESS_WIDTH * progress), 0, CONTEXT_PROGRESS_WIDTH);
		guiGraphics.blitSprite(CONTEXT_PROGRESS_BACKGROUND, x, y, CONTEXT_PROGRESS_WIDTH, CONTEXT_PROGRESS_HEIGHT);
		if (fillWidth > 0) {
			guiGraphics.enableScissor(x, y, x + fillWidth, y + CONTEXT_PROGRESS_HEIGHT);
			guiGraphics.blitSprite(CONTEXT_PROGRESS_FILL, x, y, CONTEXT_PROGRESS_WIDTH, CONTEXT_PROGRESS_HEIGHT);
			guiGraphics.disableScissor();
		}
	}

	private static void renderTargetBloodPreview(GuiGraphics guiGraphics, LivingEntity target) {
		boolean poorBlood = target.hasEffect(MobEffects.HUNGER) || !NyctoBloodUtil.hasQualityBlood(target);
		float blood = NyctoBloodUtil.getBlood(target) / (float) NyctoBloodUtil.MAX_ENTITY_BLOOD * TARGET_BLOOD_DROPLETS;
		int full = (int) blood;
		int x = guiGraphics.guiWidth() / 2 + 12;
		int y = guiGraphics.guiHeight() / 2 + 9;
		for (int i = 0; i < TARGET_BLOOD_DROPLETS; i++) {
			float remaining = i < full ? 1 : i == full ? blood - full : 0;
			guiGraphics.blitSprite(poorBlood ? bloodTexture(remaining) : targetBloodTexture(remaining), x - i * 8, y, 9, 9);
		}
	}

	private record HudState(int blood, int maxBlood, int bloodBarrierLayers, int healBlockTicks, int activePowerIndex, String activePower, int activeCooldown, List<PowerEntry> powers) {
		private static HudState read(Player player) {
			if (!NyctoData.isVampire(player)) {
				return null;
			}
			int blood = NyctoData.getBlood(player);
			int maxBlood = NyctoData.getMaxBlood(player);
			Set<String> powerSet = NyctoData.getPowers(player);
			int activePowerIndex = NyctoData.getActivePowerIndex(player);
			String activePower = NyctoData.getActivePower(player).orElse("");
			int activeCooldown = activePower.isBlank() ? 0 : NyctoData.getCooldown(player, activePower);
			List<PowerEntry> powers = new ArrayList<>(powerSet.size());
			for (String power : powerSet) {
				powers.add(new PowerEntry(power, NyctoData.getCooldown(player, power)));
			}
			return new HudState(blood, maxBlood, NyctoData.getBloodBarrierLayers(player), NyctoData.getHealBlockTicks(player), activePowerIndex, activePower, activeCooldown, powers);
		}
	}

	private record PowerEntry(String name, int cooldown) {
	}
}
