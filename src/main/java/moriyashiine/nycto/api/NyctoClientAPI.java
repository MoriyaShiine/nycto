/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api;

import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.client.event.PowerClientEvent;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricVexComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.WolfVariant;
import net.minecraft.entity.passive.WolfVariants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class NyctoClientAPI {
	private static final Identifier THRALLED_DARK_HORSE_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_dark.png");
	private static final Identifier THRALLED_LIGHT_HORSE_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_light.png");
	private static final Identifier THRALLED_DARK_WOLF_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/wolf_dark.png");
	private static final Identifier THRALLED_LIGHT_WOLF_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/wolf_light.png");
	private static final Identifier THRALLED_VEX_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/vex.png");
	private static final Identifier THRALLED_CHARGING_VEX_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/vex_charging.png");

	public static boolean isHighlightingPower(PlayerEntity player, Power power) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(player);
		return PowerClientEvent.isActive(player, transformationComponent) && transformationComponent.getPowers().get(transformationComponent.getPowerIndex()).getPower() == power;
	}

	@Nullable
	public static Identifier getSpecialThrallTexture(Entity entity) {
		if (entity instanceof HorseEntity horse) {
			return horse.getHorseColor().ordinal() > 2 ? THRALLED_DARK_HORSE_TEXTURE : THRALLED_LIGHT_HORSE_TEXTURE;
		}
		if (entity instanceof WolfEntity wolf) {
			RegistryEntry<WolfVariant> variant = wolf.getVariant();
			if (variant.matchesKey(WolfVariants.BLACK) || variant.matchesKey(WolfVariants.RUSTY) || variant.matchesKey(WolfVariants.SPOTTED) || variant.matchesKey(WolfVariants.WOODS)) {
				return THRALLED_DARK_WOLF_TEXTURE;
			}
			return THRALLED_LIGHT_WOLF_TEXTURE;
		}
		if (entity instanceof VexEntity vex) {
			return vex.isCharging() ? THRALLED_CHARGING_VEX_TEXTURE : THRALLED_VEX_TEXTURE;
		}
		return null;
	}

	public static boolean hasThrallTexture(Entity entity) {
		@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
		if (vampiricThrallComponent != null && vampiricThrallComponent.hasOwner()) {
			return true;
		}
		@Nullable VampiricVexComponent vampiricVexComponent = ModEntityComponents.VAMPIRIC_VEX.getNullable(entity);
		return vampiricVexComponent != null && vampiricVexComponent.hasOwner();
	}
}
