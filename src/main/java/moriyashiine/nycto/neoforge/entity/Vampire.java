/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity;

import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.registry.NyctoEntityTypes;
import moriyashiine.nycto.neoforge.registry.NyctoParticleTypes;
import moriyashiine.nycto.neoforge.registry.NyctoTags;
import moriyashiine.nycto.neoforge.entity.ai.BatSwarmGoal;
import moriyashiine.nycto.neoforge.entity.ai.BatstepGoal;
import moriyashiine.nycto.neoforge.entity.ai.BloodBarrierGoal;
import moriyashiine.nycto.neoforge.entity.ai.BloodFlechettesGoal;
import moriyashiine.nycto.neoforge.entity.ai.CarnageGoal;
import moriyashiine.nycto.neoforge.entity.ai.HaemogenesisGoal;
import moriyashiine.nycto.neoforge.entity.projectile.BloodFlechetteProjectile;
import moriyashiine.nycto.neoforge.event.BatSwarmEvents;
import moriyashiine.nycto.neoforge.network.AddBloodBarrierParticlesPayload;
import moriyashiine.nycto.neoforge.network.SyncBloodBarrierPayload;
import moriyashiine.nycto.neoforge.network.SyncCarnagePayload;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import moriyashiine.nycto.neoforge.registry.NyctoMobEffects;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import moriyashiine.nycto.neoforge.util.NyctoBloodUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Vampire extends Monster {
	private static final int NEW_MOON_PHASE = 4;
	private static final int BLOOD_BARRIER_LAYERS = 3;
	private static final int BLOOD_BARRIER_TICKS = 300;
	private static final int BLOOD_BARRIER_COOLDOWN = 20 * 15;
	private static final int CARNAGE_TICKS = 300;
	private static final int CARNAGE_COOLDOWN = 600;
	private static final int ABILITY_COOLDOWN = 40;
	private static final EntityDataAccessor<Integer> DATA_BLOOD_BARRIER_LAYERS = SynchedEntityData.defineId(Vampire.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_ATTACKING = SynchedEntityData.defineId(Vampire.class, EntityDataSerializers.BOOLEAN);
	private static final List<PowerCooldown> DEFAULT_USABLE_POWERS = List.of(
			new PowerCooldown(NyctoPowers.BATSTEP, 20 * 8),
			new PowerCooldown(NyctoPowers.BAT_SWARM, 20 * 20),
			new PowerCooldown(NyctoPowers.BLOOD_BARRIER, BLOOD_BARRIER_COOLDOWN),
			new PowerCooldown(NyctoPowers.BLOOD_FLECHETTES, 20 * 6),
			new PowerCooldown(NyctoPowers.CARNAGE, CARNAGE_COOLDOWN),
			new PowerCooldown(NyctoPowers.HAEMOGENESIS, 20 * 10));

	private int bloodBarrierTicks = 0;
	private int bloodBarrierCooldown = 0;
	private int carnageTicks = 0;
	private int carnageCooldown = 0;
	private int haemogenesisToHeal = 0;
	private int attackingTicks = 0;
	private int abilityCooldown = 0;
	private final List<UsablePower> usablePowers = new ArrayList<>();

	public Vampire(EntityType<? extends Vampire> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 24)
				.add(Attributes.ARMOR, 6)
				.add(Attributes.ATTACK_DAMAGE, 6)
				.add(Attributes.MOVEMENT_SPEED, 0.33)
				.add(Attributes.FOLLOW_RANGE, 32);
	}

	public static boolean checkVampireSpawnRules(EntityType<Vampire> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, random)
				&& level.getLevel().getMoonPhase() == NEW_MOON_PHASE
				&& level.getBiome(pos).is(NyctoTags.VAMPIRE_SPAWN_BIOMES);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new BloodBarrierGoal(this));
		goalSelector.addGoal(1, new HaemogenesisGoal(this));
		goalSelector.addGoal(2, new CarnageGoal(this));
		goalSelector.addGoal(3, new BatstepGoal(this));
		goalSelector.addGoal(3, new BatSwarmGoal(this));
		goalSelector.addGoal(3, new BloodFlechettesGoal(this));
		goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.1, false));
		goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
		goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		targetSelector.addGoal(0, new HurtByTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, target -> target instanceof Player player && !player.isCreative() && !player.isSpectator()));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, target -> !(target instanceof Player) && !(target instanceof AbstractVillager) && NyctoBloodUtil.hasQualityBlood(target) && !Vampire.isVampire(target)));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_BLOOD_BARRIER_LAYERS, 0);
		builder.define(DATA_ATTACKING, false);
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide()) {
			tickAttacking();
			tickAbilityCooldowns();
			tickBloodBarrier();
			tickCarnage();
			tickHaemogenesis();
		}
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		if (usablePowers.isEmpty()) {
			rollUsablePowers(getRandom());
		}
		return data;
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		boolean hurt = super.doHurtTarget(target);
		if (hurt) {
			swing(InteractionHand.MAIN_HAND);
			setAttackingTicks(10);
			spawnAttackParticles(target);
			if (target instanceof LivingEntity living && NyctoBloodUtil.hasQualityBlood(living)) {
				int amount = Math.min(5, NyctoBloodUtil.getBlood(living));
				if (amount > 0 && NyctoBloodUtil.drainAttack(living, amount)) {
					if (getHealth() < getMaxHealth()) {
						heal(Math.max(1, amount / 2F));
					}
					level().playSound(null, getX(), getY(), getZ(), NyctoSoundEvents.BLOOD_BOTTLE_DRINK.get(), SoundSource.HOSTILE, 0.7F, 0.85F + getRandom().nextFloat() * 0.25F);
				}
			} else if (getHealth() < getMaxHealth()) {
				heal(1);
			}
		}
		return hurt;
	}

	@Override
	protected net.minecraft.sounds.SoundEvent getAmbientSound() {
		return NyctoSoundEvents.VAMPIRE_AMBIENT.get();
	}

	@Override
	protected net.minecraft.sounds.SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) {
		return NyctoSoundEvents.VAMPIRE_HURT.get();
	}

	@Override
	protected net.minecraft.sounds.SoundEvent getDeathSound() {
		return NyctoSoundEvents.VAMPIRE_DEATH.get();
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return !hasCustomName() && super.removeWhenFarAway(distanceToClosestPlayer);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("NyctoBloodBarrierLayers", getBloodBarrierLayers());
		compound.putInt("NyctoBloodBarrierTicks", bloodBarrierTicks);
		compound.putInt("NyctoBloodBarrierCooldown", bloodBarrierCooldown);
		compound.putInt("NyctoCarnageTicks", carnageTicks);
		compound.putInt("NyctoCarnageCooldown", carnageCooldown);
		compound.putInt("NyctoHaemogenesisToHeal", haemogenesisToHeal);
		compound.putInt("NyctoAbilityCooldown", abilityCooldown);
		ListTag powers = new ListTag();
		for (UsablePower power : usablePowers) {
			CompoundTag tag = new CompoundTag();
			tag.putString("Power", power.name);
			tag.putInt("MaxCooldown", power.maxCooldown);
			tag.putInt("Cooldown", power.cooldown);
			powers.add(tag);
		}
		compound.put("NyctoUsablePowers", powers);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setBloodBarrierLayers(compound.getInt("NyctoBloodBarrierLayers"));
		bloodBarrierTicks = Math.max(0, compound.getInt("NyctoBloodBarrierTicks"));
		bloodBarrierCooldown = Math.max(0, compound.getInt("NyctoBloodBarrierCooldown"));
		carnageTicks = Math.max(0, compound.getInt("NyctoCarnageTicks"));
		carnageCooldown = Math.max(0, compound.getInt("NyctoCarnageCooldown"));
		haemogenesisToHeal = Math.max(0, compound.getInt("NyctoHaemogenesisToHeal"));
		abilityCooldown = Math.max(0, compound.getInt("NyctoAbilityCooldown"));
		usablePowers.clear();
		ListTag powers = compound.getList("NyctoUsablePowers", 10);
		for (int i = 0; i < powers.size(); i++) {
			CompoundTag tag = powers.getCompound(i);
			String name = tag.getString("Power");
			int maxCooldown = tag.getInt("MaxCooldown");
			if (NyctoPowers.byName(name).isPresent() && maxCooldown > 0) {
				usablePowers.add(new UsablePower(name, maxCooldown, Math.max(0, tag.getInt("Cooldown"))));
			}
		}
		if (usablePowers.isEmpty()) {
			rollUsablePowers(getRandom());
		}
		if (carnageTicks > 0) {
			NyctoPowers.applyCarnageDamage(this);
		}
	}

	public boolean canUseBloodBarrier() {
		return getBloodBarrierLayers() <= 0 && bloodBarrierTicks <= 0 && canUsePower(NyctoPowers.BLOOD_BARRIER) && isAlive();
	}

	public void startBloodBarrier() {
		if (!canUseBloodBarrier()) {
			return;
		}
		setBloodBarrierLayers(BLOOD_BARRIER_LAYERS);
		bloodBarrierTicks = BLOOD_BARRIER_TICKS;
		usePower(NyctoPowers.BLOOD_BARRIER);
		for (int i = 0; i < BLOOD_BARRIER_LAYERS; i++) {
			AddBloodBarrierParticlesPayload.send(this, i);
		}
		level().playSound(null, getX(), getY(), getZ(), NyctoSoundEvents.BLOOD_BARRIER_USE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
	}

	public int getBloodBarrierLayers() {
		return Math.max(0, entityData.get(DATA_BLOOD_BARRIER_LAYERS));
	}

	public void breakBloodBarrier() {
		int layers = getBloodBarrierLayers();
		if (layers <= 0) {
			return;
		}
		setBloodBarrierLayers(layers - 1);
		AddBloodBarrierParticlesPayload.send(this, layers - 1);
		level().playSound(null, getX(), getY(), getZ(), NyctoSoundEvents.BLOOD_BARRIER_BREAK.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
		if (layers <= 1) {
			bloodBarrierTicks = 0;
		}
	}

	public void playBloodBarrierHit() {
		level().playSound(null, getX(), getY(), getZ(), NyctoSoundEvents.BLOOD_BARRIER_HIT.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
	}

	private void tickBloodBarrier() {
		if (bloodBarrierCooldown > 0) {
			bloodBarrierCooldown--;
		}
		if (getBloodBarrierLayers() <= 0) {
			bloodBarrierTicks = 0;
			return;
		}
		if (bloodBarrierTicks <= 1) {
			int layers = getBloodBarrierLayers();
			for (int i = 0; i < layers; i++) {
				AddBloodBarrierParticlesPayload.send(this, i);
			}
			setBloodBarrierLayers(0);
			bloodBarrierTicks = 0;
			return;
		}
		bloodBarrierTicks--;
	}

	private void setBloodBarrierLayers(int layers) {
		int clampedLayers = Math.max(0, Math.min(BLOOD_BARRIER_LAYERS, layers));
		entityData.set(DATA_BLOOD_BARRIER_LAYERS, clampedLayers);
		getPersistentData().putInt(NyctoPowers.TAG_BLOOD_BARRIER_LAYERS, clampedLayers);
		SyncBloodBarrierPayload.send(this, clampedLayers);
	}

	public boolean isAttacking() {
		return entityData.get(DATA_ATTACKING);
	}

	private void setAttackingTicks(int ticks) {
		attackingTicks = Math.max(attackingTicks, ticks);
		entityData.set(DATA_ATTACKING, attackingTicks > 0);
	}

	private void tickAttacking() {
		if (attackingTicks <= 0) {
			if (isAttacking()) {
				entityData.set(DATA_ATTACKING, false);
			}
			return;
		}
		attackingTicks--;
		if (attackingTicks <= 0) {
			entityData.set(DATA_ATTACKING, false);
		}
	}

	private void spawnAttackParticles(Entity target) {
		if (level() instanceof ServerLevel serverLevel) {
			double x = target.getX();
			double y = target.getY() + target.getBbHeight() * 0.65;
			double z = target.getZ();
			serverLevel.sendParticles(NyctoParticleTypes.HYPNOSIS_SMALL.get(), x, y, z, 8, target.getBbWidth() * 0.25, target.getBbHeight() * 0.18, target.getBbWidth() * 0.25, 0.02);
			serverLevel.sendParticles(NyctoParticleTypes.HYPNOSIS_STAR.get(), x, y, z, 3, target.getBbWidth() * 0.18, target.getBbHeight() * 0.12, target.getBbWidth() * 0.18, 0.01);
		}
	}

	public boolean canUsePower(String power) {
		if (abilityCooldown > 0 || !isAlive()) {
			return false;
		}
		for (UsablePower usablePower : usablePowers) {
			if (usablePower.name.equals(power)) {
				return usablePower.cooldown <= 0;
			}
		}
		return false;
	}

	public void startBatstep() {
		if (!canUsePower(NyctoPowers.BATSTEP)) {
			return;
		}
		usePower(NyctoPowers.BATSTEP);
		LivingEntity target = getTarget();
		Vec3 start = position();
		Vec3 destination = target == null ? start.add(getLookAngle().normalize().scale(8)) : target.position().subtract(getLookAngle().normalize().scale(1.5));
		Vec3 floor = new Vec3(destination.x, destination.y, destination.z);
		for (int i = 0; i < 8; i++) {
			AABB box = getDimensions(getPose()).makeBoundingBox(floor);
			if (level().noCollision(this, box)) {
				break;
			}
			floor = floor.add(0, 0.25, 0);
		}
		Vec3 from = start.add(0, getBbHeight() * 0.5, 0);
		Vec3 to = floor.add(0, getBbHeight() * 0.5, 0);
		lineParticles(from, to, 12);
		teleportTo(floor.x, floor.y, floor.z);
		if (target != null && target.isAlive()) {
			target.addEffect(new net.minecraft.world.effect.MobEffectInstance(NyctoMobEffects.STUNNED, 20 * 3, 0, true, true));
			target.hurt(damageSources().mobAttack(this), 2.5F);
		}
		level().playSound(null, getX(), getY(), getZ(), NyctoSoundEvents.BATSTEP_USE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
	}

	public void startBloodFlechettes() {
		if (!canUsePower(NyctoPowers.BLOOD_FLECHETTES)) {
			return;
		}
		usePower(NyctoPowers.BLOOD_FLECHETTES);
		int count = getRandom().nextIntBetweenInclusive(6, 8);
		for (int i = 0; i < count; i++) {
			BloodFlechetteProjectile flechette = new BloodFlechetteProjectile(level(), this);
			flechette.shootFromRotation(this, getXRot(), getYHeadRot(), 0, 1, i == 0 ? 0 : 24);
			level().addFreshEntity(flechette);
		}
		level().playSound(null, getX(), getY(), getZ(), NyctoSoundEvents.BLOOD_FLECHETTES_USE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
	}

	public void startBatSwarm() {
		if (!canUsePower(NyctoPowers.BAT_SWARM)) {
			return;
		}
		usePower(NyctoPowers.BAT_SWARM);
		BatSwarmEvents.spawn(this);
		if (level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(NyctoParticleTypes.BAT_SWARM_CENTER.get(), getX(), getY() + getBbHeight() * 0.5, getZ(), 16, 3, 1.5, 3, 0.02);
			serverLevel.sendParticles(NyctoParticleTypes.BAT_SWARM_LEFT.get(), getX(), getY() + getBbHeight() * 0.5, getZ(), 10, 3, 1.5, 3, 0.02);
			serverLevel.sendParticles(NyctoParticleTypes.BAT_SWARM_RIGHT.get(), getX(), getY() + getBbHeight() * 0.5, getZ(), 10, 3, 1.5, 3, 0.02);
		}
		level().playSound(null, getX(), getY(), getZ(), NyctoSoundEvents.BAT_SWARM_USE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
	}

	public void startHaemogenesis() {
		if (!canUsePower(NyctoPowers.HAEMOGENESIS)) {
			return;
		}
		usePower(NyctoPowers.HAEMOGENESIS);
		NyctoData.clearHealBlock(this);
		extinguishFire();
		haemogenesisToHeal = Math.max(1, (int) (getMaxHealth() / 3F));
		if (level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(NyctoParticleTypes.AMBROSIA.get(), getX(), getY() + getBbHeight() * 0.5, getZ(), 14, 0.7, 0.35, 0.7, 0.02);
		}
		level().playSound(null, getX(), getY(), getZ(), NyctoSoundEvents.HAEMOGENESIS_USE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
	}

	private void tickHaemogenesis() {
		if (haemogenesisToHeal <= 0) {
			return;
		}
		NyctoData.clearHealBlock(this);
		extinguishFire();
		if (tickCount % 2 == 0) {
			if (getHealth() < getMaxHealth()) {
				heal(1);
				if (level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(NyctoParticleTypes.AMBROSIA.get(), getX(), getY() + getBbHeight() * 0.55, getZ(), 2, getBbWidth() * 0.35, getBbHeight() * 0.25, getBbWidth() * 0.35, 0.01);
				}
			}
			haemogenesisToHeal--;
		}
	}

	private void tickAbilityCooldowns() {
		if (abilityCooldown > 0) {
			abilityCooldown--;
		}
		for (UsablePower usablePower : usablePowers) {
			if (usablePower.cooldown > 0) {
				usablePower.cooldown--;
			}
		}
	}

	private void usePower(String power) {
		abilityCooldown = ABILITY_COOLDOWN;
		swing(InteractionHand.MAIN_HAND);
		for (UsablePower usablePower : usablePowers) {
			if (usablePower.name.equals(power)) {
				usablePower.cooldown = usablePower.maxCooldown;
				return;
			}
		}
	}

	private void rollUsablePowers(RandomSource random) {
		usablePowers.clear();
		List<PowerCooldown> selectable = new ArrayList<>(DEFAULT_USABLE_POWERS);
		for (int i = 0; i < 3 && !selectable.isEmpty(); i++) {
			PowerCooldown selected = selectable.remove(random.nextInt(selectable.size()));
			usablePowers.add(new UsablePower(selected.name, selected.maxCooldown, 0));
		}
	}

	private void lineParticles(Vec3 start, Vec3 end, int steps) {
		if (!(level() instanceof ServerLevel serverLevel)) {
			return;
		}
		Vec3 delta = end.subtract(start);
		for (int i = 0; i <= steps; i++) {
			Vec3 point = start.add(delta.scale(i / (double) steps));
			serverLevel.sendParticles(NyctoParticleTypes.BATSTEP_CENTER.get(), point.x, point.y, point.z, 1, 0.08, 0.08, 0.08, 0.01);
			if (i % 2 == 0) {
				serverLevel.sendParticles(NyctoParticleTypes.BATSTEP_LEFT.get(), point.x, point.y, point.z, 1, 0.12, 0.12, 0.12, 0.01);
				serverLevel.sendParticles(NyctoParticleTypes.BATSTEP_RIGHT.get(), point.x, point.y, point.z, 1, 0.12, 0.12, 0.12, 0.01);
			}
		}
	}

	public boolean canUseCarnage() {
		return carnageTicks <= 0 && canUsePower(NyctoPowers.CARNAGE) && isAlive();
	}

	public boolean isCarnageActive() {
		return carnageTicks > 0;
	}

	public void startCarnage() {
		if (!canUseCarnage()) {
			return;
		}
		carnageTicks = CARNAGE_TICKS;
		usePower(NyctoPowers.CARNAGE);
		NyctoPowers.applyCarnageDamage(this);
		SyncCarnagePayload.send(this, carnageTicks);
		level().playSound(null, getX(), getY(), getZ(), NyctoSoundEvents.CARNAGE_USE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
	}

	private void tickCarnage() {
		if (carnageTicks <= 0) {
			return;
		}
		NyctoPowers.applyCarnageDamage(this);
		carnageTicks--;
		if (carnageTicks <= 0) {
			NyctoPowers.removeCarnageDamage(this);
			SyncCarnagePayload.send(this, 0);
		}
	}

	public static boolean isVampire(Entity entity) {
		return entity.getType() == NyctoEntityTypes.VAMPIRE.get();
	}

	private record PowerCooldown(String name, int maxCooldown) {
	}

	private static final class UsablePower {
		private final String name;
		private final int maxCooldown;
		private int cooldown;

		private UsablePower(String name, int maxCooldown, int cooldown) {
			this.name = name;
			this.maxCooldown = maxCooldown;
			this.cooldown = cooldown;
		}
	}
}
