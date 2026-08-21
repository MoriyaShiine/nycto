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
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.tag.NyctoEntityTypeTags;
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
import java.util.stream.Collectors;

public class NyctoAPI {
	public static Transformation getTransformation(Player player) {
		return NyctoEntityComponents.TRANSFORMATION.get(player).getTransformation();
	}

	public static void setTransformation(ServerPlayer player, Transformation transformation) {
		getTransformation(player).onRemoved(player);
		NyctoAPIImpl.setTransformation(player, transformation);
		transformation.onAdded(player);
		SetTransformationPayload.send(player, player, transformation);
		PlayerLookup.tracking(player).forEach(receiver -> SetTransformationPayload.send(receiver, player, transformation));
		NyctoTriggers.CHANGE_TRANSFORMATION.trigger(player);
	}

	public static void addPower(ServerPlayer player, Power power) {
		Transformation transformation = getTransformation(player);
		transformation.applyModifiers(player, false);
		NyctoAPIImpl.addPower(player, power);
		transformation.applyModifiers(player, true);
		ModifyPowerPayload.send(player, player, power, true);
		PlayerLookup.tracking(player).forEach(receiver -> ModifyPowerPayload.send(receiver, player, power, true));
		NyctoTriggers.CHANGE_POWERS.trigger(player);
	}

	public static void removePower(ServerPlayer player, Power power) {
		Transformation transformation = getTransformation(player);
		transformation.applyModifiers(player, false);
		NyctoAPIImpl.removePower(player, power);
		transformation.applyModifiers(player, true);
		ModifyPowerPayload.send(player, player, power, false);
		PlayerLookup.tracking(player).forEach(receiver -> ModifyPowerPayload.send(receiver, player, power, false));
		NyctoTriggers.CHANGE_POWERS.trigger(player);
	}

	public static List<PowerInstance> getPowers(Player player) {
		return NyctoEntityComponents.TRANSFORMATION.get(player).getPowers();
	}

	public static boolean hasPower(Player player, Power power) {
		return NyctoEntityComponents.TRANSFORMATION.get(player).hasPower(power);
	}

	public static int getWeaknesses(Player player, TagKey<Power> choosablePowers) {
		return getPowers(player).stream().filter(instance -> instance.getPower().isWeakness() && instance.is(choosablePowers)).collect(Collectors.toSet()).size();
	}

	public static void setPowerCooldown(ServerPlayer player, Power power, int cooldown) {
		NyctoAPIImpl.setPowerCooldown(player, power, cooldown);
		SetPowerCooldownPayload.send(player, power, cooldown);
	}

	public static boolean hasBlood(Entity entity) {
		return !entity.is(NyctoEntityTypeTags.HAS_NO_BLOOD);
	}

	public static boolean hasQualityBlood(Entity entity) {
		return hasBlood(entity) && entity.is(NyctoEntityTypeTags.HAS_QUALITY_BLOOD);
	}

	public static boolean isHealingBlocked(LivingEntity entity) {
		return NyctoEntityComponents.HEAL_BLOCK.get(entity).getTicksToBlock() > 0;
	}

	public static void applyHealBlock(LivingEntity entity, int ticks, @Nullable Entity lifeStealer) {
		HaemogenesisComponent haemogenesis = NyctoEntityComponents.HAEMOGENESIS.getNullable(entity);
		if (haemogenesis == null || !haemogenesis.isHealing()) {
			if (NyctoUtil.hasHealBlockResistance(entity)) {
				ticks = (int) (ticks * 2 / 3F);
			}
			HealBlockComponent healBlock = NyctoEntityComponents.HEAL_BLOCK.get(entity);
			healBlock.setTicksToBlock(ticks);
			healBlock.setLifeStealer(lifeStealer);
			healBlock.sync();
		}
	}

	public static void applyHealBlock(LivingEntity entity, int ticks) {
		applyHealBlock(entity, ticks, null);
	}

	public static boolean isVampire(Entity entity) {
		if (entity == null) {
			return false;
		}
		VampiricThrallComponent vampiricThrall = NyctoEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
		if (vampiricThrall != null && vampiricThrall.hasOwner()) {
			return true;
		}
		return entity.is(NyctoEntityTypes.VAMPIRE) || NyctoAPIImpl.isPlayerVampire(entity);
	}

	public static boolean isWerewolf(Entity entity) {
		return NyctoAPIImpl.isPlayerWerewolf(entity);
	}

	public static boolean isBeastForm(DamageSource source) {
		return isBeastForm(source.getDirectEntity());
	}

	public static boolean isBeastForm(Entity entity) {
		if (entity == null) {
			return false;
		}
		return DarkFormPower.isDarkFormActive(entity);
	}

	public static boolean hasRespawnLeniency(LivingEntity entity) {
		RespawnLeniencyComponent respawnLeniency = NyctoEntityComponents.RESPAWN_LENIENCY.getNullable(entity);
		return respawnLeniency != null && respawnLeniency.hasLeniency();
	}

	public static void giveRespawnLeniency(LivingEntity entity) {
		RespawnLeniencyComponent respawnLeniency = NyctoEntityComponents.RESPAWN_LENIENCY.getNullable(entity);
		if (respawnLeniency != null) {
			respawnLeniency.giveLeniency();
		}
	}

	public static boolean isSunExposed(Entity entity) {
		SunExposureComponent sunExposure = NyctoEntityComponents.SUN_EXPOSURE.getNullable(entity);
		return sunExposure != null && sunExposure.isExposed();
	}

	public static boolean hasSunDebuff(Entity entity) {
		SunExposureComponent sunExposure = NyctoEntityComponents.SUN_EXPOSURE.getNullable(entity);
		return sunExposure != null && sunExposure.hasVampireSunDebuff() && sunExposure.getExposureTime() >= SunExposureComponent.MIN_DEBUFF_EXPOSURE_TIME;
	}

	public static void removePowerOrCure(ServerPlayer player, TagKey<Power> choosablePowers) {
		List<PowerInstance> powers = getPowers(player);
		Set<Power> toRemove = new HashSet<>();
		for (int i = powers.size() - 1; i >= 0; i--) {
			PowerInstance instance = powers.get(i);
			if (instance.is(choosablePowers)) {
				toRemove.add(instance.getPower());
				if (!instance.getPower().isWeakness()) {
					break;
				}
			}
		}
		toRemove.forEach(power -> removePower(player, power));
		SLibUtils.addParticles(player, ParticleTypes.SMOKE, 16, ParticleAnchor.BODY);
		if (toRemove.isEmpty()) {
			SLibUtils.playAnchoredSound(player, NyctoSoundEvents.GENERIC_TRANSFORM_HUMAN);
			setTransformation(player, NyctoTransformations.HUMAN);
		} else {
			SLibUtils.playAnchoredSound(player, NyctoSoundEvents.GENERIC_REMOVE_POWER);
		}
	}
}
