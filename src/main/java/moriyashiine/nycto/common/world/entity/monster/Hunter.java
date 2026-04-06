/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.monster;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModBannerPatterns;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.world.entity.ai.goal.hunter.PathToContractPosGoal;
import moriyashiine.nycto.common.world.entity.ai.goal.hunter.UltimateTargetGoal;
import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import moriyashiine.superbsteeds.common.init.ModEntityComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

public class Hunter extends Pillager {
	public static final EntityDataSerializer<HunterType> HUNTER_TYPE = EntityDataSerializer.forValueType(Hunter.HunterType.STREAM_CODEC);
	public static final EntityDataAccessor<HunterType> HUNTER_TYPE_ID = SynchedEntityData.defineId(Hunter.class, HUNTER_TYPE);

	private UUID ultimateTarget = null;
	private BlockPos contractPos = null;
	private int contractPathTicks = 0;

	public Hunter(EntityType<? extends Pillager> type, Level level) {
		super(type, level);
		getNavigation().setCanOpenDoors(true);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3F)
				.add(Attributes.FOLLOW_RANGE, 92)
				.add(Attributes.MAX_HEALTH, 20)
				.add(Attributes.ATTACK_DAMAGE, 4);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		setCanPickUpLoot(false);
		entityData.set(HUNTER_TYPE_ID, input.read("HunterType", HunterType.CODEC).orElse(HunterType.VAMPIRE));
		ultimateTarget = input.read("UltimateTarget", UUIDUtil.AUTHLIB_CODEC).orElse(null);
		contractPos = input.read("ContractPos", BlockPos.CODEC).orElse(null);
		contractPathTicks = input.getIntOr("ContractPathTicks", 0);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.store("HunterType", HunterType.CODEC, entityData.get(HUNTER_TYPE_ID));
		if (ultimateTarget != null) {
			output.store("UltimateTarget", UUIDUtil.AUTHLIB_CODEC, ultimateTarget);
		}
		if (contractPos != null) {
			output.store("ContractPos", BlockPos.CODEC, contractPos);
		}
		output.putInt("ContractPathTicks", contractPathTicks);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(HUNTER_TYPE_ID, HunterType.VAMPIRE);
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return SoundEvents.VILLAGER_CELEBRATE;
	}

	@Override
	public boolean canBeLeader() {
		return false;
	}

	@Override
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @org.jspecify.annotations.Nullable SpawnGroupData groupData) {
		SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, groupData);
		setCanJoinRaid(false);
		setPatrolLeader(false);
		setPatrolling(false);
		return data;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		equipGear(random.nextBoolean() ? HunterType.VAMPIRE : HunterType.WEREWOLF, false);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(0, new PathToContractPosGoal(this));
		goalSelector.addGoal(0, new UltimateTargetGoal(this));
		goalSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.OpenDoorGoal(this, true));
		goalSelector.addGoal(2, new RangedCrossbowAttackGoal<>(this, 1, 16));
		goalSelector.addGoal(3, new MeleeAttackGoal(this, 1, false));
		goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8));
		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8));
		goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		targetSelector.addGoal(0, new HurtByTargetGoal(this, PatrollingMonster.class).setAlertOthers());
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, false, this::shouldAttack));
	}

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return ModSoundEvents.ENTITY_HUNTER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSoundEvents.ENTITY_HUNTER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ENTITY_HUNTER_DEATH;
	}

	@Override
	public void aiStep() {
		if (contractPathTicks > 0 && --contractPathTicks == 0) {
			getNavigation().stop();
			setContractPos(null);
		}
		if (getTarget() != null) {
			boolean mainHandRanged = getMainHandItem().getItem() instanceof ProjectileWeaponItem;
			if (distanceTo(getTarget()) < 8) {
				if (mainHandRanged) {
					swapHandStacks();
				}
			} else if (!mainHandRanged && !getOffhandItem().isEmpty()) {
				swapHandStacks();
			}
		}
		updateSwingTime();
		super.aiStep();
	}

	@Override
	public IllagerArmPose getArmPose() {
		IllagerArmPose state = super.getArmPose();
		if (state == IllagerArmPose.ATTACKING) {
			state = IllagerArmPose.NEUTRAL;
		}
		return state;
	}

	@Override
	public boolean removeWhenFarAway(double distSqr) {
		return false;
	}

	@Override
	public boolean requiresCustomPersistence() {
		return true;
	}

	@Override
	public boolean isHolding(Predicate<ItemStack> itemPredicate) {
		return itemPredicate.test(getMainHandItem());
	}

	@Override
	protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean killedByPlayer) {
	}

	@Nullable
	public Player getUltimateTarget() {
		return ultimateTarget == null ? null : level().getPlayerByUUID(ultimateTarget);
	}

	public void setUltimateTarget(Player ultimateTarget) {
		setTarget(ultimateTarget);
		this.ultimateTarget = ultimateTarget.getUUID();
	}

	public void setContractPos(BlockPos contractPos) {
		this.contractPos = contractPos;
		contractPathTicks = 2400;
	}

	@Nullable
	public BlockPos getContractPos() {
		return contractPos;
	}

	public void equipGear(HunterType hunterType, boolean hasHorse) {
		entityData.set(HUNTER_TYPE_ID, hunterType);
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			setItemSlot(slot, ItemStack.EMPTY);
		}
		if (hasHorse) {
			ItemStack shield = Items.SHIELD.getDefaultInstance();
			shield.set(DataComponents.BANNER_PATTERNS, new BannerPatternLayers.Builder()
					.add(registryAccess().lookupOrThrow(Registries.BANNER_PATTERN).getOrThrow(BannerPatterns.BASE), DyeColor.BLACK)
					.add(registryAccess().lookupOrThrow(Registries.BANNER_PATTERN).getOrThrow(ModBannerPatterns.HUNTERS_MARK), DyeColor.YELLOW)
					.build());
			setItemSlot(EquipmentSlot.OFFHAND, shield);
		}
		if (hunterType == HunterType.VAMPIRE) {
			setItemSlot(EquipmentSlot.HEAD, ModItems.VAMPIRE_HUNTER_HELMET.getDefaultInstance());
			setItemSlot(EquipmentSlot.CHEST, ModItems.VAMPIRE_HUNTER_CHESTPLATE.getDefaultInstance());
			setItemSlot(EquipmentSlot.LEGS, ModItems.VAMPIRE_HUNTER_LEGGINGS.getDefaultInstance());
			setItemSlot(EquipmentSlot.FEET, ModItems.VAMPIRE_HUNTER_BOOTS.getDefaultInstance());
			if (hasHorse) {
				setItemSlot(EquipmentSlot.MAINHAND, ModItems.GARLIC_COATED_HALBERD.getDefaultInstance());
			} else {
				setItemSlot(EquipmentSlot.MAINHAND, ModItems.WOODEN_STAKE.getDefaultInstance());
				setItemSlot(EquipmentSlot.OFFHAND, Items.CROSSBOW.getDefaultInstance());
			}
		} else if (hunterType == HunterType.WEREWOLF) {
			setItemSlot(EquipmentSlot.HEAD, ModItems.WEREWOLF_HUNTER_HELMET.getDefaultInstance());
			setItemSlot(EquipmentSlot.CHEST, ModItems.WEREWOLF_HUNTER_CHESTPLATE.getDefaultInstance());
			setItemSlot(EquipmentSlot.LEGS, ModItems.WEREWOLF_HUNTER_LEGGINGS.getDefaultInstance());
			setItemSlot(EquipmentSlot.FEET, ModItems.WEREWOLF_HUNTER_BOOTS.getDefaultInstance());
			if (hasHorse) {
				setItemSlot(EquipmentSlot.MAINHAND, ModItems.ACONITE_COATED_HALBERD.getDefaultInstance());
			} else {
				setItemSlot(EquipmentSlot.MAINHAND, Items.IRON_SWORD.getDefaultInstance());
				setItemSlot(EquipmentSlot.OFFHAND, Items.BOW.getDefaultInstance());
			}
		}
	}

	private boolean shouldAttack(LivingEntity target, ServerLevel level) {
		if (target == getUltimateTarget()) {
			return !NyctoAPI.hasRespawnLeniency(target);
		} else if (NyctoAPI.isVampire(target)) {
			return isHoldingItem(ModItems.WOODEN_STAKE);
		}
		return false;
	}

	private boolean isHoldingItem(Item item) {
		return getMainHandItem().is(item) || getOffhandItem().is(item);
	}

	private void swapHandStacks() {
		ItemStack stack = getMainHandItem().copy();
		setItemInHand(InteractionHand.MAIN_HAND, getOffhandItem().copy());
		setItemInHand(InteractionHand.OFF_HAND, stack);
	}

	public static void mountHorse(ServerLevel level, LivingEntity entity) {
		Horse horse = EntityType.HORSE.create(level, EntitySpawnReason.TRIGGERED);
		if (horse.randomTeleport(entity.getX(), entity.getY(), entity.getZ(), false)) {
			horse.finalizeSpawn(level, level.getCurrentDifficultyAt(entity.blockPosition()), EntitySpawnReason.TRIGGERED, null);
			horse.setOwner(entity);
			horse.setTamed(true);
			level.addFreshEntity(horse);
			entity.startRiding(horse);
			if (Nycto.superbSteedsLoaded) {
				HorseAttributesComponent horseAttributesComponent = ModEntityComponents.HORSE_ATTRIBUTES.get(horse);
				while (horseAttributesComponent.getSpeed() < 5) {
					horseAttributesComponent.incrementSpeed();
				}
				horseAttributesComponent.sync();
			} else {
				horse.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3375);
			}
		}
	}

	public record HunterType(Identifier texture) {
		public static final Codec<HunterType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Identifier.CODEC.fieldOf("texture").forGetter(HunterType::texture)
		).apply(instance, HunterType::new));
		public static final StreamCodec<FriendlyByteBuf, HunterType> STREAM_CODEC = StreamCodec.composite(
				Identifier.STREAM_CODEC, HunterType::texture,
				HunterType::new
		);

		public static final HunterType VAMPIRE = new HunterType(Nycto.id("textures/entity/hunter/vampire_hunter.png"));
		public static final HunterType WEREWOLF = new HunterType(Nycto.id("textures/entity/hunter/werewolf_hunter.png"));

		public boolean shouldTarget(LivingEntity entity) {
			if (this == VAMPIRE) {
				return NyctoAPI.isVampire(entity);
			} else if (this == WEREWOLF) {
				return NyctoAPI.isWerewolf(entity);
			}
			return false;
		}
	}
}
