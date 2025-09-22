/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.world.power;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Uuids;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BatSwarmComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final World obj;
	private final List<BatSwarm> batSwarms = new ArrayList<>();

	public BatSwarmComponent(World obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		batSwarms.clear();
		for (BatSwarm batSwarm : readView.read("BatSwarms", BatSwarm.CODEC.listOf()).orElse(List.of())) {
			batSwarms.add(new BatSwarm(batSwarm.getOwnerId(), new ArrayList<>(batSwarm.getTargetIds()), batSwarm.getPos(), batSwarm.getAge(), batSwarm.getBlood()));
		}
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.put("BatSwarms", BatSwarm.CODEC.listOf(), batSwarms);
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
		ModWorldComponents.BAT_SWARM.sync(obj);
	}

	public void addBatSwarm(LivingEntity owner) {
		batSwarms.add(new BatSwarm(owner.getUuid(), new ArrayList<>(), owner.getEyePos(), owner.isPlayer() ? 0 : BatSwarm.MAX_AGE / 2, 0));
	}

	public void addTarget(LivingEntity owner, LivingEntity target) {
		for (BatSwarm batSwarm : batSwarms) {
			if (batSwarm.owner == owner) {
				for (int i = batSwarm.targetIds.size() - 1; i >= 0; i--) {
					if (batSwarm.targetIds.get(i) == target.getId()) {
						batSwarm.targetIds.remove(i);
					}
				}
				batSwarm.targetIds.add(target.getId());
			}
		}
	}

	public static class BatSwarm {
		public static final Codec<BatSwarm> CODEC = RecordCodecBuilder.create(instance -> instance.group(
						Uuids.CODEC.fieldOf("owner_id").forGetter(BatSwarm::getOwnerId),
						Codec.INT.listOf().fieldOf("target_ids").forGetter(BatSwarm::getTargetIds),
						Vec3d.CODEC.fieldOf("pos").forGetter(BatSwarm::getPos),
						Codec.INT.fieldOf("age").forGetter(BatSwarm::getAge),
						Codec.INT.fieldOf("blood").forGetter(BatSwarm::getBlood))
				.apply(instance, BatSwarm::new));

		public static final int MAX_AGE = 20 * 30;
		private static final int MAX_BLOOD = 50, BLOOD_DRAIN_AMOUNT = 5, BLOOD_FILL_AMOUNT = 3;
		private static final int RANGE = 4;

		private final UUID ownerId;
		private final List<Integer> targetIds;
		private Vec3d pos;
		private int age, blood;

		@Nullable
		private LivingEntity owner = null;
		private Vec3d velocity = Vec3d.ZERO;
		private int deathTicks = 0, nextSoundTimer = 1;

		private BatSwarm(UUID ownerId, List<Integer> targetIds, Vec3d pos, int age, int blood) {
			this.ownerId = ownerId;
			this.targetIds = targetIds;
			this.pos = pos;
			this.age = age;
			this.blood = blood;
		}

		private void tick(World world) {
			if (owner == null) {
				if (world.getEntity(getOwnerId()) instanceof LivingEntity living) {
					owner = living;
				}
				if (owner != null && !targetIds.contains(owner.getId())) {
					targetIds.add(owner.getId());
				}
			}
			if (shouldDie()) {
				deathTicks++;
				return;
			}
			deathTicks = 0;
			age++;

			if (age % 5 == 0) {
				boolean moveTo = false;
				for (int i = targetIds.size() - 1; i >= 0; i--) {
					Entity target = world.getEntityById(targetIds.get(i));
					if (!NyctoUtil.isSurvival(target)) {
						targetIds.remove(i);
					} else if (canSee(target)) {
						LivingEntity living = (LivingEntity) target;
						if (!world.isClient && age % 20 == 0 && pos.distanceTo(living.getPos()) < RANGE + 1) {
							feed(living);
						}
						if (!moveTo) {
							setVelocity(target);
							moveTo = true;
						}
					}
				}
			}

			pos = pos.add(velocity);
			if (--nextSoundTimer == 0) {
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_BAT_AMBIENT, SoundCategory.PLAYERS, 0.15F, 1);
				nextSoundTimer = owner.getRandom().nextBetween(4, 12);
			}
			if (world.isClient && age < MAX_AGE - 20) {
				spawnParticles(world, owner.getRandom());
			}
		}

		private UUID getOwnerId() {
			return ownerId;
		}

		private List<Integer> getTargetIds() {
			return targetIds;
		}

		private Vec3d getPos() {
			return pos;
		}

		private int getAge() {
			return age;
		}

		private int getBlood() {
			return blood;
		}

		private boolean canSee(Vec3d pos) {
			return owner.getWorld().raycast(new RaycastContext(getPos(), pos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, owner)).getType() == HitResult.Type.MISS;
		}

		private boolean canSee(Entity entity) {
			return entity != null && getPos().distanceTo(entity.getPos()) < 64 && canSee(entity.getEyePos());
		}

		private boolean shouldDie() {
			return age >= BatSwarm.MAX_AGE || owner == null || !owner.isPartOfGame();
		}

		private void setVelocity(Entity target) {
			velocity = new Vec3d(
					target.getX() - getPos().getX(),
					target.getEyeY() - getPos().getY(),
					target.getZ() - getPos().getZ()
			).normalize().multiply(0.2);
		}

		private void feed(LivingEntity target) {
			if (target == owner) {
				if (getBlood() >= BLOOD_FILL_AMOUNT && ModEntityComponents.BLOOD.get(target).fill(BLOOD_FILL_AMOUNT)) {
					SLibUtils.playSound(target, ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value());
					blood -= BLOOD_FILL_AMOUNT;
					ModWorldComponents.BAT_SWARM.sync(target.getWorld());
				}
			} else {
				boolean hasBlood = !target.getType().isIn(ModEntityTypeTags.HAS_NO_BLOOD);
				boolean qualityBlood = target.getType().isIn(ModEntityTypeTags.HAS_QUALITY_BLOOD);
				boolean canDrain = hasBlood && getBlood() < MAX_BLOOD;
				if (!qualityBlood && owner instanceof PlayerEntity player && NyctoAPI.hasPower(player, ModPowers.RICH_TASTES)) {
					canDrain = false;
				}
				target.serverDamage(target.getDamageSources().create(ModDamageTypes.BLEED), 1);
				if (canDrain) {
					if (ModEntityComponents.BLOOD.get(target).drainAttack(BLOOD_DRAIN_AMOUNT)) {
						SLibUtils.playSound(target, ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value());
						blood += BLOOD_DRAIN_AMOUNT / (qualityBlood ? 1 : 2);
						ModWorldComponents.BAT_SWARM.sync(target.getWorld());
					}
					if (owner instanceof PlayerEntity player) {
						NyctoAPI.increaseHunterHeat(player, target);
					}
				}
			}
		}

		private void spawnParticles(World world, Random random) {
			for (int i = 0; i < 8; i++) {
				Vec3d particlePos = new Vec3d(
						getPos().getX() + MathHelper.nextFloat(random, -RANGE, RANGE),
						getPos().getY() + MathHelper.nextFloat(random, -RANGE, RANGE),
						getPos().getZ() + MathHelper.nextFloat(random, -RANGE, RANGE)
				);
				if (canSee(particlePos)) {
					ParticleEffect particle = switch (random.nextInt(3)) {
						case 0 -> ModParticleTypes.BAT_SWARM_LEFT;
						case 1 -> ModParticleTypes.BAT_SWARM_RIGHT;
						default -> ModParticleTypes.BAT_SWARM_CENTER;
					};
					world.addImportantParticleClient(particle,
							particlePos.getX(), particlePos.getY(), particlePos.getZ(),
							blood > 0 ? 1 : 0, 0, 0);
					for (int j = 0; j < 2; j++) {
						world.addParticleClient(ParticleTypes.SMOKE,
								particlePos.getX() + random.nextGaussian() / 2, particlePos.getY() + random.nextGaussian() / 2, particlePos.getZ() + random.nextGaussian() / 2,
								0, 0, 0);
					}
				}
			}
		}
	}
}
