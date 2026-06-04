/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.network.DarkFormClientState;
import moriyashiine.nycto.neoforge.network.DarkFormJumpPayload;
import moriyashiine.nycto.neoforge.network.SetActivePowerPayload;
import moriyashiine.nycto.neoforge.network.UseActivePowerPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;

import java.util.List;

@EventBusSubscriber(modid = NyctoNeoForge.MOD_ID, value = Dist.CLIENT)
public final class NyctoClientInputEvents {
	private static int jumpPacketCooldown = 0;

	private NyctoClientInputEvents() {
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Post event) {
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		if (player == null || minecraft.screen != null) {
			jumpPacketCooldown = 0;
			return;
		}
		boolean jumpDown = minecraft.options.keyJump.isDown();
		if (jumpPacketCooldown > 0) {
			jumpPacketCooldown--;
		}
		if (jumpDown && jumpPacketCooldown <= 0 && DarkFormClientState.isActive(player)) {
			DarkFormJumpPayload.send();
			jumpPacketCooldown = 5;
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		if (minecraft.screen != null || !PowerHotbarClientState.isActive(player)) {
			return;
		}
		if (scrollPower(player, -event.getScrollDeltaY())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onUseKey(InputEvent.InteractionKeyMappingTriggered event) {
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		if (minecraft.screen != null || !event.isUseItem() || !PowerHotbarClientState.isActive(player)) {
			return;
		}
		NyctoData.getActivePower(player).ifPresent(power -> {
			if (NyctoData.getCooldown(player, power) <= 0) {
				UseActivePowerPayload.send();
			}
		});
		event.setSwingHand(false);
		event.setCanceled(true);
	}

	private static boolean scrollPower(Player player, double wheel) {
		if (wheel == 0) {
			return false;
		}
		List<String> powers = PowerHotbarClientState.activePowers(player);
		if (powers.isEmpty()) {
			return false;
		}
		int current = PowerHotbarClientState.activePowerSlot(player, powers);
		selectPower(player, Math.floorMod(current + (int) Math.signum(wheel), powers.size()));
		return true;
	}

	public static void selectPower(Player player, int index) {
		List<String> powers = PowerHotbarClientState.activePowers(player);
		if (index >= 0 && index < powers.size()) {
			SetActivePowerPayload.send(index);
		}
	}
}
