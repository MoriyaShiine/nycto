/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class DarkFormRenderState extends ArmedEntityRenderState {
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState idleSneakAnimationState = new AnimationState();
	public final AnimationState jumpAnimationState = new AnimationState();
	public final AnimationState flyAnimationState = new AnimationState();
	public final AnimationState leftAttackAnimationState = new AnimationState();
	public final AnimationState rightAttackAnimationState = new AnimationState();

	public boolean onGround = false;
	public boolean sprinting = false;
}
