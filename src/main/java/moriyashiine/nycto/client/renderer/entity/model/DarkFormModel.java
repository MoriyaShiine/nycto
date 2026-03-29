/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.nycto.client.renderer.entity.animation.DarkFormAnimation;
import moriyashiine.nycto.client.renderer.entity.state.DarkFormRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;

public class DarkFormModel extends EntityModel<DarkFormRenderState> implements ArmedModel<DarkFormRenderState> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(Nycto.id("dark_form"), "main");

	private final ModelPart head;
	private final ModelPart[] leftHeldItemTranslations, rightHeldItemTranslations;

	public final ModelPart leftArm, rightArm;

	public final KeyframeAnimation idleAnimation;
	public final KeyframeAnimation idleSneakAnimation;
	public final KeyframeAnimation walkAnimation;
	public final KeyframeAnimation runAnimation;
	public final KeyframeAnimation jumpAnimation;
	public final KeyframeAnimation flyAnimation;
	public final KeyframeAnimation leftAttackAnimation;
	public final KeyframeAnimation rightAttackAnimation;

	public DarkFormModel(ModelPart root) {
		super(root);
		head = root.getChild("body").getChild("head");
		leftArm = root.getChild("left_arm");
		rightArm = root.getChild("right_arm");

		leftHeldItemTranslations = new ModelPart[]{root, leftArm, leftArm.getChild("left_forearm"), leftArm.getChild("left_forearm").getChild("left_hand")};
		rightHeldItemTranslations = new ModelPart[]{root, rightArm, rightArm.getChild("right_forearm"), rightArm.getChild("right_forearm").getChild("right_hand")};

		idleAnimation = DarkFormAnimation.IDLE.bake(root);
		idleSneakAnimation = DarkFormAnimation.IDLE_SNEAK.bake(root);
		walkAnimation = DarkFormAnimation.WALK.bake(root);
		runAnimation = DarkFormAnimation.RUN.bake(root);
		jumpAnimation = DarkFormAnimation.JUMP.bake(root);
		flyAnimation = DarkFormAnimation.FLY.bake(root);
		leftAttackAnimation = DarkFormAnimation.LEFT_ATTACK.bake(root);
		rightAttackAnimation = DarkFormAnimation.RIGHT_ATTACK.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(102, 20).addBox(-3.5F, -6, -3, 7, 9, 6, CubeDeformation.NONE), PartPose.offset(0, 3.7382F, 1.5058F));
		PartDefinition chest = body.addOrReplaceChild("chest", CubeListBuilder.create(), PartPose.offset(0, -4.75F, -1));
		chest.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(92, 0).addBox(-5, -4, -4.5F, 10, 10, 8, CubeDeformation.NONE), PartPose.offsetAndRotation(0, -6, 0, 0.2182F, 0, 0));
		PartDefinition left_wing1 = chest.addOrReplaceChild("left_wing1", CubeListBuilder.create().texOffs(108, 67).addBox(-2, -5, -1, 3, 6, 4, CubeDeformation.NONE), PartPose.offsetAndRotation(3.75F, -7.75F, 2, -1.0036F, 0.2182F, -0.0873F));
		PartDefinition left_wing2 = left_wing1.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(84, 58).addBox(0, -14, -2, 1, 21, 21, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.5F, -5.25F, 0.75F, 0.6109F, 0, 0.3054F));
		left_wing2.addOrReplaceChild("left_membranewing", CubeListBuilder.create().texOffs(68, 73).addBox(0.25F, -14, -2, 0, 25, 30, CubeDeformation.NONE), PartPose.offsetAndRotation(0.25F, 0, 0, -0.1309F, 0, 0));
		left_wing1.addOrReplaceChild("lWing01Membrane", CubeListBuilder.create().texOffs(36, 101).addBox(0, -13, -1, 0, 13, 14, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.75F, -1, 3, -1.0472F, 0, 0));
		PartDefinition right_wing1 = chest.addOrReplaceChild("right_wing1", CubeListBuilder.create().texOffs(108, 67).mirror().addBox(-1, -5, -1, 3, 6, 4, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-3.75F, -7.75F, 2, -1.0036F, -0.2182F, 0.0873F));
		PartDefinition right_wing2 = right_wing1.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(84, 58).mirror().addBox(-1, -14, -2, 1, 21, 21, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(0.5F, -5.25F, 0.75F, 0.6109F, 0, -0.3054F));
		right_wing2.addOrReplaceChild("right_membranewing", CubeListBuilder.create().texOffs(68, 73).mirror().addBox(-0.25F, -14, -2, 0, 25, 30, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-0.25F, 0, 0, -0.1309F, 0, 0));
		right_wing1.addOrReplaceChild("rWing01Membrane", CubeListBuilder.create().texOffs(36, 101).mirror().addBox(0, -13, -1, 0, 13, 14, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(0.75F, -1, 3, -1.0472F, 0, 0));
		PartDefinition hair = chest.addOrReplaceChild("hair", CubeListBuilder.create(), PartPose.offsetAndRotation(0, -7.5F, 4.75F, 0.7854F, 0, 0));
		hair.addOrReplaceChild("lTruff01_r1", CubeListBuilder.create().texOffs(57, 9).addBox(1, 0, -0.5F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, -9, 0, -1.2217F, 0, 0.4363F));
		hair.addOrReplaceChild("rTruff01_r1", CubeListBuilder.create().texOffs(59, 27).addBox(-9, 0, -0.5F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, -9, 0, -1.2217F, 0, -0.5236F));
		PartDefinition innerTruff = hair.addOrReplaceChild("innerTruff", CubeListBuilder.create(), PartPose.offsetAndRotation(0, 1, -4.75F, -0.2618F, 0, 0));
		PartDefinition lowerTruff = innerTruff.addOrReplaceChild("lowerTruff", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -7, 5.25F, -0.3927F, 0, 0));
		lowerTruff.addOrReplaceChild("lTruff03_r1", CubeListBuilder.create().texOffs(60, 54).addBox(1.9096F, 0.7376F, 0.0368F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, -1.309F, 0, 0.5236F));
		lowerTruff.addOrReplaceChild("rTruff03_r1", CubeListBuilder.create().texOffs(60, 45).addBox(-8.9096F, 0.7376F, 0.0368F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, -1.309F, 0, -0.5236F));
		PartDefinition upperTruff = innerTruff.addOrReplaceChild("upperTruff", CubeListBuilder.create(), PartPose.offsetAndRotation(0, 0, 3.5F, -0.2182F, 0, 0));
		upperTruff.addOrReplaceChild("lTruff02_r1", CubeListBuilder.create().texOffs(57, 9).addBox(1.4096F, 0.7816F, -0.0296F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, -9, 0, -1.2217F, 0, 0.5236F));
		upperTruff.addOrReplaceChild("rTruff02_r1", CubeListBuilder.create().texOffs(59, 27).addBox(-9.4096F, 0.7816F, -0.0296F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, -9, 0, -1.2217F, 0, -0.5236F));
		body.addOrReplaceChild("cloth", CubeListBuilder.create().texOffs(74, 65).addBox(-3, -1, 0.6F, 6, 11, 0, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 1.75F, -3.75F, -0.2182F, 0, 0));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(86, 37).addBox(-3.5F, -8, -6, 7, 8, 8, new CubeDeformation(-0.01F)), PartPose.offset(0, -13.75F, -3));
		PartDefinition upperjaw = head.addOrReplaceChild("upperjaw", CubeListBuilder.create().texOffs(24, 105).addBox(-1.5F, -1, -2, 3, 1, 2, CubeDeformation.NONE).texOffs(0, 0).addBox(-1.5F, -3.5F, -1.75F, 3, 3, 0, CubeDeformation.NONE), PartPose.offset(0, -1.5F, -6));
		upperjaw.addOrReplaceChild("lips", CubeListBuilder.create().texOffs(36, 105).addBox(-1, -0.5F, -2, 3, 1, 2, new CubeDeformation(0.01F)), PartPose.offset(-0.5F, 0.5F, 0));
		upperjaw.addOrReplaceChild("fangs", CubeListBuilder.create().texOffs(24, 109).addBox(-1.5F, 0, -2, 3, 2, 2, new CubeDeformation(-0.01F)), PartPose.offset(0, -1, 0));
		head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(114, 46).mirror().addBox(0.25F, -10, -1.5F, 0, 11, 7, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(3, -3.75F, -3, -0.48F, 0.4363F, 0.0873F));
		head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(114, 46).addBox(-0.25F, -10, -1.5F, 0, 11, 7, CubeDeformation.NONE), PartPose.offsetAndRotation(-3, -3.75F, -3, -0.48F, -0.4363F, -0.0873F));
		head.addOrReplaceChild("lowerJaw", CubeListBuilder.create().texOffs(24, 115).addBox(-1.5F, 0, -2, 3, 1, 2, CubeDeformation.NONE), PartPose.offset(0, -1, -6));

		PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(23, 71).addBox(-1, -2, -1, 3, 10, 4, CubeDeformation.NONE).texOffs(0, 113).addBox(-0.5F, -2.25F, -1.5F, 3, 5, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(5, -7.0118F, -0.4942F, 0.0436F, 0, -0.1309F));
		PartDefinition left_forearm = left_arm.addOrReplaceChild("left_forearm", CubeListBuilder.create().texOffs(24, 89).addBox(-0.5F, 0, -3, 2, 8, 3, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 8, 3, -0.2618F, 0, 0));
		PartDefinition left_hand = left_forearm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(0.5F, 9, 0));
		left_hand.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(42, 92).addBox(-1.5F, -1, -3, 2, 5, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(0.5F, -0.7382F, -1.0057F, 0, 0, 0.4363F));
		PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(23, 71).mirror().addBox(-2, -2, -1, 3, 10, 4, CubeDeformation.NONE).mirror(false).texOffs(0, 113).mirror().addBox(-2.5F, -2.25F, -1.5F, 3, 5, 5, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-5, -7.0118F, -0.4942F, 0.0436F, 0, 0.1309F));
		PartDefinition right_forearm = right_arm.addOrReplaceChild("right_forearm", CubeListBuilder.create().texOffs(24, 89).mirror().addBox(-1.5F, 0, -3, 2, 8, 3, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(0, 8, 3, -0.2618F, 0, 0));
		PartDefinition right_hand = right_forearm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-0.5F, 9, 1));
		right_hand.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(42, 92).mirror().addBox(-0.5F, -1, -3, 2, 5, 5, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-0.5F, -0.7382F, -2.0058F, 0, 0, -0.4363F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(3, 23.9647F, 1.8651F));
		PartDefinition left_leg_upper = left_leg.addOrReplaceChild("left_leg_upper", CubeListBuilder.create().texOffs(0, 69).addBox(-2, -1, -3, 4, 13, 5, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0, -19.2266F, 0.3906F, -0.2488F, -0.194F, -0.0552F));
		PartDefinition left_foreleg = left_leg_upper.addOrReplaceChild("left_foreleg", CubeListBuilder.create().texOffs(0, 88).addBox(-1.5F, 0, -1.5F, 3, 11, 3, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 8, 2, 0.1745F, 0, 0));
		left_foreleg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(0, 104).addBox(-2, -1.75F, -3.75F, 4, 2, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 11, -1, 0.0873F, 0, 0.0873F));
		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-3, 23.9413F, 1.8651F));
		PartDefinition right_leg_upper = right_leg.addOrReplaceChild("right_leg_upper", CubeListBuilder.create().texOffs(0, 69).mirror().addBox(-2, -1, -3, 4, 13, 5, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0, -19.2031F, 0.3906F, -0.2488F, 0.194F, 0.0552F));
		PartDefinition right_foreleg = right_leg_upper.addOrReplaceChild("right_foreleg", CubeListBuilder.create().texOffs(0, 88).mirror().addBox(-1.5F, 0, -1.5F, 3, 11, 3, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(0, 8, 2, 0.1745F, 0, 0));
		right_foreleg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(0, 104).mirror().addBox(-2, -1.75F, -3.75F, 4, 2, 5, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(0, 11, -1, 0.0873F, 0, -0.0873F));

		return LayerDefinition.create(mesh, 128, 128);
	}

	@Override
	public void setupAnim(DarkFormRenderState state) {
		super.setupAnim(state);
		head.xRot = (float) Math.toRadians(state.xRot);
		head.yRot = (float) Math.toRadians(state.yRot);
		if (!state.leftHandItemState.isEmpty()) {
			leftArm.xRot = leftArm.xRot * 0.5F - (float) (Math.PI / 10);
		}
		if (!state.rightHandItemState.isEmpty()) {
			rightArm.xRot = rightArm.xRot * 0.5F - (float) (Math.PI / 10);
		}

		(state.sprinting && !state.isDiscrete ? runAnimation : walkAnimation).applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, state.onGround ? 2 : 1, 1);
		idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
		idleSneakAnimation.apply(state.idleSneakAnimationState, state.ageInTicks);
		jumpAnimation.apply(state.jumpAnimationState, state.ageInTicks);
		flyAnimation.apply(state.flyAnimationState, state.ageInTicks);
		leftAttackAnimation.apply(state.leftAttackAnimationState, state.ageInTicks, 2);
		rightAttackAnimation.apply(state.rightAttackAnimationState, state.ageInTicks, 2);
	}

	@Override
	public void translateToHand(DarkFormRenderState state, HumanoidArm arm, PoseStack poseStack) {
		for (ModelPart part : arm == HumanoidArm.LEFT ? leftHeldItemTranslations : rightHeldItemTranslations) {
			part.translateAndRotate(poseStack);
		}
		poseStack.translate(0.15 * (arm == HumanoidArm.LEFT ? -1 : 1), -0.45, 0.2);
	}
}
