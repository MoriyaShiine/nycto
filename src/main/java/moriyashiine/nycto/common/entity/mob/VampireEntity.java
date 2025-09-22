/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.mob;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.entity.ai.goal.vampire.*;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.transformation.VampireTransformation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class VampireEntity extends HostileEntity {
	public static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(VampireEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
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

	public VampireEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createVampireAttributes() {
		return HostileEntity.createHostileAttributes()
				.add(EntityAttributes.MOVEMENT_SPEED, 0.35F)
				.add(EntityAttributes.FOLLOW_RANGE, 32)
				.add(EntityAttributes.MAX_HEALTH, 20)
				.add(EntityAttributes.ARMOR, 10)
				.add(EntityAttributes.ATTACK_DAMAGE, 6);
	}

	public static boolean canSpawn(EntityType<VampireEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return HostileEntity.canSpawnInDark(type, world, spawnReason, pos, random) && world.getMoonPhase() == 4;
	}

	@Override
	protected void readCustomData(ReadView view) {
		super.readCustomData(view);
		usablePowers.clear();
		usablePowers.addAll(view.read("UsablePowers", UsablePower.CODEC.listOf()).orElse(List.of()));
		dataTracker.set(ATTACKING, view.getBoolean("Attacking", false));
		abilityCooldown = view.getInt("AbilityCooldown", 0);
	}

	@Override
	protected void writeCustomData(WriteView view) {
		super.writeCustomData(view);
		view.put("UsablePowers", UsablePower.CODEC.listOf(), new ArrayList<>(usablePowers));
		view.putBoolean("Attacking", dataTracker.get(ATTACKING));
		view.putInt("AbilityCooldown", abilityCooldown);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(ATTACKING, false);
	}

	@Override
	public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		EntityData data = super.initialize(world, difficulty, spawnReason, entityData);
		VampireTransformation.setComponents(this, true);
		List<Power> selectablePowers = new ArrayList<>(USABLE_POWERS.keySet());
		for (int i = 0; i < 3; i++) {
			Power selectablePower = selectablePowers.remove(getRandom().nextInt(selectablePowers.size()));
			usablePowers.add(new UsablePower(selectablePower, USABLE_POWERS.getInt(selectablePower), 0));
		}
		return data;
	}

	@Override
	protected void initGoals() {
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new VampireEscapeSunlightGoal(this, 1));
		// power start
		goalSelector.add(2, new BloodBarrierGoal(this));
		goalSelector.add(2, new HaemogenesisGoal(this));
		goalSelector.add(3, new CarnageGoal(this));
		goalSelector.add(4, new BatstepGoal(this));
		goalSelector.add(4, new BatSwarmGoal(this));
		goalSelector.add(5, new BloodFlechettesGoal(this));
		// power end
		goalSelector.add(6, new MeleeAttackGoal(this, 1, false));
		goalSelector.add(6, new WanderAroundFarGoal(this, 0.8));
		goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(7, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this));
		targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		targetSelector.add(2, new ActiveTargetGoal<>(this, LivingEntity.class, false, (target, world) -> !target.isPlayer() && target.getType().isIn(ModEntityTypeTags.HAS_QUALITY_BLOOD) && !NyctoAPI.isVampire(target)));
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
		dataTracker.set(ATTACKING, target != null);
	}

	@Override
	public boolean tryAttack(ServerWorld world, Entity target) {
		boolean attacked = super.tryAttack(world, target);
		if (attacked) {
			swingHand(Hand.MAIN_HAND);
			if (target.getType().isIn(ModEntityTypeTags.HAS_QUALITY_BLOOD)) {
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
		return attacked;
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return super.canImmediatelyDespawn(distanceSquared) && !getWorld().isNight();
	}

	@Override
	public boolean cannotDespawn() {
		return super.cannotDespawn() || getWorld().isNight();
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
		swingHand(Hand.MAIN_HAND);
		for (UsablePower usablePower : usablePowers) {
			if (usablePower.power == power) {
				usablePower.cooldown = usablePower.maxCooldown;
			}
		}
	}

	public static class UsablePower {
		private static final Codec<UsablePower> CODEC = RecordCodecBuilder.create(instance -> instance.group(
						NyctoRegistries.POWER.getCodec().fieldOf("power").forGetter(UsablePower::getPower),
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
