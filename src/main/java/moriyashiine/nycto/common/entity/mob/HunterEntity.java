/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.mob;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.entity.ai.goal.hunter.PathToContractPosGoal;
import moriyashiine.nycto.common.entity.ai.goal.hunter.UltimateTargetGoal;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import moriyashiine.superbsteeds.common.init.ModEntityComponents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

public class HunterEntity extends PillagerEntity {
	public static final TrackedDataHandler<HunterType> HUNTER_TYPE_TRACKED_DATA = TrackedDataHandler.create(HunterEntity.HunterType.PACKET_CODEC);
	public static final TrackedData<HunterType> HUNTER_TYPE = DataTracker.registerData(HunterEntity.class, HUNTER_TYPE_TRACKED_DATA);

	private UUID ultimateTarget = null;
	private BlockPos contractPos = null;
	private int contractPathTicks = 0;

	public HunterEntity(EntityType<? extends PillagerEntity> entityType, World world) {
		super(entityType, world);
		getNavigation().setCanOpenDoors(true);
	}

	public static DefaultAttributeContainer.Builder createHunterAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.MOVEMENT_SPEED, 0.3F)
				.add(EntityAttributes.FOLLOW_RANGE, 92)
				.add(EntityAttributes.MAX_HEALTH, 20)
				.add(EntityAttributes.ATTACK_DAMAGE, 4);
	}

	@Override
	protected void readCustomData(ReadView view) {
		super.readCustomData(view);
		setCanPickUpLoot(false);
		dataTracker.set(HUNTER_TYPE, view.read("HunterType", HunterType.CODEC).orElse(HunterType.VAMPIRE));
		ultimateTarget = view.read("UltimateTarget", Uuids.CODEC).orElse(null);
		contractPos = view.read("ContractPos", BlockPos.CODEC).orElse(null);
		contractPathTicks = view.getInt("ContractPathTicks", 0);
	}

	@Override
	protected void writeCustomData(WriteView view) {
		super.writeCustomData(view);
		view.put("HunterType", HunterType.CODEC, dataTracker.get(HUNTER_TYPE));
		if (ultimateTarget != null) {
			view.put("UltimateTarget", Uuids.CODEC, ultimateTarget);
		}
		if (contractPos != null) {
			view.put("ContractPos", BlockPos.CODEC, contractPos);
		}
		view.putInt("ContractPathTicks", contractPathTicks);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(HUNTER_TYPE, HunterType.VAMPIRE);
	}

	@Override
	public SoundEvent getCelebratingSound() {
		return SoundEvents.ENTITY_VILLAGER_CELEBRATE;
	}

	@Override
	public boolean canLead() {
		return false;
	}

	@Override
	public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		EntityData data = super.initialize(world, difficulty, spawnReason, entityData);
		setAbleToJoinRaid(false);
		setPatrolLeader(false);
		setPatrolling(false);
		return data;
	}

	@Override
	protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
		equipGear(random.nextBoolean() ? HunterType.VAMPIRE : HunterType.WEREWOLF, false);
	}

	@Override
	protected void initGoals() {
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(0, new PathToContractPosGoal(this));
		goalSelector.add(0, new UltimateTargetGoal(this));
		goalSelector.add(1, new net.minecraft.entity.ai.goal.LongDoorInteractGoal(this, true));
		goalSelector.add(2, new CrossbowAttackGoal<>(this, 1, 16));
		goalSelector.add(3, new MeleeAttackGoal(this, 1, false));
		goalSelector.add(4, new WanderAroundFarGoal(this, 0.8));
		goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(5, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this, PatrolEntity.class).setGroupRevenge());
		targetSelector.add(1, new ActiveTargetGoal<>(this, LivingEntity.class, false, this::shouldAttack));
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
	public void tickMovement() {
		if (contractPathTicks > 0 && --contractPathTicks == 0) {
			getNavigation().stop();
			setContractPos(null);
		}
		if (getTarget() != null) {
			boolean mainHandRanged = getMainHandStack().getItem() instanceof RangedWeaponItem;
			if (distanceTo(getTarget()) < 8) {
				if (mainHandRanged) {
					swapHandStacks();
				}
			} else if (!mainHandRanged && !getOffHandStack().isEmpty()) {
				swapHandStacks();
			}
		}
		tickHandSwing();
		super.tickMovement();
	}

	@Override
	public State getState() {
		State state = super.getState();
		if (state == State.ATTACKING) {
			state = State.NEUTRAL;
		}
		return state;
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return false;
	}

	@Override
	public boolean cannotDespawn() {
		return true;
	}

	@Override
	public boolean isHolding(Predicate<ItemStack> predicate) {
		return predicate.test(getMainHandStack());
	}

	@Override
	protected void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer) {
	}

	@Nullable
	public PlayerEntity getUltimateTarget() {
		return ultimateTarget == null ? null : getEntityWorld().getPlayerByUuid(ultimateTarget);
	}

	public void setUltimateTarget(PlayerEntity ultimateTarget) {
		setTarget(ultimateTarget);
		this.ultimateTarget = ultimateTarget.getUuid();
	}

	public void setContractPos(BlockPos contractPos) {
		this.contractPos = contractPos;
		contractPathTicks = 2400;
	}

	@Nullable
	public BlockPos getContractPos() {
		return contractPos;
	}

	public void equipGear(HunterType hunterType, boolean horse) {
		dataTracker.set(HUNTER_TYPE, hunterType);
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			equipStack(slot, ItemStack.EMPTY);
		}
		if (hunterType == HunterType.VAMPIRE) {
			equipStack(EquipmentSlot.HEAD, ModItems.VAMPIRE_HUNTER_HELMET.getDefaultStack());
			equipStack(EquipmentSlot.CHEST, ModItems.VAMPIRE_HUNTER_CHESTPLATE.getDefaultStack());
			equipStack(EquipmentSlot.LEGS, ModItems.VAMPIRE_HUNTER_LEGGINGS.getDefaultStack());
			equipStack(EquipmentSlot.FEET, ModItems.VAMPIRE_HUNTER_BOOTS.getDefaultStack());
			if (horse) {
				equipStack(EquipmentSlot.MAINHAND, ModItems.GARLIC_COATED_HALBERD.getDefaultStack());
			} else {
				equipStack(EquipmentSlot.MAINHAND, ModItems.WOODEN_STAKE.getDefaultStack());
				equipStack(EquipmentSlot.OFFHAND, Items.CROSSBOW.getDefaultStack());
			}
		} else if (hunterType == HunterType.WEREWOLF) {
			equipStack(EquipmentSlot.HEAD, ModItems.WEREWOLF_HUNTER_HELMET.getDefaultStack());
			equipStack(EquipmentSlot.CHEST, ModItems.WEREWOLF_HUNTER_CHESTPLATE.getDefaultStack());
			equipStack(EquipmentSlot.LEGS, ModItems.WEREWOLF_HUNTER_LEGGINGS.getDefaultStack());
			equipStack(EquipmentSlot.FEET, ModItems.WEREWOLF_HUNTER_BOOTS.getDefaultStack());
			if (horse) {
				equipStack(EquipmentSlot.MAINHAND, ModItems.ACONITE_COATED_HALBERD.getDefaultStack());
			} else {
				equipStack(EquipmentSlot.MAINHAND, Items.IRON_SWORD.getDefaultStack());
				equipStack(EquipmentSlot.OFFHAND, Items.BOW.getDefaultStack());
			}
		}
	}

	private boolean shouldAttack(LivingEntity target, ServerWorld world) {
		if (target == getUltimateTarget()) {
			return !NyctoAPI.hasRespawnLeniency(target);
		} else if (NyctoAPI.isVampire(target)) {
			return isHoldingItem(ModItems.WOODEN_STAKE);
		}
		return false;
	}

	private boolean isHoldingItem(Item item) {
		return getMainHandStack().isOf(item) || getOffHandStack().isOf(item);
	}

	private void swapHandStacks() {
		ItemStack stack = getMainHandStack().copy();
		setStackInHand(Hand.MAIN_HAND, getOffHandStack().copy());
		setStackInHand(Hand.OFF_HAND, stack);
	}

	public static void mountHorse(LivingEntity entity) {
		HorseEntity horse = EntityType.HORSE.create(entity.getEntityWorld(), SpawnReason.TRIGGERED);
		if (horse.teleport(entity.getX(), entity.getY(), entity.getZ(), false)) {
			horse.initialize((ServerWorldAccess) entity.getEntityWorld(), entity.getEntityWorld().getLocalDifficulty(entity.getBlockPos()), SpawnReason.TRIGGERED, null);
			horse.setOwner(entity);
			horse.setTame(true);
			entity.getEntityWorld().spawnEntity(horse);
			entity.startRiding(horse);
			if (Nycto.superbSteedsLoaded) {
				HorseAttributesComponent horseAttributesComponent = ModEntityComponents.HORSE_ATTRIBUTES.get(horse);
				while (horseAttributesComponent.getSpeed() < 5) {
					horseAttributesComponent.incrementSpeed();
				}
				horseAttributesComponent.sync();
			} else {
				horse.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.3375);
			}
		}
	}

	public record HunterType(Identifier texture) {
		public static final Codec<HunterType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Identifier.CODEC.fieldOf("texture").forGetter(HunterType::texture)
		).apply(instance, HunterType::new));
		public static final PacketCodec<PacketByteBuf, HunterType> PACKET_CODEC = PacketCodec.tuple(
				Identifier.PACKET_CODEC, HunterType::texture,
				HunterType::new
		);

		public static final HunterType VAMPIRE = new HunterType(Nycto.id("textures/entity/hunter/vampire.png"));
		public static final HunterType WEREWOLF = new HunterType(Nycto.id("textures/entity/hunter/werewolf.png"));

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
