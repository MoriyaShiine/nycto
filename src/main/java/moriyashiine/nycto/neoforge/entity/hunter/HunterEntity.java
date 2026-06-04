/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity.hunter;

import moriyashiine.nycto.neoforge.entity.hunter.goal.PathToContractPosGoal;
import moriyashiine.nycto.neoforge.entity.hunter.goal.UltimateTargetGoal;
import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.hunter.NyctoHunterUtil;
import moriyashiine.nycto.neoforge.registry.NyctoHunterContent;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.EnumSet;
import java.util.UUID;

public class HunterEntity extends Monster implements CrossbowAttackMob {
	private static final DustParticleOptions HUNTER_SPARK = new DustParticleOptions(new Vector3f(0.18F, 0.95F, 0.85F), 1.15F);
	private static final ResourceKey<BannerPattern> HUNTERS_MARK = ResourceKey.create(Registries.BANNER_PATTERN, ResourceLocation.fromNamespaceAndPath("nycto", "hunters_mark"));

	private UUID ultimateTarget = null;
	private BlockPos contractPos = null;
	private int contractPathTicks = 0;
	private boolean chargingCrossbow = false;

	public HunterEntity(EntityType<? extends HunterEntity> type, Level level) {
		super(type, level);
		if (getNavigation() instanceof GroundPathNavigation navigation) {
			navigation.setCanOpenDoors(true);
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 20)
				.add(Attributes.MOVEMENT_SPEED, 0.3)
				.add(Attributes.ATTACK_DAMAGE, 4)
				.add(Attributes.FOLLOW_RANGE, 92);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(0, new PathToContractPosGoal(this));
		goalSelector.addGoal(0, new UltimateTargetGoal(this));
		goalSelector.addGoal(1, new OpenDoorGoal(this, true));
		goalSelector.addGoal(2, new ShieldBlockGoal(this));
		goalSelector.addGoal(3, new RangedCrossbowAttackGoal<>(this, 1, 16));
		goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.1, false));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8));
		goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new HurtByTargetGoal(this));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, NyctoHunterUtil::isVampire));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, NyctoHunterUtil::isVampire));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.hasUUID("UltimateTarget")) {
			ultimateTarget = compound.getUUID("UltimateTarget");
		}
		if (compound.contains("ContractPos", 4)) {
			contractPos = BlockPos.of(compound.getLong("ContractPos"));
		}
		contractPathTicks = compound.getInt("ContractPathTicks");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (ultimateTarget != null) {
			compound.putUUID("UltimateTarget", ultimateTarget);
		}
		if (contractPos != null) {
			compound.putLong("ContractPos", contractPos.asLong());
		}
		compound.putInt("ContractPathTicks", contractPathTicks);
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		equipCombatLoadout();
		setItemSlot(EquipmentSlot.HEAD, new ItemStack(NyctoHunterContent.VAMPIRE_HUNTER_HELMET.get()));
		setItemSlot(EquipmentSlot.CHEST, new ItemStack(NyctoHunterContent.VAMPIRE_HUNTER_CHESTPLATE.get()));
		setItemSlot(EquipmentSlot.LEGS, new ItemStack(NyctoHunterContent.VAMPIRE_HUNTER_LEGGINGS.get()));
		setItemSlot(EquipmentSlot.FEET, new ItemStack(NyctoHunterContent.VAMPIRE_HUNTER_BOOTS.get()));
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			setDropChance(slot, 0);
		}
		spawnSmokeBurst();
		return data;
	}

	private void equipCombatLoadout() {
		int roll = random.nextInt(100);
		if (roll < 35) {
			setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(NyctoItems.WOODEN_STAKE.get()));
			setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.CROSSBOW));
		} else if (roll < 60) {
			setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(NyctoHunterContent.GARLIC_COATED_HALBERD.get()));
			setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
		} else if (roll < 75) {
			setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(NyctoHunterContent.HALBERD.get()));
			setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(NyctoItems.WOODEN_STAKE.get()));
		} else if (roll < 90) {
			setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
		} else {
			setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.CROSSBOW));
		}
	}

	public void equipMountedLoadout() {
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(NyctoHunterContent.GARLIC_COATED_HALBERD.get()));
		setItemSlot(EquipmentSlot.OFFHAND, hunterShield());
	}

	private ItemStack hunterShield() {
		ItemStack shield = new ItemStack(Items.SHIELD);
		shield.set(DataComponents.BANNER_PATTERNS, new BannerPatternLayers.Builder()
				.addIfRegistered(registryAccess().lookupOrThrow(Registries.BANNER_PATTERN), BannerPatterns.BASE, DyeColor.BLACK)
				.addIfRegistered(registryAccess().lookupOrThrow(Registries.BANNER_PATTERN), HUNTERS_MARK, DyeColor.YELLOW)
				.build());
		return shield;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean hurt = super.hurt(source, amount);
		if (hurt && amount > 0) {
			spawnHunterSparks();
		}
		return hurt;
	}

	@Override
	public void die(DamageSource source) {
		spawnSmokeBurst();
		super.die(source);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return NyctoSoundEvents.HUNTER_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) {
		return NyctoSoundEvents.HUNTER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return NyctoSoundEvents.HUNTER_DEATH.get();
	}

	@Override
	public void aiStep() {
		if (contractPathTicks > 0 && --contractPathTicks == 0) {
			getNavigation().stop();
			setContractPos(null);
		}
		swapWeaponsForRange();
		super.aiStep();
	}

	@Override
	public void setChargingCrossbow(boolean chargingCrossbow) {
		this.chargingCrossbow = chargingCrossbow;
	}

	public boolean isChargingCrossbow() {
		return chargingCrossbow;
	}

	@Override
	public void onCrossbowAttackPerformed() {
		noActionTime = 0;
	}

	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeapon) {
		return projectileWeapon == Items.CROSSBOW;
	}

	@Override
	public ItemStack getProjectile(ItemStack shootable) {
		if (shootable.is(Items.CROSSBOW)) {
			return new ItemStack(NyctoItems.WOODEN_STAKE.get());
		}
		return super.getProjectile(shootable);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		performCrossbowAttack(this, 1.6F);
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public boolean requiresCustomPersistence() {
		return true;
	}

	@Nullable
	public Player getUltimateTarget() {
		return ultimateTarget == null ? null : level().getPlayerByUUID(ultimateTarget);
	}

	public void setUltimateTarget(Player player) {
		setTarget(player);
		ultimateTarget = player.getUUID();
	}

	@Nullable
	public BlockPos getContractPos() {
		return contractPos;
	}

	public void setContractPos(@Nullable BlockPos contractPos) {
		this.contractPos = contractPos;
		contractPathTicks = contractPos == null ? 0 : 2400;
	}

	private void swapWeaponsForRange() {
		LivingEntity target = getTarget();
		if (target == null) {
			return;
		}
		boolean mainHandRanged = getMainHandItem().getItem() instanceof ProjectileWeaponItem;
		boolean offHandRanged = getOffhandItem().getItem() instanceof ProjectileWeaponItem;
		if (distanceTo(target) < 8) {
			if (mainHandRanged && !getOffhandItem().isEmpty()) {
				swapHandStacks();
			}
		} else if (!mainHandRanged && offHandRanged) {
			swapHandStacks();
		}
	}

	private void swapHandStacks() {
		ItemStack main = getMainHandItem().copy();
		setItemInHand(InteractionHand.MAIN_HAND, getOffhandItem().copy());
		setItemInHand(InteractionHand.OFF_HAND, main);
	}

	private void spawnSmokeBurst() {
		if (level() instanceof ServerLevel serverLevel) {
			double y = getY() + getBbHeight() * 0.45;
			serverLevel.sendParticles(ParticleTypes.WHITE_SMOKE, getX(), y, getZ(), 24, getBbWidth() * 0.45, getBbHeight() * 0.35, getBbWidth() * 0.45, 0.03);
			serverLevel.sendParticles(ParticleTypes.SMOKE, getX(), y, getZ(), 10, getBbWidth() * 0.35, getBbHeight() * 0.25, getBbWidth() * 0.35, 0.02);
			serverLevel.sendParticles(ParticleTypes.CLOUD, getX(), y, getZ(), 8, getBbWidth() * 0.28, getBbHeight() * 0.18, getBbWidth() * 0.28, 0.01);
		}
	}

	private void spawnHunterSparks() {
		if (level() instanceof ServerLevel serverLevel) {
			double y = getY() + getBbHeight() * 0.55;
			serverLevel.sendParticles(HUNTER_SPARK, getX(), y, getZ(), 12, getBbWidth() * 0.35, getBbHeight() * 0.25, getBbWidth() * 0.35, 0.03);
			serverLevel.sendParticles(ParticleTypes.CRIT, getX(), y, getZ(), 4, getBbWidth() * 0.25, getBbHeight() * 0.15, getBbWidth() * 0.25, 0.02);
		}
	}

	private static final class ShieldBlockGoal extends Goal {
		private final HunterEntity hunter;
		private int blockTicks = 0;
		private int cooldownTicks = 0;

		private ShieldBlockGoal(HunterEntity hunter) {
			this.hunter = hunter;
			setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			if (cooldownTicks > 0) {
				cooldownTicks--;
				return false;
			}
			LivingEntity target = hunter.getTarget();
			return target != null && target.isAlive() && hasShield() && hunter.distanceToSqr(target) <= 64 && hunter.hasLineOfSight(target) && hunter.getRandom().nextInt(20) == 0;
		}

		@Override
		public boolean canContinueToUse() {
			LivingEntity target = hunter.getTarget();
			return blockTicks > 0 && target != null && target.isAlive() && hasShield() && hunter.distanceToSqr(target) <= 81;
		}

		@Override
		public void start() {
			blockTicks = 18 + hunter.getRandom().nextInt(18);
			hunter.startUsingItem(InteractionHand.OFF_HAND);
		}

		@Override
		public void tick() {
			blockTicks--;
			LivingEntity target = hunter.getTarget();
			if (target != null) {
				hunter.getLookControl().setLookAt(target, 30, 30);
			}
		}

		@Override
		public void stop() {
			hunter.stopUsingItem();
			blockTicks = 0;
			cooldownTicks = 35 + hunter.getRandom().nextInt(45);
		}

		private boolean hasShield() {
			return hunter.getOffhandItem().is(Items.SHIELD);
		}
	}
}
