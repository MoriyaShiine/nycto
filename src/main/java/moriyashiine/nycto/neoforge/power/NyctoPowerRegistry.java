/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.power;

import moriyashiine.nycto.neoforge.NyctoData;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class NyctoPowerRegistry {
	private static final Map<String, NyctoPower> POWERS = new LinkedHashMap<>();

	private NyctoPowerRegistry() {
	}

	public static boolean register(String name, NyctoPower power) {
		String normalized = NyctoData.normalizePowerName(name);
		if (normalized.isEmpty() || NyctoData.isRejectedWeakness(normalized)) {
			return false;
		}
		POWERS.put(normalized, power);
		return true;
	}

	public static Optional<NyctoPower> get(String name) {
		return Optional.ofNullable(POWERS.get(NyctoData.normalizePowerName(name)));
	}

	public static Set<String> keys() {
		return Collections.unmodifiableSet(POWERS.keySet());
	}

	public static boolean isRegistered(String name) {
		return POWERS.containsKey(NyctoData.normalizePowerName(name));
	}

	public static boolean use(ServerPlayer player, String name) {
		String normalized = NyctoData.normalizePowerName(name);
		if (!NyctoData.isVampire(player) || !NyctoData.hasPower(player, normalized) || NyctoData.getCooldown(player, normalized) > 0) {
			return false;
		}
		Optional<NyctoPower> optional = get(normalized);
		if (optional.isEmpty()) {
			return false;
		}
		NyctoPower power = optional.get();
		int bloodCost = Math.max(0, power.bloodCost(player));
		if (NyctoData.getBlood(player) < bloodCost || !power.canUse(player)) {
			return false;
		}
		if (!power.use(player)) {
			return false;
		}
		if (bloodCost > 0) {
			NyctoData.addBlood(player, -bloodCost);
		}
		NyctoData.setCooldown(player, normalized, Math.max(0, power.cooldownTicks(player)));
		return true;
	}

	public static void tick(ServerPlayer player, String name) {
		get(name).ifPresent(power -> power.tick(player));
	}
}
