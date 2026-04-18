/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api;

import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.api.world.power.PowerInstance;
import moriyashiine.nycto.api.world.transformation.Transformation;
import moriyashiine.nycto.client.payload.ModifyPowerPayload;
import moriyashiine.nycto.client.payload.SetPowerCooldownPayload;
import moriyashiine.nycto.client.payload.SetTransformationPayload;
import moriyashiine.nycto.common.NyctoAPIImpl;
import moriyashiine.nycto.common.component.entity.HealBlockComponent;
import moriyashiine.nycto.common.component.entity.RespawnLeniencyComponent;
import moriyashiine.nycto.common.component.entity.SunExposureComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.HaemogenesisComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.init.ModTransformations;
import moriyashiine.nycto.common.init.ModTriggers;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.power.vampire.DarkFormPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NyctoAPI {
	public static Transformation getTransformation(Player player) {
		return ModEntityComponents.TRANSFORMATION.get(player).getTransformation();
	}

	public static void setTransformation(ServerPlayer player, Transformation transformation) {
		getTransformation(player).onRemoved(player);
		NyctoAPIImpl.setTransformation(player, transformation);
		transformation.onAdded(player);
		SetTransformationPayload.send(player, player, transformation);
		PlayerLookup.tracking(player).forEach(receiver -> SetTransformationPayload.send(receiver, player, transformation));
		ModTriggers.CHANGE_TRANSFORMATION.trigger(player);
	}

	public static void addPower(ServerPlayer player, Power power) {
		Transformation transformation = getTransformation(player);
		transformation.applyModifiers(player, false);
		NyctoAPIImpl.addPower(player, power);
		transformation.applyModifiers(player, true);
		ModifyPowerPayload.send(player, player, power, true);
		PlayerLookup.tracking(player).forEach(receiver -> ModifyPowerPayload.send(receiver, player, power, true));
		ModTriggers.CHANGE_POWERS.trigger(player);
	}

	public static void removePower(ServerPlayer player, Power power) {
		Transformation transformation = getTransformation(player);
		transformation.applyModifiers(player, false);
		NyctoAPIImpl.removePower(player, power);
		transformation.applyModifiers(player, true);
		ModifyPowerPayload.send(player, player, power, false);
		PlayerLookup.tracking(player).forEach(receiver -> ModifyPowerPayload.send(receiver, player, power, false));
		ModTriggers.CHANGE_POWERS.trigger(player);
	}

	public static List<PowerInstance> getPowers(Player player) {
		return ModEntityComponents.TRANSFORMATION.get(player).getPowers();
	}

	public static boolean hasPower(Player player, Power power) {
		return ModEntityComponents.TRANSFORMATION.get(player).hasPower(power);
	}

	public static void setPowerCooldown(ServerPlayer player, Power power, int cooldown) {
		NyctoAPIImpl.setPowerCooldown(player, power, cooldown);
		SetPowerCooldownPayload.send(player, power, cooldown);
	}

	public static void applyHealBlock(LivingEntity entity, int ticks, @Nullable Entity lifeStealer) {
		HaemogenesisComponent haemogenesisComponent = ModEntityComponents.HAEMOGENESIS.getNullable(entity);
		if (haemogenesisComponent == null || !haemogenesisComponent.isHealing()) {
			if (NyctoUtil.hasHealBlockResistance(entity)) {
				ticks = (int) (ticks * 2 / 3F);
			}
			HealBlockComponent healBlockComponent = ModEntityComponents.HEAL_BLOCK.get(entity);
			healBlockComponent.setTicksToBlock(ticks);
			healBlockComponent.setLifeStealer(lifeStealer);
			healBlockComponent.sync();
		}
	}

	public static void applyHealBlock(LivingEntity entity, int ticks) {
		applyHealBlock(entity, ticks, null);
	}

	public static boolean isWerewolf(Entity entity) {
		return NyctoAPIImpl.isPlayerWerewolf(entity);
	}

	public static boolean isVampire(Entity entity) {
		if (entity == null) {
			return false;
		}
		VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
		if (vampiricThrallComponent != null && vampiricThrallComponent.hasOwner()) {
			return true;
		}
		return entity.is(ModEntityTypeTags.VAMPIRES) || NyctoAPIImpl.isPlayerVampire(entity);
	}

	public static boolean isBeastForm(DamageSource source) {
		return isBeastForm(source.getDirectEntity());
	}

	public static boolean isBeastForm(Entity entity) {
		return DarkFormPower.isDarkFormActive(entity);
	}

	public static boolean hasRespawnLeniency(LivingEntity entity) {
		RespawnLeniencyComponent respawnLeniencyComponent = ModEntityComponents.RESPAWN_LENIENCY.getNullable(entity);
		return respawnLeniencyComponent != null && respawnLeniencyComponent.hasLeniency();
	}

	public static void giveRespawnLeniency(LivingEntity entity) {
		RespawnLeniencyComponent respawnLeniencyComponent = ModEntityComponents.RESPAWN_LENIENCY.getNullable(entity);
		if (respawnLeniencyComponent != null) {
			respawnLeniencyComponent.giveLeniency();
		}
	}

	public static boolean isSunExposed(Entity entity) {
		SunExposureComponent sunExposureComponent = ModEntityComponents.SUN_EXPOSURE.getNullable(entity);
		return sunExposureComponent != null && sunExposureComponent.isExposed();
	}

	public static boolean hasSunDebuff(Entity entity) {
		SunExposureComponent sunExposureComponent = ModEntityComponents.SUN_EXPOSURE.getNullable(entity);
		return sunExposureComponent != null && sunExposureComponent.isExposed() && sunExposureComponent.hasVampireSunDebuff();
	}

	public static void partiallyCureTransformation(ServerPlayer player, TagKey<Power> choosablePowers) {
		List<PowerInstance> powers = getPowers(player);
		Set<Power> toRemove = new HashSet<>();
		boolean foundNegative = false;
		for (int i = powers.size() - 1; i >= 0; i--) {
			PowerInstance instance = powers.get(i);
			if (instance.is(choosablePowers)) {
				toRemove.add(instance.getPower());
				if (foundNegative) {
					break;
				}
				if (instance.getPower().isWeakness()) {
					foundNegative = true;
				}
			}
		}
		toRemove.forEach(power -> removePower(player, power));
		SLibUtils.addParticles(player, ParticleTypes.SMOKE, 16, ParticleAnchor.BODY);
		if (toRemove.isEmpty()) {
			SLibUtils.playAnchoredSound(player, ModSoundEvents.ENTITY_GENERIC_TRANSFORM_HUMAN);
			setTransformation(player, ModTransformations.HUMAN);
		} else {
			SLibUtils.playAnchoredSound(player, ModSoundEvents.ENTITY_GENERIC_REMOVE_POWER);
		}
	}
}
