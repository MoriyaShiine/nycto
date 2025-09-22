/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api;

import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.api.power.PowerInstance;
import moriyashiine.nycto.api.transformation.Transformation;
import moriyashiine.nycto.client.payload.ModifyPowerPayload;
import moriyashiine.nycto.client.payload.SetPowerCooldownPayload;
import moriyashiine.nycto.client.payload.SetTransformationPayload;
import moriyashiine.nycto.common.NyctoAPIImpl;
import moriyashiine.nycto.common.component.entity.HealBlockComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.HaemogenesisComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModGameRules;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.init.ModTransformations;
import moriyashiine.nycto.common.power.vampire.DarkFormPower;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NyctoAPI {
	public static Transformation getTransformation(PlayerEntity player) {
		return ModEntityComponents.TRANSFORMATION.get(player).getTransformation();
	}

	public static void setTransformation(ServerPlayerEntity player, Transformation transformation) {
		getTransformation(player).onRemoved(player);
		NyctoAPIImpl.setTransformation(player, transformation);
		transformation.onAdded(player);
		SetTransformationPayload.send(player, player, transformation);
		PlayerLookup.tracking(player).forEach(receiver -> SetTransformationPayload.send(receiver, player, transformation));
	}

	public static void addPower(ServerPlayerEntity player, Power power) {
		Transformation transformation = getTransformation(player);
		transformation.applyModifiers(player, false);
		NyctoAPIImpl.addPower(player, power);
		transformation.applyModifiers(player, true);
		ModifyPowerPayload.send(player, player, power, true);
		PlayerLookup.tracking(player).forEach(receiver -> ModifyPowerPayload.send(receiver, player, power, true));
	}

	public static void removePower(ServerPlayerEntity player, Power power) {
		Transformation transformation = getTransformation(player);
		transformation.applyModifiers(player, false);
		NyctoAPIImpl.removePower(player, power);
		transformation.applyModifiers(player, true);
		ModifyPowerPayload.send(player, player, power, false);
		PlayerLookup.tracking(player).forEach(receiver -> ModifyPowerPayload.send(receiver, player, power, false));
	}

	public static List<PowerInstance> getPowers(PlayerEntity player) {
		return ModEntityComponents.TRANSFORMATION.get(player).getPowers();
	}

	public static boolean hasPower(PlayerEntity player, Power power) {
		return ModEntityComponents.TRANSFORMATION.get(player).hasPower(power);
	}

	public static void setPowerCooldown(ServerPlayerEntity player, Power power, int cooldown) {
		NyctoAPIImpl.setPowerCooldown(player, power, cooldown);
		SetPowerCooldownPayload.send(player, power, cooldown);
	}

	public static void applyHealBlock(LivingEntity entity, int ticks, @Nullable Entity lifeStealer) {
		@Nullable HaemogenesisComponent haemogenesisComponent = ModEntityComponents.HAEMOGENESIS.getNullable(entity);
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

	public static void increaseHunterHeat(PlayerEntity attacker, LivingEntity target) {
		if (!attacker.isCreative() && !target.isPlayer() && target.getType().isIn(ModEntityTypeTags.HAS_QUALITY_BLOOD) && attacker.getWorld() instanceof ServerWorld serverWorld && serverWorld.getGameRules().getBoolean(ModGameRules.DO_HUNTER_SPAWNING)) {
			ModEntityComponents.HUNTER_HEAT.get(attacker).increaseHeat();
		}
	}

	public static boolean isWerewolf(Entity entity) {
		return NyctoAPIImpl.isPlayerWerewolf(entity);
	}

	public static boolean isVampire(Entity entity) {
		if (entity == null) {
			return false;
		}
		@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
		if (vampiricThrallComponent != null && vampiricThrallComponent.isThralled()) {
			return true;
		}
		return entity.getType().isIn(ModEntityTypeTags.VAMPIRES) || NyctoAPIImpl.isPlayerVampire(entity);
	}

	public static boolean isBeastForm(DamageSource source) {
		return isBeastForm(source.getSource());
	}

	public static boolean isBeastForm(Entity entity) {
		return DarkFormPower.isDarkFormActive(entity);
	}

	public static boolean hasRespawnLeniency(LivingEntity entity) {
		return ModEntityComponents.RESPAWN_LENIENCY.get(entity).hasLeniency();
	}

	public static void giveRespawnLeniency(LivingEntity entity) {
		ModEntityComponents.RESPAWN_LENIENCY.get(entity).giveLeniency();
	}

	public static void partiallyCureTransformation(ServerPlayerEntity player, TagKey<Power> tagKey) {
		List<PowerInstance> powers = getPowers(player);
		Set<Power> toRemove = new HashSet<>();
		boolean foundNegative = false;
		for (int i = powers.size() - 1; i >= 0; i--) {
			PowerInstance instance = powers.get(i);
			if (instance.getPower().isIn(tagKey)) {
				toRemove.add(instance.getPower());
				if (foundNegative) {
					break;
				}
				if (instance.getPower().isNegative()) {
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
