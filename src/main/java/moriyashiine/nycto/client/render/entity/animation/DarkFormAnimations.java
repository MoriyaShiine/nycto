/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.animation;

import net.minecraft.client.render.entity.animation.AnimationDefinition;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class DarkFormAnimations {
	public static final AnimationDefinition IDLE = AnimationDefinition.Builder.create(2).looping()
			.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-2, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(2, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("body", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, -0.2F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0, -0.17F, 0.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0, -0.1F, 0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 0.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0, 0.1F, 0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0, 0.17F, 0.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 0.2F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createTranslationalVector(0, 0.17F, -0.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createTranslationalVector(0, 0.1F, -0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0, 0, -0.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createTranslationalVector(0, -0.1F, -0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createTranslationalVector(0, -0.17F, -0.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createTranslationalVector(0, -0.2F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("chest", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1.29F, 0, -0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-1.88F, 0, -0.64F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-1.97F, 0, -0.94F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-1.53F, 0, -0.98F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-0.68F, 0, -0.77F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.35F, 0, -0.34F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(1.29F, 0, 0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(1.88F, 0, 0.64F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(1.97F, 0, 0.94F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(1.53F, 0, 0.98F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(0.68F, 0, 0.77F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-0.35F, 0, 0.34F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-1.29F, 0, -0.17F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-0.64F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.94F, 0, 0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-0.98F, 0, 0.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-0.77F, 0, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-0.34F, 0, 0.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.17F, 0, 0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(0.64F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.94F, 0, -0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(0.98F, 0, -0.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.77F, 0, -1), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(0.34F, 0, -0.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-0.17F, 0, -0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-0.64F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(2, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-2, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(2.25F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.61F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-1.2F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-2.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-3.45F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-3.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-2.25F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-0.61F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(1.2F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(2.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(3.45F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(3.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(2.25F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foot", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(2.25F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.61F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-1.2F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-2.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-3.45F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-3.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-2.25F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-0.61F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(1.2F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(2.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(3.45F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(3.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(2.25F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foot", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(1.93F, 0, 3), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.52F, 0, 2.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-1.03F, 0, 1.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-2.3F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-2.95F, 0, -1.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-2.82F, 0, -2.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-1.93F, 0, -3), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-0.52F, 0, -2.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(1.03F, 0, -1.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(2.3F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(2.95F, 0, 1.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(2.82F, 0, 2.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(1.93F, 0, 3), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1.93F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-2.82F, 0, 1.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-2.95F, 0, 2.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-2.3F, 0, 3), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-1.03F, 0, 2.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.52F, 0, 1.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(1.93F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(2.82F, 0, -1.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(2.95F, 0, -2.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(2.3F, 0, -3), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(1.03F, 0, -2.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-0.52F, 0, -1.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-1.93F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("cloth", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(2, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-2, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(1.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(2, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition IDLE_SNEAK = AnimationDefinition.Builder.create(2).looping()
			.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(20.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(21, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(21.37F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(21.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(21.37F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(21, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(20.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(20, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(19.63F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(19.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(19.63F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(20, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(20.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("body", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, -1.1F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0, -1.09F, 0.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0, -1.05F, 0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, -1, 0.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0, -0.95F, 0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0, -0.91F, 0.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, -0.9F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createTranslationalVector(0, -0.91F, -0.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createTranslationalVector(0, -0.95F, -0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0, -1, -0.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createTranslationalVector(0, -1.05F, -0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createTranslationalVector(0, -1.09F, -0.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createTranslationalVector(0, -1.1F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("chest", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(14.83F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(14.62F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(14.51F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(14.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(14.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(14.91F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(15.17F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(15.38F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(15.49F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(15.47F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(15.32F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(15.09F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(14.83F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-9.68F, 0, -0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-9.53F, 0, -0.64F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-9.51F, 0, -0.94F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-9.62F, 0, -0.98F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-9.83F, 0, -0.77F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-10.09F, 0, -0.34F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-10.32F, 0, 0.17F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-10.47F, 0, 0.64F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-10.49F, 0, 0.94F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-10.38F, 0, 0.98F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-10.17F, 0, 0.77F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-9.91F, 0, 0.34F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-9.68F, 0, -0.17F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-21.82F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-20.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-20.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-20.62F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-21.21F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-22.15F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-23.18F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-24.03F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-24.47F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-24.38F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-23.79F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-22.85F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-21.82F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-9.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-9.53F, 0, 0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-9.51F, 0, 0.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-9.62F, 0, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-9.83F, 0, 0.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-10.09F, 0, 0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-10.32F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-10.47F, 0, -0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-10.49F, 0, -0.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-10.38F, 0, -1), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-10.17F, 0, -0.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-9.91F, 0, -0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-9.68F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-21.82F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-20.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-20.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-20.62F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-21.21F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-22.15F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-23.18F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-24.03F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-24.47F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-24.38F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-23.79F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-22.85F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-21.82F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-25, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-24, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-23.27F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-23, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-23.27F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-24, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-25, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-26, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-26.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-27, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-26.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-26, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-25, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, -1, -2), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(2.25F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.61F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-1.2F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-2.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-3.45F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-3.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-2.25F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-0.61F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(1.2F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(2.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(3.45F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(3.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(2.25F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foot", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(2.25F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.61F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-1.2F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-2.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-3.45F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-3.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-2.25F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-0.61F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(1.2F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(2.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(3.45F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(3.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(2.25F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foot", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(1.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.35F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-0.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-1.53F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-1.97F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-1.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-1.29F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-9, 20, 18), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-8.75F, 20, 18.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-8.57F, 20, 18.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-8.5F, 20, 19), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-8.57F, 20, 18.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-8.75F, 20, 18.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-9, 20, 18), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-9.25F, 20, 17.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-9.43F, 20, 17.13F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-9.5F, 20, 17), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-9.43F, 20, 17.13F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-9.25F, 20, 17.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-9, 20, 18), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-9, -20, -17), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-8.75F, -20, -17.13F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-8.57F, -20, -17.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-8.5F, -20, -18), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-8.57F, -20, -18.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-8.75F, -20, -18.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-9, -20, -19), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-9.25F, -20, -18.87F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-9.43F, -20, -18.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-9.5F, -20, -18), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-9.43F, -20, -17.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-9.25F, -20, -17.13F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-9, -20, -17), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("cloth", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-10.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-10.77F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-11.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-12.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-13.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-14.23F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-14.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-14.23F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-13.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-12.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-11.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-10.77F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-10.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_wing2", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-12, -5, 96), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_wing2", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-12, 5, -96), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition WALK = AnimationDefinition.Builder.create(1).looping()
			.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(5, 2.5F, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(5, 5, 0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(5, 2.5F, -0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(5, -2.5F, -1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(5, -5, -0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(5, -2.5F, 0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(5, 2.5F, 1), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-12.88F, 6.48F, -6.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-19.72F, 6.48F, -6.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-6.87F, 6.48F, -6.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(12.83F, 6.48F, -6.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(19.67F, 6.48F, -6.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(6.81F, 6.48F, -6.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-12.88F, 6.48F, -6.27F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, -3), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 0, -3), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(4.33F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-4.33F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-4.33F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(4.33F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(4.33F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0.01F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(17.33F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(17.33F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.01F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-17.31F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-17.31F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(0.01F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, -3), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-4.99F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-2.49F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(2.51F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(5.01F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(2.51F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-2.49F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-4.99F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(5, -2.5F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(5, -5, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(5, -2.5F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(5, 2.5F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(5, 5, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(5, 2.5F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(5, -2.5F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(3.47F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(18.79F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(15.32F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-3.47F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-18.79F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-15.32F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(3.47F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(17.98F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(16.58F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-1.4F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-17.98F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-16.58F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.4F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(17.98F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foreleg", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0.1F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0, 0.93F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0, 1.83F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 1.9F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0, 1.07F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0, 0.17F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 0.1F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-3.47F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-18.79F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-15.32F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(3.47F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(18.79F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(15.32F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-3.47F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-16.58F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-17.98F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-1.4F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(16.58F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(17.98F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.4F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-16.58F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foreleg", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 1.9F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0, 1.83F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0, 0.93F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0.1F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0, 0.17F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0, 1.07F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 1.9F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-0.43F, -3.21F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-1.92F, -4.92F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(2.35F, -1.71F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-0.43F, 3.21F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-1.92F, 4.92F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(2.35F, 1.71F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-0.43F, -3.21F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1.61F, -3.21F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.86F, -4.92F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(2.46F, -1.71F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-1.61F, 3.21F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-0.86F, 4.92F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(2.46F, 1.71F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-1.61F, -3.21F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("cloth", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(6.43F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(9.85F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(3.42F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-6.43F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-9.85F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-3.42F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(6.43F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition RUN = AnimationDefinition.Builder.create(1).looping()
			.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(19.49F, -3, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(14.65F, -6, 0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(10.85F, -3, -0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(19.49F, 3, -1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(14.65F, 6, -0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(10.85F, 3, 0.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(19.49F, -3, 1), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-0.38F, 8.98F, -16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-7.22F, 8.98F, -16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(5.63F, 8.98F, -16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(25.33F, 8.98F, -16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(32.17F, 8.98F, -16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(19.31F, 8.98F, -16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-0.38F, 8.98F, -16.27F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-42.49F, 7.71F, -4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-51.15F, 7.71F, -4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-51.15F, 7.71F, -4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-42.49F, 7.71F, -4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-33.83F, 7.71F, -4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-33.83F, 7.71F, -4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-42.49F, 7.71F, -4.67F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(25.33F, -8.98F, 16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(32.17F, -8.98F, 16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(19.31F, -8.98F, 16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-0.38F, -8.98F, 16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-7.22F, -8.98F, 16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(5.63F, -8.98F, 16.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(25.33F, -8.98F, 16.27F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-42.49F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-33.83F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-33.83F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-42.49F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-51.15F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-51.15F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-42.49F, -7.71F, 4.67F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-22, 4.95F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-11.5F, 6.76F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-11.5F, 1.81F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-22, -4.95F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-11.5F, -6.76F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-11.5F, -1.81F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-22, 4.95F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-3.21F, 6.43F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-1.71F, 9.85F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(4.92F, 3.42F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-3.21F, -6.43F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-1.71F, -9.85F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(4.92F, -3.42F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-3.21F, 6.43F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-3.21F, 1.74F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-1.71F, 9.4F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(4.92F, 7.66F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-3.21F, -1.74F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-1.71F, -9.4F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(4.92F, -7.66F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-3.21F, 1.74F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(6.95F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(37.59F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(30.64F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-6.95F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-37.59F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-30.64F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(6.95F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_leg_upper", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, -1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 0, -1), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-19.39F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(6.32F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(45.71F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(59.39F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(33.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-5.71F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-19.39F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foreleg", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 1.41F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0, 0.5F, -0.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0, 1.5F, -1.93F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 2, -1.41F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0, 1.5F, 0.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0, 0.5F, 1.93F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 0, 1.41F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-6.95F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-37.59F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-30.64F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(6.95F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(37.59F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(30.64F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-6.95F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_leg_upper", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, -1), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 0, 1), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(59.39F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(33.68F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-5.71F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-19.39F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(6.32F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(45.71F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(59.39F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foreleg", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 2, -1.41F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0, 1.5F, 0.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0, 0.5F, 1.93F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 1.41F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0, 0.5F, -0.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0, 1.5F, -1.93F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 2, -1.41F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("cloth", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-10, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(5, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(5, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-10, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(5, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(5, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-10, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lips", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 1.25F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 1.25F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lips", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0, AnimationHelper.createScalingVector(1, 0.6F, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createScalingVector(1, 0.6F, 1), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition JUMP = AnimationDefinition.Builder.create(1)
			.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(13.19F, 6.51F, 12), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(15.51F, 7.23F, 12.82F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(17.14F, 6, 8.36F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(16.52F, 2.47F, -1.83F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(14.95F, 1.29F, -4.83F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(13.84F, 1.05F, -5.24F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(10.63F, 0.86F, -4.88F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-10.05F, 1.29F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("chest", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(6.84F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(7.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(5.83F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(1.45F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-0.72F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-1.44F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(20, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-54.53F, 10.83F, -27.31F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-59.16F, 11.31F, -31.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-25.16F, 5.71F, -31.51F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-2.74F, -5.71F, -25.51F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(9.1F, -7.94F, -23.66F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(10.8F, -6.15F, -23.46F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.41F, 3.55F, -24.56F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-8.47F, 11.95F, -25.72F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0, -2, -4), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-23.32F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-27.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-28.42F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-29.19F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-29.51F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-29.49F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-27.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(18.1F, 18.54F, 20.21F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(18.67F, 18.75F, 24.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(44.5231F, 11.083F, 28.1201F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(38.7F, 7.25F, 29.88F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-11.76F, -16.18F, 34.24F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-16.69F, -22.6F, 34.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-16.66F, -23.24F, 34.23F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-13.58F, -21.66F, 31.94F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(19.61F, 8.43F, 27.52F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0, 0, -2), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-7.86F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-19.14F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-29.28F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-32.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-33.47F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-34.61F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-32.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-10.41F, -4.51F, 5.23F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(9.31F, 0.88F, 2.37F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(6.2485F, 1.7026F, 5.7416F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(16.78F, -10.09F, -15.18F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(19.41F, -8.3F, -13.26F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(19.41F, 6.42F, 5.06F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(15.75F, 33.11F, 39.23F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(14.48F, 39.4F, 48.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(13.99F, 36.94F, 47.04F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(13.51F, 18.85F, 30.04F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-7.32F, 11.34F, 13.14F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(5.8F, -5.99F, 7.45F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(7.76F, -9.94F, 4.6F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(12.13F, -23.47F, -12.97F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(18.3F, -44.33F, -43.81F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(19.91F, -48.09F, -50.92F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(19.67F, -43.74F, -47.7F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(16.21F, -21.77F, -25.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-7.32F, -11.34F, -13.14F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(8.36F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(12.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(24.81F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(43.49F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(47.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(45.76F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(32.92F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(5.08F, -14.77F, -5.66F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(9.28F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(10, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(6.48F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-1.14F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-2.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-2.25F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-0.93F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(12.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foot", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-27.8F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-27.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-8.06F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(31.12F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(42.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(43.77F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(43.88F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(22.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-42.96F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-45, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-25.69F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(16.83F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(30, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(33.51F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(28.47F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-19.4F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(31.76F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(35, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(26.48F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(6.15F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(2.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(3.18F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(6.74F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(42.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foot", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(4.28F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(7.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(19.07F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(37, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(40, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(39.42F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(36.16F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(2.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_wing2", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0, 0, -12.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0, 0, -20), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, -1.95F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0, 0, 18.74F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7083F, AnimationHelper.createRotationalVector(0, 0, 20), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0, 0, 11.36F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(0, 0, -20), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lWing01Membrane", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(5, -11.86F, 17.82F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(5.15F, -11.83F, 19.28F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(2.48F, -3.21F, 13.33F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-3.14F, 13.4F, -0.38F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-4.69F, 16.67F, -3.73F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-4.63F, 13.63F, -2.53F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-2.3F, -2.1F, 6.31F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-1, 3.13F, -1.71F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_wing2", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0, 0, 12.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0, 0, 20), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 1.95F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0, 0, -18.74F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7083F, AnimationHelper.createRotationalVector(0, 0, -20), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0, 0, -11.36F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(0, 0, 20), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rWing01Membrane", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(5, 11.86F, -17.82F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(5.15F, 11.83F, -19.28F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(2.48F, 3.21F, -13.33F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-3.14F, -13.4F, 0.38F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-4.69F, -16.67F, 3.73F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-4.63F, -13.63F, 2.53F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-2.3F, 2.1F, -6.31F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-1, -3.13F, 1.71F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lips", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0, 1.25F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0, 1.25F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lips", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.1667F, AnimationHelper.createScalingVector(1, 1, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createScalingVector(1, 0.6F, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createScalingVector(1, 0.6F, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createScalingVector(1, 1, 1), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition FLY = AnimationDefinition.Builder.create(2).looping()
			.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-10.05F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-11.05F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-11.78F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-12.05F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-11.78F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-11.05F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-10.05F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-9.05F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-8.32F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-8.05F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-8.32F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-9.05F, 1.29F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-10.05F, 1.29F, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("chest", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(22, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(21.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(21, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(20, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(19, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(18.27F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(18, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(18.27F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(19, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(20, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(21, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(21.73F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(22, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-12.47F, 11.95F, -30.05F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-11.93F, 11.95F, -30.72F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-10.47F, 11.95F, -30.05F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-8.47F, 11.95F, -28.22F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-6.47F, 11.95F, -25.72F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-5.01F, 11.95F, -23.22F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-4.47F, 11.95F, -21.39F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-5.01F, 11.95F, -20.72F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-6.47F, 11.95F, -21.39F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-8.47F, 11.95F, -23.22F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-10.47F, 11.95F, -25.72F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-11.93F, 11.95F, -28.22F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-12.47F, 11.95F, -30.05F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-27.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(16.78F, 8.43F, 31.85F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(15.75F, 8.43F, 32.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(15.75F, 8.43F, 31.85F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(16.78F, 8.43F, 30.02F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(18.57F, 8.43F, 27.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(20.65F, 8.43F, 25.02F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(22.44F, 8.43F, 23.19F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(23.47F, 8.43F, 22.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(23.47F, 8.43F, 23.19F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(22.44F, 8.43F, 25.02F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(20.65F, 8.43F, 27.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(18.57F, 8.43F, 30.02F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(16.78F, 8.43F, 31.85F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-32.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(13, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(12.6F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(11.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(10, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(8.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(7.4F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(7, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(7.4F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(8.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(10, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(11.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(12.6F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(13, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, -1, -2), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-7.32F, 8.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-7.32F, 8.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-7.32F, 53.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-7.32F, 8.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-7.32F, 8.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-7.32F, 53.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-7.32F, 8.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-7.32F, 8.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-7.32F, 53.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-7.32F, 8.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-7.32F, 8.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-7.32F, 53.84F, 13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-7.32F, 8.84F, 13.14F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_wing2", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, -5.86F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0, 0, -39.32F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0, 0, -14.82F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, -5.86F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0, 0, -39.32F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0, 0, -14.82F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(0, 0, -5.86F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0, 0, -39.32F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(0, 0, -14.82F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0, 0, -5.86F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(0, 0, -39.32F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(0, 0, -14.82F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(0, 0, -5.86F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lWing01Membrane", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1, 3.13F, -1.71F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-7.32F, -8.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-7.32F, -8.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-7.32F, -53.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-7.32F, -8.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-7.32F, -8.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-7.32F, -53.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-7.32F, -8.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-7.32F, -8.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-7.32F, -53.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-7.32F, -8.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-7.32F, -8.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-7.32F, -53.84F, -13.14F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-7.32F, -8.84F, -13.14F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_wing2", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 5.86F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0, 0, 39.32F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0, 0, 14.82F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 5.86F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0, 0, 39.32F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0, 0, 14.82F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(0, 0, 5.86F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0, 0, 39.32F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(0, 0, 14.82F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0, 0, 5.86F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(0, 0, 39.32F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(0, 0, 14.82F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(0, 0, 5.86F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rWing01Membrane", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-1, -3.13F, 1.71F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0.75F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.08F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.75F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(2.58F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(5.08F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(7.58F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(9.41F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(10.08F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(9.41F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(7.58F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(5.08F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(2.58F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(0.75F, -14.77F, 0.48F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(16.33F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(14.21F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(11.63F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(9.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(7.8F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(7.58F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(8.67F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(10.79F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(13.37F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(15.71F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(17.2F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(17.42F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(16.33F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_foot", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(22.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_leg_upper", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(-23.73F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-24.4F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-23.73F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-21.9F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-19.4F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-16.9F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(-15.07F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-14.4F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-15.07F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-16.9F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-19.4F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-21.9F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(-23.73F, 13.44F, 0.48F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foreleg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(46.33F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(44.21F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(41.63F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(39.29F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(37.8F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(37.58F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1, AnimationHelper.createRotationalVector(38.67F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(40.79F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.3333F, AnimationHelper.createRotationalVector(43.37F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(45.71F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.6667F, AnimationHelper.createRotationalVector(47.2F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(1.8333F, AnimationHelper.createRotationalVector(47.42F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(2, AnimationHelper.createRotationalVector(46.33F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_foot", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(2.5F, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition LEFT_ATTACK = AnimationDefinition.Builder.create(0.5F)
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-12.8802F, -62.8844F, -23.9225F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(-48.1604F, -49.4071F, -18.5941F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-46.2627F, -62.3891F, -48.9984F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-56.755F, -23.9214F, 11.2722F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-60.5117F, 28.8721F, 15.6735F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-41.5571F, 22.71F, 14.0863F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-8.1F, 0.78F, -1.44F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(1.5F, 1, 2), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createTranslationalVector(1.37F, 1.25F, 2), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createTranslationalVector(-3, 2, -2), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(-15.0263F, 13.9795F, 14.7481F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-17.89F, 7.64F, 11.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-10.1593F, 21.2842F, 37.6978F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-5.793F, 20.0209F, 37.9595F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-0.06F, 0.92F, 2.31F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_forearm", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(-7.87F, -14.17F, 0.93F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-13.83F, -18.49F, 4.95F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(3.13F, 21.49F, -11.4F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createRotationalVector(2.82F, 19.36F, -10.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.31F, 2.13F, -1.13F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-7.49F, -9.1F, 10.83F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-3.1397F, 36.1783F, 16.6094F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-0.25F, 0.22F, 1.08F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.33F, -0.42F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(-2.81F, -0.24F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-22.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-18.9507F, -2.7973F, -4.1459F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-13.5106F, 4.8109F, 8.777F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-1.42F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0, -12.5F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-0.8307F, 7.0379F, -7.4592F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(1, 0, 2), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createTranslationalVector(-1.45F, -1, 2), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(8.35F, -21.08F, 0.05F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(11.13F, -27.7F, 0.66F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(10.54F, -26.14F, 0.71F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(1.19F, -2.94F, 0.08F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-7.58F, 13.01F, -4.63F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-10.27F, 17.37F, -6.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(1.42F, -5.06F, -1.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.86F, -2.14F, 0.06F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_hand", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0, 0, -32.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(0, 0, -28.46F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(0, 0, 2.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(0, 0, 4.19F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0, 0, 3.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0, 0, 2.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lowerJaw", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(42.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(42.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("cloth", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(13.86F, -0.37F, 1.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(14.28F, -0.42F, 1.7F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-11, 2.38F, 12.24F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-11.14F, 2.77F, 13.38F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-12.71F, 1.69F, 5.38F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createRotationalVector(-12.03F, 1.33F, 3.47F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-1.84F, 0.18F, 0.37F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-2.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-2.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(2.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.52F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(5, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(7.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-0.27F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lips", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0, 1.25F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0, 1.25F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lips", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0, AnimationHelper.createScalingVector(1, 1, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createScalingVector(1, 0.6F, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createScalingVector(1, 0.6F, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createScalingVector(1, 1, 1), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition RIGHT_ATTACK = AnimationDefinition.Builder.create(0.5F)
			.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(-7.87F, 14.17F, -0.93F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-13.83F, 18.49F, -4.95F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(3.13F, -21.49F, 11.4F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createRotationalVector(2.82F, -19.36F, 10.27F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.31F, -2.13F, 1.13F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-7.58F, -13.01F, 4.63F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-10.27F, -17.37F, 6.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(1.42F, 5.06F, 1.52F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.86F, 2.14F, -0.06F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(8.35F, 21.08F, -0.05F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(11.13F, 27.7F, -0.66F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(10.54F, 26.14F, -0.71F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(1.19F, 2.94F, -0.08F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("cloth", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(13.86F, 0.37F, -1.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(14.28F, 0.42F, -1.7F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-11, -2.38F, -12.24F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-11.14F, -2.77F, -13.38F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-12.71F, -1.69F, -5.38F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createRotationalVector(-12.03F, -1.33F, -3.47F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-1.84F, -0.18F, -0.37F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(5, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(7.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-0.27F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-2.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-2.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(2.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.52F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-7.49F, 9.1F, -10.83F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-3.1397F, -36.1783F, -16.6094F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-0.25F, -0.22F, -1.08F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_arm", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.33F, 0.42F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(2.81F, 0.24F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("left_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-22.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-18.9507F, 2.7973F, 4.1459F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-13.5106F, -4.8109F, -8.777F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-1.42F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-12.8802F, 62.8844F, 23.9225F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(-48.1604F, 49.4071F, 18.5941F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-46.2627F, 62.3891F, 48.9984F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-56.755F, 23.9214F, -11.2722F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-60.5117F, -28.8721F, -15.6735F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-41.5571F, -22.71F, -14.0863F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-8.1F, -0.78F, 1.44F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_arm", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(1.5F, 1, 2), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createTranslationalVector(1.37F, 1.25F, 2), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createTranslationalVector(3, 2, -2), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_forearm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(-15.0263F, -13.9795F, -14.7481F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-17.89F, -7.64F, -11.1F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-10.1593F, -21.2842F, -37.6978F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-5.793F, -20.0209F, -37.9595F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-0.06F, -0.92F, -2.31F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_forearm", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(-1, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_hand", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0, 0, 32.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(0, 0, 28.46F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(0, 0, -2.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(0, 0, -4.19F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0, 0, -3.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0, 0, -2.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0, 12.5F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.8307F, -7.0379F, 7.4592F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(-1, 0, 2), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createTranslationalVector(1.45F, -1, 2), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lips", new Transformation(Transformation.Targets.MOVE_ORIGIN,
					new Keyframe(0, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0, 1.25F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0, 1.25F, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lips", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0, AnimationHelper.createScalingVector(1, 1, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createScalingVector(1, 0.6F, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createScalingVector(1, 0.6F, 1), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createScalingVector(1, 1, 1), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("lowerJaw", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(42.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(42.5F, 0, 0), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0, 0, 0), Transformation.Interpolations.LINEAR)
			))
			.build();
}
