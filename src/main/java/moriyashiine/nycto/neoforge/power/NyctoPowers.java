/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.power;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.event.BatSwarmEvents;
import moriyashiine.nycto.neoforge.block.BloodFountainBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.item.VampireArmorItem;
import moriyashiine.nycto.neoforge.entity.projectile.BloodFlechetteProjectile;
import moriyashiine.nycto.neoforge.network.SyncCarnagePayload;
import moriyashiine.nycto.neoforge.network.SyncBloodrushPayload;
import moriyashiine.nycto.neoforge.network.SyncBatFormPayload;
import moriyashiine.nycto.neoforge.network.SyncDarkFormPayload;
import moriyashiine.nycto.neoforge.network.SyncKeenSensesPayload;
import moriyashiine.nycto.neoforge.network.SyncMistFormPayload;
import moriyashiine.nycto.neoforge.network.SyncThrallPayload;
import moriyashiine.nycto.neoforge.registry.NyctoParticleTypes;
import moriyashiine.nycto.neoforge.registry.NyctoBlocks;
import moriyashiine.nycto.neoforge.registry.NyctoEntityTypes;
import moriyashiine.nycto.neoforge.registry.NyctoMobEffects;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import moriyashiine.nycto.neoforge.registry.NyctoTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class NyctoPowers {
	public static final String NIGHT_VISION = "night_vision";
	public static final String BAT_FORM = "bat_form";
	public static final String BAT_SWARM = "bat_swarm";
	public static final String BATSTEP = "batstep";
	public static final String BLOOD_BARRIER = "blood_barrier";
	public static final String BLOOD_FLECHETTES = "blood_flechettes";
	public static final String BLOODRUSH = "bloodrush";
	public static final String CARNAGE = "carnage";
	public static final String DARK_FORM = "dark_form";
	public static final String HAEMOGENESIS = "haemogenesis";
	public static final String HYPNOTIZE = "hypnotize";
	public static final String KEEN_SENSES = "keen_senses";
	public static final String MIST_FORM = "mist_form";
	public static final String VAMPIRIC_THRALL = "vampiric_thrall";

	public static final String TAG_COOLDOWN_PREFIX = "nycto_power_cooldown_";
	public static final String TAG_ACTIVE_PREFIX = "nycto_power_active_";
	public static final String TAG_BLOOD_BARRIER_LAYERS = "nycto_blood_barrier_layers";
	public static final String TAG_HEAL_BLOCK_UNTIL = "nycto_heal_block_until";
	public static final String TAG_BLOOD_FLECHETTES_MARK_UNTIL = "nycto_blood_flechettes_mark_until";
	public static final String TAG_CARNAGE_BLEED_UNTIL = "nycto_carnage_bleed_until";
	public static final String TAG_THRALL_OWNER = "nycto_thrall_owner";
	public static final String TAG_THRALL_UNTIL = "nycto_thrall_until";
	public static final String TAG_THRALL_MODE = "nycto_thrall_mode";
	public static final String TAG_THRALL_HOME_X = "nycto_thrall_home_x";
	public static final String TAG_THRALL_HOME_Y = "nycto_thrall_home_y";
	public static final String TAG_THRALL_HOME_Z = "nycto_thrall_home_z";
	public static final String TAG_THRALL_BLOOD = "nycto_thrall_blood";
	private static final String TAG_THRALL_ALTERNATE_DRAIN = "nycto_thrall_alternate_drain";
	private static final String TAG_THRALL_FOUNTAIN_X = "nycto_thrall_fountain_x";
	private static final String TAG_THRALL_FOUNTAIN_Y = "nycto_thrall_fountain_y";
	private static final String TAG_THRALL_FOUNTAIN_Z = "nycto_thrall_fountain_z";
	private static final String TAG_THRALL_FOUNTAIN_TIME = "nycto_thrall_fountain_time";
	private static final int MAX_THRALL_BLOOD = 20;
	private static final int VAMPIRIC_VEX_LIFETIME = 20 * 30;
	public static final String TAG_SWARM_OWNER = "nycto_bat_swarm_owner";
	public static final String TAG_SWARM_UNTIL = "nycto_bat_swarm_until";
	public static final String TAG_BAT_FORM_RESTORE = "nycto_bat_form_restore";
	public static final String TAG_BAT_FORM_MAYFLY = "nycto_bat_form_mayfly";
	public static final String TAG_BAT_FORM_FLYING = "nycto_bat_form_flying";
	public static final String TAG_BAT_FORM_DRAIN_TICKS = "nycto_bat_form_drain_ticks";
	public static final String TAG_DARK_FORM_DRAIN_TICKS = "nycto_dark_form_drain_ticks";
	public static final String TAG_DARK_FORM_JUMP_COOLDOWN = "nycto_dark_form_jump_cooldown";
	public static final String TAG_KEEN_SENSES_DISTANCE = "nycto_keen_senses_distance";
	public static final String TAG_KEEN_SENSES_DRAIN_TICKS = "nycto_keen_senses_drain_ticks";
	public static final String TAG_KEEN_SENSES_RENDER_TICKS = "nycto_keen_senses_render_ticks";
	public static final String TAG_MIST_FORM_DRAIN_TICKS = "nycto_mist_form_drain_ticks";
	private static final AttributeModifier CARNAGE_DAMAGE_MODIFIER = new AttributeModifier(NyctoNeoForge.id("carnage_damage"), 2, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier BAT_FORM_MAX_HEALTH_MODIFIER = new AttributeModifier(NyctoNeoForge.id("bat_form_max_health"), -0.7, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	private static final AttributeModifier DARK_FORM_ARMOR_MODIFIER = new AttributeModifier(NyctoNeoForge.id("dark_form_armor"), 20, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier DARK_FORM_ARMOR_TOUGHNESS_MODIFIER = new AttributeModifier(NyctoNeoForge.id("dark_form_armor_toughness"), 8, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier DARK_FORM_ATTACK_DAMAGE_MODIFIER = new AttributeModifier(NyctoNeoForge.id("dark_form_attack_damage"), 10, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier DARK_FORM_ATTACK_SPEED_MODIFIER = new AttributeModifier(NyctoNeoForge.id("dark_form_attack_speed"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	private static final AttributeModifier DARK_FORM_BLOCK_INTERACTION_RANGE_MODIFIER = new AttributeModifier(NyctoNeoForge.id("dark_form_block_interaction_range"), 1, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier DARK_FORM_ENTITY_INTERACTION_RANGE_MODIFIER = new AttributeModifier(NyctoNeoForge.id("dark_form_entity_interaction_range"), 1, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier DARK_FORM_KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(NyctoNeoForge.id("dark_form_knockback_resistance"), 0.7, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier KEEN_SENSES_SPEED_MODIFIER = new AttributeModifier(NyctoNeoForge.id("keen_senses_speed"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

	private static final Map<String, NyctoPower> POWERS = new LinkedHashMap<>();

	public static final NyctoPower NIGHT_VISION_POWER = register(new SimplePower(NIGHT_VISION, 0, 20, player -> {
		boolean enabled = !NyctoData.isNightVisionEnabled(player);
		NyctoData.setNightVisionEnabled(player, enabled);
		if (enabled) {
			player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 260, 0, true, false));
			player.addEffect(new MobEffectInstance(NyctoMobEffects.NIGHT_VISION, 260, 0, true, true));
			play(player, NyctoSoundEvents.NIGHT_VISION_ON);
			message(player, "message.nycto.power.night_vision");
		} else {
			player.removeEffect(MobEffects.NIGHT_VISION);
			player.removeEffect(NyctoMobEffects.NIGHT_VISION);
			play(player, NyctoSoundEvents.NIGHT_VISION_OFF);
			message(player, "message.nycto.power.night_vision.off");
		}
		return true;
	}));
	public static final NyctoPower BAT_FORM_POWER = register(new SimplePower(BAT_FORM, 0, 0, NyctoPowers::toggleBatForm));
	public static final NyctoPower BAT_SWARM_POWER = register(new SimplePower(BAT_SWARM, 10, 20 * 30, player -> {
		BatSwarmEvents.spawn(player);
		particles(player, NyctoParticleTypes.BAT_SWARM_CENTER.get(), 16, 3);
		particles(player, NyctoParticleTypes.BAT_SWARM_LEFT.get(), 10, 3);
		particles(player, NyctoParticleTypes.BAT_SWARM_RIGHT.get(), 10, 3);
		play(player, NyctoSoundEvents.BAT_SWARM_USE);
		message(player, "message.nycto.power.bat_swarm");
		return true;
	}));
	public static final NyctoPower BATSTEP_POWER = register(new SimplePower(BATSTEP, 3, 20 * 8, player -> {
		Vec3 start = player.position();
		Vec3 target = findTeleportTarget(player, 12);
		int hits = damageLine(player, start.add(0, player.getBbHeight() * 0.5, 0), target.add(0, player.getBbHeight() * 0.5, 0), 2.5F, 1.2, true);
		player.teleportTo(target.x, target.y, target.z);
		damageAround(player, target, 2.5, 3, true);
		lineParticles(player, start.add(0, player.getBbHeight() * 0.5, 0), target.add(0, player.getBbHeight() * 0.5, 0), 12);
		play(player, NyctoSoundEvents.BATSTEP_USE);
		message(player, "message.nycto.power.batstep", hits);
		return true;
	}));
	public static final NyctoPower BLOOD_BARRIER_POWER = register(new SimplePower(BLOOD_BARRIER, 10, 600, player -> {
		NyctoData.startBloodBarrier(player, 3, 300);
		particles(player, NyctoParticleTypes.BLOOD.get(), 28, 1);
		play(player, NyctoSoundEvents.BLOOD_BARRIER_USE);
		message(player, "message.nycto.power.blood_barrier");
		return true;
	}));
	public static final NyctoPower BLOOD_FLECHETTES_POWER = register(new SimplePower(BLOOD_FLECHETTES, 3, 40, player -> {
		int count = player.getRandom().nextIntBetweenInclusive(6, 8);
		for (int i = 0; i < count; i++) {
			BloodFlechetteProjectile flechette = new BloodFlechetteProjectile(player.level(), player);
			flechette.shootFromRotation(player, player.getXRot(), player.getYHeadRot(), 0, 1, i == 0 ? 0 : 24);
			player.level().addFreshEntity(flechette);
		}
		play(player, NyctoSoundEvents.BLOOD_FLECHETTES_USE);
		message(player, "message.nycto.power.blood_flechettes", count);
		return true;
	}));
	public static final NyctoPower BLOODRUSH_POWER = register(new SimplePower(BLOODRUSH, 3, 200, player -> {
		int ticks = 90;
		setActive(player, BLOODRUSH, ticks);
		player.removeVehicle();
		player.startAutoSpinAttack(ticks, 0, null);
		player.setDeltaMovement(player.getLookAngle().normalize().scale(2 / 3F));
		player.hurtMarked = true;
		player.clearFire();
		particles(player, ParticleTypes.DAMAGE_INDICATOR, 18, 0.6);
		particles(player, NyctoParticleTypes.BLOOD.get(), 18, 0.45);
		SyncBloodrushPayload.send(player, ticks);
		play(player, NyctoSoundEvents.BLOODRUSH_USE);
		message(player, "message.nycto.power.bloodrush");
		return true;
	}));
	public static final NyctoPower CARNAGE_POWER = register(new SimplePower(CARNAGE, 10, 600, player -> {
		int ticks = 300;
		setActive(player, CARNAGE, ticks);
		applyCarnageDamage(player);
		SyncCarnagePayload.send(player, ticks);
		particles(player, NyctoParticleTypes.BLOOD.get(), 20, 0.8);
		play(player, NyctoSoundEvents.CARNAGE_USE);
		message(player, "message.nycto.power.carnage");
		return true;
	}));
	public static final NyctoPower DARK_FORM_POWER = register(new SimplePower(DARK_FORM, 0, 0, NyctoPowers::toggleDarkForm));
	public static final NyctoPower HAEMOGENESIS_POWER = register(new SimplePower(HAEMOGENESIS, 10, 20 * 20, player -> {
		NyctoData.clearHealBlock(player);
		player.extinguishFire();
		setActive(player, HAEMOGENESIS, Math.max(1, (int) (player.getMaxHealth() / 3F)) * 2);
		particles(player, NyctoParticleTypes.AMBROSIA.get(), 14, 0.7);
		play(player, NyctoSoundEvents.HAEMOGENESIS_USE);
		message(player, "message.nycto.power.haemogenesis");
		return true;
	}));
	public static final NyctoPower HYPNOTIZE_POWER = register(new SimplePower(HYPNOTIZE, 4, 20 * 16, player -> {
		int hits = 0;
		for (LivingEntity target : nearbyLiving(player, 8)) {
			int duration = target instanceof Player ? 20 * 5 : 20 * 30;
			target.addEffect(new MobEffectInstance(NyctoMobEffects.HYPNOTIZED, duration, 0, true, true));
			target.addEffect(new MobEffectInstance(NyctoMobEffects.STUNNED, target instanceof Player ? 20 * 5 : 20 * 8, 0, true, true));
			target.addEffect(new MobEffectInstance(MobEffects.GLOWING, Math.min(duration, 20 * 8), 0, true, true));
			target.setDeltaMovement(Vec3.ZERO);
			hits++;
		}
		particles(player, NyctoParticleTypes.HYPNOSIS_STAR.get(), 20, 4);
		particles(player, NyctoParticleTypes.HYPNOSIS_SMALL.get(), 24, 4);
		play(player, NyctoSoundEvents.HYPNOTIZE_USE);
		message(player, "message.nycto.power.hypnotize", hits);
		return hits > 0;
	}));
	public static final NyctoPower KEEN_SENSES_POWER = register(new SimplePower(KEEN_SENSES, 0, 0, NyctoPowers::toggleKeenSenses));
	public static final NyctoPower MIST_FORM_POWER = register(new SimplePower(MIST_FORM, 0, 0, NyctoPowers::toggleMistForm));
	public static final NyctoPower VAMPIRIC_THRALL_POWER = register(new SimplePower(VAMPIRIC_THRALL, 20, 200, player -> {
		Optional<Mob> target = firstLookedAtMob(player, 10, 1.2);
		if (target.isEmpty()) {
			message(player, "message.nycto.power.vampiric_thrall.no_target");
			return false;
		}
		Mob mob = target.get();
		if (!canBeThralled(player, mob)) {
			message(player, "message.nycto.power.vampiric_thrall.invalid_target");
			return false;
		}
		mob.getPersistentData().putUUID(TAG_THRALL_OWNER, player.getUUID());
		mob.getPersistentData().putString(TAG_THRALL_MODE, ThrallMode.FOLLOW.name());
		mob.getPersistentData().remove(TAG_THRALL_UNTIL);
		clearThrallHome(mob);
		applyThrallConversionRules(mob);
		mob.setPersistenceRequired();
		mob.setTarget(null);
		mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20 * 12, 0, true, true));
		mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 20, 0, true, true));
		SyncThrallPayload.send(mob, true);
		particles(player, NyctoParticleTypes.THRALLED.get(), 18, 0.8);
		play(player, NyctoSoundEvents.VAMPIRIC_THRALL_CONVERT);
		message(player, "message.nycto.power.vampiric_thrall", mob.getDisplayName());
		return true;
	}));

	private NyctoPowers() {
	}

	public static Collection<NyctoPower> all() {
		return POWERS.values();
	}

	public static Optional<NyctoPower> byName(String name) {
		return Optional.ofNullable(POWERS.get(normalize(name)));
	}

	public static String normalize(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public static boolean isActive(ServerPlayer player, String name) {
		return activeTicks(player, name) > 0;
	}

	public static int activeTicks(ServerPlayer player, String name) {
		return player.getPersistentData().getInt(TAG_ACTIVE_PREFIX + name);
	}

	public static void setActive(ServerPlayer player, String name, int ticks) {
		player.getPersistentData().putInt(TAG_ACTIVE_PREFIX + name, Math.max(0, ticks));
	}

	public static int getCooldown(ServerPlayer player, String name) {
		return NyctoData.getCooldown(player, name);
	}

	public static void setCooldown(ServerPlayer player, String name, int ticks) {
		NyctoData.setCooldown(player, name, Math.max(0, ticks));
	}

	public static boolean isBatFormActive(ServerPlayer player) {
		return activeTicks(player, BAT_FORM) > 0;
	}

	public static boolean toggleBatForm(ServerPlayer player) {
		if (isBatFormActive(player)) {
			disableBatForm(player, true);
			return true;
		}
		int cost = VampireArmorItem.adjustPowerCost(player, 3);
		if (NyctoData.getBlood(player) < cost) {
			message(player, "message.nycto.power.not_enough_blood", cost, NyctoData.getBlood(player));
			return false;
		}
		disableDarkForm(player, false);
		disableMistForm(player, false);
		NyctoData.addBlood(player, -cost);
		Abilities abilities = player.getAbilities();
		player.getPersistentData().putBoolean(TAG_BAT_FORM_RESTORE, true);
		player.getPersistentData().putBoolean(TAG_BAT_FORM_MAYFLY, abilities.mayfly);
		player.getPersistentData().putBoolean(TAG_BAT_FORM_FLYING, abilities.flying);
		setActive(player, BAT_FORM, 1);
		player.getPersistentData().putInt(TAG_BAT_FORM_DRAIN_TICKS, 300);
		applyBatFormAttributes(player);
		abilities.mayfly = true;
		abilities.flying = true;
		player.onUpdateAbilities();
		SyncBatFormPayload.send(player, 20 * 60 * 60);
		particles(player, ParticleTypes.SMOKE, 48, 0.8);
		play(player, NyctoSoundEvents.BAT_FORM_ON);
		message(player, "message.nycto.power.bat_form");
		return true;
	}

	public static void disableBatForm(ServerPlayer player, boolean applyCooldown) {
		if (!isBatFormActive(player)) {
			removeBatFormAttributes(player);
			restoreBatFormFlight(player);
			return;
		}
		setActive(player, BAT_FORM, 0);
		player.getPersistentData().putInt(TAG_BAT_FORM_DRAIN_TICKS, 0);
		removeBatFormAttributes(player);
		restoreBatFormFlight(player);
		SyncBatFormPayload.send(player, 0);
		particles(player, ParticleTypes.SMOKE, 48, 0.8);
		play(player, NyctoSoundEvents.BAT_FORM_OFF);
		if (applyCooldown) {
			setCooldown(player, BAT_FORM, 200);
		}
	}

	public static void restoreBatFormFlight(ServerPlayer player) {
		if (!player.getPersistentData().getBoolean(TAG_BAT_FORM_RESTORE)) {
			return;
		}
		Abilities abilities = player.getAbilities();
		abilities.mayfly = player.getPersistentData().getBoolean(TAG_BAT_FORM_MAYFLY);
		abilities.flying = player.getPersistentData().getBoolean(TAG_BAT_FORM_FLYING);
		player.getPersistentData().putBoolean(TAG_BAT_FORM_RESTORE, false);
		player.onUpdateAbilities();
	}

	public static boolean isDarkFormActive(ServerPlayer player) {
		return activeTicks(player, DARK_FORM) > 0;
	}

	public static boolean isKeenSensesActive(ServerPlayer player) {
		return activeTicks(player, KEEN_SENSES) > 0;
	}

	public static boolean toggleKeenSenses(ServerPlayer player) {
		if (isKeenSensesActive(player)) {
			disableKeenSenses(player);
			return true;
		}
		int cost = VampireArmorItem.adjustPowerCost(player, 1);
		if (NyctoData.getBlood(player) < cost) {
			message(player, "message.nycto.power.not_enough_blood", cost, NyctoData.getBlood(player));
			return false;
		}
		NyctoData.addBlood(player, -cost);
		setActive(player, KEEN_SENSES, 1);
		player.getPersistentData().putInt(TAG_KEEN_SENSES_DISTANCE, 0);
		player.getPersistentData().putInt(TAG_KEEN_SENSES_DRAIN_TICKS, 200);
		player.getPersistentData().putInt(TAG_KEEN_SENSES_RENDER_TICKS, 20);
		applyKeenSensesAttributes(player);
		player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 260, 0, true, false));
		particles(player, NyctoParticleTypes.HYPNOSIS_INDICATOR.get(), 18, 1.0);
		SyncKeenSensesPayload.send(player);
		play(player, NyctoSoundEvents.KEEN_SENSES_ON);
		message(player, "message.nycto.power.keen_senses");
		return true;
	}

	public static void disableKeenSenses(ServerPlayer player) {
		if (!isKeenSensesActive(player)) {
			removeKeenSensesAttributes(player);
			return;
		}
		setActive(player, KEEN_SENSES, 0);
		player.getPersistentData().putInt(TAG_KEEN_SENSES_DISTANCE, 0);
		player.getPersistentData().putInt(TAG_KEEN_SENSES_DRAIN_TICKS, 0);
		player.getPersistentData().putInt(TAG_KEEN_SENSES_RENDER_TICKS, 20);
		removeKeenSensesAttributes(player);
		SyncKeenSensesPayload.send(player);
		play(player, NyctoSoundEvents.KEEN_SENSES_OFF);
	}

	public static boolean toggleDarkForm(ServerPlayer player) {
		if (isDarkFormActive(player)) {
			disableDarkForm(player, true);
			return true;
		}
		int cost = VampireArmorItem.adjustPowerCost(player, 3);
		if (NyctoData.getBlood(player) < cost) {
			message(player, "message.nycto.power.not_enough_blood", cost, NyctoData.getBlood(player));
			return false;
		}
		disableBatForm(player, false);
		disableMistForm(player, false);
		NyctoData.addBlood(player, -cost);
		setActive(player, DARK_FORM, 1);
		player.getPersistentData().putInt(TAG_DARK_FORM_DRAIN_TICKS, 300);
		player.getPersistentData().putInt(TAG_DARK_FORM_JUMP_COOLDOWN, 0);
		applyDarkFormAttributes(player);
		SyncDarkFormPayload.send(player, 20 * 60 * 60);
		particles(player, ParticleTypes.SMOKE, 48, 1.2);
		play(player, NyctoSoundEvents.DARK_FORM_ON);
		message(player, "message.nycto.power.dark_form");
		return true;
	}

	public static void disableDarkForm(ServerPlayer player, boolean applyCooldown) {
		if (!isDarkFormActive(player)) {
			removeDarkFormAttributes(player);
			return;
		}
		setActive(player, DARK_FORM, 0);
		player.getPersistentData().putInt(TAG_DARK_FORM_DRAIN_TICKS, 0);
		player.getPersistentData().putInt(TAG_DARK_FORM_JUMP_COOLDOWN, 0);
		removeDarkFormAttributes(player);
		SyncDarkFormPayload.send(player, 0);
		particles(player, ParticleTypes.SMOKE, 32, 0.9);
		play(player, NyctoSoundEvents.DARK_FORM_OFF);
		if (applyCooldown) {
			setCooldown(player, DARK_FORM, 200);
		}
	}

	public static boolean isMistFormActive(ServerPlayer player) {
		return activeTicks(player, MIST_FORM) > 0;
	}

	public static boolean toggleMistForm(ServerPlayer player) {
		if (isMistFormActive(player)) {
			disableMistForm(player, true);
			return true;
		}
		int cost = VampireArmorItem.adjustPowerCost(player, 3);
		if (NyctoData.getBlood(player) < cost) {
			message(player, "message.nycto.power.not_enough_blood", cost, NyctoData.getBlood(player));
			return false;
		}
		disableBatForm(player, false);
		disableDarkForm(player, false);
		NyctoData.addBlood(player, -cost);
		setActive(player, MIST_FORM, 1);
		player.getPersistentData().putInt(TAG_MIST_FORM_DRAIN_TICKS, 300);
		SyncMistFormPayload.send(player, 20 * 60 * 60);
		particles(player, ParticleTypes.SMOKE, 48, 1.0);
		particles(player, ParticleTypes.WHITE_SMOKE, 16, 0.9);
		play(player, NyctoSoundEvents.MIST_FORM_ON);
		message(player, "message.nycto.power.mist_form");
		return true;
	}

	public static void disableMistForm(ServerPlayer player, boolean applyCooldown) {
		if (!isMistFormActive(player)) {
			return;
		}
		setActive(player, MIST_FORM, 0);
		player.getPersistentData().putInt(TAG_MIST_FORM_DRAIN_TICKS, 0);
		SyncMistFormPayload.send(player, 0);
		particles(player, ParticleTypes.SMOKE, 32, 0.8);
		particles(player, ParticleTypes.WHITE_SMOKE, 12, 0.65);
		play(player, NyctoSoundEvents.MIST_FORM_OFF);
		if (applyCooldown) {
			setCooldown(player, MIST_FORM, 200);
		}
	}

	public static void applyDarkFormAttributes(LivingEntity entity) {
		applyModifier(entity, Attributes.ARMOR, DARK_FORM_ARMOR_MODIFIER);
		applyModifier(entity, Attributes.ARMOR_TOUGHNESS, DARK_FORM_ARMOR_TOUGHNESS_MODIFIER);
		applyModifier(entity, Attributes.ATTACK_DAMAGE, DARK_FORM_ATTACK_DAMAGE_MODIFIER);
		applyModifier(entity, Attributes.ATTACK_SPEED, DARK_FORM_ATTACK_SPEED_MODIFIER);
		applyModifier(entity, Attributes.BLOCK_INTERACTION_RANGE, DARK_FORM_BLOCK_INTERACTION_RANGE_MODIFIER);
		applyModifier(entity, Attributes.ENTITY_INTERACTION_RANGE, DARK_FORM_ENTITY_INTERACTION_RANGE_MODIFIER);
		applyModifier(entity, Attributes.KNOCKBACK_RESISTANCE, DARK_FORM_KNOCKBACK_RESISTANCE_MODIFIER);
	}

	public static void applyBatFormAttributes(LivingEntity entity) {
		float healthPercent = entity.getMaxHealth() <= 0 ? 1 : entity.getHealth() / entity.getMaxHealth();
		applyModifier(entity, Attributes.MAX_HEALTH, BAT_FORM_MAX_HEALTH_MODIFIER);
		entity.setHealth(Math.max(1, entity.getMaxHealth() * healthPercent));
	}

	public static void applyKeenSensesAttributes(LivingEntity entity) {
		applyModifier(entity, Attributes.MOVEMENT_SPEED, KEEN_SENSES_SPEED_MODIFIER);
	}

	public static void removeBatFormAttributes(LivingEntity entity) {
		float healthPercent = entity.getMaxHealth() <= 0 ? 1 : entity.getHealth() / entity.getMaxHealth();
		removeModifier(entity, Attributes.MAX_HEALTH, BAT_FORM_MAX_HEALTH_MODIFIER);
		entity.setHealth(Math.min(entity.getMaxHealth(), Math.max(1, entity.getMaxHealth() * healthPercent)));
	}

	public static void removeDarkFormAttributes(LivingEntity entity) {
		removeModifier(entity, Attributes.ARMOR, DARK_FORM_ARMOR_MODIFIER);
		removeModifier(entity, Attributes.ARMOR_TOUGHNESS, DARK_FORM_ARMOR_TOUGHNESS_MODIFIER);
		removeModifier(entity, Attributes.ATTACK_DAMAGE, DARK_FORM_ATTACK_DAMAGE_MODIFIER);
		removeModifier(entity, Attributes.ATTACK_SPEED, DARK_FORM_ATTACK_SPEED_MODIFIER);
		removeModifier(entity, Attributes.BLOCK_INTERACTION_RANGE, DARK_FORM_BLOCK_INTERACTION_RANGE_MODIFIER);
		removeModifier(entity, Attributes.ENTITY_INTERACTION_RANGE, DARK_FORM_ENTITY_INTERACTION_RANGE_MODIFIER);
		removeModifier(entity, Attributes.KNOCKBACK_RESISTANCE, DARK_FORM_KNOCKBACK_RESISTANCE_MODIFIER);
	}

	public static void removeKeenSensesAttributes(LivingEntity entity) {
		removeModifier(entity, Attributes.MOVEMENT_SPEED, KEEN_SENSES_SPEED_MODIFIER);
	}

	public static boolean tickActive(ServerPlayer player, String name) {
		int active = activeTicks(player, name);
		if (active <= 0) {
			return false;
		}
		setActive(player, name, active - 1);
		if (active == 1 && BLOODRUSH.equals(name)) {
			SyncBloodrushPayload.send(player, 0);
		}
		if (active == 1 && CARNAGE.equals(name)) {
			removeCarnageDamage(player);
			SyncCarnagePayload.send(player, 0);
		}
		return true;
	}

	public static void applyCarnageDamage(LivingEntity entity) {
		applyModifier(entity, Attributes.ATTACK_DAMAGE, CARNAGE_DAMAGE_MODIFIER);
	}

	public static void removeCarnageDamage(LivingEntity entity) {
		removeModifier(entity, Attributes.ATTACK_DAMAGE, CARNAGE_DAMAGE_MODIFIER);
	}

	private static void applyModifier(LivingEntity entity, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, AttributeModifier modifier) {
		AttributeInstance attributeInstance = entity.getAttribute(attribute);
		if (attributeInstance != null) {
			attributeInstance.removeModifier(modifier.id());
			attributeInstance.addTransientModifier(modifier);
		}
	}

	private static void removeModifier(LivingEntity entity, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, AttributeModifier modifier) {
		AttributeInstance attributeInstance = entity.getAttribute(attribute);
		if (attributeInstance != null) {
			attributeInstance.removeModifier(modifier.id());
		}
	}

	public static void tickCooldowns(ServerPlayer player) {
		for (String name : POWERS.keySet()) {
			int cooldown = getCooldown(player, name);
			if (cooldown > 0) {
				setCooldown(player, name, cooldown - 1);
			}
		}
	}

	public static List<LivingEntity> nearbyLiving(ServerPlayer player, double radius) {
		return player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(radius), target -> isValidTarget(player, target) && target.distanceToSqr(player) <= radius * radius);
	}

	public static int revealNearby(ServerPlayer player, double radius, int ticks) {
		int hits = 0;
		for (LivingEntity target : nearbyLiving(player, radius)) {
			target.addEffect(new MobEffectInstance(MobEffects.GLOWING, ticks, 0, true, true));
			hits++;
		}
		return hits;
	}

	public static int damageAround(ServerPlayer player, Vec3 center, double radius, float damage, boolean stun) {
		int hits = 0;
		for (LivingEntity target : player.level().getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(radius), target -> isValidTarget(player, target) && target.distanceToSqr(center) <= radius * radius)) {
			if (hurt(player, target, damage)) {
				if (stun) {
					target.addEffect(new MobEffectInstance(NyctoMobEffects.STUNNED, 20 * 3, 0, true, true));
				}
				hits++;
			}
		}
		return hits;
	}

	public static int damageLine(ServerPlayer player, Vec3 start, Vec3 end, float damage, double width, boolean stun) {
		int hits = 0;
		for (LivingEntity target : entitiesOnLine(player, start, end, width, 64)) {
			if (hurt(player, target, damage)) {
				if (stun) {
					target.addEffect(new MobEffectInstance(NyctoMobEffects.STUNNED, 20 * 3, 0, true, true));
				}
				hits++;
			}
		}
		return hits;
	}

	public static List<LivingEntity> entitiesOnLine(ServerPlayer player, Vec3 start, Vec3 end, double width, int limit) {
		AABB bounds = new AABB(start, end).inflate(width + 1);
		Vec3 segment = end.subtract(start);
		double lengthSqr = segment.lengthSqr();
		return player.level().getEntitiesOfClass(LivingEntity.class, bounds, target -> {
					if (!isValidTarget(player, target)) {
						return false;
					}
					Vec3 targetCenter = target.position().add(0, target.getBbHeight() * 0.5, 0);
					double scalar = targetCenter.subtract(start).dot(segment) / lengthSqr;
					if (scalar < 0 || scalar > 1) {
						return false;
					}
					Vec3 closest = start.add(segment.scale(scalar));
					return targetCenter.distanceToSqr(closest) <= width * width;
				}).stream()
				.sorted(Comparator.comparingDouble(target -> target.distanceToSqr(player)))
				.limit(limit)
				.toList();
	}

	public static Optional<LivingEntity> firstLookedAt(ServerPlayer player, double distance, double width) {
		Vec3 start = player.getEyePosition();
		Vec3 end = start.add(player.getLookAngle().scale(distance));
		return entitiesOnLine(player, start, end, width, 1).stream().findFirst();
	}

	private static Optional<Mob> firstLookedAtMob(ServerPlayer player, double distance, double width) {
		Vec3 start = player.getEyePosition();
		Vec3 end = start.add(player.getLookAngle().scale(distance));
		AABB bounds = new AABB(start, end).inflate(width + 1);
		Vec3 segment = end.subtract(start);
		double lengthSqr = segment.lengthSqr();
		return player.level().getEntitiesOfClass(Mob.class, bounds, mob -> {
					if (!mob.isAlive()) {
						return false;
					}
					Vec3 targetCenter = mob.position().add(0, mob.getBbHeight() * 0.5, 0);
					double scalar = targetCenter.subtract(start).dot(segment) / lengthSqr;
					if (scalar < 0 || scalar > 1) {
						return false;
					}
					Vec3 closest = start.add(segment.scale(scalar));
					return targetCenter.distanceToSqr(closest) <= width * width && mob.hasLineOfSight(player);
				}).stream()
				.min(Comparator.comparingDouble(mob -> mob.distanceToSqr(player)));
	}

	public static Vec3 findTeleportTarget(ServerPlayer player, double distance) {
		Level level = player.level();
		Vec3 start = player.getEyePosition();
		Vec3 end = start.add(player.getLookAngle().scale(distance));
		BlockHitResult hit = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
		Vec3 target = hit.getType() == HitResult.Type.MISS ? end : hit.getLocation().subtract(player.getLookAngle().normalize().scale(0.8));
		Vec3 floor = new Vec3(target.x, target.y - player.getEyeHeight(), target.z);
		for (int i = 0; i < 8; i++) {
			AABB box = player.getDimensions(player.getPose()).makeBoundingBox(floor);
			if (level.noCollision(player, box)) {
				return floor;
			}
			floor = floor.add(0, 0.25, 0);
		}
		return player.position();
	}

	public static boolean hurt(ServerPlayer player, LivingEntity target, float amount) {
		return target.hurt(player.damageSources().playerAttack(player), amount);
	}

	public static boolean isValidTarget(ServerPlayer player, LivingEntity target) {
		return target.isAlive() && target != player && !target.isAlliedTo(player);
	}

	public static void guideThralls(ServerPlayer owner) {
		UUID ownerId = owner.getUUID();
		for (Mob mob : owner.level().getEntitiesOfClass(Mob.class, owner.getBoundingBox().inflate(24), mob -> isThrallOf(mob, ownerId))) {
			guideThrall(owner, mob);
		}
	}

	public static void tickLoadedThrall(Mob mob) {
		if (!isThrall(mob)) {
			return;
		}
		if (mob instanceof Vex vex && tickVampiricVex(vex)) {
			return;
		}
		int until = mob.getPersistentData().getInt(TAG_THRALL_UNTIL);
		if (until > 0 && mob.tickCount > until) {
			clearThrall(mob);
			return;
		}
		ServerPlayer owner = loadedThrallOwner(mob).orElse(null);
		if (owner == null) {
			SyncThrallPayload.send(mob, true);
			return;
		}
		if (!NyctoData.isVampire(owner) || !NyctoData.hasPower(owner, VAMPIRIC_THRALL)) {
			clearThrall(mob);
			return;
		}
		guideThrall(owner, mob);
	}

	public static void inheritThrallOwnerFromSummoner(Vex vex) {
		if (!(vex.getOwner() instanceof Mob summoner) || !isThrall(summoner) || isThrall(vex)) {
			return;
		}
		thrallOwner(summoner).ifPresent(ownerId -> {
			vex.getPersistentData().putUUID(TAG_THRALL_OWNER, ownerId);
			vex.getPersistentData().putString(TAG_THRALL_MODE, ThrallMode.DEFEND.name());
			vex.getPersistentData().putInt(TAG_THRALL_UNTIL, vex.tickCount + VAMPIRIC_VEX_LIFETIME);
			vex.setPersistenceRequired();
			SyncThrallPayload.send(vex, true);
			if (vex.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(NyctoParticleTypes.THRALLED.get(), vex.getX(), vex.getY() + vex.getBbHeight() * 0.5, vex.getZ(), 8, 0.25, 0.25, 0.25, 0.01);
			}
		});
	}

	private static boolean tickVampiricVex(Vex vex) {
		if (!(vex.getOwner() instanceof Mob summoner) || !isThrall(summoner)) {
			clearThrall(vex);
			vex.discard();
			return true;
		}
		int until = vex.getPersistentData().getInt(TAG_THRALL_UNTIL);
		if (until > 0 && vex.tickCount > until) {
			clearThrall(vex);
			vex.discard();
			return true;
		}
		SyncThrallPayload.send(vex, true);
		LivingEntity target = summoner.getTarget();
		if (target != null && target.isAlive()) {
			vex.setTarget(target);
		} else {
			vex.setTarget(null);
		}
		return true;
	}

	public static void guideThrall(ServerPlayer owner, Mob mob) {
		SyncThrallPayload.send(mob, true);
		tickThrallBlood(owner, mob);
		if (mob instanceof Villager villager) {
			tickThralledVillager(villager);
		}
		if (mob instanceof AbstractPiglin piglin) {
			tickThralledPiglin(owner, piglin);
		}
		rememberNearbyBloodFountain(mob);
		shareBloodFountainMemory(owner, mob);
		ThrallMode mode = thrallMode(mob);
		LivingEntity target = owner.getLastHurtMob();
		if (target == null) {
			target = owner.getLastHurtByMob();
		}
		if (mode != ThrallMode.STAY && isValidThrallTarget(owner, mob, target) && mob.distanceToSqr(target) < 24 * 24) {
			mob.setTarget(target);
		}
		if (!isValidThrallTarget(owner, mob, mob.getTarget())) {
			mob.setTarget(null);
		}
		if (mob.getTarget() != null) {
			return;
		}
		switch (mode) {
			case FOLLOW -> {
				if (mob.distanceToSqr(owner) > 10) {
					mob.getNavigation().moveTo(owner, mob instanceof PathfinderMob ? 1.15 : 1.0);
				}
			}
			case STAY -> {
				mob.getNavigation().stop();
				mob.setTarget(null);
			}
			case WANDER -> guideThrallToHome(mob);
			case DEFEND -> {
				LivingEntity defendTarget = findThrallDefenseTarget(owner, mob);
				if (defendTarget != null) {
					mob.setTarget(defendTarget);
					return;
				}
				if (mob.distanceToSqr(owner) > 24 * 24) {
					mob.getNavigation().moveTo(owner, mob instanceof PathfinderMob ? 1.05 : 1.0);
				}
			}
		}
	}

	private static Optional<ServerPlayer> loadedThrallOwner(Mob mob) {
		return thrallOwner(mob)
				.map(ownerId -> mob.level().getServer() == null ? null : mob.level().getServer().getPlayerList().getPlayer(ownerId));
	}

	public static int addThrallBlood(Mob mob, int amount) {
		int blood = Mth.clamp(mob.getPersistentData().getInt(TAG_THRALL_BLOOD) + amount, 0, MAX_THRALL_BLOOD);
		mob.getPersistentData().putInt(TAG_THRALL_BLOOD, blood);
		return blood;
	}

	private static void tickThrallBlood(ServerPlayer owner, Mob mob) {
		if (mob.getHealth() >= mob.getMaxHealth() || NyctoData.getHealBlockTicks(mob) > 0) {
			return;
		}
		if (addThrallBlood(mob, 0) <= 0) {
			seekBloodFountain(mob);
			return;
		}
		if (mob.tickCount % 15 != 0) {
			return;
		}
		boolean alternateDrain = mob.getPersistentData().getBoolean(TAG_THRALL_ALTERNATE_DRAIN);
		if (!alternateDrain) {
			addThrallBlood(mob, -1);
		}
		mob.getPersistentData().putBoolean(TAG_THRALL_ALTERNATE_DRAIN, !alternateDrain);
		mob.heal(1);
		if (mob.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(NyctoParticleTypes.AMBROSIA.get(), mob.getX(), mob.getY() + mob.getBbHeight() * 0.55, mob.getZ(), 3, mob.getBbWidth() * 0.25, mob.getBbHeight() * 0.2, mob.getBbWidth() * 0.25, 0.01);
		}
	}

	private static void seekBloodFountain(Mob mob) {
		BlockPos fountain = closestFilledBloodFountain(mob);
		if (fountain == null) {
			return;
		}
		if (fountain.closerToCenterThan(mob.position(), 2.5)) {
			BlockState state = mob.level().getBlockState(fountain);
			mob.level().setBlockAndUpdate(fountain, state.setValue(BloodFountainBlock.FILL_STATE, BloodFountainBlock.FillState.EMPTY));
			addThrallBlood(mob, 10);
			mob.level().playSound(null, fountain, NyctoSoundEvents.BLOOD_BOTTLE_DRINK.get(), SoundSource.NEUTRAL, 0.7F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
			if (mob.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(NyctoParticleTypes.BLOOD.get(), mob.getX(), mob.getY() + mob.getBbHeight() * 0.5, mob.getZ(), 10, mob.getBbWidth() * 0.3, mob.getBbHeight() * 0.25, mob.getBbWidth() * 0.3, 0.02);
			}
			return;
		}
		if (mob.getNavigation().isDone()) {
			mob.getNavigation().moveTo(fountain.getX() + 0.5, fountain.getY(), fountain.getZ() + 0.5, mob instanceof PathfinderMob ? 1.0 : 0.9);
		}
	}

	private static BlockPos closestFilledBloodFountain(Mob mob) {
		BlockPos remembered = validRememberedBloodFountain(mob, 64);
		if (remembered != null) {
			return remembered;
		}
		BlockPos best = null;
		double bestDistance = Double.MAX_VALUE;
		for (BlockPos pos : BlockPos.withinManhattan(mob.blockPosition(), 12, 8, 12)) {
			BlockState state = mob.level().getBlockState(pos);
			if (!state.is(NyctoBlocks.BLOOD_FOUNTAIN.get()) || state.getValue(BloodFountainBlock.FILL_STATE) != BloodFountainBlock.FillState.BLOOD || state.getValue(BlockStateProperties.LOCKED)) {
				continue;
			}
			double distance = pos.distToCenterSqr(mob.position());
			if (distance < bestDistance && hasLineToBlock(mob, pos)) {
				best = pos.immutable();
				bestDistance = distance;
			}
		}
		if (best != null) {
			rememberBloodFountain(mob, best);
		}
		return best;
	}

	private static void rememberNearbyBloodFountain(Mob mob) {
		rememberedBloodFountain(mob).ifPresent(pos -> {
			if (pos.closerToCenterThan(mob.position(), 12) && !isUsableBloodFountain(mob, pos)) {
				clearBloodFountainMemory(mob);
			}
		});
		BlockPos closest = null;
		double closestDistance = Double.MAX_VALUE;
		for (BlockPos pos : BlockPos.withinManhattan(mob.blockPosition(), 12, 8, 12)) {
			if (isUsableBloodFountain(mob, pos) && hasLineToBlock(mob, pos)) {
				double distance = pos.distToCenterSqr(mob.position());
				if (distance < closestDistance) {
					closest = pos.immutable();
					closestDistance = distance;
				}
			}
		}
		if (closest != null) {
			rememberBloodFountain(mob, closest);
		}
	}

	private static void shareBloodFountainMemory(ServerPlayer owner, Mob mob) {
		BlockPos memory = validRememberedBloodFountain(mob, 256);
		if (memory == null) {
			return;
		}
		long memoryTime = mob.getPersistentData().getLong(TAG_THRALL_FOUNTAIN_TIME);
		for (Mob other : mob.level().getEntitiesOfClass(Mob.class, mob.getBoundingBox().inflate(12), other -> other != mob && isThrallOf(other, owner.getUUID()) && mob.hasLineOfSight(other))) {
			long otherTime = other.getPersistentData().getLong(TAG_THRALL_FOUNTAIN_TIME);
			if (otherTime < memoryTime) {
				rememberBloodFountain(other, memory, memoryTime);
			}
			BlockPos otherMemory = validRememberedBloodFountain(other, 256);
			if (otherMemory != null && otherTime > memoryTime) {
				rememberBloodFountain(mob, otherMemory, otherTime);
			}
		}
	}

	private static BlockPos validRememberedBloodFountain(Mob mob, double range) {
		Optional<BlockPos> memory = rememberedBloodFountain(mob);
		if (memory.isEmpty()) {
			return null;
		}
		BlockPos pos = memory.get();
		if (!pos.closerToCenterThan(mob.position(), range)) {
			clearBloodFountainMemory(mob);
			return null;
		}
		return isUsableBloodFountain(mob, pos) ? pos : null;
	}

	private static Optional<BlockPos> rememberedBloodFountain(Mob mob) {
		if (!mob.getPersistentData().contains(TAG_THRALL_FOUNTAIN_X) || !mob.getPersistentData().contains(TAG_THRALL_FOUNTAIN_Y) || !mob.getPersistentData().contains(TAG_THRALL_FOUNTAIN_Z)) {
			return Optional.empty();
		}
		return Optional.of(new BlockPos(mob.getPersistentData().getInt(TAG_THRALL_FOUNTAIN_X), mob.getPersistentData().getInt(TAG_THRALL_FOUNTAIN_Y), mob.getPersistentData().getInt(TAG_THRALL_FOUNTAIN_Z)));
	}

	private static void rememberBloodFountain(Mob mob, BlockPos pos) {
		rememberBloodFountain(mob, pos, mob.level().getGameTime());
	}

	private static void rememberBloodFountain(Mob mob, BlockPos pos, long time) {
		mob.getPersistentData().putInt(TAG_THRALL_FOUNTAIN_X, pos.getX());
		mob.getPersistentData().putInt(TAG_THRALL_FOUNTAIN_Y, pos.getY());
		mob.getPersistentData().putInt(TAG_THRALL_FOUNTAIN_Z, pos.getZ());
		mob.getPersistentData().putLong(TAG_THRALL_FOUNTAIN_TIME, time);
	}

	private static boolean isUsableBloodFountain(Mob mob, BlockPos pos) {
		BlockState state = mob.level().getBlockState(pos);
		return state.is(NyctoBlocks.BLOOD_FOUNTAIN.get()) && state.getValue(BloodFountainBlock.FILL_STATE) == BloodFountainBlock.FillState.BLOOD && !state.getValue(BlockStateProperties.LOCKED);
	}

	private static void clearBloodFountainMemory(Mob mob) {
		mob.getPersistentData().remove(TAG_THRALL_FOUNTAIN_X);
		mob.getPersistentData().remove(TAG_THRALL_FOUNTAIN_Y);
		mob.getPersistentData().remove(TAG_THRALL_FOUNTAIN_Z);
		mob.getPersistentData().remove(TAG_THRALL_FOUNTAIN_TIME);
	}

	private static boolean hasLineToBlock(Entity entity, BlockPos pos) {
		BlockHitResult result = entity.level().clip(new ClipContext(entity.getEyePosition(), pos.getCenter(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
		return result.getType() == HitResult.Type.BLOCK && result.getBlockPos().equals(pos);
	}

	private static void clearThrall(Mob mob) {
		mob.getPersistentData().remove(TAG_THRALL_OWNER);
		mob.getPersistentData().remove(TAG_THRALL_UNTIL);
		mob.getPersistentData().remove(TAG_THRALL_MODE);
		mob.getPersistentData().remove(TAG_THRALL_BLOOD);
		mob.getPersistentData().remove(TAG_THRALL_ALTERNATE_DRAIN);
		clearBloodFountainMemory(mob);
		clearThrallHome(mob);
		mob.setTarget(null);
		SyncThrallPayload.send(mob, false);
	}

	private static void tickThralledVillager(Villager villager) {
		Brain<Villager> brain = villager.getBrain();
		brain.eraseMemory(MemoryModuleType.BREED_TARGET);
		brain.eraseMemory(MemoryModuleType.HOME);
		brain.eraseMemory(MemoryModuleType.JOB_SITE);
		brain.eraseMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
		brain.eraseMemory(MemoryModuleType.MEETING_POINT);
		brain.eraseMemory(MemoryModuleType.SECONDARY_JOB_SITE);
		brain.eraseMemory(MemoryModuleType.HIDING_PLACE);
		brain.eraseMemory(MemoryModuleType.GOLEM_DETECTED_RECENTLY);
		brain.setDefaultActivity(Activity.IDLE);
		brain.setActiveActivityIfPossible(Activity.IDLE);
		villager.setSprinting(false);
	}

	private static void tickThralledPiglin(ServerPlayer owner, AbstractPiglin piglin) {
		piglin.setImmuneToZombification(true);
		Brain<?> brain = piglin.getBrain();
		brain.getMemory(MemoryModuleType.ATTACK_TARGET)
				.filter(target -> !isValidThrallTarget(owner, piglin, target))
				.ifPresent(target -> {
					brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
					piglin.setTarget(null);
				});
		brain.getMemory(MemoryModuleType.ANGRY_AT)
				.filter(owner.getUUID()::equals)
				.ifPresent(ownerId -> brain.eraseMemory(MemoryModuleType.ANGRY_AT));
		thrallOwner(piglin).ifPresent(ownerId -> brain.getMemory(MemoryModuleType.ANGRY_AT)
				.filter(angryAt -> piglin.level() instanceof ServerLevel serverLevel
						&& serverLevel.getEntity(angryAt) instanceof Mob mob
						&& isThrallOf(mob, ownerId))
				.ifPresent(angryAt -> brain.eraseMemory(MemoryModuleType.ANGRY_AT)));
	}

	public static boolean isThrallOf(Mob mob, UUID ownerId) {
		return mob.getPersistentData().hasUUID(TAG_THRALL_OWNER) && mob.getPersistentData().getUUID(TAG_THRALL_OWNER).equals(ownerId);
	}

	public static boolean isThrall(Mob mob) {
		return mob.getPersistentData().hasUUID(TAG_THRALL_OWNER);
	}

	public static Optional<UUID> thrallOwner(Mob mob) {
		return mob.getPersistentData().hasUUID(TAG_THRALL_OWNER) ? Optional.of(mob.getPersistentData().getUUID(TAG_THRALL_OWNER)) : Optional.empty();
	}

	public static boolean canBeThralled(ServerPlayer owner, Mob mob) {
		if (isThrall(mob) || !mob.isAlive() || mob.getType().is(NyctoTags.CANNOT_BE_TARGETED_BY_THRALLS) || !mob.getType().is(NyctoTags.CAN_BE_THRALLED)) {
			return false;
		}
		if (mob instanceof AbstractHorse horse) {
			return horse.isTamed() && owner.getUUID().equals(horse.getOwnerUUID());
		}
		if (mob instanceof TamableAnimal tamable) {
			return tamable.isTame() && tamable.isOwnedBy(owner);
		}
		return mob.getHealth() <= 15;
	}

	public static boolean isValidThrallTarget(ServerPlayer owner, Mob thrall, LivingEntity target) {
		if (target == null || !target.isAlive() || target == owner || target == thrall || target.isAlliedTo(owner) || target.isAlliedTo(thrall)) {
			return false;
		}
		if (target instanceof Mob mob && isThrallOf(mob, owner.getUUID())) {
			return false;
		}
		if (target.getType().is(NyctoTags.CANNOT_BE_TARGETED_BY_THRALLS) || target.getType() == NyctoEntityTypes.VAMPIRE.get()) {
			return false;
		}
		return !(target instanceof Player player && NyctoData.isVampire(player)) && thrall.hasLineOfSight(target);
	}

	private static void applyThrallConversionRules(Mob mob) {
		mob.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
		mob.removeEffect(MobEffects.WEAKNESS);
		mob.removeEffect(MobEffects.GLOWING);
		if (mob instanceof Villager villager) {
			AttributeInstance movementSpeed = villager.getAttribute(Attributes.MOVEMENT_SPEED);
			if (movementSpeed != null) {
				movementSpeed.setBaseValue(0.35);
			}
			if (villager.level() instanceof ServerLevel serverLevel) {
				villager.refreshBrain(serverLevel);
			}
		}
		if (mob instanceof AbstractPiglin piglin) {
			piglin.setImmuneToZombification(true);
		}
		if (mob instanceof Raider raider) {
			if (raider.getCurrentRaid() != null) {
				raider.getCurrentRaid().removeFromRaid(raider, true);
			}
			raider.setCanJoinRaid(false);
			raider.setCurrentRaid(null);
		}
	}

	private static LivingEntity findThrallDefenseTarget(ServerPlayer owner, Mob thrall) {
		return thrall.level().getEntitiesOfClass(LivingEntity.class, thrall.getBoundingBox().inflate(16), target -> isValidThrallTarget(owner, thrall, target)).stream()
				.min(Comparator.comparingDouble(thrall::distanceToSqr))
				.orElse(null);
	}

	public static ThrallMode thrallMode(Mob mob) {
		try {
			return ThrallMode.valueOf(mob.getPersistentData().getString(TAG_THRALL_MODE));
		} catch (IllegalArgumentException exception) {
			return ThrallMode.FOLLOW;
		}
	}

	public static ThrallMode cycleThrallMode(Mob mob) {
		ThrallMode next = switch (thrallMode(mob)) {
			case FOLLOW -> ThrallMode.STAY;
			case STAY -> ThrallMode.WANDER;
			case WANDER -> ThrallMode.DEFEND;
			case DEFEND -> ThrallMode.FOLLOW;
		};
		mob.getPersistentData().putString(TAG_THRALL_MODE, next.name());
		if (next == ThrallMode.WANDER || next == ThrallMode.STAY) {
			mob.getPersistentData().putInt(TAG_THRALL_HOME_X, mob.blockPosition().getX());
			mob.getPersistentData().putInt(TAG_THRALL_HOME_Y, mob.blockPosition().getY());
			mob.getPersistentData().putInt(TAG_THRALL_HOME_Z, mob.blockPosition().getZ());
		}
		if (next == ThrallMode.FOLLOW || next == ThrallMode.DEFEND) {
			clearThrallHome(mob);
		}
		mob.setTarget(null);
		return next;
	}

	private static void clearThrallHome(Mob mob) {
		mob.getPersistentData().remove(TAG_THRALL_HOME_X);
		mob.getPersistentData().remove(TAG_THRALL_HOME_Y);
		mob.getPersistentData().remove(TAG_THRALL_HOME_Z);
	}

	private static void guideThrallToHome(Mob mob) {
		if (!mob.getPersistentData().contains(TAG_THRALL_HOME_X) || !mob.getPersistentData().contains(TAG_THRALL_HOME_Y) || !mob.getPersistentData().contains(TAG_THRALL_HOME_Z)) {
			mob.getPersistentData().putInt(TAG_THRALL_HOME_X, mob.blockPosition().getX());
			mob.getPersistentData().putInt(TAG_THRALL_HOME_Y, mob.blockPosition().getY());
			mob.getPersistentData().putInt(TAG_THRALL_HOME_Z, mob.blockPosition().getZ());
			return;
		}
		double x = mob.getPersistentData().getInt(TAG_THRALL_HOME_X) + 0.5;
		double y = mob.getPersistentData().getInt(TAG_THRALL_HOME_Y);
		double z = mob.getPersistentData().getInt(TAG_THRALL_HOME_Z) + 0.5;
		if (mob.distanceToSqr(x, y, z) > 12 * 12 && mob.getNavigation().isDone()) {
			mob.getNavigation().moveTo(x, y, z, mob instanceof PathfinderMob ? 1.0 : 0.9);
		}
	}

	public enum ThrallMode {
		FOLLOW,
		STAY,
		WANDER,
		DEFEND
	}

	public static void particles(ServerPlayer player, net.minecraft.core.particles.ParticleOptions particle, int count, double spread) {
		ServerLevel level = player.serverLevel();
		level.sendParticles(particle, player.getX(), player.getY() + player.getBbHeight() * 0.5, player.getZ(), count, spread, spread * 0.5, spread, 0.02);
	}

	public static void lineParticles(ServerPlayer player, Vec3 start, Vec3 end, int steps) {
		Vec3 delta = end.subtract(start);
		for (int i = 0; i <= steps; i++) {
			Vec3 point = start.add(delta.scale(i / (double) steps));
			player.serverLevel().sendParticles(NyctoParticleTypes.BATSTEP_CENTER.get(), point.x, point.y, point.z, 1, 0.08, 0.08, 0.08, 0.01);
			if (i % 2 == 0) {
				player.serverLevel().sendParticles(NyctoParticleTypes.BATSTEP_LEFT.get(), point.x, point.y, point.z, 1, 0.12, 0.12, 0.12, 0.01);
				player.serverLevel().sendParticles(NyctoParticleTypes.BATSTEP_RIGHT.get(), point.x, point.y, point.z, 1, 0.12, 0.12, 0.12, 0.01);
			}
		}
	}

	public static void play(ServerPlayer player, DeferredHolder<SoundEvent, SoundEvent> sound) {
		player.level().playSound(null, player.getX(), player.getY(), player.getZ(), sound.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
	}

	public static void message(ServerPlayer player, String key, Object... args) {
		player.displayClientMessage(Component.translatable(key, args), true);
	}

	public static void addBlood(ServerPlayer player, int amount) {
		moriyashiine.nycto.neoforge.NyctoData.addBlood(player, amount);
	}

	private static NyctoPower register(NyctoPower power) {
		POWERS.put(power.name(), power);
		NyctoPowerRegistry.register(power.name(), power);
		return power;
	}

	@FunctionalInterface
	private interface PowerUse {
		boolean use(ServerPlayer player);
	}

	private record SimplePower(String name, int bloodCost, int cooldownTicks, PowerUse action) implements NyctoPower {
		@Override
		public boolean use(ServerPlayer player) {
			return action.use(player);
		}
	}
}
