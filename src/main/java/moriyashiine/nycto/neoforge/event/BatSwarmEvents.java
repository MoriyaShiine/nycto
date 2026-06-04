/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.event;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.entity.Vampire;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import moriyashiine.nycto.neoforge.registry.NyctoParticleTypes;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import moriyashiine.nycto.neoforge.util.NyctoBloodUtil;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = NyctoNeoForge.MOD_ID)
public final class BatSwarmEvents {
	private static final int MAX_AGE = 20 * 30;
	private static final int RANGE = 4;
	private static final int MAX_STORED_BLOOD = 50;
	private static final int BLOOD_DRAIN_AMOUNT = 5;
	private static final int BLOOD_FILL_AMOUNT = 3;
	private static final Map<ServerLevel, List<Swarm>> SWARMS = new HashMap<>();

	private BatSwarmEvents() {
	}

	public static void spawn(LivingEntity owner) {
		if (owner.level() instanceof ServerLevel serverLevel) {
			SWARMS.computeIfAbsent(serverLevel, ignored -> new ArrayList<>()).add(new Swarm(owner.getUUID(), owner.getEyePosition(), owner instanceof Vampire ? MAX_AGE / 2 : 0));
		}
	}

	@SubscribeEvent
	public static void onLevelTick(LevelTickEvent.Post event) {
		if (!(event.getLevel() instanceof ServerLevel level)) {
			return;
		}
		List<Swarm> swarms = SWARMS.get(level);
		if (swarms == null) {
			return;
		}
		for (Iterator<Swarm> iterator = swarms.iterator(); iterator.hasNext(); ) {
			Swarm swarm = iterator.next();
			if (!swarm.tick(level)) {
				iterator.remove();
			}
		}
		if (swarms.isEmpty()) {
			SWARMS.remove(level);
		}
	}

	@SubscribeEvent
	public static void onLivingDamagePost(LivingDamageEvent.Post event) {
		if (event.getSource().getEntity() instanceof LivingEntity attacker) {
			addTarget(attacker, event.getEntity());
		}
		if (event.getEntity() instanceof Player victim && event.getSource().getEntity() instanceof LivingEntity attacker) {
			addTarget(victim, attacker);
		}
	}

	private static void addTarget(LivingEntity owner, LivingEntity target) {
		if (!(owner.level() instanceof ServerLevel level) || target == owner || !target.isAlive()) {
			return;
		}
		if (owner instanceof Player player && !NyctoData.isVampire(player)) {
			return;
		}
		List<Swarm> swarms = SWARMS.get(level);
		if (swarms == null) {
			return;
		}
		for (Swarm swarm : swarms) {
			if (swarm.isOwnedBy(owner)) {
				swarm.addTarget(target);
			}
		}
	}

	private static final class Swarm {
		private final UUID ownerId;
		private final List<Integer> targets = new ArrayList<>();
		private Vec3 pos;
		private Vec3 delta = Vec3.ZERO;
		private int age;
		private int storedBlood;
		private int soundTimer = 1;

		private Swarm(UUID ownerId, Vec3 pos, int initialAge) {
			this.ownerId = ownerId;
			this.pos = pos;
			age = initialAge;
		}

		private boolean tick(ServerLevel level) {
			Entity ownerEntity = level.getEntity(ownerId);
			if (!(ownerEntity instanceof LivingEntity owner) || !owner.isAlive() || !isValidOwner(owner) || age++ >= MAX_AGE) {
				return false;
			}
			if (!targets.contains(owner.getId())) {
				targets.add(owner.getId());
			}
			if (age % 5 == 0) {
				updateTargets(level, owner);
			}
			pos = pos.add(delta);
			playAmbient(level, owner);
			spawnParticles(level, owner);
			return true;
		}

		private boolean isOwnedBy(LivingEntity living) {
			return ownerId.equals(living.getUUID());
		}

		private void addTarget(LivingEntity target) {
			targets.remove(Integer.valueOf(target.getId()));
			targets.add(target.getId());
		}

		private void updateTargets(ServerLevel level, LivingEntity owner) {
			boolean moving = false;
			for (int i = targets.size() - 1; i >= 0; i--) {
				Entity entity = level.getEntity(targets.get(i));
				if (!(entity instanceof LivingEntity living) || !living.isAlive() || living.isSpectator()) {
					targets.remove(i);
					continue;
				}
				if (!canSee(level, owner, living.getEyePosition()) || pos.distanceToSqr(living.position()) > 64 * 64) {
					continue;
				}
				if (age % 20 == 0 && pos.distanceTo(living.position()) < RANGE + 1) {
					feed(level, owner, living);
				}
				if (!moving) {
					delta = living.getEyePosition().subtract(pos).normalize().scale(0.2);
					moving = true;
				}
			}
			if (!moving) {
				delta = owner.getEyePosition().subtract(pos).normalize().scale(0.16);
			}
		}

		private void feed(ServerLevel level, LivingEntity owner, LivingEntity target) {
			if (target == owner) {
				if (storedBlood >= BLOOD_FILL_AMOUNT) {
					if (owner instanceof ServerPlayer player && NyctoData.getBlood(player) < NyctoData.getMaxBlood(player)) {
						NyctoData.addBlood(player, BLOOD_FILL_AMOUNT);
						storedBlood -= BLOOD_FILL_AMOUNT;
						level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), NyctoSoundEvents.BLOOD_BOTTLE_DRINK.get(), SoundSource.PLAYERS, 0.8F, 1);
					} else if (owner instanceof Vampire vampire && vampire.getHealth() < vampire.getMaxHealth()) {
						vampire.heal(BLOOD_FILL_AMOUNT);
						storedBlood -= BLOOD_FILL_AMOUNT;
						level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), NyctoSoundEvents.BLOOD_BOTTLE_DRINK.get(), SoundSource.HOSTILE, 0.8F, 1);
					}
				}
				return;
			}
			target.hurt(level.damageSources().magic(), 1);
			if (storedBlood < MAX_STORED_BLOOD && NyctoBloodUtil.hasDrainableBlood(target) && NyctoBloodUtil.drainAttack(target, BLOOD_DRAIN_AMOUNT)) {
				storedBlood += BLOOD_DRAIN_AMOUNT / (NyctoBloodUtil.hasQualityBlood(target) ? 1 : 2);
				level.playSound(null, target.getX(), target.getY(), target.getZ(), NyctoSoundEvents.BLOOD_BOTTLE_DRINK.get(), target.getSoundSource(), 0.75F, 1);
			}
		}

		private boolean canSee(ServerLevel level, LivingEntity owner, Vec3 target) {
			return level.clip(new ClipContext(pos, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, owner)).getType() == HitResult.Type.MISS;
		}

		private void playAmbient(ServerLevel level, LivingEntity owner) {
			if (--soundTimer > 0) {
				return;
			}
			level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.BAT_AMBIENT, owner instanceof Vampire ? SoundSource.HOSTILE : SoundSource.PLAYERS, 0.15F, 1);
			soundTimer = owner.getRandom().nextIntBetweenInclusive(4, 12);
		}

		private void spawnParticles(ServerLevel level, LivingEntity owner) {
			for (int i = 0; i < 8; i++) {
				Vec3 particlePos = new Vec3(
						pos.x + Mth.nextFloat(owner.getRandom(), -RANGE, RANGE),
						pos.y + Mth.nextFloat(owner.getRandom(), -RANGE, RANGE),
						pos.z + Mth.nextFloat(owner.getRandom(), -RANGE, RANGE));
				if (!canSee(level, owner, particlePos)) {
					continue;
				}
				ParticleOptions bat = switch (owner.getRandom().nextInt(3)) {
					case 0 -> NyctoParticleTypes.BAT_SWARM_LEFT.get();
					case 1 -> NyctoParticleTypes.BAT_SWARM_RIGHT.get();
					default -> NyctoParticleTypes.BAT_SWARM_CENTER.get();
				};
				level.sendParticles(bat, particlePos.x, particlePos.y, particlePos.z, 1, 0.15, 0.15, 0.15, 0.02);
				if (storedBlood > 0 && owner.getRandom().nextBoolean()) {
					level.sendParticles(NyctoParticleTypes.BLOOD.get(), particlePos.x, particlePos.y, particlePos.z, 1, 0.08, 0.08, 0.08, 0.01);
				}
				level.sendParticles(ParticleTypes.SMOKE, particlePos.x, particlePos.y, particlePos.z, 2, 0.35, 0.35, 0.35, 0);
			}
		}
	}

	private static boolean isValidOwner(LivingEntity owner) {
		return owner instanceof Vampire || owner instanceof ServerPlayer player && NyctoData.isVampire(player);
	}
}
