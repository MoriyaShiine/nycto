/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.mob;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DarkFormEntity extends HostileEntity {
	public static final int JUMP_COOLDOWN = 20;

	private static final EntityDimensions CROUCHING_DIMENSIONS = EntityDimensions.changing(0.8F, 2.2F);

	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState idleSneakAnimationState = new AnimationState();
	public final AnimationState jumpAnimationState = new AnimationState();
	public final AnimationState flyAnimationState = new AnimationState();
	public final AnimationState leftAttackAnimationState = new AnimationState();
	public final AnimationState rightAttackAnimationState = new AnimationState();

	private int jumpCooldown = JUMP_COOLDOWN;

	public DarkFormEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void readCustomData(ReadView view) {
		super.readCustomData(view);
		jumpCooldown = view.getInt("JumpCooldown", 0);
	}

	@Override
	protected void writeCustomData(WriteView view) {
		super.writeCustomData(view);
		view.putInt("JumpCooldown", jumpCooldown);
	}

	@Override
	public void tick() {
		super.tick();
		if (isOnGround()) {
			jumpCooldown = 0;
		} else if (jumpCooldown > 0) {
			jumpCooldown--;
		}
		if (getWorld().isClient) {
			idleAnimationState.setRunning(!isInSneakingPose(), age);
			idleSneakAnimationState.setRunning(isInSneakingPose(), age);
			jumpAnimationState.setRunning(jumpCooldown > 0 && !isOnGround(), age);
			flyAnimationState.setRunning(jumpCooldown == 0 && !isOnGround(), age);
			leftAttackAnimationState.setRunning(preferredHand == Hand.OFF_HAND && handSwingTicks > 0, age);
			rightAttackAnimationState.setRunning(preferredHand == Hand.MAIN_HAND && handSwingTicks > 0, age);
		}
	}

	@Override
	protected EntityDimensions getBaseDimensions(EntityPose pose) {
		if (pose == EntityPose.CROUCHING) {
			return CROUCHING_DIMENSIONS;
		}
		return super.getBaseDimensions(pose);
	}

	@Override
	public void setJumping(boolean jumping) {
		super.setJumping(jumping);
		if (jumping && jumpCooldown == 0) {
			jumpCooldown = JUMP_COOLDOWN;
		}
	}
}
