/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.api.world.power.PowerInstance;
import moriyashiine.nycto.api.world.transformation.Transformation;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModTransformations;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class NyctoAPIImpl {
	public static void setTransformation(Player player, Transformation transformation) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(player);
		transformationComponent.setTransformation(transformation);
		transformationComponent.setPowerIndex(0);
	}

	public static void addPower(Player player, Power power) {
		ModEntityComponents.TRANSFORMATION.get(player).addPower(new PowerInstance(power));
	}

	public static void removePower(Player player, Power power) {
		ModEntityComponents.TRANSFORMATION.get(player).removePower(power);
	}

	public static void setPowerCooldown(Player player, Power power, int cooldown) {
		NyctoAPI.getPowers(player).forEach(powerInstance -> {
			if (powerInstance.getPower() == power) {
				powerInstance.setCooldown(cooldown);
			}
		});
	}

	public static boolean isPlayerWerewolf(Entity entity) {
		return false;
	}

	public static boolean isPlayerVampire(Entity entity) {
		if (entity instanceof Player player) {
			return NyctoAPI.getTransformation(player) == ModTransformations.VAMPIRE;
		}
		for (Player player : entity.level().players()) {
			if (SLibUtils.getModelReplacement(player) == entity) {
				return isPlayerVampire(player);
			}
		}
		return false;
	}
}
