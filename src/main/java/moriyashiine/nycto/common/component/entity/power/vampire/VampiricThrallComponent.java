/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.component.entity.power.util.HasOwnerComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.UUID;

public class VampiricThrallComponent extends HasOwnerComponent implements AutoSyncedComponent, ServerTickingComponent {
	private FollowMode followMode = FollowMode.FOLLOW;
	private boolean alternateDrain = false;

	public VampiricThrallComponent(MobEntity obj) {
		super(obj);
	}

	@Override
	public void readData(ReadView readView) {
		super.readData(readView);
		followMode = FollowMode.valueOf(readView.getString("FollowMode", FollowMode.FOLLOW.name()));
		alternateDrain = readView.getBoolean("AlternateDrain", false);
		if (ownerUuid != null && obj instanceof VillagerEntity villager && villager.getEntityWorld() instanceof ServerWorld world) {
			villager.reinitializeBrain(world);
		}
	}

	@Override
	public void writeData(WriteView writeView) {
		super.writeData(writeView);
		writeView.putString("FollowMode", followMode.name());
		writeView.putBoolean("AlternateDrain", alternateDrain);
	}

	@Override
	public void serverTick() {
		if (isThralled() && obj.isAlive()) {
			if (obj.getHealth() < obj.getMaxHealth() && obj.age % 15 == 0 && !ModEntityComponents.HEAL_BLOCK.get(obj).isHealingBlocked()) {
				BloodComponent bloodComponent = ModEntityComponents.BLOOD.get(obj);
				if (bloodComponent.getBlood() > 0) {
					if (!alternateDrain) {
						bloodComponent.drain(1);
					}
					obj.heal(1);
					alternateDrain = !alternateDrain;
				}
			}
			if (obj.age % 20 == 0 && getFollowMode() == FollowMode.FOLLOW && (obj.getTarget() == null || obj.getTarget().isDead())) {
				Entity owner = obj.getEntityWorld().getEntity(ownerUuid);
				if (owner instanceof LivingEntity living && obj.distanceTo(owner) > 24 && living.isPartOfGame()) {
					obj.teleport(owner.getX() + obj.getRandom().nextBetween(-3, 3), owner.getY(), owner.getZ() + obj.getRandom().nextBetween(-3, 3), false);
				}
			}
		}
	}

	public void sync() {
		ModEntityComponents.VAMPIRIC_THRALL.sync(obj);
	}

	@Nullable
	public UUID getOwnerUuid() {
		return ownerUuid;
	}

	public boolean isThralled() {
		return ownerUuid != null;
	}

	public FollowMode getFollowMode() {
		return hasFollowModes() ? followMode : FollowMode.NONE;
	}

	public boolean cannotWanderIfThralled() {
		return hasFollowModes() && getFollowMode() != FollowMode.WANDER;
	}

	public boolean hasFollowModes() {
		return isThralled() && !(obj instanceof TameableEntity);
	}

	public void cycleFollowMode() {
		followMode = switch (followMode) {
			case FOLLOW -> FollowMode.STAY;
			case STAY -> FollowMode.WANDER;
			default -> FollowMode.FOLLOW;
		};
	}

	public enum FollowMode {
		NONE, FOLLOW, STAY, WANDER
	}
}
