/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.level.power;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BatSwarmComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final Level obj;
	private final List<BatSwarm> batSwarms = new ArrayList<>();

	public BatSwarmComponent(Level obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		batSwarms.clear();
		for (BatSwarm batSwarm : input.read("BatSwarms", BatSwarm.CODEC.listOf()).orElse(List.of())) {
			batSwarms.add(new BatSwarm(batSwarm.getOwnerId(), new ArrayList<>(batSwarm.getTargets()), batSwarm.getPos(), batSwarm.getAge(), batSwarm.getBlood()));
		}
	}

	@Override
	public void writeData(ValueOutput output) {
		output.store("BatSwarms", BatSwarm.CODEC.listOf(), batSwarms);
	}

	@Override
	public void tick() {
		for (int i = batSwarms.size() - 1; i >= 0; i--) {
			BatSwarm batSwarm = batSwarms.get(i);
			batSwarm.tick(obj);
			if (batSwarm.deathTicks > 20) {
				batSwarms.remove(i);
			}
		}
	}

	public void sync() {
		ModLevelComponents.BAT_SWARM.sync(obj);
	}

	public void addBatSwarm(LivingEntity owner) {
		batSwarms.add(new BatSwarm(owner.getUUID(), new ArrayList<>(), owner.getEyePosition(), owner.slib$isPlayer() ? 0 : BatSwarm.MAX_AGE / 2, 0));
	}

	public void addTarget(LivingEntity owner, LivingEntity target) {
		for (BatSwarm batSwarm : batSwarms) {
			if (batSwarm.owner == owner) {
				for (int i = batSwarm.targets.size() - 1; i >= 0; i--) {
					if (batSwarm.targets.get(i) == target.getId()) {
						batSwarm.targets.remove(i);
					}
				}
				batSwarm.targets.add(target.getId());
			}
		}
	}

	public static class BatSwarm {
		public static final Codec<BatSwarm> CODEC = RecordCodecBuilder.create(instance -> instance.group(
						UUIDUtil.AUTHLIB_CODEC.fieldOf("owner").forGetter(BatSwarm::getOwnerId),
						Codec.INT.listOf().fieldOf("targets").forGetter(BatSwarm::getTargets),
						Vec3.CODEC.fieldOf("pos").forGetter(BatSwarm::getPos),
						Codec.INT.fieldOf("age").forGetter(BatSwarm::getAge),
						Codec.INT.fieldOf("blood").forGetter(BatSwarm::getBlood))
				.apply(instance, BatSwarm::new));

		public static final int MAX_AGE = 20 * 30;
		private static final int MAX_BLOOD = 50, BLOOD_DRAIN_AMOUNT = 5, BLOOD_FILL_AMOUNT = 3;
		private static final int RANGE = 4;

		private final UUID ownerId;
		private final List<Integer> targets;
		private Vec3 pos;
		private int age, blood;

		@Nullable
		private LivingEntity owner = null;
		private Vec3 deltaMovement = Vec3.ZERO;
		private int deathTicks = 0, nextSoundTimer = 1;

		private BatSwarm(UUID ownerId, List<Integer> targets, Vec3 pos, int age, int blood) {
			this.ownerId = ownerId;
			this.targets = targets;
			this.pos = pos;
			this.age = age;
			this.blood = blood;
		}

		private void tick(Level level) {
			if (owner == null) {
				if (level.getEntity(getOwnerId()) instanceof LivingEntity living) {
					owner = living;
				}
				if (owner != null && !targets.contains(owner.getId())) {
					targets.add(owner.getId());
				}
			}
			if (shouldDie(level)) {
				deathTicks++;
				return;
			}
			deathTicks = 0;
			age++;

			if (age % 5 == 0) {
				boolean moveTo = false;
				for (int i = targets.size() - 1; i >= 0; i--) {
					Entity target = level.getEntity(targets.get(i));
					if (!NyctoUtil.isSurvivalNullable(target)) {
						targets.remove(i);
					} else if (canSee(target)) {
						LivingEntity living = (LivingEntity) target;
						if (!level.isClientSide() && age % 20 == 0 && pos.distanceTo(living.position()) < RANGE + 1) {
							feed(living);
						}
						if (!moveTo) {
							setDeltaMovement(target);
							moveTo = true;
						}
					}
				}
			}

			pos = pos.add(deltaMovement);
			if (--nextSoundTimer == 0) {
				level.playSound(null, pos.x(), pos.y(), pos.z(), SoundEvents.BAT_AMBIENT, SoundSource.PLAYERS, 0.15F, 1);
				nextSoundTimer = owner.getRandom().nextIntBetweenInclusive(4, 12);
			}
			if (level.isClientSide() && age < MAX_AGE - 20) {
				spawnParticles(level, owner.getRandom());
			}
		}

		private UUID getOwnerId() {
			return ownerId;
		}

		private List<Integer> getTargets() {
			return targets;
		}

		private Vec3 getPos() {
			return pos;
		}

		private int getAge() {
			return age;
		}

		private int getBlood() {
			return blood;
		}

		private boolean canSee(Vec3 pos) {
			return owner.level().clip(new ClipContext(getPos(), pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, owner)).getType() == HitResult.Type.MISS;
		}

		private boolean canSee(Entity entity) {
			return entity != null && getPos().distanceTo(entity.position()) < 64 && canSee(entity.getEyePosition());
		}

		private boolean shouldDie(Level level) {
			if (age >= BatSwarm.MAX_AGE || owner == null || !owner.slib$exists()) {
				return true;
			}
			BlockPos blockPos = new BlockPos(Mth.floor(getPos().x()), Mth.floor(getPos().y()), Mth.floor(getPos().z()));
			return level.isBrightOutside() && !level.isRainingAt(blockPos) && level.canSeeSky(blockPos);
		}

		private void setDeltaMovement(Entity target) {
			deltaMovement = new Vec3(
					target.getX() - getPos().x(),
					target.getEyeY() - getPos().y(),
					target.getZ() - getPos().z()
			).normalize().scale(0.2);
		}

		private void feed(LivingEntity target) {
			if (target == owner) {
				if (getBlood() >= BLOOD_FILL_AMOUNT && ModEntityComponents.BLOOD.get(target).fill(BLOOD_FILL_AMOUNT)) {
					SLibUtils.playSound(target, ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value());
					blood -= BLOOD_FILL_AMOUNT;
					ModLevelComponents.BAT_SWARM.sync(target.level());
				}
			} else {
				boolean hasBlood = !target.is(ModEntityTypeTags.HAS_NO_BLOOD);
				boolean qualityBlood = target.is(ModEntityTypeTags.HAS_QUALITY_BLOOD);
				boolean canDrain = hasBlood && getBlood() < MAX_BLOOD;
				if (!qualityBlood && owner instanceof Player player && NyctoAPI.hasPower(player, ModPowers.RICH_TASTES)) {
					canDrain = false;
				}
				target.hurt(target.damageSources().source(ModDamageTypes.BLEED, null, owner), 1);
				if (canDrain) {
					if (ModEntityComponents.BLOOD.get(target).drainAttack(BLOOD_DRAIN_AMOUNT)) {
						SLibUtils.playSound(target, ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value());
						blood += BLOOD_DRAIN_AMOUNT / (qualityBlood ? 1 : 2);
						ModLevelComponents.BAT_SWARM.sync(target.level());
					}
				}
			}
		}

		private void spawnParticles(Level level, RandomSource random) {
			for (int i = 0; i < 8; i++) {
				Vec3 particlePos = new Vec3(
						getPos().x() + Mth.nextFloat(random, -RANGE, RANGE),
						getPos().y() + Mth.nextFloat(random, -RANGE, RANGE),
						getPos().z() + Mth.nextFloat(random, -RANGE, RANGE)
				);
				if (canSee(particlePos)) {
					ParticleOptions particle = switch (random.nextInt(3)) {
						case 0 -> ModParticleTypes.BAT_SWARM_LEFT;
						case 1 -> ModParticleTypes.BAT_SWARM_RIGHT;
						default -> ModParticleTypes.BAT_SWARM_CENTER;
					};
					level.addAlwaysVisibleParticle(particle,
							particlePos.x(), particlePos.y(), particlePos.z(),
							blood > 0 ? 1 : 0, 0, 0);
					for (int j = 0; j < 2; j++) {
						level.addParticle(ParticleTypes.SMOKE,
								particlePos.x() + random.nextGaussian() / 2, particlePos.y() + random.nextGaussian() / 2, particlePos.z() + random.nextGaussian() / 2,
								0, 0, 0);
					}
				}
			}
		}
	}
}
