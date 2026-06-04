/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.network;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraft.world.entity.monster.Vex;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ThrallClientState {
	private static final Map<Integer, Boolean> THRALLS = new ConcurrentHashMap<>();

	private ThrallClientState() {
	}

	public static void setThrall(int entityId, boolean active) {
		if (active) {
			THRALLS.put(entityId, true);
		} else {
			THRALLS.remove(entityId);
		}
	}

	public static boolean isThrall(Entity entity) {
		return THRALLS.containsKey(entity.getId());
	}

	public static void pruneMissing(Level level) {
		THRALLS.keySet().removeIf(id -> level.getEntity(id) == null);
	}

	public static void clear() {
		THRALLS.clear();
	}

	public static ResourceLocation textureFor(LivingEntity entity) {
		if (!isThrall(entity)) {
			return null;
		}
		String path = thrallTexturePath(entity);
		return path == null ? null : NyctoNeoForge.id("textures/entity/vampiric_thrall/" + path + ".png");
	}

	private static String thrallTexturePath(LivingEntity entity) {
		if (entity instanceof Horse horse) {
			String shade = switch (horse.getVariant()) {
				case WHITE, CREAMY, GRAY -> "light";
				case CHESTNUT, BROWN, BLACK, DARK_BROWN -> "dark";
			};
			return "minecraft/horse_" + shade + (horse.isBaby() ? "_baby" : "");
		}
		if (entity instanceof Wolf wolf) {
			String texturePath = wolf.getTexture().getPath();
			String shade = texturePath.contains("pale") || texturePath.contains("snowy") || texturePath.contains("ashen") || texturePath.contains("woods") ? "light" : "dark";
			return "minecraft/wolf_" + shade + (wolf.isBaby() ? "_baby" : "");
		}
		if (entity instanceof Vex vex) {
			return "minecraft/vex" + (vex.isCharging() ? "_charging" : "");
		}
		ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
		if (key == null) {
			return null;
		}
		return key.getNamespace() + "/" + key.getPath() + (entity.isBaby() ? "_baby" : "");
	}
}
