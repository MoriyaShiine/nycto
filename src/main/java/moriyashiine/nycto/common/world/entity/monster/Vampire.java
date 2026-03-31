/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.monster;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModGameRules;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.world.entity.ai.goal.vampire.*;
import moriyashiine.nycto.common.world.transformation.VampireTransformation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class Vampire extends Monster {
	public static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(Vampire.class, EntityDataSerializers.BOOLEAN);
	private static final int ABILITY_COOLDOWN = 40;

	private static final Object2IntMap<Power> USABLE_POWERS = new Object2IntArrayMap<>(Map.of(
			ModPowers.BATSTEP, 20 * 8,
			ModPowers.BAT_SWARM, 20 * 20,
			ModPowers.BLOOD_BARRIER, 20 * 15,
			ModPowers.BLOOD_FLECHETTES, 20 * 6,
			ModPowers.CARNAGE, 20 * 15,
			ModPowers.HAEMOGENESIS, 20 * 10
	));

	private final Set<UsablePower> usablePowers = new HashSet<>();

	private int abilityCooldown = 0;

	public Vampire(EntityType<? extends Monster> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.35F)
				.add(Attributes.FOLLOW_RANGE, 32)
				.add(Attributes.MAX_HEALTH, 20)
				.add(Attributes.ARMOR, 10)
				.add(Attributes.ATTACK_DAMAGE, 6);
	}

	public static boolean checkVampireSpawnRules(EntityType<Vampire> type, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
		MoonPhase moonPhase = level.getLevel().environmentAttributes().getValue(EnvironmentAttributes.MOON_PHASE, pos);
		return Monster.checkMonsterSpawnRules(type, level, spawnReason, pos, random) && moonPhase == MoonPhase.NEW_MOON && level.getLevel().getGameRules().get(ModGameRules.SPAWN_VAMPIRES);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		usablePowers.clear();
		usablePowers.addAll(input.read("UsablePowers", UsablePower.CODEC.listOf()).orElse(List.of()));
		entityData.set(ATTACKING, input.getBooleanOr("Attacking", false));
		abilityCooldown = input.getIntOr("AbilityCooldown", 0);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.store("UsablePowers", UsablePower.CODEC.listOf(), new ArrayList<>(usablePowers));
		output.putBoolean("Attacking", entityData.get(ATTACKING));
		output.putInt("AbilityCooldown", abilityCooldown);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(ATTACKING, false);
	}

	@Override
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
		SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, groupData);
		VampireTransformation.setComponents(this, true);
		List<Power> selectablePowers = new ArrayList<>(USABLE_POWERS.keySet());
		for (int i = 0; i < 3; i++) {
			Power selectablePower = selectablePowers.remove(getRandom().nextInt(selectablePowers.size()));
			usablePowers.add(new UsablePower(selectablePower, USABLE_POWERS.getInt(selectablePower), 0));
		}
		return data;
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new VampireFleeSunGoal(this, 1));
		// power start
		goalSelector.addGoal(2, new BloodBarrierGoal(this));
		goalSelector.addGoal(2, new HaemogenesisGoal(this));
		goalSelector.addGoal(3, new CarnageGoal(this));
		goalSelector.addGoal(4, new BatstepGoal(this));
		goalSelector.addGoal(4, new BatSwarmGoal(this));
		goalSelector.addGoal(5, new BloodFlechettesGoal(this));
		// power end
		goalSelector.addGoal(6, new MeleeAttackGoal(this, 1, false));
		goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
		goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8));
		goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		targetSelector.addGoal(0, new HurtByTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, false, (target, _) -> !target.slib$isPlayer() && target.is(ModEntityTypeTags.HAS_QUALITY_BLOOD) && !NyctoAPI.isVampire(target)));
	}

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return ModSoundEvents.ENTITY_VAMPIRE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSoundEvents.ENTITY_VAMPIRE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ENTITY_VAMPIRE_DEATH;
	}

	@Override
	public void tick() {
		super.tick();
		if (abilityCooldown > 0) {
			abilityCooldown--;
		}
		usablePowers.forEach(usablePower -> {
			if (usablePower.cooldown > 0) {
				usablePower.cooldown--;
			}
		});
	}

	@Override
	public void setTarget(@Nullable LivingEntity target) {
		super.setTarget(target);
		entityData.set(ATTACKING, target != null);
	}

	@Override
	public boolean doHurtTarget(ServerLevel level, Entity target) {
		boolean hurtTarget = super.doHurtTarget(level, target);
		if (hurtTarget) {
			swing(InteractionHand.MAIN_HAND);
			if (target.is(ModEntityTypeTags.HAS_QUALITY_BLOOD)) {
				BloodComponent targetBloodComponent = ModEntityComponents.BLOOD.getNullable(target);
				if (targetBloodComponent != null) {
					BloodComponent selfBloodComponent = ModEntityComponents.BLOOD.get(this);
					if (selfBloodComponent.canFill()) {
						int amount = Math.min(5, targetBloodComponent.getBlood());
						if (targetBloodComponent.drainAttack(amount)) {
							selfBloodComponent.fill(amount);
							SLibUtils.playSound(target, ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value());
						}
					}
				}
			}
		}
		return hurtTarget;
	}

	@Override
	public boolean removeWhenFarAway(double distSqr) {
		return super.removeWhenFarAway(distSqr) && !level().isDarkOutside();
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || level().isDarkOutside();
	}

	public boolean canUsePower(Power power) {
		if (abilityCooldown == 0 && !ModEntityComponents.BLOOD.get(this).lowBlood()) {
			for (UsablePower usablePower : usablePowers) {
				if (usablePower.power == power) {
					return usablePower.cooldown == 0;
				}
			}
		}
		return false;
	}

	public void useAbility(Power power) {
		abilityCooldown = ABILITY_COOLDOWN;
		swing(InteractionHand.MAIN_HAND);
		for (UsablePower usablePower : usablePowers) {
			if (usablePower.power == power) {
				usablePower.cooldown = usablePower.maxCooldown;
			}
		}
	}

	public static class UsablePower {
		private static final Codec<UsablePower> CODEC = RecordCodecBuilder.create(instance -> instance.group(
						NyctoRegistries.POWER.byNameCodec().fieldOf("power").forGetter(UsablePower::getPower),
						Codec.INT.fieldOf("max_cooldown").forGetter(UsablePower::getMaxCooldown),
						Codec.INT.fieldOf("cooldown").forGetter(UsablePower::getCooldown))
				.apply(instance, UsablePower::new));

		private final Power power;
		private final int maxCooldown;
		private int cooldown;

		public UsablePower(Power power, int maxCooldown, int cooldown) {
			this.power = power;
			this.maxCooldown = maxCooldown;
			this.cooldown = cooldown;
		}

		private Power getPower() {
			return power;
		}

		private int getMaxCooldown() {
			return maxCooldown;
		}

		private int getCooldown() {
			return cooldown;
		}
	}
}
