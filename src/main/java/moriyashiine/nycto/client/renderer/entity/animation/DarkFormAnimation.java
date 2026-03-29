/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class DarkFormAnimation {
	public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(2).looping()
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(2, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, -0.2F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(0, -0.17F, 0.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0, -0.1F, 0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 0.2F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(0, 0.1F, 0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0, 0.17F, 0.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 0.2F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.posVec(0, 0.17F, -0.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.posVec(0, 0.1F, -0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.posVec(0, 0, -0.2F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(0, -0.1F, -0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.posVec(0, -0.17F, -0.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.posVec(0, -0.2F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("chest", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1.29F, 0, -0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-1.88F, 0, -0.64F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-1.97F, 0, -0.94F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-1.53F, 0, -0.98F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-0.68F, 0, -0.77F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.35F, 0, -0.34F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(1.29F, 0, 0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(1.88F, 0, 0.64F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.97F, 0, 0.94F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(1.53F, 0, 0.98F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0.68F, 0, 0.77F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-0.35F, 0, 0.34F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-1.29F, 0, -0.17F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-0.64F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.94F, 0, 0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-0.98F, 0, 0.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0.77F, 0, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-0.34F, 0, 0.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.17F, 0, 0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(0.64F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.94F, 0, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.98F, 0, -0.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.77F, 0, -1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0.34F, 0, -0.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-0.17F, 0, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-0.64F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(2, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-2, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.61F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-1.2F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-3.45F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-3.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-0.61F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.2F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(3.45F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(3.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.61F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-1.2F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-3.45F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-3.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-0.61F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.2F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(3.45F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(3.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(1.93F, 0, 3), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.52F, 0, 2.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-1.03F, 0, 1.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.3F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-2.95F, 0, -1.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-2.82F, 0, -2.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-1.93F, 0, -3), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-0.52F, 0, -2.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.03F, 0, -1.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.3F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(2.95F, 0, 1.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(2.82F, 0, 2.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(1.93F, 0, 3), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1.93F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-2.82F, 0, 1.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-2.95F, 0, 2.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.3F, 0, 3), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-1.03F, 0, 2.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.52F, 0, 1.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(1.93F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(2.82F, 0, -1.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(2.95F, 0, -2.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.3F, 0, -3), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(1.03F, 0, -2.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-0.52F, 0, -1.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-1.93F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("cloth", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(2, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-2, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(1.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(2, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition IDLE_SNEAK = AnimationDefinition.Builder.withLength(2).looping()
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(20.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(21, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(21.37F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(21.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(21.37F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(21, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(20.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(19.63F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(19.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(19.63F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(20.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, -1.1F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(0, -1.09F, 0.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0, -1.05F, 0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, -1, 0.2F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(0, -0.95F, 0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0, -0.91F, 0.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, -0.9F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.posVec(0, -0.91F, -0.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.posVec(0, -0.95F, -0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.posVec(0, -1, -0.2F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.posVec(0, -1.05F, -0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.posVec(0, -1.09F, -0.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.posVec(0, -1.1F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("chest", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(14.83F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(14.62F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(14.51F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(14.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(14.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(14.91F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(15.17F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(15.38F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(15.49F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(15.47F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(15.32F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(15.09F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(14.83F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-9.68F, 0, -0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-9.53F, 0, -0.64F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-9.51F, 0, -0.94F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-9.62F, 0, -0.98F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-9.83F, 0, -0.77F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-10.09F, 0, -0.34F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-10.32F, 0, 0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-10.47F, 0, 0.64F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-10.49F, 0, 0.94F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-10.38F, 0, 0.98F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-10.17F, 0, 0.77F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-9.91F, 0, 0.34F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-9.68F, 0, -0.17F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-21.82F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-20.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-20.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-20.62F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-21.21F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-22.15F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-23.18F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-24.03F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-24.47F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-24.38F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-23.79F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-22.85F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-21.82F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-9.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-9.53F, 0, 0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-9.51F, 0, 0.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-9.62F, 0, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-9.83F, 0, 0.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-10.09F, 0, 0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-10.32F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-10.47F, 0, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-10.49F, 0, -0.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-10.38F, 0, -1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-10.17F, 0, -0.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-9.91F, 0, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-9.68F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-21.82F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-20.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-20.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-20.62F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-21.21F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-22.15F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-23.18F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-24.03F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-24.47F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-24.38F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-23.79F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-22.85F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-21.82F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-24, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-23.27F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-23, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-23.27F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-24, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-26, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-26.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-27, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-26.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-26, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, -1, -2), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.61F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-1.2F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-3.45F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-3.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-0.61F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.2F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(3.45F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(3.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.61F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-1.2F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-3.45F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-3.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-0.61F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.2F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(3.45F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(3.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.35F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-0.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.53F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-1.97F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-1.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-1.29F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-9, 20, 18), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-8.75F, 20, 18.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-8.57F, 20, 18.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-8.5F, 20, 19), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-8.57F, 20, 18.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-8.75F, 20, 18.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-9, 20, 18), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-9.25F, 20, 17.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-9.43F, 20, 17.13F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-9.5F, 20, 17), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-9.43F, 20, 17.13F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-9.25F, 20, 17.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-9, 20, 18), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-9, -20, -17), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-8.75F, -20, -17.13F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-8.57F, -20, -17.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-8.5F, -20, -18), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-8.57F, -20, -18.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-8.75F, -20, -18.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-9, -20, -19), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-9.25F, -20, -18.87F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-9.43F, -20, -18.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-9.5F, -20, -18), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-9.43F, -20, -17.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-9.25F, -20, -17.13F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-9, -20, -17), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("cloth", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-10.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-10.77F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-11.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-13.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-14.23F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-14.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-14.23F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-13.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-12.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-11.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-10.77F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-10.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-12, -5, 96), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-12, 5, -96), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(1).looping()
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(5, 2.5F, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(5, 5, 0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5, 2.5F, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(5, -2.5F, -1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(5, -5, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(5, -2.5F, 0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(5, 2.5F, 1), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-12.88F, 6.48F, -6.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-19.72F, 6.48F, -6.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-6.87F, 6.48F, -6.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(12.83F, 6.48F, -6.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(19.67F, 6.48F, -6.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(6.81F, 6.48F, -6.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-12.88F, 6.48F, -6.27F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, -3), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 0, -3), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(4.33F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-4.33F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-4.33F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(4.33F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(4.33F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0.01F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(17.33F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(17.33F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.01F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-17.31F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-17.31F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(0.01F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, -3), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-4.99F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-2.49F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(2.51F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.01F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(2.51F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-2.49F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-4.99F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(5, -2.5F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(5, -5, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5, -2.5F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(5, 2.5F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(5, 5, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(5, 2.5F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(5, -2.5F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(3.47F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(18.79F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(15.32F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-3.47F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-18.79F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-15.32F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(3.47F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(17.98F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(16.58F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-1.4F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-17.98F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-16.58F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.4F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(17.98F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foreleg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0.1F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(0, 0.93F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0, 1.83F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 1.9F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(0, 1.07F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0, 0.17F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 0.1F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-3.47F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-18.79F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-15.32F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(3.47F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(18.79F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(15.32F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-3.47F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-16.58F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-17.98F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-1.4F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(16.58F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(17.98F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.4F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-16.58F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foreleg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 1.9F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(0, 1.83F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0, 0.93F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0.1F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(0, 0.17F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0, 1.07F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 1.9F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-0.43F, -3.21F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-1.92F, -4.92F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(2.35F, -1.71F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0.43F, 3.21F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-1.92F, 4.92F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(2.35F, 1.71F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-0.43F, -3.21F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1.61F, -3.21F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-0.86F, -4.92F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(2.46F, -1.71F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-1.61F, 3.21F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-0.86F, 4.92F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(2.46F, 1.71F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-1.61F, -3.21F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("cloth", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(6.43F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(9.85F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(3.42F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-6.43F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-9.85F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-3.42F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(6.43F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition RUN = AnimationDefinition.Builder.withLength(1).looping()
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(19.49F, -3, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(14.65F, -6, 0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(10.85F, -3, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(19.49F, 3, -1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(14.65F, 6, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(10.85F, 3, 0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(19.49F, -3, 1), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-0.38F, 8.98F, -16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-7.22F, 8.98F, -16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5.63F, 8.98F, -16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(25.33F, 8.98F, -16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(32.17F, 8.98F, -16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(19.31F, 8.98F, -16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-0.38F, 8.98F, -16.27F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-42.49F, 7.71F, -4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-51.15F, 7.71F, -4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-51.15F, 7.71F, -4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-42.49F, 7.71F, -4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-33.83F, 7.71F, -4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-33.83F, 7.71F, -4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-42.49F, 7.71F, -4.67F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(25.33F, -8.98F, 16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(32.17F, -8.98F, 16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(19.31F, -8.98F, 16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0.38F, -8.98F, 16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-7.22F, -8.98F, 16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(5.63F, -8.98F, 16.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(25.33F, -8.98F, 16.27F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-42.49F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-33.83F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-33.83F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-42.49F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-51.15F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-51.15F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-42.49F, -7.71F, 4.67F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-22, 4.95F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-11.5F, 6.76F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-11.5F, 1.81F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-22, -4.95F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-11.5F, -6.76F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-11.5F, -1.81F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-22, 4.95F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-3.21F, 6.43F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-1.71F, 9.85F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(4.92F, 3.42F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-3.21F, -6.43F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-1.71F, -9.85F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(4.92F, -3.42F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-3.21F, 6.43F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-3.21F, 1.74F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-1.71F, 9.4F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(4.92F, 7.66F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-3.21F, -1.74F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-1.71F, -9.4F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(4.92F, -7.66F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-3.21F, 1.74F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(6.95F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(37.59F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(30.64F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-6.95F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-37.59F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-30.64F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(6.95F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg_upper", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, -1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 0, -1), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-19.39F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(6.32F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(45.71F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(59.39F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(33.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-5.71F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-19.39F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foreleg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 1.41F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(0, 0.5F, -0.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0, 1.5F, -1.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 2, -1.41F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(0, 1.5F, 0.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0, 0.5F, 1.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 0, 1.41F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-6.95F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-37.59F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-30.64F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(6.95F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(37.59F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(30.64F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-6.95F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg_upper", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, -1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 0, 1), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(59.39F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(33.68F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-5.71F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-19.39F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(6.32F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(45.71F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(59.39F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foreleg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 2, -1.41F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(0, 1.5F, 0.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0, 0.5F, 1.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 1.41F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.posVec(0, 0.5F, -0.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0, 1.5F, -1.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 2, -1.41F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("cloth", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-10, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-10, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lips", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 1.25F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 1.25F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lips", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0, KeyframeAnimations.scaleVec(1, 0.6F, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.scaleVec(1, 0.6F, 1), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition JUMP = AnimationDefinition.Builder.withLength(1)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(13.19F, 6.51F, 12), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(15.51F, 7.23F, 12.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(17.14F, 6, 8.36F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(16.52F, 2.47F, -1.83F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(14.95F, 1.29F, -4.83F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(13.84F, 1.05F, -5.24F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(10.63F, 0.86F, -4.88F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-10.05F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("chest", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(6.84F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(7.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5.83F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.45F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-0.72F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-1.44F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-54.53F, 10.83F, -27.31F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-59.16F, 11.31F, -31.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-25.16F, 5.71F, -31.51F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.74F, -5.71F, -25.51F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(9.1F, -7.94F, -23.66F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(10.8F, -6.15F, -23.46F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.41F, 3.55F, -24.56F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-8.47F, 11.95F, -25.72F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0, -2, -4), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-23.32F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-27.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-28.42F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-29.19F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-29.51F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-29.49F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-27.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(18.1F, 18.54F, 20.21F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(18.67F, 18.75F, 24.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(44.5231F, 11.083F, 28.1201F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(38.7F, 7.25F, 29.88F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-11.76F, -16.18F, 34.24F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-16.69F, -22.6F, 34.67F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-16.66F, -23.24F, 34.23F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-13.58F, -21.66F, 31.94F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(19.61F, 8.43F, 27.52F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0, 0, -2), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-7.86F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-19.14F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-29.28F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-32.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-33.47F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-34.61F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-32.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.41F, -4.51F, 5.23F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(9.31F, 0.88F, 2.37F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(6.2485F, 1.7026F, 5.7416F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(16.78F, -10.09F, -15.18F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(19.41F, -8.3F, -13.26F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(19.41F, 6.42F, 5.06F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(15.75F, 33.11F, 39.23F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(14.48F, 39.4F, 48.2F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(13.99F, 36.94F, 47.04F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(13.51F, 18.85F, 30.04F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-7.32F, 11.34F, 13.14F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(5.8F, -5.99F, 7.45F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(7.76F, -9.94F, 4.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(12.13F, -23.47F, -12.97F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(18.3F, -44.33F, -43.81F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(19.91F, -48.09F, -50.92F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(19.67F, -43.74F, -47.7F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(16.21F, -21.77F, -25.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-7.32F, -11.34F, -13.14F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(8.36F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(12.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(24.81F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(43.49F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(47.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(45.76F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(32.92F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(5.08F, -14.77F, -5.66F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(9.28F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(10, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(6.48F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-1.14F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-2.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-2.25F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-0.93F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(12.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-27.8F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-27.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-8.06F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(31.12F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(42.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(43.77F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(43.88F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(22.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-42.96F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-45, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-25.69F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(16.83F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(33.51F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(28.47F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-19.4F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(31.76F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(35, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(26.48F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(6.15F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(2.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(3.18F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(6.74F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(42.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(4.28F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(7.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(19.07F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(37, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(40, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(39.42F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(36.16F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(2.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0, 0, -12.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0, 0, -20), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, -1.95F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0, 0, 18.74F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7083F, KeyframeAnimations.degreeVec(0, 0, 20), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0, 0, 11.36F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(0, 0, -20), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lWing01Membrane", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(5, -11.86F, 17.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(5.15F, -11.83F, 19.28F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(2.48F, -3.21F, 13.33F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-3.14F, 13.4F, -0.38F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-4.69F, 16.67F, -3.73F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-4.63F, 13.63F, -2.53F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-2.3F, -2.1F, 6.31F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-1, 3.13F, -1.71F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0, 0, 12.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0, 0, 20), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 1.95F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0, 0, -18.74F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7083F, KeyframeAnimations.degreeVec(0, 0, -20), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0, 0, -11.36F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(0, 0, 20), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("rWing01Membrane", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(5, 11.86F, -17.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(5.15F, 11.83F, -19.28F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(2.48F, 3.21F, -13.33F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-3.14F, -13.4F, 0.38F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-4.69F, -16.67F, 3.73F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-4.63F, -13.63F, 2.53F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-2.3F, 2.1F, -6.31F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-1, -3.13F, 1.71F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lips", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.1667F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.posVec(0, 1.25F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(0, 1.25F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lips", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1, 1, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.scaleVec(1, 0.6F, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.scaleVec(1, 0.6F, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.scaleVec(1, 1, 1), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition FLY = AnimationDefinition.Builder.withLength(2).looping()
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-10.05F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-11.05F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-11.78F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.05F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-11.78F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-11.05F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-10.05F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-9.05F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-8.32F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-8.05F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-8.32F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-9.05F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-10.05F, 1.29F, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("chest", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(22, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(21.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(21, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(19, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(18.27F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(18, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(18.27F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(19, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(21, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(21.73F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(22, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-12.47F, 11.95F, -30.05F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-11.93F, 11.95F, -30.72F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-10.47F, 11.95F, -30.05F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-8.47F, 11.95F, -28.22F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-6.47F, 11.95F, -25.72F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-5.01F, 11.95F, -23.22F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-4.47F, 11.95F, -21.39F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-5.01F, 11.95F, -20.72F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-6.47F, 11.95F, -21.39F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-8.47F, 11.95F, -23.22F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-10.47F, 11.95F, -25.72F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-11.93F, 11.95F, -28.22F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-12.47F, 11.95F, -30.05F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-27.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(16.78F, 8.43F, 31.85F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(15.75F, 8.43F, 32.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(15.75F, 8.43F, 31.85F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(16.78F, 8.43F, 30.02F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(18.57F, 8.43F, 27.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(20.65F, 8.43F, 25.02F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(22.44F, 8.43F, 23.19F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(23.47F, 8.43F, 22.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(23.47F, 8.43F, 23.19F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(22.44F, 8.43F, 25.02F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(20.65F, 8.43F, 27.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(18.57F, 8.43F, 30.02F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(16.78F, 8.43F, 31.85F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-32.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(13, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(12.6F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(11.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(10, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(8.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(7.4F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(7, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(7.4F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(8.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(10, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(11.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(12.6F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(13, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, -1, -2), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-7.32F, 8.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-7.32F, 8.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-7.32F, 53.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.32F, 8.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-7.32F, 8.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-7.32F, 53.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-7.32F, 8.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-7.32F, 8.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-7.32F, 53.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-7.32F, 8.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-7.32F, 8.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-7.32F, 53.84F, 13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-7.32F, 8.84F, 13.14F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, -5.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0, 0, -39.32F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0, 0, -14.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, -5.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0, 0, -39.32F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0, 0, -14.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(0, 0, -5.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0, 0, -39.32F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0, 0, -14.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(0, 0, -5.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0, 0, -39.32F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0, 0, -14.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(0, 0, -5.86F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lWing01Membrane", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1, 3.13F, -1.71F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-7.32F, -8.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-7.32F, -8.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-7.32F, -53.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.32F, -8.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-7.32F, -8.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-7.32F, -53.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-7.32F, -8.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-7.32F, -8.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-7.32F, -53.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-7.32F, -8.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-7.32F, -8.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-7.32F, -53.84F, -13.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-7.32F, -8.84F, -13.14F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 5.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0, 0, 39.32F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0, 0, 14.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 5.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0, 0, 39.32F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0, 0, 14.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(0, 0, 5.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0, 0, 39.32F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0, 0, 14.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(0, 0, 5.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0, 0, 39.32F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0, 0, 14.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(0, 0, 5.86F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("rWing01Membrane", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-1, -3.13F, 1.71F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0.75F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.08F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.75F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(2.58F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(5.08F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(7.58F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(9.41F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(10.08F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(9.41F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(7.58F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(5.08F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(2.58F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(0.75F, -14.77F, 0.48F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(16.33F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(14.21F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(11.63F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(9.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(7.8F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(7.58F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(8.67F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(10.79F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(13.37F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(15.71F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(17.2F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(17.42F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(16.33F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(22.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(-23.73F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-24.4F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-23.73F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-21.9F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-19.4F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-16.9F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(-15.07F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-14.4F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-15.07F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-16.9F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-19.4F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-21.9F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(-23.73F, 13.44F, 0.48F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foreleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(46.33F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(44.21F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(41.63F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(39.29F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6667F, KeyframeAnimations.degreeVec(37.8F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.degreeVec(37.58F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1, KeyframeAnimations.degreeVec(38.67F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1667F, KeyframeAnimations.degreeVec(40.79F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3333F, KeyframeAnimations.degreeVec(43.37F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(45.71F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6667F, KeyframeAnimations.degreeVec(47.2F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8333F, KeyframeAnimations.degreeVec(47.42F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2, KeyframeAnimations.degreeVec(46.33F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(2.5F, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition LEFT_ATTACK = AnimationDefinition.Builder.withLength(0.5F)
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-12.8802F, -62.8844F, -23.9225F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(-48.1604F, -49.4071F, -18.5941F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-46.2627F, -62.3891F, -48.9984F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-56.755F, -23.9214F, 11.2722F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-60.5117F, 28.8721F, 15.6735F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-41.5571F, 22.71F, 14.0863F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-8.1F, 0.78F, -1.44F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(1.5F, 1, 2), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(1.37F, 1.25F, 2), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.375F, KeyframeAnimations.posVec(-3, 2, -2), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(-15.0263F, 13.9795F, 14.7481F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-17.89F, 7.64F, 11.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.1593F, 21.2842F, 37.6978F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-5.793F, 20.0209F, 37.9595F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-0.06F, 0.92F, 2.31F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_forearm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.posVec(1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(-7.87F, -14.17F, 0.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-13.83F, -18.49F, 4.95F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(3.13F, 21.49F, -11.4F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(2.82F, 19.36F, -10.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.31F, 2.13F, -1.13F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-7.49F, -9.1F, 10.83F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-3.1397F, 36.1783F, 16.6094F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-0.25F, 0.22F, 1.08F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(0.33F, -0.42F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(-2.81F, -0.24F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-22.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-18.9507F, -2.7973F, -4.1459F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-13.5106F, 4.8109F, 8.777F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-1.42F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0, -12.5F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-0.8307F, 7.0379F, -7.4592F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(1, 0, 2), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-1.45F, -1, 2), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(8.35F, -21.08F, 0.05F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(11.13F, -27.7F, 0.66F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(10.54F, -26.14F, 0.71F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(1.19F, -2.94F, 0.08F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-7.58F, 13.01F, -4.63F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-10.27F, 17.37F, -6.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(1.42F, -5.06F, -1.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.86F, -2.14F, 0.06F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0, 0, -32.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(0, 0, -28.46F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0, 0, 2.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(0, 0, 4.19F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0, 0, 3.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0, 0, 2.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lowerJaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(42.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(42.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("cloth", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(13.86F, -0.37F, 1.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(14.28F, -0.42F, 1.7F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-11, 2.38F, 12.24F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-11.14F, 2.77F, 13.38F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-12.71F, 1.69F, 5.38F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(-12.03F, 1.33F, 3.47F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-1.84F, 0.18F, 0.37F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-2.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-2.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(2.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.52F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(7.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-0.27F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lips", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.posVec(0, 1.25F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0, 1.25F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lips", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0, KeyframeAnimations.scaleVec(1, 1, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.scaleVec(1, 0.6F, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1, 0.6F, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.scaleVec(1, 1, 1), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition RIGHT_ATTACK = AnimationDefinition.Builder.withLength(0.5F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(-7.87F, 14.17F, -0.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-13.83F, 18.49F, -4.95F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(3.13F, -21.49F, 11.4F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(2.82F, -19.36F, 10.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.31F, -2.13F, 1.13F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-7.58F, -13.01F, 4.63F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-10.27F, -17.37F, 6.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(1.42F, 5.06F, 1.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.86F, 2.14F, -0.06F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(8.35F, 21.08F, -0.05F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(11.13F, 27.7F, -0.66F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(10.54F, 26.14F, -0.71F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(1.19F, 2.94F, -0.08F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("cloth", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(13.86F, 0.37F, -1.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(14.28F, 0.42F, -1.7F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-11, -2.38F, -12.24F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-11.14F, -2.77F, -13.38F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-12.71F, -1.69F, -5.38F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(-12.03F, -1.33F, -3.47F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-1.84F, -0.18F, -0.37F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(7.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-0.27F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-2.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-2.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(2.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.52F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-7.49F, 9.1F, -10.83F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-3.1397F, -36.1783F, -16.6094F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-0.25F, -0.22F, -1.08F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(0.33F, 0.42F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(2.81F, 0.24F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-22.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-18.9507F, 2.7973F, 4.1459F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-13.5106F, -4.8109F, -8.777F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-1.42F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-12.8802F, 62.8844F, 23.9225F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(-48.1604F, 49.4071F, 18.5941F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-46.2627F, 62.3891F, 48.9984F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-56.755F, 23.9214F, -11.2722F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-60.5117F, -28.8721F, -15.6735F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-41.5571F, -22.71F, -14.0863F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-8.1F, -0.78F, 1.44F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(1.5F, 1, 2), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(1.37F, 1.25F, 2), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.375F, KeyframeAnimations.posVec(3, 2, -2), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_forearm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(-15.0263F, -13.9795F, -14.7481F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-17.89F, -7.64F, -11.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.1593F, -21.2842F, -37.6978F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-5.793F, -20.0209F, -37.9595F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-0.06F, -0.92F, -2.31F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_forearm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2917F, KeyframeAnimations.posVec(-1, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0, 0, 32.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(0, 0, 28.46F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0, 0, -2.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(0, 0, -4.19F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0, 0, -3.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0, 0, -2.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0, 12.5F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.8307F, -7.0379F, 7.4592F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(-1, 0, 2), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(1.45F, -1, 2), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lips", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.posVec(0, 1.25F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0, 1.25F, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lips", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0, KeyframeAnimations.scaleVec(1, 1, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.125F, KeyframeAnimations.scaleVec(1, 0.6F, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1, 0.6F, 1), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.scaleVec(1, 1, 1), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("lowerJaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(42.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(42.5F, 0, 0), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
}
