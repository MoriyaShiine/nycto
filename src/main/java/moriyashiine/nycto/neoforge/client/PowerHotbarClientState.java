/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.power.NyctoPowerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public final class PowerHotbarClientState {
	private static final int MAX_VISIBLE_POWERS = 9;
	private static final int POWER_SLOT_STEP = 20;
	private static final ResourceLocation POWER_HOTBAR = NyctoNeoForge.id("hud/power_hotbar/vampire/hotbar");
	private static final ResourceLocation POWER_HOTBAR_OVERLAY = NyctoNeoForge.id("hud/power_hotbar/vampire/hotbar_overlay");
	private static final ResourceLocation POWER_SELECTION = NyctoNeoForge.id("hud/power_hotbar/vampire/selection");
	private static final ResourceLocation POWER_SELECTION_OVERLAY = NyctoNeoForge.id("hud/power_hotbar/vampire/selection_overlay");

	private PowerHotbarClientState() {
	}

	public static boolean isActive(Player player) {
		return player != null
				&& NyctoData.isVampire(player)
				&& NyctoClientNeoForge.POWER_HOTBAR_KEYMAPPING.isDown()
				&& !activePowers(player).isEmpty();
	}

	public static List<String> activePowers(Player player) {
		List<String> powers = new ArrayList<>(NyctoData.getPowers(player).size());
		for (String power : NyctoData.getPowers(player)) {
			if (NyctoPowerRegistry.isRegistered(power)) {
				powers.add(power);
			}
		}
		return powers;
	}

	public static int activePowerSlot(Player player, List<String> powers) {
		if (powers.isEmpty()) {
			return 0;
		}
		String activePower = NyctoData.getActivePower(player).orElse(powers.getFirst());
		int index = powers.indexOf(activePower);
		return index < 0 ? 0 : Mth.clamp(index, 0, powers.size() - 1);
	}

	public static int activePageStart(Player player, List<String> powers) {
		if (powers.isEmpty()) {
			return 0;
		}
		return activePowerSlot(player, powers) / MAX_VISIBLE_POWERS * MAX_VISIBLE_POWERS;
	}

	public static int visiblePagePowerIndex(Player player, int visibleSlot) {
		List<String> powers = activePowers(player);
		int index = activePageStart(player, powers) + visibleSlot;
		return index >= 0 && index < powers.size() ? index : -1;
	}

	public static void render(GuiGraphics guiGraphics, Player player) {
		List<String> powers = activePowers(player);
		int x = guiGraphics.guiWidth() / 2 - 91;
		int y = guiGraphics.guiHeight() - 22;
		guiGraphics.blitSprite(POWER_HOTBAR, x, y, 182, 22);
		guiGraphics.blitSprite(POWER_HOTBAR_OVERLAY, x, y, 182, 22);
		int pageStart = activePageStart(player, powers);
		int activeIndex = activePowerSlot(player, powers) - pageStart;
		guiGraphics.blitSprite(POWER_SELECTION, x - 1 + activeIndex * POWER_SLOT_STEP, y - 1, 24, 23);
		guiGraphics.blitSprite(POWER_SELECTION_OVERLAY, x - 1 + activeIndex * POWER_SLOT_STEP, y - 1, 24, 23);
		for (int i = pageStart; i < powers.size() && i < pageStart + MAX_VISIBLE_POWERS; i++) {
			String power = powers.get(i);
			int visibleSlot = i - pageStart;
			int iconX = x + 3 + visibleSlot * POWER_SLOT_STEP;
			int iconY = y + 3;
			guiGraphics.blit(powerTexture(power), iconX, iconY, 0, 0, 16, 16, 16, 16);
			int cooldown = NyctoData.getCooldown(player, power);
			if (cooldown > 0) {
				int maximumCooldown = NyctoPowerRegistry.get(power).map(instance -> instance.cooldownTicks()).orElse(cooldown);
				float cooldownProgress = Mth.clamp(cooldown / (float) Math.max(1, maximumCooldown), 0, 1);
				int y1 = iconY + Mth.floor(16 * (1 - cooldownProgress));
				int y2 = y1 + Mth.ceil(16 * cooldownProgress);
				guiGraphics.fill(iconX, y1, iconX + 16, y2, 0x7FFFFF7F);
			}
		}
		String activePower = NyctoData.getActivePower(player).orElse("");
		if (!activePower.isBlank()) {
			guiGraphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable("power.nycto." + powerPath(activePower)), guiGraphics.guiWidth() / 2, y - 11, 0xFFE6E0E0);
		}
	}

	private static ResourceLocation powerTexture(String power) {
		return NyctoNeoForge.id("textures/power/" + powerPath(power) + ".png");
	}

	private static String powerPath(String power) {
		String normalized = NyctoData.normalizePowerName(power);
		int namespaceSeparator = normalized.indexOf(':');
		return namespaceSeparator >= 0 ? normalized.substring(namespaceSeparator + 1) : normalized;
	}
}
