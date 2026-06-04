/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class DarkForm extends Monster {
	public static final int JUMP_COOLDOWN = 20;

	private static final EntityDimensions CROUCHING_DIMENSIONS = EntityDimensions.scalable(0.8F, 1.9F).withEyeHeight(1.75F);

	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState idleSneakAnimationState = new AnimationState();
	public final AnimationState jumpAnimationState = new AnimationState();
	public final AnimationState flyAnimationState = new AnimationState();
	public final AnimationState leftAttackAnimationState = new AnimationState();
	public final AnimationState rightAttackAnimationState = new AnimationState();

	private int jumpCooldown = JUMP_COOLDOWN;

	public DarkForm(EntityType<? extends DarkForm> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("JumpCooldown", jumpCooldown);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		jumpCooldown = Math.max(0, compound.getInt("JumpCooldown"));
	}

	@Override
	public void tick() {
		super.tick();
		if (onGround()) {
			jumpCooldown = 0;
		} else if (jumpCooldown > 0) {
			jumpCooldown--;
		}
		if (level().isClientSide()) {
			idleAnimationState.animateWhen(!isCrouching(), tickCount);
			idleSneakAnimationState.animateWhen(isCrouching(), tickCount);
			jumpAnimationState.animateWhen(jumpCooldown > 0 && !onGround(), tickCount);
			flyAnimationState.animateWhen(jumpCooldown == 0 && !onGround(), tickCount);
			leftAttackAnimationState.animateWhen(swingingArm == InteractionHand.OFF_HAND && swingTime > 0, tickCount);
			rightAttackAnimationState.animateWhen(swingingArm == InteractionHand.MAIN_HAND && swingTime > 0, tickCount);
		}
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		if (pose == Pose.CROUCHING) {
			return CROUCHING_DIMENSIONS;
		}
		return super.getDefaultDimensions(pose);
	}

	@Override
	public void setJumping(boolean jumping) {
		super.setJumping(jumping);
		if (jumping && jumpCooldown == 0) {
			jumpCooldown = JUMP_COOLDOWN;
		}
	}
}
