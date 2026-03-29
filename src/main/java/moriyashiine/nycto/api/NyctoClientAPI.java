/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api;

import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.client.event.PowerClientEvent;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricVexComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.entity.animal.wolf.WolfVariants;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public class NyctoClientAPI {
	private static final Identifier THRALLED_DARK_HORSE_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_dark.png");
	private static final Identifier THRALLED_DARK_HORSE_BABY_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_dark_baby.png");
	private static final Identifier THRALLED_LIGHT_HORSE_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_light.png");
	private static final Identifier THRALLED_LIGHT_HORSE_BABY_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/horse_light_baby.png");
	private static final Identifier THRALLED_DARK_WOLF_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/wolf_dark.png");
	private static final Identifier THRALLED_DARK_WOLF_BABY_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/wolf_dark_baby.png");
	private static final Identifier THRALLED_LIGHT_WOLF_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/wolf_light.png");
	private static final Identifier THRALLED_LIGHT_WOLF_BABY_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/wolf_light_baby.png");
	private static final Identifier THRALLED_VEX_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/vex.png");
	private static final Identifier THRALLED_CHARGING_VEX_TEXTURE = Nycto.id("textures/entity/vampiric_thrall/minecraft/vex_charging.png");

	public static boolean isHighlightingPower(Player player, Power power) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(player);
		return PowerClientEvent.isActive(player, transformationComponent) && transformationComponent.getPowers().get(transformationComponent.getPowerIndex()).getPower() == power;
	}

	@Nullable
	public static Identifier getSpecialThrallTexture(Entity entity) {
		if (entity instanceof Horse horse) {
			return horse.getVariant().ordinal() > 2 ? horse.isBaby() ? THRALLED_DARK_HORSE_BABY_TEXTURE : THRALLED_DARK_HORSE_TEXTURE : horse.isBaby() ? THRALLED_LIGHT_HORSE_BABY_TEXTURE : THRALLED_LIGHT_HORSE_TEXTURE;
		}
		if (entity instanceof Wolf wolf) {
			Holder<WolfVariant> variant = wolf.getVariant();
			if (variant.is(WolfVariants.BLACK) || variant.is(WolfVariants.RUSTY) || variant.is(WolfVariants.SPOTTED) || variant.is(WolfVariants.WOODS)) {
				return wolf.isBaby() ? THRALLED_DARK_WOLF_BABY_TEXTURE : THRALLED_DARK_WOLF_TEXTURE;
			}
			return wolf.isBaby() ? THRALLED_LIGHT_WOLF_BABY_TEXTURE : THRALLED_LIGHT_WOLF_TEXTURE;
		}
		if (entity instanceof Vex vex) {
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
