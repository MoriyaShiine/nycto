/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.component.entity.power.util.HasOwnerComponent;
import moriyashiine.nycto.common.event.power.util.HasOwnerEvent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.power.vampire.VampiricThrallPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.UUID;

public class VampiricThrallComponent extends HasOwnerComponent implements ServerTickingComponent {
	private FollowMode followMode = FollowMode.FOLLOW;
	@Nullable
	private BlockPos wanderHome = null;
	private boolean alternateDrain = false;

	public VampiricThrallComponent(Mob obj) {
		super(obj);
	}

	@Override
	public void readData(ValueInput input) {
		super.readData(input);
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
				Entity owner = obj.level().getEntity(ownerUuid);
				if (owner instanceof Player player && !NyctoAPI.hasPower(player, ModPowers.VAMPIRIC_THRALL)) {
					SLibUtils.addParticles(obj, ParticleTypes.SMOKE, 16, ParticleAnchor.BODY);
					SLibUtils.playSound(obj, ModSoundEvents.ENTITY_GENERIC_TRANSFORM_HUMAN);
					VampiricThrallPower.setThrall(obj, null);
					return;
				}
				if (getFollowMode() == FollowMode.FOLLOW && (obj.getTarget() == null || obj.getTarget().isDeadOrDying())) {
					if (owner instanceof LivingEntity living && obj.distanceTo(owner) > 24 && living.slib$exists()) {
						obj.randomTeleport(owner.getX() + obj.getRandom().nextIntBetweenInclusive(-3, 3), owner.getY(), owner.getZ() + obj.getRandom().nextIntBetweenInclusive(-3, 3), false);
					}
				}
				if (wanderHome != null && obj.getNavigation().isDone() && !NyctoUtil.isSurvivalNullable(obj.getTarget()) && !wanderHome.closerToCenterThan(obj.position(), obj.getNavigation().getMaxPathLength())) {
					obj.getNavigation().moveTo(wanderHome.getX(), wanderHome.getY(), wanderHome.getZ(), 1);
				}
			}
		}
	}

	@Override
	public void sync() {
		ModEntityComponents.VAMPIRIC_THRALL.sync(obj);
	}

	public @Nullable UUID getOwnerUuid() {
		return ownerUuid;
	}

	public FollowMode getFollowMode() {
		return hasFollowModes() ? followMode : FollowMode.NONE;
	}

	public @Nullable BlockPos getWanderHome() {
		return wanderHome;
	}

	public boolean cannotWanderIfThralled() {
		return hasFollowModes() && !getFollowMode().canWander;
	}

	public boolean hasFollowModes() {
		return hasOwner() && !(obj instanceof TamableAnimal);
	}

	private boolean hasDefendMode() {
		return obj.targetSelector.getAvailableGoals().stream().anyMatch(goal -> goal.getGoal() instanceof TargetGoal) || obj.getBrain().checkMemory(MemoryModuleType.ATTACK_TARGET, MemoryStatus.REGISTERED);
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

	public void reset(@Nullable Player owner) {
		setOwner(owner);
		followMode = FollowMode.FOLLOW;
		wanderHome = null;
		alternateDrain = false;
	}

	public enum FollowMode {
		NONE(false), FOLLOW(false), STAY(false), WANDER(true), DEFEND(true);

		public final boolean canWander;

		FollowMode(boolean canWander) {
			this.canWander = canWander;
		}
	}
}
