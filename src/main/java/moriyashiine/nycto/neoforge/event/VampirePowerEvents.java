/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.event;

import com.mojang.brigadier.arguments.StringArgumentType;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.entity.Vampire;
import moriyashiine.nycto.neoforge.hunter.NyctoHunterUtil;
import moriyashiine.nycto.neoforge.item.VampireArmorItem;
import moriyashiine.nycto.neoforge.network.AddBloodBarrierParticlesPayload;
import moriyashiine.nycto.neoforge.network.SyncBatFormPayload;
import moriyashiine.nycto.neoforge.network.SyncBloodBarrierPayload;
import moriyashiine.nycto.neoforge.network.SyncCarnagePayload;
import moriyashiine.nycto.neoforge.network.SyncDarkFormPayload;
import moriyashiine.nycto.neoforge.network.SyncEntityBloodPayload;
import moriyashiine.nycto.neoforge.network.SyncBloodrushPayload;
import moriyashiine.nycto.neoforge.network.SyncMistFormPayload;
import moriyashiine.nycto.neoforge.network.SyncThrallPayload;
import moriyashiine.nycto.neoforge.power.NyctoPower;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import moriyashiine.nycto.neoforge.registry.NyctoMobEffects;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import moriyashiine.nycto.neoforge.util.NyctoBloodUtil;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.schedule.Activity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

@EventBusSubscriber(modid = NyctoNeoForge.MOD_ID)
public final class VampirePowerEvents {
	private static final int FORM_SYNC_TICKS = 20 * 60 * 60;

	private VampirePowerEvents() {
	}

	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(literal("nycto_power")
				.then(literal("list")
						.executes(context -> {
							context.getSource().sendSuccess(() -> Component.literal("Nycto powers: " + String.join(", ", NyctoPowers.all().stream().map(NyctoPower::name).toList())), false);
							return NyctoPowers.all().size();
						}))
				.then(literal("use")
						.then(argument("name", StringArgumentType.word())
								.suggests((context, builder) -> SharedSuggestionProvider.suggest(NyctoPowers.all().stream().map(NyctoPower::name), builder))
								.executes(context -> usePower(context.getSource().getPlayerOrException(), StringArgumentType.getString(context, "name"))))));
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		if (!(event.getEntity() instanceof ServerPlayer player)) {
			return;
		}
		if (!NyctoData.isVampire(player)) {
			NyctoPowers.disableBatForm(player, false);
			NyctoPowers.disableKeenSenses(player);
			NyctoPowers.disableDarkForm(player, false);
			NyctoPowers.disableMistForm(player, false);
			if (NyctoPowers.activeTicks(player, NyctoPowers.BLOODRUSH) > 0) {
				NyctoPowers.setActive(player, NyctoPowers.BLOODRUSH, 0);
				SyncBloodrushPayload.send(player, 0);
			}
			if (NyctoPowers.activeTicks(player, NyctoPowers.CARNAGE) > 0) {
				NyctoPowers.setActive(player, NyctoPowers.CARNAGE, 0);
				NyctoPowers.removeCarnageDamage(player);
				SyncCarnagePayload.send(player, 0);
			}
			if (NyctoData.getBloodBarrierLayers(player) > 0) {
				NyctoData.setBloodBarrierLayers(player, 0);
				SyncBloodBarrierPayload.send(player, 0);
			}
			return;
		}
		if (hasPower(player, NyctoPowers.NIGHT_VISION) && NyctoData.isNightVisionEnabled(player)) {
			player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 260, 0, true, false));
			player.addEffect(new MobEffectInstance(NyctoMobEffects.NIGHT_VISION, 260, 0, true, true));
		}
		tickTimedPowers(player);
		NyctoData.tickBloodBarrier(player);
	}

	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Post event) {
		if (!(event.getEntity() instanceof LivingEntity living) || living.level().isClientSide()) {
			return;
		}
		tickCarnageBleed(living);
		if (!living.hasEffect(NyctoMobEffects.STUNNED)) {
			return;
		}
		living.setDeltaMovement(living.getDeltaMovement().multiply(0, 0, 0));
		living.hurtMarked = true;
		if (living instanceof Mob mob) {
			mob.getNavigation().stop();
			mob.setTarget(null);
		}
	}

	@SubscribeEvent
	public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			if (NyctoPowers.isMistFormActive(player)) {
				if (event.getSource().is(DamageTypeTags.IS_FALL)) {
					event.setCanceled(true);
					return;
				}
				player.hurtTime = player.hurtDuration = 10;
				NyctoPowers.disableMistForm(player, true);
				event.setCanceled(true);
				return;
			}
			int layers = NyctoData.getBloodBarrierLayers(player);
			if (layers > 0 && !event.getSource().is(DamageTypeTags.BYPASSES_ARMOR) && !NyctoHunterUtil.isVampireWeaknessDamage(event.getSource())) {
				player.hurtTime = player.hurtDuration = 10;
				if (event.getAmount() >= 3) {
					NyctoData.setBloodBarrierLayers(player, layers - 1);
					AddBloodBarrierParticlesPayload.send(player, layers - 1);
					NyctoPowers.play(player, NyctoSoundEvents.BLOOD_BARRIER_BREAK);
					NyctoPowers.message(player, "message.nycto.power.blood_barrier.absorb", layers - 1);
				} else {
					NyctoPowers.play(player, NyctoSoundEvents.BLOOD_BARRIER_HIT);
				}
				event.setCanceled(true);
			}
		}
		if (event.getEntity() instanceof Vampire vampire) {
			int layers = vampire.getBloodBarrierLayers();
			if (layers > 0 && !event.getSource().is(DamageTypeTags.BYPASSES_ARMOR) && !NyctoHunterUtil.isVampireWeaknessDamage(event.getSource())) {
				vampire.hurtTime = vampire.hurtDuration = 10;
				if (event.getAmount() >= 3) {
					vampire.breakBloodBarrier();
				} else {
					vampire.playBloodBarrierHit();
				}
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDamagePre(LivingDamageEvent.Pre event) {
		if (event.getSource().getEntity() instanceof ServerPlayer player && NyctoPowers.isBatFormActive(player)) {
			event.setNewDamage(event.getNewDamage() * 0.1F);
		}
		if (event.getEntity() instanceof ServerPlayer player && NyctoPowers.isDarkFormActive(player) && !event.getSource().is(DamageTypeTags.BYPASSES_ARMOR) && !NyctoHunterUtil.isVampireWeaknessDamage(event.getSource())) {
			event.setNewDamage(event.getNewDamage() * 0.68F);
		}
	}

	@SubscribeEvent
	public static void onLivingDamagePost(LivingDamageEvent.Post event) {
		if (event.getSource().getEntity() instanceof ServerPlayer player && NyctoPowers.isMistFormActive(player)) {
			NyctoPowers.disableMistForm(player, true);
		}
		if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
			return;
		}
		LivingEntity target = event.getEntity();
		tryBloodFlechettesLifeDrain(attacker, target);
		if (!isCarnageActive(attacker)) {
			return;
		}
		target.getPersistentData().putInt(NyctoPowers.TAG_CARNAGE_BLEED_UNTIL, target.tickCount + 80);
		NyctoBloodUtil.drainAttack(target, Math.max(4, NyctoBloodUtil.drainAmount(target) / 2));
		NyctoData.applyHealBlock(target, 80);
		if (target.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
			serverLevel.sendParticles(moriyashiine.nycto.neoforge.registry.NyctoParticleTypes.BLOOD.get(), target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(), 10, 0.25, 0.35, 0.25, 0.02);
		}
		target.level().playSound(null, target.getX(), target.getY(), target.getZ(), NyctoSoundEvents.CARNAGE_HIT.get(), SoundSource.PLAYERS, 1.0F, Mth.nextFloat(target.getRandom(), 0.8F, 1.2F));
	}

	private static void tryBloodFlechettesLifeDrain(LivingEntity attacker, LivingEntity target) {
		int markedUntil = target.getPersistentData().getInt(NyctoPowers.TAG_BLOOD_FLECHETTES_MARK_UNTIL);
		if (markedUntil <= target.tickCount) {
			if (markedUntil > 0) {
				target.getPersistentData().remove(NyctoPowers.TAG_BLOOD_FLECHETTES_MARK_UNTIL);
			}
			return;
		}
		if (!isVampireAttacker(attacker) || !NyctoBloodUtil.drainAttack(target, Math.max(3, NyctoBloodUtil.drainAmount(target) / 3))) {
			return;
		}
		NyctoData.applyHealBlock(target, 80);
		if (attacker instanceof ServerPlayer player) {
			NyctoData.addBlood(player, NyctoBloodUtil.fillAmount(target, true));
			player.heal(1);
			NyctoPowers.play(player, NyctoSoundEvents.BLOOD_FLECHETTES_LIFE_DRAIN);
		} else if (attacker instanceof Vampire vampire) {
			vampire.heal(1);
			vampire.level().playSound(null, vampire.getX(), vampire.getY(), vampire.getZ(), NyctoSoundEvents.BLOOD_FLECHETTES_LIFE_DRAIN.get(), SoundSource.HOSTILE, 0.7F, 1.0F);
		}
	}

	private static boolean isVampireAttacker(LivingEntity attacker) {
		return attacker instanceof Vampire || attacker instanceof ServerPlayer player && NyctoData.isVampire(player);
	}

	private static void tickCarnageBleed(LivingEntity living) {
		int bleedUntil = living.getPersistentData().getInt(NyctoPowers.TAG_CARNAGE_BLEED_UNTIL);
		if (bleedUntil <= 0) {
			return;
		}
		if (bleedUntil <= living.tickCount || !NyctoBloodUtil.hasDrainableBlood(living)) {
			living.getPersistentData().remove(NyctoPowers.TAG_CARNAGE_BLEED_UNTIL);
			return;
		}
		if (living.tickCount % 20 == 0) {
			NyctoData.applyHealBlock(living, 40);
			NyctoBloodUtil.drainAttack(living, 2);
		}
	}

	private static boolean isCarnageActive(LivingEntity attacker) {
		if (attacker instanceof ServerPlayer player) {
			return NyctoPowers.isActive(player, NyctoPowers.CARNAGE);
		}
		return attacker instanceof Vampire vampire && vampire.isCarnageActive();
	}

	@SubscribeEvent
	public static void onLivingHeal(LivingHealEvent event) {
		int blockedUntil = event.getEntity().getPersistentData().getInt(NyctoPowers.TAG_HEAL_BLOCK_UNTIL);
		if (blockedUntil > event.getEntity().tickCount) {
			if (event.getEntity() instanceof ServerPlayer player && VampireArmorItem.hasHealBlockResistance(player)) {
				event.setAmount(event.getAmount() * 0.5F);
				return;
			}
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		if (event.getEntity() instanceof ServerPlayer player && (NyctoPowers.isMistFormActive(player) || NyctoPowers.isActive(player, NyctoPowers.BAT_FORM))) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onCriticalHit(CriticalHitEvent event) {
		if (event.getEntity() instanceof ServerPlayer player && NyctoPowers.isMistFormActive(player)) {
			event.setCriticalHit(true);
			event.setDamageMultiplier(Math.max(event.getDamageMultiplier(), 1.5F));
		}
	}

	@SubscribeEvent
	public static void onStartTracking(PlayerEvent.StartTracking event) {
		if (!(event.getEntity() instanceof ServerPlayer viewer)) {
			return;
		}
		if (event.getTarget() instanceof ServerPlayer tracked) {
			syncVisiblePowerState(viewer, tracked);
		}
		if (event.getTarget() instanceof LivingEntity living && NyctoBloodUtil.hasDrainableBlood(living)) {
			PacketDistributor.sendToPlayer(viewer, new SyncEntityBloodPayload(living.getId(), NyctoBloodUtil.getBlood(living)));
		}
		if (event.getTarget() instanceof Mob mob) {
			PacketDistributor.sendToPlayer(viewer, new SyncThrallPayload(mob.getId(), NyctoPowers.isThrall(mob)));
		}
		if (event.getTarget() instanceof Vampire vampire) {
			PacketDistributor.sendToPlayer(viewer, new SyncBloodBarrierPayload(vampire.getId(), vampire.getBloodBarrierLayers()));
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			syncVisiblePowerState(player, player);
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			syncVisiblePowerState(player, player);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			syncVisiblePowerState(player, player);
		}
	}

	private static void syncVisiblePowerState(ServerPlayer viewer, ServerPlayer tracked) {
		Entity entity = tracked;
		if (NyctoPowers.isBatFormActive(tracked)) {
			PacketDistributor.sendToPlayer(viewer, new SyncBatFormPayload(entity.getId(), FORM_SYNC_TICKS));
		}
		PacketDistributor.sendToPlayer(viewer, new SyncBloodBarrierPayload(entity.getId(), NyctoData.getBloodBarrierLayers(tracked)));
		if (NyctoPowers.isDarkFormActive(tracked)) {
			PacketDistributor.sendToPlayer(viewer, new SyncDarkFormPayload(entity.getId(), FORM_SYNC_TICKS, tracked.getPersistentData().getInt(NyctoPowers.TAG_DARK_FORM_JUMP_COOLDOWN)));
		}
		if (NyctoPowers.isMistFormActive(tracked)) {
			PacketDistributor.sendToPlayer(viewer, new SyncMistFormPayload(entity.getId(), FORM_SYNC_TICKS));
		}
		int bloodrushTicks = NyctoPowers.activeTicks(tracked, NyctoPowers.BLOODRUSH);
		if (bloodrushTicks > 0) {
			PacketDistributor.sendToPlayer(viewer, new SyncBloodrushPayload(entity.getId(), bloodrushTicks));
		}
		int carnageTicks = NyctoPowers.activeTicks(tracked, NyctoPowers.CARNAGE);
		if (carnageTicks > 0) {
			PacketDistributor.sendToPlayer(viewer, new SyncCarnagePayload(entity.getId(), carnageTicks));
		}
	}

	private static void tickTimedPowers(ServerPlayer player) {
		if (NyctoPowers.isBatFormActive(player)) {
			tickBatForm(player);
		}
		if (NyctoPowers.activeTicks(player, NyctoPowers.BLOODRUSH) > 0) {
			if (player.horizontalCollision || player.isShiftKeyDown()) {
				NyctoPowers.setActive(player, NyctoPowers.BLOODRUSH, 0);
				player.startAutoSpinAttack(1, 0, null);
				SyncBloodrushPayload.send(player, 0);
			} else {
				player.setDeltaMovement(player.getLookAngle().normalize().scale(2 / 3F));
				player.hurtMarked = true;
				player.resetFallDistance();
				player.clearFire();
				player.serverLevel().sendParticles(moriyashiine.nycto.neoforge.registry.NyctoParticleTypes.BLOOD.get(), player.getX(), player.getEyeY(), player.getZ(), 1, 0.08, 0.08, 0.08, 0.01);
			}
		}
		if (NyctoPowers.activeTicks(player, NyctoPowers.CARNAGE) > 0) {
			NyctoPowers.applyCarnageDamage(player);
		}
		if (NyctoPowers.activeTicks(player, NyctoPowers.HAEMOGENESIS) > 0) {
			tickHaemogenesis(player);
		}
		if (NyctoPowers.isDarkFormActive(player)) {
			tickDarkForm(player);
		}
		if (NyctoPowers.isKeenSensesActive(player)) {
			tickKeenSenses(player);
		}
		if (NyctoPowers.isMistFormActive(player)) {
			tickMistForm(player);
		}
		List.of(NyctoPowers.BLOODRUSH, NyctoPowers.CARNAGE, NyctoPowers.HAEMOGENESIS).forEach(name -> NyctoPowers.tickActive(player, name));
	}

	private static void tickHaemogenesis(ServerPlayer player) {
		NyctoData.clearHealBlock(player);
		player.extinguishFire();
		int activeTicks = NyctoPowers.activeTicks(player, NyctoPowers.HAEMOGENESIS);
		if (activeTicks % 2 == 0 && player.getHealth() < player.getMaxHealth()) {
			player.heal(1);
			player.serverLevel().sendParticles(moriyashiine.nycto.neoforge.registry.NyctoParticleTypes.AMBROSIA.get(), player.getX(), player.getY() + player.getBbHeight() * 0.55, player.getZ(), 2, player.getBbWidth() * 0.35, player.getBbHeight() * 0.25, player.getBbWidth() * 0.35, 0.01);
		}
	}

	private static void tickKeenSenses(ServerPlayer player) {
		NyctoPowers.applyKeenSensesAttributes(player);
		player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 260, 0, true, false));
		int distance = player.getPersistentData().getInt(NyctoPowers.TAG_KEEN_SENSES_DISTANCE);
		if (distance < 24) {
			distance = Math.min(24, distance + 2);
			player.getPersistentData().putInt(NyctoPowers.TAG_KEEN_SENSES_DISTANCE, distance);
		}
		int renderTicks = player.getPersistentData().getInt(NyctoPowers.TAG_KEEN_SENSES_RENDER_TICKS);
		if (renderTicks > 0) {
			player.getPersistentData().putInt(NyctoPowers.TAG_KEEN_SENSES_RENDER_TICKS, renderTicks - 1);
		}
		if (player.tickCount % 10 == 0) {
			moriyashiine.nycto.neoforge.network.SyncKeenSensesPayload.send(player);
		}
		if (player.tickCount % 20 == 0) {
			int frequency = 60;
			for (LivingEntity target : NyctoPowers.nearbyLiving(player, 12)) {
				float targetDistance = player.distanceTo(target);
				if (frequency != 40 && targetDistance <= 10) {
					frequency = 40;
				}
				if (targetDistance <= 4) {
					frequency = 20;
					break;
				}
			}
			if (player.tickCount % frequency == 0) {
				NyctoPowers.play(player, NyctoSoundEvents.KEEN_SENSES_HEARTBEAT);
			}
		}
		int drainTicks = player.getPersistentData().getInt(NyctoPowers.TAG_KEEN_SENSES_DRAIN_TICKS);
		if (drainTicks <= 1) {
			if (NyctoData.getBlood(player) <= 0) {
				NyctoPowers.disableKeenSenses(player);
				return;
			}
			NyctoData.addBlood(player, -1);
			drainTicks = 200;
		} else {
			drainTicks--;
		}
		player.getPersistentData().putInt(NyctoPowers.TAG_KEEN_SENSES_DRAIN_TICKS, drainTicks);
	}

	private static void tickBatForm(ServerPlayer player) {
		if (!player.isAlive()) {
			NyctoPowers.disableBatForm(player, false);
			return;
		}
		if (player.tickCount % 600 == 0) {
			SyncBatFormPayload.send(player, FORM_SYNC_TICKS);
		}
		NyctoPowers.applyBatFormAttributes(player);
		player.getAbilities().mayfly = true;
		if (player.isSprinting()) {
			player.setSprinting(false);
		}
		if (!player.onGround()) {
			player.setDeltaMovement(player.getDeltaMovement().multiply(0.75, 1, 0.75));
			player.resetFallDistance();
		}
		player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 40, 0, true, true));
		int drainTicks = player.getPersistentData().getInt(NyctoPowers.TAG_BAT_FORM_DRAIN_TICKS);
		if (drainTicks <= 1) {
			if (NyctoData.getBlood(player) <= 0) {
				NyctoPowers.disableBatForm(player, true);
				return;
			}
			NyctoData.addBlood(player, -1);
			drainTicks = 300;
		} else {
			drainTicks--;
		}
		player.getPersistentData().putInt(NyctoPowers.TAG_BAT_FORM_DRAIN_TICKS, drainTicks);
	}

	private static void tickMistForm(ServerPlayer player) {
		if (player.tickCount % 600 == 0) {
			SyncMistFormPayload.send(player, FORM_SYNC_TICKS);
		}
		player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 10, 0, true, false));
		player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 10, 0, true, true));
		player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 2, true, true));
		int drainTicks = player.getPersistentData().getInt(NyctoPowers.TAG_MIST_FORM_DRAIN_TICKS);
		if (drainTicks <= 1) {
			if (NyctoData.getBlood(player) <= 0) {
				NyctoPowers.disableMistForm(player, true);
				return;
			}
			NyctoData.addBlood(player, -1);
			drainTicks = 300;
		} else {
			drainTicks--;
		}
		player.getPersistentData().putInt(NyctoPowers.TAG_MIST_FORM_DRAIN_TICKS, drainTicks);
		spawnMistFormTrail(player);
	}

	private static void spawnMistFormTrail(ServerPlayer player) {
		double y = player.getY() + player.getBbHeight() * 0.48;
		if (player.tickCount % 2 == 0) {
			player.serverLevel().sendParticles(ParticleTypes.SMOKE, player.getX(), y, player.getZ(), 1, player.getBbWidth() * 0.45, player.getBbHeight() * 0.34, player.getBbWidth() * 0.45, 0.01);
		}
		if (player.tickCount % 3 == 0) {
			player.serverLevel().sendParticles(ParticleTypes.WHITE_SMOKE, player.getX(), y, player.getZ(), 1, player.getBbWidth() * 0.52, player.getBbHeight() * 0.4, player.getBbWidth() * 0.52, 0.01);
		}
	}

	private static void tickDarkForm(ServerPlayer player) {
		if (player.tickCount % 600 == 0) {
			SyncDarkFormPayload.send(player, FORM_SYNC_TICKS);
		}
		NyctoPowers.applyDarkFormAttributes(player);
		int drainTicks = player.getPersistentData().getInt(NyctoPowers.TAG_DARK_FORM_DRAIN_TICKS);
		if (drainTicks <= 1) {
			if (NyctoData.getBlood(player) <= 0) {
				NyctoPowers.disableDarkForm(player, true);
				return;
			}
			NyctoData.addBlood(player, -1);
			drainTicks = 300;
		} else {
			drainTicks--;
		}
		player.getPersistentData().putInt(NyctoPowers.TAG_DARK_FORM_DRAIN_TICKS, drainTicks);
		int jumpCooldown = player.getPersistentData().getInt(NyctoPowers.TAG_DARK_FORM_JUMP_COOLDOWN);
		if (player.onGround()) {
			jumpCooldown = 0;
		} else if (jumpCooldown > 0) {
			jumpCooldown--;
		}
		player.getPersistentData().putInt(NyctoPowers.TAG_DARK_FORM_JUMP_COOLDOWN, jumpCooldown);
		if (!player.onGround() && player.getDeltaMovement().y() < 0 && !player.isShiftKeyDown()) {
			player.setDeltaMovement(player.getDeltaMovement().multiply(1, 0.8, 1));
			player.resetFallDistance();
			player.fallDistance = 1;
		}
		if (player.tickCount % 20 == 0) {
			for (Villager villager : player.level().getEntitiesOfClass(Villager.class, player.getBoundingBox().inflate(16), LivingEntity::isAlive)) {
				villager.getBrain().setActiveActivityIfPossible(Activity.PANIC);
			}
		}
	}

	public static void tryDarkFormJump(net.minecraft.world.entity.player.Player rawPlayer) {
		if (!(rawPlayer instanceof ServerPlayer player) || !NyctoPowers.isDarkFormActive(player)) {
			return;
		}
		if (player.getAbilities().flying || player.isInWater() || player.isSwimming() || player.isFallFlying() || player.getVehicle() != null || player.onClimbable()) {
			return;
		}
		int jumpCooldown = player.getPersistentData().getInt(NyctoPowers.TAG_DARK_FORM_JUMP_COOLDOWN);
		if (player.onGround() || jumpCooldown > 0) {
			return;
		}
		player.getPersistentData().putInt(NyctoPowers.TAG_DARK_FORM_JUMP_COOLDOWN, 20);
		player.jumpFromGround();
		player.setDeltaMovement(player.getDeltaMovement().multiply(1.3, 1.1, 1.3));
		player.hurtMarked = true;
		SyncDarkFormPayload.send(player, FORM_SYNC_TICKS);
		player.level().playSound(null, player.getX(), player.getY(), player.getZ(), NyctoSoundEvents.DARK_FORM_FLAP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
	}

	private static int usePower(ServerPlayer player, String rawName) {
		String name = NyctoPowers.normalize(rawName);
		NyctoPower power = NyctoPowers.byName(name).orElse(null);
		if (power == null) {
			player.sendSystemMessage(Component.translatable("commands.nycto.power.failed", Component.translatable("commands.nycto.power.unknown", rawName)));
			return 0;
		}
		if (!NyctoData.isVampire(player)) {
			player.sendSystemMessage(Component.translatable("commands.nycto.power.not_vampire"));
			return 0;
		}
		if (!hasPower(player, power.name())) {
			player.sendSystemMessage(Component.translatable("commands.nycto.power.missing", power.name()));
			return 0;
		}
		if (NyctoPowers.getCooldown(player, power.name()) > 0) {
			player.sendSystemMessage(Component.translatable("commands.nycto.power.cooldown", power.name(), NyctoPowers.getCooldown(player, power.name()) / 20 + 1));
			return 0;
		}
		int bloodCost = Math.max(0, power.bloodCost(player));
		int cooldownTicks = Math.max(0, power.cooldownTicks(player));
		if (NyctoData.getBlood(player) < bloodCost) {
			player.sendSystemMessage(Component.translatable("message.nycto.power.not_enough_blood", bloodCost, NyctoData.getBlood(player)));
			return 0;
		}
		if (!power.use(player)) {
			return 0;
		}
		if (bloodCost > 0) {
			NyctoData.addBlood(player, -bloodCost);
		}
		if (cooldownTicks > 0) {
			NyctoPowers.setCooldown(player, power.name(), cooldownTicks);
		}
		player.sendSystemMessage(Component.translatable("commands.nycto.power.used_status", power.name(), NyctoData.getBlood(player), cooldownTicks / 20));
		return 1;
	}

	private static boolean hasPower(ServerPlayer player, String name) {
		return NyctoData.getPowers(player).contains(name);
	}
}
