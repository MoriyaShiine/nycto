/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class KeenSensesClientState {
	private static boolean active = false;
	private static int distance = 0;
	private static int renderUntil = 0;

	private KeenSensesClientState() {
	}

	public static void set(boolean active, int distance, int renderTicks) {
		KeenSensesClientState.active = active;
		KeenSensesClientState.distance = Math.max(0, distance);
		Player player = Minecraft.getInstance().player;
		KeenSensesClientState.renderUntil = player == null ? 0 : player.tickCount + Math.max(0, renderTicks);
	}

	public static boolean isActive(Player player) {
		return player != null && Minecraft.getInstance().player == player && active;
	}

	public static int distance() {
		return distance;
	}

	public static int renderTicks() {
		Player player = Minecraft.getInstance().player;
		return player == null ? 0 : Math.max(0, renderUntil - player.tickCount);
	}
}
