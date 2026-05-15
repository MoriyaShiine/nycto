/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.component.entity.power.util.HasOwnerComponent;
import moriyashiine.nycto.common.event.power.util.HasOwnerEvent;
import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.world.level.block.entity.BloodFountainBlockEntity;
import moriyashiine.nycto.common.world.power.vampire.VampiricThrallPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VampiricThrallComponent extends HasOwnerComponent implements ServerTickingComponent {
	private final List<FountainMemory> fountainMemories = new ArrayList<>();
	private FollowMode followMode = FollowMode.FOLLOW;
	@Nullable
	private BlockPos wanderHome = null;
	private boolean alternateDrain = false;

	private int fountainTicks = 0;

	public VampiricThrallComponent(Mob obj) {
		super(obj);
	}

	@Override
	public void readData(ValueInput input) {
		super.readData(input);
		fountainMemories.clear();
		fountainMemories.addAll(input.read("FountainMemories", FountainMemory.CODEC.listOf()).orElse(List.of()));
		followMode = FollowMode.valueOf(input.getStringOr("FollowMode", FollowMode.FOLLOW.name()));
		wanderHome = input.read("WanderHome", BlockPos.CODEC).orElse(null);
		alternateDrain = input.getBooleanOr("AlternateDrain", false);
		if (ownerUuid != null && obj instanceof Villager villager && villager.level() instanceof ServerLevel level) {
			villager.refreshBrain(level);
		}
	}

	@Override
	public void writeData(ValueOutput output) {
		super.writeData(output);
		output.store("FountainMemories", FountainMemory.CODEC.listOf(), fountainMemories);
		output.putString("FollowMode", followMode.name());
		output.storeNullable("WanderHome", BlockPos.CODEC, wanderHome);
		output.putBoolean("AlternateDrain", alternateDrain);
	}

	@Override
	public void serverTick() {
		if (hasOwner() && obj.isAlive()) {
			if (obj.getHealth() < obj.getMaxHealth() && obj.tickCount % 15 == 0 && !ModEntityComponents.HEAL_BLOCK.get(obj).isHealingBlocked()) {
				BloodComponent bloodComponent = ModEntityComponents.BLOOD.get(obj);
				if (bloodComponent.getBlood() > 0) {
					if (!alternateDrain) {
						bloodComponent.drain(1);
					}
					obj.heal(1);
					alternateDrain = !alternateDrain;
				}
			}
			if ((obj.tickCount + obj.getId()) % 20 == 0) {
				Entity owner = obj.level().getEntityInAnyDimension(ownerUuid);
				if (owner instanceof Player player && !NyctoAPI.hasPower(player, ModPowers.VAMPIRIC_THRALL)) {
					SLibUtils.addParticles(obj, ParticleTypes.SMOKE, 16, ParticleAnchor.BODY);
					SLibUtils.playSound(obj, ModSoundEvents.ENTITY_GENERIC_TRANSFORM_HUMAN);
					VampiricThrallPower.setThrall(obj, null);
					return;
				}
				if (getFollowMode() == FollowMode.FOLLOW && obj.getTarget() == null) {
					if (owner instanceof LivingEntity living && obj.distanceTo(owner) > 24 && living.slib$exists()) {
						obj.randomTeleport(owner.getX() + obj.getRandom().nextIntBetweenInclusive(-3, 3), owner.getY(), owner.getZ() + obj.getRandom().nextIntBetweenInclusive(-3, 3), false);
					}
				}
				fountainTick();
				if (wanderHome != null && obj.getNavigation().isDone() && obj.getTarget() == null && !wanderHome.closerToCenterThan(obj.position(), obj.getNavigation().getMaxPathLength())) {
					obj.getNavigation().moveTo(wanderHome.getX(), wanderHome.getY(), wanderHome.getZ(), 1);
				}
			}
			if (fountainTicks > 0) {
				fountainTicks--;
			}
		}
	}

	@Override
	public void sync() {
		ModEntityComponents.VAMPIRIC_THRALL.sync(obj);
	}

	public void reset(@Nullable Player owner) {
		setOwner(owner);
		fountainMemories.clear();
		followMode = FollowMode.FOLLOW;
		wanderHome = null;
		alternateDrain = false;
	}

	public @Nullable UUID getOwnerUuid() {
		return ownerUuid;
	}

	public FollowMode getFollowMode() {
		return hasFollowModes() ? followMode : FollowMode.NONE;
	}

	public void cycleFollowMode() {
		HasOwnerEvent.setTarget(obj, null);
		followMode = switch (followMode) {
			case FOLLOW -> FollowMode.STAY;
			case STAY -> FollowMode.WANDER;
			case WANDER -> hasDefendMode() ? FollowMode.DEFEND : FollowMode.FOLLOW;
			default -> FollowMode.FOLLOW;
		};
		wanderHome = followMode.canWander ? obj.blockPosition() : null;
	}

	public @Nullable BlockPos getWanderHome() {
		return wanderHome;
	}

	public boolean hasFollowModes() {
		return hasOwner() && !(obj instanceof TamableAnimal);
	}

	public boolean cannotWanderIfThralled() {
		return hasFollowModes() && !getFollowMode().canWander;
	}

	private boolean hasDefendMode() {
		return obj.targetSelector.getAvailableGoals().stream().anyMatch(goal -> goal.getGoal() instanceof TargetGoal) || obj.getBrain().checkMemory(MemoryModuleType.ATTACK_TARGET, MemoryStatus.REGISTERED);
	}

	private void fountainTick() {
		// update seen
		final int range = 12;
		for (int i = fountainMemories.size() - 1; i >= 0; i--) {
			FountainMemory memory = fountainMemories.get(i);
			if (memory.exists()) {
				if (memory.isInRange(obj, range) && !obj.level().getBlockState(memory.pos()).is(ModBlocks.BLOOD_FOUNTAIN)) {
					fountainMemories.set(i, new FountainMemory(memory.pos(), memory.bottles(), memory.timeCreated(), false));
				}
			} else if (obj.level().getGameTime() - memory.timeCreated() >= 24000 || !memory.isInRange(obj, 256)) {
				fountainMemories.remove(i);
			}
		}
		BlockPos.withinManhattan(obj.blockPosition(), range, range, range).forEach(pos -> {
			if (obj.level().getBlockEntity(pos) instanceof BloodFountainBlockEntity fountain) {
				if (FountainMemory.canSee(obj, pos)) {
					FountainMemory.addMemory(fountainMemories, new FountainMemory(pos.immutable(), fountain.getFilledBottles(), obj.level().getGameTime(), true));
				}
			}
		});
		// share with others
		if (obj.getTarget() == null) {
			obj.level().getEntities(obj, new AABB(obj.blockPosition()).inflate(range)).forEach(foundEntity -> {
				VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(foundEntity);
				if (vampiricThrallComponent != null && getOwnerUuid().equals(vampiricThrallComponent.getOwnerUuid()) && obj.hasLineOfSight(foundEntity)) {
					fountainMemories.forEach(memory -> FountainMemory.addMemory(vampiricThrallComponent.fountainMemories, memory));
				}
			});
		}
		// drink if hungry
		if (getFollowMode().canWander && obj.getTarget() == null && BloodFountainBlockEntity.isHungryVampire(obj)) {
			@Nullable Path fountain = getClosestFountain();
			if (fountain != null) {
				obj.getNavigation().moveTo(fountain, 1);
			}
		}
	}

	private @Nullable Path getClosestFountain() {
		for (FountainMemory memory : fountainMemories) {
			if (memory.exists() && memory.bottles() > 0 && memory.isInRange(obj, 64)) {
				List<BlockPos> potentialPoses = new ArrayList<>();
				BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
				for (Direction direction : Direction.values()) {
					if (direction.getAxis() != Direction.Axis.Y) {
						mutable.setWithOffset(memory.pos(), direction);
						if (obj.level().getBlockState(mutable).canBeReplaced()) {
							potentialPoses.add(mutable.immutable());
						}
					}
				}
				if (!potentialPoses.isEmpty()) {
					BlockPos pos = potentialPoses.get(obj.getRandom().nextInt(potentialPoses.size()));
					@Nullable Path path = obj.getNavigation().createPath(pos, 1);
					if (path != null && Math.sqrt(pos.distToCenterSqr(path.getEndNode().asVec3())) < 8) {
						return path;
					}
				}
			}
		}
		return null;
	}

	public void setFeeding() {
		fountainTicks = 10;
	}

	public boolean isFeeding() {
		return fountainTicks > 0;
	}

	public enum FollowMode {
		NONE(false), FOLLOW(false), STAY(false), WANDER(true), DEFEND(true);

		public final boolean canWander;

		FollowMode(boolean canWander) {
			this.canWander = canWander;
		}
	}

	private record FountainMemory(BlockPos pos, int bottles, long timeCreated, boolean exists) {
		private static final Codec<FountainMemory> CODEC = RecordCodecBuilder.create(instance -> instance.group(
						BlockPos.CODEC.fieldOf("pos").forGetter(FountainMemory::pos),
						Codec.INT.fieldOf("bottles").forGetter(FountainMemory::bottles),
						Codec.LONG.fieldOf("time_created").forGetter(FountainMemory::timeCreated),
						Codec.BOOL.fieldOf("exists").forGetter(FountainMemory::exists))
				.apply(instance, FountainMemory::new));

		private boolean isInRange(Entity entity, double distance) {
			return Math.sqrt(pos().distToCenterSqr(entity.position())) <= distance;
		}

		private static boolean canSee(Entity entity, BlockPos pos) {
			BlockHitResult result = entity.level().clip(new ClipContext(entity.getEyePosition(), pos.getCenter(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			return result.getType() == HitResult.Type.BLOCK && result.getBlockPos().equals(pos);
		}

		private static void addMemory(List<FountainMemory> memories, FountainMemory memory) {
			for (int i = 0; i < memories.size(); i++) {
				FountainMemory currentMemory = memories.get(i);
				if (currentMemory.pos().equals(memory.pos())) {
					if (currentMemory.timeCreated() < memory.timeCreated()) {
						memories.set(i, memory);
					}
					return;
				}
			}
			memories.add(memory);
		}
	}
}
