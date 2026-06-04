/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import moriyashiine.nycto.neoforge.item.VampireArmorItem;
import moriyashiine.nycto.neoforge.network.AddBloodBarrierParticlesPayload;
import moriyashiine.nycto.neoforge.network.SyncBloodBarrierPayload;
import moriyashiine.nycto.neoforge.power.NyctoPowers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class NyctoData {
	public static final int DEFAULT_MAX_BLOOD = 100;
	public static final int DEFAULT_STARTING_BLOOD = DEFAULT_MAX_BLOOD;

	private static final String VAMPIRE = "nycto_vampire";
	private static final String BLOOD = "nycto_blood";
	private static final String MAX_BLOOD = "nycto_max_blood";
	private static final String POWERS = "nycto_powers";
	private static final String COOLDOWNS = "nycto_power_cooldowns";
	private static final String ACTIVE_POWER_INDEX = "nycto_active_power_index";
	private static final String BLOOD_BARRIER_TICKS = "nycto_blood_barrier_ticks";
	private static final String UPGRADE_COST_SEED = "nycto_upgrade_cost_seed";
	private static final String NIGHT_VISION_ENABLED = "nycto_night_vision_enabled";

	private NyctoData() {
	}

	public static boolean isVampire(Player player) {
		return player.getPersistentData().getBoolean(VAMPIRE);
	}

	public static void setVampire(Player player, boolean vampire) {
		CompoundTag data = player.getPersistentData();
		data.putBoolean(VAMPIRE, vampire);
		if (vampire) {
			setMaxBlood(player, getMaxBlood(player));
			if (getBlood(player) <= 0) {
				setBlood(player, DEFAULT_STARTING_BLOOD);
			}
		}
		syncIfServer(player);
	}

	public static int getBlood(Player player) {
		return clamp(player.getPersistentData().getInt(BLOOD), 0, getMaxBlood(player));
	}

	public static int setBlood(Player player, int amount) {
		int blood = clamp(amount, 0, getMaxBlood(player));
		player.getPersistentData().putInt(BLOOD, blood);
		syncIfServer(player);
		return blood;
	}

	public static int addBlood(Player player, int amount) {
		return setBlood(player, getBlood(player) + amount);
	}

	public static int getMaxBlood(Player player) {
		int maxBlood = player.getPersistentData().getInt(MAX_BLOOD);
		int baseMaxBlood = maxBlood > 0 ? maxBlood : DEFAULT_MAX_BLOOD;
		return player.level().isClientSide() ? baseMaxBlood : baseMaxBlood + VampireArmorItem.getMaxBloodBonus(player);
	}

	public static int setMaxBlood(Player player, int amount) {
		int maxBlood = Math.max(1, amount);
		player.getPersistentData().putInt(MAX_BLOOD, maxBlood);
		setBlood(player, getBlood(player));
		syncIfServer(player);
		return maxBlood;
	}

	public static Set<String> getPowers(Player player) {
		LinkedHashSet<String> powers = new LinkedHashSet<>();
		CompoundTag data = player.getPersistentData();
		if (data.contains(POWERS, Tag.TAG_LIST)) {
			ListTag list = data.getList(POWERS, Tag.TAG_STRING);
			for (int i = 0; i < list.size(); i++) {
				addSanitizedPower(powers, list.getString(i));
			}
		} else {
			String raw = data.getString(POWERS);
			if (!raw.isBlank()) {
				Arrays.stream(raw.split(",")).forEach(power -> addSanitizedPower(powers, power));
			}
		}
		writePowers(player, powers);
		return powers;
	}

	public static boolean hasPower(Player player, String power) {
		return getPowers(player).contains(normalizePowerName(power));
	}

	public static boolean addPower(Player player, String power) {
		String normalized = normalizePowerName(power);
		if (isRejectedWeakness(normalized)) {
			return false;
		}
		Set<String> powers = getPowers(player);
		boolean added = powers.add(normalized);
		writePowers(player, powers);
		if (added) {
			if (NyctoPowers.NIGHT_VISION.equals(normalized)) {
				setNightVisionEnabled(player, true);
			}
			syncIfServer(player);
		}
		return added;
	}

	public static boolean removePower(Player player, String power) {
		String normalized = normalizePowerName(power);
		Set<String> powers = getPowers(player);
		boolean removed = powers.remove(normalized);
		writePowers(player, powers);
		if (removed) {
			if (NyctoPowers.NIGHT_VISION.equals(normalized)) {
				setNightVisionEnabled(player, false);
			}
			syncIfServer(player);
		}
		return removed;
	}

	public static int getCooldown(Player player, String power) {
		return Math.max(0, cooldowns(player).getInt(normalizePowerName(power)));
	}

	public static void setCooldown(Player player, String power, int ticks) {
		String normalized = normalizePowerName(power);
		CompoundTag cooldowns = cooldowns(player);
		if (ticks <= 0) {
			cooldowns.remove(normalized);
		} else {
			cooldowns.putInt(normalized, ticks);
		}
		player.getPersistentData().put(COOLDOWNS, cooldowns);
		syncIfServer(player);
	}

	public static int getUpgradeCostSeed(Player player) {
		CompoundTag data = player.getPersistentData();
		if (!data.contains(UPGRADE_COST_SEED, Tag.TAG_ANY_NUMERIC)) {
			data.putInt(UPGRADE_COST_SEED, player.getUUID().hashCode());
		}
		return data.getInt(UPGRADE_COST_SEED);
	}

	public static void advanceUpgradeCostSeed(Player player) {
		int seed = getUpgradeCostSeed(player);
		player.getPersistentData().putInt(UPGRADE_COST_SEED, seed * 1103515245 + 12345);
		syncIfServer(player);
	}

	public static boolean isNightVisionEnabled(Player player) {
		return player.getPersistentData().getBoolean(NIGHT_VISION_ENABLED);
	}

	public static void setNightVisionEnabled(Player player, boolean enabled) {
		player.getPersistentData().putBoolean(NIGHT_VISION_ENABLED, enabled);
		syncIfServer(player);
	}

	public static boolean tickCooldowns(Player player) {
		CompoundTag cooldowns = cooldowns(player);
		boolean changed = false;
		for (String power : new HashSet<>(cooldowns.getAllKeys())) {
			int ticks = cooldowns.getInt(power) - 1;
			if (ticks <= 0) {
				cooldowns.remove(power);
			} else {
				cooldowns.putInt(power, ticks);
			}
			changed = true;
		}
		player.getPersistentData().put(COOLDOWNS, cooldowns);
		if (changed) {
			syncIfServer(player);
		}
		return changed;
	}

	public static int getActivePowerIndex(Player player) {
		Set<String> powers = getPowers(player);
		if (powers.isEmpty()) {
			return 0;
		}
		int activePowerIndex = player.getPersistentData().getInt(ACTIVE_POWER_INDEX);
		return clamp(activePowerIndex, 0, powers.size() - 1);
	}

	public static int getBloodBarrierLayers(Player player) {
		return clamp(player.getPersistentData().getInt(NyctoPowers.TAG_BLOOD_BARRIER_LAYERS), 0, 3);
	}

	public static int getHealBlockTicks(LivingEntity entity) {
		return Math.max(0, entity.getPersistentData().getInt(NyctoPowers.TAG_HEAL_BLOCK_UNTIL) - entity.tickCount);
	}

	public static void applyHealBlock(LivingEntity entity, int ticks) {
		int until = ticks <= 0 ? 0 : entity.tickCount + ticks;
		entity.getPersistentData().putInt(NyctoPowers.TAG_HEAL_BLOCK_UNTIL, until);
		if (entity instanceof ServerPlayer player) {
			syncIfServer(player);
		}
	}

	public static void clearHealBlock(LivingEntity entity) {
		entity.getPersistentData().remove(NyctoPowers.TAG_HEAL_BLOCK_UNTIL);
		if (entity instanceof ServerPlayer player) {
			syncIfServer(player);
		}
	}

	public static void setBloodBarrierLayers(Player player, int layers) {
		int clampedLayers = clamp(layers, 0, 3);
		player.getPersistentData().putInt(NyctoPowers.TAG_BLOOD_BARRIER_LAYERS, clampedLayers);
		if (clampedLayers == 0) {
			player.getPersistentData().putInt(BLOOD_BARRIER_TICKS, 0);
		}
		SyncBloodBarrierPayload.send(player, clampedLayers);
		syncIfServer(player);
	}

	public static void startBloodBarrier(Player player, int layers, int ticks) {
		int clampedLayers = clamp(layers, 0, 3);
		player.getPersistentData().putInt(NyctoPowers.TAG_BLOOD_BARRIER_LAYERS, clampedLayers);
		player.getPersistentData().putInt(BLOOD_BARRIER_TICKS, Math.max(0, ticks));
		SyncBloodBarrierPayload.send(player, clampedLayers);
		syncIfServer(player);
		for (int i = 0; i < clampedLayers; i++) {
			AddBloodBarrierParticlesPayload.send(player, i);
		}
	}

	public static void tickBloodBarrier(Player player) {
		int layers = getBloodBarrierLayers(player);
		if (layers <= 0) {
			return;
		}
		int ticks = player.getPersistentData().getInt(BLOOD_BARRIER_TICKS);
		if (ticks <= 1) {
			for (int i = 0; i < layers; i++) {
				AddBloodBarrierParticlesPayload.send(player, i);
			}
			setBloodBarrierLayers(player, 0);
			return;
		}
		player.getPersistentData().putInt(BLOOD_BARRIER_TICKS, ticks - 1);
	}

	public static int setActivePowerIndex(Player player, int index) {
		Set<String> powers = getPowers(player);
		int activePowerIndex = powers.isEmpty() ? 0 : clamp(index, 0, powers.size() - 1);
		player.getPersistentData().putInt(ACTIVE_POWER_INDEX, activePowerIndex);
		syncIfServer(player);
		return activePowerIndex;
	}

	public static Optional<String> getActivePower(Player player) {
		Set<String> powers = getPowers(player);
		if (powers.isEmpty()) {
			return Optional.empty();
		}
		int index = getActivePowerIndex(player);
		int current = 0;
		for (String power : powers) {
			if (current == index) {
				return Optional.of(power);
			}
			current++;
		}
		return Optional.empty();
	}

	public static void copy(ServerPlayer original, ServerPlayer target) {
		CompoundTag source = original.getPersistentData();
		CompoundTag destination = target.getPersistentData();
		destination.putBoolean(VAMPIRE, source.getBoolean(VAMPIRE));
		destination.putInt(BLOOD, source.getInt(BLOOD));
		destination.putInt(MAX_BLOOD, source.contains(MAX_BLOOD, Tag.TAG_ANY_NUMERIC) ? source.getInt(MAX_BLOOD) : DEFAULT_MAX_BLOOD);
		if (source.contains(POWERS)) {
			destination.put(POWERS, source.get(POWERS).copy());
		}
		if (source.contains(COOLDOWNS, Tag.TAG_COMPOUND)) {
			destination.put(COOLDOWNS, source.getCompound(COOLDOWNS).copy());
		}
		if (source.contains(UPGRADE_COST_SEED, Tag.TAG_ANY_NUMERIC)) {
			destination.putInt(UPGRADE_COST_SEED, source.getInt(UPGRADE_COST_SEED));
		}
		destination.putBoolean(NIGHT_VISION_ENABLED, source.getBoolean(NIGHT_VISION_ENABLED));
		destination.putInt(ACTIVE_POWER_INDEX, source.getInt(ACTIVE_POWER_INDEX));
		destination.putInt(NyctoPowers.TAG_BLOOD_BARRIER_LAYERS, source.getInt(NyctoPowers.TAG_BLOOD_BARRIER_LAYERS));
		destination.putInt(BLOOD_BARRIER_TICKS, source.getInt(BLOOD_BARRIER_TICKS));
		getPowers(target);
		setBlood(target, getBlood(target));
		syncIfServer(target);
	}

	public static Map<String, Integer> getCooldowns(Player player) {
		CompoundTag cooldowns = cooldowns(player);
		Map<String, Integer> result = new LinkedHashMap<>();
		for (String power : cooldowns.getAllKeys()) {
			int ticks = cooldowns.getInt(power);
			if (ticks > 0) {
				result.put(power, ticks);
			}
		}
		return result;
	}

	public static void applySyncedState(Player player, boolean vampire, int blood, int maxBlood, List<String> powers, Map<String, Integer> cooldowns, int activePowerIndex, int bloodBarrierLayers, int healBlockTicks, int upgradeCostSeed, boolean nightVisionEnabled) {
		CompoundTag data = player.getPersistentData();
		data.putBoolean(VAMPIRE, vampire);
		data.putInt(MAX_BLOOD, Math.max(1, maxBlood));
		data.putInt(BLOOD, clamp(blood, 0, getMaxBlood(player)));
		LinkedHashSet<String> sanitizedPowers = new LinkedHashSet<>();
		for (String power : powers) {
			addSanitizedPower(sanitizedPowers, power);
		}
		writePowers(player, sanitizedPowers);
		CompoundTag syncedCooldowns = new CompoundTag();
		for (Map.Entry<String, Integer> entry : cooldowns.entrySet()) {
			String power = normalizePowerName(entry.getKey());
			int ticks = Math.max(0, entry.getValue());
			if (!power.isEmpty() && ticks > 0) {
				syncedCooldowns.putInt(power, ticks);
			}
		}
		data.put(COOLDOWNS, syncedCooldowns);
		data.putInt(ACTIVE_POWER_INDEX, sanitizedPowers.isEmpty() ? 0 : clamp(activePowerIndex, 0, sanitizedPowers.size() - 1));
		data.putInt(NyctoPowers.TAG_BLOOD_BARRIER_LAYERS, clamp(bloodBarrierLayers, 0, 3));
		data.putInt(NyctoPowers.TAG_HEAL_BLOCK_UNTIL, healBlockTicks <= 0 ? 0 : player.tickCount + healBlockTicks);
		data.putInt(UPGRADE_COST_SEED, upgradeCostSeed);
		data.putBoolean(NIGHT_VISION_ENABLED, nightVisionEnabled);
	}

	public static String normalizePowerName(String power) {
		return power == null ? "" : power.trim().toLowerCase(Locale.ROOT);
	}

	public static boolean isRejectedWeakness(String power) {
		String normalized = normalizePowerName(power);
		String path = normalized.contains(":") ? normalized.substring(normalized.indexOf(':') + 1) : normalized;
		return switch (path) {
			case "thin_blood", "pyrophobia", "hydrophobia", "rich_tastes", "vile_presence", "humanity" -> true;
			default -> false;
		};
	}

	private static void addSanitizedPower(Set<String> powers, String power) {
		String normalized = normalizePowerName(power);
		if (!normalized.isEmpty() && !isRejectedWeakness(normalized)) {
			powers.add(normalized);
		}
	}

	private static void writePowers(Player player, Set<String> powers) {
		ListTag list = new ListTag(powers.size());
		for (String power : powers) {
			if (!isRejectedWeakness(power)) {
				list.add(StringTag.valueOf(normalizePowerName(power)));
			}
		}
		player.getPersistentData().put(POWERS, list);
		int activePowerIndex = list.isEmpty() ? 0 : clamp(player.getPersistentData().getInt(ACTIVE_POWER_INDEX), 0, list.size() - 1);
		player.getPersistentData().putInt(ACTIVE_POWER_INDEX, activePowerIndex);
	}

	private static CompoundTag cooldowns(Player player) {
		CompoundTag data = player.getPersistentData();
		if (!data.contains(COOLDOWNS, Tag.TAG_COMPOUND)) {
			data.put(COOLDOWNS, new CompoundTag());
		}
		return data.getCompound(COOLDOWNS);
	}

	private static int clamp(int value, int min, int max) {
		return Math.max(min, Math.min(max, value));
	}

	private static void syncIfServer(Player player) {
		if (player instanceof ServerPlayer serverPlayer) {
			moriyashiine.nycto.neoforge.network.SyncPlayerDataPayload.send(serverPlayer);
		}
	}
}
