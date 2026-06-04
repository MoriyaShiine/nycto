/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.util;

import moriyashiine.nycto.neoforge.entity.Vampire;
import moriyashiine.nycto.neoforge.network.SyncEntityBloodPayload;
import moriyashiine.nycto.neoforge.registry.NyctoParticleTypes;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import moriyashiine.nycto.neoforge.registry.NyctoTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;

public final class NyctoBloodUtil {
	public static final int MAX_ENTITY_BLOOD = 100;
	public static final int QUALITY_DRAIN = 10;
	public static final int LESSER_DRAIN = 25;
	public static final int QUALITY_FILL = 5;
	public static final int LESSER_FILL = 1;

	private static final String ENTITY_BLOOD = "nycto_entity_blood";
	private static final String ENTITY_BLOOD_INITIALIZED = "nycto_entity_blood_initialized";
	private static final String REGENERATION_BLOCK_TICKS = "nycto_blood_regeneration_block_ticks";

	private NyctoBloodUtil() {
	}

	public static boolean hasDrainableBlood(LivingEntity entity) {
		return entity.isAlive()
				&& !(entity instanceof Player player && (player.isCreative() || player.isSpectator()))
				&& !Vampire.isVampire(entity)
				&& !entity.getType().is(NyctoTags.HAS_NO_BLOOD)
				&& !entity.getType().is(EntityTypeTags.UNDEAD)
				&& !entity.hasInfiniteMaterials();
	}

	public static boolean hasQualityBlood(LivingEntity entity) {
		if (!hasDrainableBlood(entity)) {
			return false;
		}
		return hasQualityBloodType(entity);
	}

	public static boolean hasQualityBloodType(LivingEntity entity) {
		if (entity.getType().is(NyctoTags.HAS_QUALITY_BLOOD)) {
			return true;
		}
		return entity instanceof Player
				|| entity instanceof AbstractVillager
				|| entity instanceof AbstractIllager
				|| entity instanceof Witch
				|| entity instanceof AbstractPiglin;
	}

	public static int getBlood(LivingEntity entity) {
		ensureBlood(entity);
		return Mth.clamp(entity.getPersistentData().getInt(ENTITY_BLOOD), 0, MAX_ENTITY_BLOOD);
	}

	public static boolean canFill(LivingEntity entity) {
		return getBlood(entity) < MAX_ENTITY_BLOOD;
	}

	public static int drainAmount(LivingEntity entity) {
		return Mth.ceil((hasQualityBlood(entity) ? QUALITY_DRAIN : LESSER_DRAIN) * armorMultiplier(entity));
	}

	public static int fillAmount(LivingEntity entity, boolean moreBlood) {
		int fill = hasQualityBlood(entity) ? QUALITY_FILL : LESSER_FILL;
		if (moreBlood) {
			fill += 2;
		}
		return Math.max(1, Mth.ceil(fill * armorMultiplier(entity)));
	}

	public static boolean canSafelyDrain(LivingEntity target, int amount) {
		return getBlood(target) > MAX_ENTITY_BLOOD / 2 || getBlood(target) - amount > 0;
	}

	public static boolean drainAttack(LivingEntity target, int amount) {
		if (!hasDrainableBlood(target) || amount <= 0 || target.isInvulnerable()) {
			return false;
		}
		int blood = getBlood(target);
		if (blood <= 0) {
			return false;
		}
		if (target.getRandom().nextFloat() <= 2 / 3F && hasBloodDrainResistance(target)) {
			target.level().playSound(null, target.getX(), target.getY(), target.getZ(), NyctoSoundEvents.GENERIC_BLOOD_DRAIN_BLOCKED.get(), target.getSoundSource(), 1, Mth.nextFloat(target.getRandom(), 0.95F, 1.05F));
			return false;
		}
		setBlood(target, Math.max(0, blood - amount));
		target.getPersistentData().putInt(REGENERATION_BLOCK_TICKS, 200);
		spawnBloodParticles(target, Math.max(4, amount / 2));
		applyLowBloodEffects(target);
		return true;
	}

	public static void tickEntityBlood(LivingEntity entity) {
		if (!hasDrainableBlood(entity)) {
			return;
		}
		ensureBlood(entity);
		int blocked = entity.getPersistentData().getInt(REGENERATION_BLOCK_TICKS);
		if (blocked > 0) {
			entity.getPersistentData().putInt(REGENERATION_BLOCK_TICKS, blocked - 1);
			return;
		}
		if (entity.tickCount % 100 == 0 && getBlood(entity) < MAX_ENTITY_BLOOD) {
			setBlood(entity, Math.min(MAX_ENTITY_BLOOD, getBlood(entity) + (entity.isSleeping() ? 5 : 1)));
		}
		if (entity.tickCount % 20 == 0) {
			applyLowBloodEffects(entity);
		}
	}

	public static void spawnBloodParticles(LivingEntity target, int count) {
		if (target.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(NyctoParticleTypes.BLOOD.get(), target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(), count, target.getBbWidth() * 0.35, target.getBbHeight() * 0.25, target.getBbWidth() * 0.35, 0.02);
		}
	}

	private static void ensureBlood(LivingEntity entity) {
		if (!entity.getPersistentData().getBoolean(ENTITY_BLOOD_INITIALIZED)) {
			entity.getPersistentData().putBoolean(ENTITY_BLOOD_INITIALIZED, true);
			setBlood(entity, MAX_ENTITY_BLOOD);
		}
	}

	public static void applySyncedBlood(LivingEntity entity, int blood) {
		entity.getPersistentData().putBoolean(ENTITY_BLOOD_INITIALIZED, true);
		entity.getPersistentData().putInt(ENTITY_BLOOD, Mth.clamp(blood, 0, MAX_ENTITY_BLOOD));
	}

	private static void setBlood(LivingEntity entity, int blood) {
		applySyncedBlood(entity, blood);
		SyncEntityBloodPayload.send(entity, getBlood(entity));
	}

	private static void applyLowBloodEffects(LivingEntity entity) {
		int blood = getBlood(entity);
		if (blood < MAX_ENTITY_BLOOD / 5) {
			entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, true, false));
			entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, true, false));
		}
		if (blood < MAX_ENTITY_BLOOD / 10 && entity.level() instanceof ServerLevel serverLevel) {
			spawnBloodParticles(entity, 8);
			entity.hurt(serverLevel.damageSources().generic(), 1);
		}
	}

	private static double armorMultiplier(LivingEntity entity) {
		return Math.max(1 / 3F, Mth.lerp(Math.min(30, entity.getArmorValue()) / 30F, 1, 0));
	}

	private static boolean hasBloodDrainResistance(LivingEntity entity) {
		return entity.getArmorValue() >= 20;
	}
}
