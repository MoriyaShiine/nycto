/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.model;

import moriyashiine.nycto.client.render.entity.animation.DarkFormAnimations;
import moriyashiine.nycto.client.render.entity.state.DarkFormEntityRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

public class DarkFormEntityModel extends EntityModel<DarkFormEntityRenderState> implements ModelWithArms {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Nycto.id("dark_form"), "main");

	private final ModelPart head;
	private final ModelPart[] leftHeldItemTranslations, rightHeldItemTranslations;

	public final ModelPart leftArm, rightArm;

	public final Animation idleAnimation;
	public final Animation idleSneakAnimation;
	public final Animation walkAnimation;
	public final Animation runAnimation;
	public final Animation jumpAnimation;
	public final Animation flyAnimation;
	public final Animation leftAttackAnimation;
	public final Animation rightAttackAnimation;

	public DarkFormEntityModel(ModelPart root) {
		super(root);
		head = root.getChild("body").getChild("head");
		leftArm = root.getChild("left_arm");
		rightArm = root.getChild("right_arm");

		leftHeldItemTranslations = new ModelPart[]{root, leftArm, leftArm.getChild("left_forearm"), leftArm.getChild("left_forearm").getChild("left_hand")};
		rightHeldItemTranslations = new ModelPart[]{root, rightArm, rightArm.getChild("right_forearm"), rightArm.getChild("right_forearm").getChild("right_hand")};

		idleAnimation = DarkFormAnimations.IDLE.createAnimation(root);
		idleSneakAnimation = DarkFormAnimations.IDLE_SNEAK.createAnimation(root);
		walkAnimation = DarkFormAnimations.WALK.createAnimation(root);
		runAnimation = DarkFormAnimations.RUN.createAnimation(root);
		jumpAnimation = DarkFormAnimations.JUMP.createAnimation(root);
		flyAnimation = DarkFormAnimations.FLY.createAnimation(root);
		leftAttackAnimation = DarkFormAnimations.LEFT_ATTACK.createAnimation(root);
		rightAttackAnimation = DarkFormAnimations.RIGHT_ATTACK.createAnimation(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(102, 20).cuboid(-3.5F, -6, -3, 7, 9, 6, Dilation.NONE), ModelTransform.origin(0, 3.7382F, 1.5058F));
		ModelPartData chest = body.addChild("chest", ModelPartBuilder.create(), ModelTransform.origin(0, -4.75F, -1));
		chest.addChild("cube_r1", ModelPartBuilder.create().uv(92, 0).cuboid(-5, -4, -4.5F, 10, 10, 8, Dilation.NONE), ModelTransform.of(0, -6, 0, 0.2182F, 0, 0));
		ModelPartData left_wing1 = chest.addChild("left_wing1", ModelPartBuilder.create().uv(108, 67).cuboid(-2, -5, -1, 3, 6, 4, Dilation.NONE), ModelTransform.of(3.75F, -7.75F, 2, -1.0036F, 0.2182F, -0.0873F));
		ModelPartData left_wing2 = left_wing1.addChild("left_wing2", ModelPartBuilder.create().uv(84, 58).cuboid(0, -14, -2, 1, 21, 21, Dilation.NONE), ModelTransform.of(-0.5F, -5.25F, 0.75F, 0.6109F, 0, 0.3054F));
		left_wing2.addChild("left_membranewing", ModelPartBuilder.create().uv(68, 73).cuboid(0.25F, -14, -2, 0, 25, 30, Dilation.NONE), ModelTransform.of(0.25F, 0, 0, -0.1309F, 0, 0));
		left_wing1.addChild("lWing01Membrane", ModelPartBuilder.create().uv(36, 101).cuboid(0, -13, -1, 0, 13, 14, Dilation.NONE), ModelTransform.of(-0.75F, -1, 3, -1.0472F, 0, 0));
		ModelPartData right_wing1 = chest.addChild("right_wing1", ModelPartBuilder.create().uv(108, 67).mirrored().cuboid(-1, -5, -1, 3, 6, 4, Dilation.NONE).mirrored(false), ModelTransform.of(-3.75F, -7.75F, 2, -1.0036F, -0.2182F, 0.0873F));
		ModelPartData right_wing2 = right_wing1.addChild("right_wing2", ModelPartBuilder.create().uv(84, 58).mirrored().cuboid(-1, -14, -2, 1, 21, 21, Dilation.NONE).mirrored(false), ModelTransform.of(0.5F, -5.25F, 0.75F, 0.6109F, 0, -0.3054F));
		right_wing2.addChild("right_membranewing", ModelPartBuilder.create().uv(68, 73).mirrored().cuboid(-0.25F, -14, -2, 0, 25, 30, Dilation.NONE).mirrored(false), ModelTransform.of(-0.25F, 0, 0, -0.1309F, 0, 0));
		right_wing1.addChild("rWing01Membrane", ModelPartBuilder.create().uv(36, 101).mirrored().cuboid(0, -13, -1, 0, 13, 14, Dilation.NONE).mirrored(false), ModelTransform.of(0.75F, -1, 3, -1.0472F, 0, 0));
		ModelPartData hair = chest.addChild("hair", ModelPartBuilder.create(), ModelTransform.of(0, -7.5F, 4.75F, 0.7854F, 0, 0));
		hair.addChild("lTruff01_r1", ModelPartBuilder.create().uv(57, 9).cuboid(1, 0, -0.5F, 8, 0, 9, Dilation.NONE), ModelTransform.of(0, -9, 0, -1.2217F, 0, 0.4363F));
		hair.addChild("rTruff01_r1", ModelPartBuilder.create().uv(59, 27).cuboid(-9, 0, -0.5F, 8, 0, 9, Dilation.NONE), ModelTransform.of(0, -9, 0, -1.2217F, 0, -0.5236F));
		ModelPartData innerTruff = hair.addChild("innerTruff", ModelPartBuilder.create(), ModelTransform.of(0, 1, -4.75F, -0.2618F, 0, 0));
		ModelPartData lowerTruff = innerTruff.addChild("lowerTruff", ModelPartBuilder.create(), ModelTransform.of(-0.5F, -7, 5.25F, -0.3927F, 0, 0));
		lowerTruff.addChild("lTruff03_r1", ModelPartBuilder.create().uv(60, 54).cuboid(1.9096F, 0.7376F, 0.0368F, 8, 0, 9, Dilation.NONE), ModelTransform.of(0, 0, 0, -1.309F, 0, 0.5236F));
		lowerTruff.addChild("rTruff03_r1", ModelPartBuilder.create().uv(60, 45).cuboid(-8.9096F, 0.7376F, 0.0368F, 8, 0, 9, Dilation.NONE), ModelTransform.of(0, 0, 0, -1.309F, 0, -0.5236F));
		ModelPartData upperTruff = innerTruff.addChild("upperTruff", ModelPartBuilder.create(), ModelTransform.of(0, 0, 3.5F, -0.2182F, 0, 0));
		upperTruff.addChild("lTruff02_r1", ModelPartBuilder.create().uv(57, 9).cuboid(1.4096F, 0.7816F, -0.0296F, 8, 0, 9, Dilation.NONE), ModelTransform.of(0, -9, 0, -1.2217F, 0, 0.5236F));
		upperTruff.addChild("rTruff02_r1", ModelPartBuilder.create().uv(59, 27).cuboid(-9.4096F, 0.7816F, -0.0296F, 8, 0, 9, Dilation.NONE), ModelTransform.of(0, -9, 0, -1.2217F, 0, -0.5236F));
		body.addChild("cloth", ModelPartBuilder.create().uv(74, 65).cuboid(-3, -1, 0.6F, 6, 11, 0, Dilation.NONE), ModelTransform.of(0, 1.75F, -3.75F, -0.2182F, 0, 0));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(86, 37).cuboid(-3.5F, -8, -6, 7, 8, 8, new Dilation(-0.01F)), ModelTransform.origin(0, -13.75F, -3));
		ModelPartData upperjaw = head.addChild("upperjaw", ModelPartBuilder.create().uv(24, 105).cuboid(-1.5F, -1, -2, 3, 1, 2, Dilation.NONE).uv(0, 0).cuboid(-1.5F, -3.5F, -1.75F, 3, 3, 0, Dilation.NONE), ModelTransform.origin(0, -1.5F, -6));
		upperjaw.addChild("lips", ModelPartBuilder.create().uv(36, 105).cuboid(-1, -0.5F, -2, 3, 1, 2, new Dilation(0.01F)), ModelTransform.origin(-0.5F, 0.5F, 0));
		upperjaw.addChild("fangs", ModelPartBuilder.create().uv(24, 109).cuboid(-1.5F, 0, -2, 3, 2, 2, new Dilation(-0.01F)), ModelTransform.origin(0, -1, 0));
		head.addChild("left_ear", ModelPartBuilder.create().uv(114, 46).mirrored().cuboid(0.25F, -10, -1.5F, 0, 11, 7, Dilation.NONE).mirrored(false), ModelTransform.of(3, -3.75F, -3, -0.48F, 0.4363F, 0.0873F));
		head.addChild("right_ear", ModelPartBuilder.create().uv(114, 46).cuboid(-0.25F, -10, -1.5F, 0, 11, 7, Dilation.NONE), ModelTransform.of(-3, -3.75F, -3, -0.48F, -0.4363F, -0.0873F));
		head.addChild("lowerJaw", ModelPartBuilder.create().uv(24, 115).cuboid(-1.5F, 0, -2, 3, 1, 2, Dilation.NONE), ModelTransform.origin(0, -1, -6));

		ModelPartData left_arm = root.addChild("left_arm", ModelPartBuilder.create().uv(23, 71).cuboid(-1, -2, -1, 3, 10, 4, Dilation.NONE).uv(0, 113).cuboid(-0.5F, -2.25F, -1.5F, 3, 5, 5, Dilation.NONE), ModelTransform.of(5, -7.0118F, -0.4942F, 0.0436F, 0, -0.1309F));
		ModelPartData left_forearm = left_arm.addChild("left_forearm", ModelPartBuilder.create().uv(24, 89).cuboid(-0.5F, 0, -3, 2, 8, 3, Dilation.NONE), ModelTransform.of(0, 8, 3, -0.2618F, 0, 0));
		ModelPartData left_hand = left_forearm.addChild("left_hand", ModelPartBuilder.create(), ModelTransform.origin(0.5F, 9, 0));
		left_hand.addChild("cube_r2", ModelPartBuilder.create().uv(42, 92).cuboid(-1.5F, -1, -3, 2, 5, 5, Dilation.NONE), ModelTransform.of(0.5F, -0.7382F, -1.0057F, 0, 0, 0.4363F));
		ModelPartData right_arm = root.addChild("right_arm", ModelPartBuilder.create().uv(23, 71).mirrored().cuboid(-2, -2, -1, 3, 10, 4, Dilation.NONE).mirrored(false).uv(0, 113).mirrored().cuboid(-2.5F, -2.25F, -1.5F, 3, 5, 5, Dilation.NONE).mirrored(false), ModelTransform.of(-5, -7.0118F, -0.4942F, 0.0436F, 0, 0.1309F));
		ModelPartData right_forearm = right_arm.addChild("right_forearm", ModelPartBuilder.create().uv(24, 89).mirrored().cuboid(-1.5F, 0, -3, 2, 8, 3, Dilation.NONE).mirrored(false), ModelTransform.of(0, 8, 3, -0.2618F, 0, 0));
		ModelPartData right_hand = right_forearm.addChild("right_hand", ModelPartBuilder.create(), ModelTransform.origin(-0.5F, 9, 1));
		right_hand.addChild("cube_r3", ModelPartBuilder.create().uv(42, 92).mirrored().cuboid(-0.5F, -1, -3, 2, 5, 5, Dilation.NONE).mirrored(false), ModelTransform.of(-0.5F, -0.7382F, -2.0058F, 0, 0, -0.4363F));

		ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.origin(3, 23.9647F, 1.8651F));
		ModelPartData left_leg_upper = left_leg.addChild("left_leg_upper", ModelPartBuilder.create().uv(0, 69).cuboid(-2, -1, -3, 4, 13, 5, new Dilation(-0.1F)), ModelTransform.of(0, -19.2266F, 0.3906F, -0.2488F, -0.194F, -0.0552F));
		ModelPartData left_foreleg = left_leg_upper.addChild("left_foreleg", ModelPartBuilder.create().uv(0, 88).cuboid(-1.5F, 0, -1.5F, 3, 11, 3, Dilation.NONE), ModelTransform.of(0, 8, 2, 0.1745F, 0, 0));
		left_foreleg.addChild("left_foot", ModelPartBuilder.create().uv(0, 104).cuboid(-2, -1.75F, -3.75F, 4, 2, 5, Dilation.NONE), ModelTransform.of(0, 11, -1, 0.0873F, 0, 0.0873F));
		ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.origin(-3, 23.9413F, 1.8651F));
		ModelPartData right_leg_upper = right_leg.addChild("right_leg_upper", ModelPartBuilder.create().uv(0, 69).mirrored().cuboid(-2, -1, -3, 4, 13, 5, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(0, -19.2031F, 0.3906F, -0.2488F, 0.194F, 0.0552F));
		ModelPartData right_foreleg = right_leg_upper.addChild("right_foreleg", ModelPartBuilder.create().uv(0, 88).mirrored().cuboid(-1.5F, 0, -1.5F, 3, 11, 3, Dilation.NONE).mirrored(false), ModelTransform.of(0, 8, 2, 0.1745F, 0, 0));
		right_foreleg.addChild("right_foot", ModelPartBuilder.create().uv(0, 104).mirrored().cuboid(-2, -1.75F, -3.75F, 4, 2, 5, Dilation.NONE).mirrored(false), ModelTransform.of(0, 11, -1, 0.0873F, 0, -0.0873F));

		return TexturedModelData.of(data, 128, 128);
	}

	@Override
	public void setAngles(DarkFormEntityRenderState state) {
		super.setAngles(state);
		head.pitch = (float) Math.toRadians(state.pitch);
		head.yaw = (float) Math.toRadians(state.relativeHeadYaw);
		if (!state.leftHandItemState.isEmpty()) {
			leftArm.pitch = leftArm.pitch * 0.5F - (float) (Math.PI / 10);
		}
		if (!state.rightHandItemState.isEmpty()) {
			rightArm.pitch = rightArm.pitch * 0.5F - (float) (Math.PI / 10);
		}

		(state.sprinting && !state.sneaking ? runAnimation : walkAnimation).applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, state.onGround ? 2 : 1, 1);
		idleAnimation.apply(state.idleAnimationState, state.age);
		idleSneakAnimation.apply(state.idleSneakAnimationState, state.age);
		jumpAnimation.apply(state.jumpAnimationState, state.age);
		flyAnimation.apply(state.flyAnimationState, state.age);
		leftAttackAnimation.apply(state.leftAttackAnimationState, state.age, 2);
		rightAttackAnimation.apply(state.rightAttackAnimationState, state.age, 2);
	}

	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		for (ModelPart part : arm == Arm.LEFT ? leftHeldItemTranslations : rightHeldItemTranslations) {
			part.applyTransform(matrices);
		}
		matrices.translate(0.15 * (arm == Arm.LEFT ? -1 : 1), -0.45, 0.2);
	}
}
