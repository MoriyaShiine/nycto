/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.state;

import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.entity.AnimationState;

public class DarkFormEntityRenderState extends ArmedEntityRenderState {
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState idleSneakAnimationState = new AnimationState();
	public final AnimationState jumpAnimationState = new AnimationState();
	public final AnimationState flyAnimationState = new AnimationState();
	public final AnimationState leftAttackAnimationState = new AnimationState();
	public final AnimationState rightAttackAnimationState = new AnimationState();

	public boolean onGround = false;
	public boolean sprinting = false;
}
