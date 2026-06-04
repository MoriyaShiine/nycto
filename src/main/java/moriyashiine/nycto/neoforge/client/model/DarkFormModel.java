/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.client.animation.DarkFormAnimation;
import moriyashiine.nycto.neoforge.entity.DarkForm;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.HumanoidArm;

public final class DarkFormModel<T extends LivingEntity> extends HierarchicalModel<T> implements ArmedModel {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(NyctoNeoForge.id("dark_form"), "main");

	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftWing1;
	private final ModelPart rightWing1;
	private final ModelPart[] leftHeldItemTranslations;
	private final ModelPart[] rightHeldItemTranslations;

	public DarkFormModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.root = root;
		ModelPart body = root.getChild("body");
		ModelPart chest = body.getChild("chest");
		head = body.getChild("head");
		leftArm = root.getChild("left_arm");
		rightArm = root.getChild("right_arm");
		leftWing1 = chest.getChild("left_wing1");
		rightWing1 = chest.getChild("right_wing1");
		leftHeldItemTranslations = new ModelPart[]{root, leftArm, leftArm.getChild("left_forearm"), leftArm.getChild("left_forearm").getChild("left_hand")};
		rightHeldItemTranslations = new ModelPart[]{root, rightArm, rightArm.getChild("right_forearm"), rightArm.getChild("right_forearm").getChild("right_hand")};
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
				.texOffs(102, 20).addBox(-3.5F, -6, -3, 7, 9, 6, CubeDeformation.NONE), PartPose.offset(0, 3.7382F, 1.5058F));
		PartDefinition chest = body.addOrReplaceChild("chest", CubeListBuilder.create(), PartPose.offset(0, -4.75F, -1));
		chest.addOrReplaceChild("cube_r1", CubeListBuilder.create()
				.texOffs(92, 0).addBox(-5, -4, -4.5F, 10, 10, 8, CubeDeformation.NONE), PartPose.offsetAndRotation(0, -6, 0, 0.2182F, 0, 0));

		PartDefinition leftWing1 = chest.addOrReplaceChild("left_wing1", CubeListBuilder.create()
				.texOffs(108, 67).addBox(-2, -5, -1, 3, 6, 4, CubeDeformation.NONE), PartPose.offsetAndRotation(3.75F, -7.75F, 2, -1.0036F, 0.2182F, -0.0873F));
		PartDefinition leftWing2 = leftWing1.addOrReplaceChild("left_wing2", CubeListBuilder.create()
				.texOffs(84, 58).addBox(0, -14, -2, 1, 21, 21, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.5F, -5.25F, 0.75F, 0.6109F, 0, 0.3054F));
		leftWing2.addOrReplaceChild("left_membranewing", CubeListBuilder.create()
				.texOffs(68, 73).addBox(0.25F, -14, -2, 0, 25, 30, CubeDeformation.NONE), PartPose.offsetAndRotation(0.25F, 0, 0, -0.1309F, 0, 0));
		leftWing1.addOrReplaceChild("lWing01Membrane", CubeListBuilder.create()
				.texOffs(36, 101).addBox(0, -13, -1, 0, 13, 14, CubeDeformation.NONE), PartPose.offsetAndRotation(-0.75F, -1, 3, -1.0472F, 0, 0));

		PartDefinition rightWing1 = chest.addOrReplaceChild("right_wing1", CubeListBuilder.create()
				.texOffs(108, 67).mirror().addBox(-1, -5, -1, 3, 6, 4, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-3.75F, -7.75F, 2, -1.0036F, -0.2182F, 0.0873F));
		PartDefinition rightWing2 = rightWing1.addOrReplaceChild("right_wing2", CubeListBuilder.create()
				.texOffs(84, 58).mirror().addBox(-1, -14, -2, 1, 21, 21, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(0.5F, -5.25F, 0.75F, 0.6109F, 0, -0.3054F));
		rightWing2.addOrReplaceChild("right_membranewing", CubeListBuilder.create()
				.texOffs(68, 73).mirror().addBox(-0.25F, -14, -2, 0, 25, 30, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-0.25F, 0, 0, -0.1309F, 0, 0));
		rightWing1.addOrReplaceChild("rWing01Membrane", CubeListBuilder.create()
				.texOffs(36, 101).mirror().addBox(0, -13, -1, 0, 13, 14, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(0.75F, -1, 3, -1.0472F, 0, 0));

		PartDefinition hair = chest.addOrReplaceChild("hair", CubeListBuilder.create(), PartPose.offsetAndRotation(0, -7.5F, 4.75F, 0.7854F, 0, 0));
		hair.addOrReplaceChild("lTruff01_r1", CubeListBuilder.create()
				.texOffs(57, 9).addBox(1, 0, -0.5F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, -9, 0, -1.2217F, 0, 0.4363F));
		hair.addOrReplaceChild("rTruff01_r1", CubeListBuilder.create()
				.texOffs(59, 27).addBox(-9, 0, -0.5F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, -9, 0, -1.2217F, 0, -0.5236F));
		PartDefinition innerTruff = hair.addOrReplaceChild("innerTruff", CubeListBuilder.create(), PartPose.offsetAndRotation(0, 1, -4.75F, -0.2618F, 0, 0));
		PartDefinition lowerTruff = innerTruff.addOrReplaceChild("lowerTruff", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -7, 5.25F, -0.3927F, 0, 0));
		lowerTruff.addOrReplaceChild("lTruff03_r1", CubeListBuilder.create()
				.texOffs(60, 54).addBox(1.9096F, 0.7376F, 0.0368F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, -1.309F, 0, 0.5236F));
		lowerTruff.addOrReplaceChild("rTruff03_r1", CubeListBuilder.create()
				.texOffs(60, 45).addBox(-8.9096F, 0.7376F, 0.0368F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, -1.309F, 0, -0.5236F));
		PartDefinition upperTruff = innerTruff.addOrReplaceChild("upperTruff", CubeListBuilder.create(), PartPose.offsetAndRotation(0, 0, 3.5F, -0.2182F, 0, 0));
		upperTruff.addOrReplaceChild("lTruff02_r1", CubeListBuilder.create()
				.texOffs(57, 9).addBox(1.4096F, 0.7816F, -0.0296F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, -9, 0, -1.2217F, 0, 0.5236F));
		upperTruff.addOrReplaceChild("rTruff02_r1", CubeListBuilder.create()
				.texOffs(59, 27).addBox(-9.4096F, 0.7816F, -0.0296F, 8, 0, 9, CubeDeformation.NONE), PartPose.offsetAndRotation(0, -9, 0, -1.2217F, 0, -0.5236F));
		body.addOrReplaceChild("cloth", CubeListBuilder.create()
				.texOffs(74, 65).addBox(-3, -1, 0.6F, 6, 11, 0, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 1.75F, -3.75F, -0.2182F, 0, 0));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(86, 37).addBox(-3.5F, -8, -6, 7, 8, 8, new CubeDeformation(-0.01F)), PartPose.offset(0, -13.75F, -3));
		PartDefinition upperjaw = head.addOrReplaceChild("upperjaw", CubeListBuilder.create()
				.texOffs(24, 105).addBox(-1.5F, -1, -2, 3, 1, 2, CubeDeformation.NONE)
				.texOffs(0, 0).addBox(-1.5F, -3.5F, -1.75F, 3, 3, 0, CubeDeformation.NONE), PartPose.offset(0, -1.5F, -6));
		upperjaw.addOrReplaceChild("lips", CubeListBuilder.create()
				.texOffs(36, 105).addBox(-1, -0.5F, -2, 3, 1, 2, new CubeDeformation(0.01F)), PartPose.offset(-0.5F, 0.5F, 0));
		upperjaw.addOrReplaceChild("fangs", CubeListBuilder.create()
				.texOffs(24, 109).addBox(-1.5F, 0, -2, 3, 2, 2, new CubeDeformation(-0.01F)), PartPose.offset(0, -1, 0));
		head.addOrReplaceChild("left_ear", CubeListBuilder.create()
				.texOffs(114, 46).mirror().addBox(0.25F, -10, -1.5F, 0, 11, 7, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(3, -3.75F, -3, -0.48F, 0.4363F, 0.0873F));
		head.addOrReplaceChild("right_ear", CubeListBuilder.create()
				.texOffs(114, 46).addBox(-0.25F, -10, -1.5F, 0, 11, 7, CubeDeformation.NONE), PartPose.offsetAndRotation(-3, -3.75F, -3, -0.48F, -0.4363F, -0.0873F));
		head.addOrReplaceChild("lowerJaw", CubeListBuilder.create()
				.texOffs(24, 115).addBox(-1.5F, 0, -2, 3, 1, 2, CubeDeformation.NONE), PartPose.offset(0, -1, -6));

		PartDefinition leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create()
				.texOffs(23, 71).addBox(-1, -2, -1, 3, 10, 4, CubeDeformation.NONE)
				.texOffs(0, 113).addBox(-0.5F, -2.25F, -1.5F, 3, 5, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(5, -7.0118F, -0.4942F, 0.0436F, 0, -0.1309F));
		PartDefinition leftForearm = leftArm.addOrReplaceChild("left_forearm", CubeListBuilder.create()
				.texOffs(24, 89).addBox(-0.5F, 0, -3, 2, 8, 3, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 8, 3, -0.2618F, 0, 0));
		PartDefinition leftHand = leftForearm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(0.5F, 9, 0));
		leftHand.addOrReplaceChild("cube_r2", CubeListBuilder.create()
				.texOffs(42, 92).addBox(-1.5F, -1, -3, 2, 5, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(0.5F, -0.7382F, -1.0057F, 0, 0, 0.4363F));

		PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create()
				.texOffs(23, 71).mirror().addBox(-2, -2, -1, 3, 10, 4, CubeDeformation.NONE).mirror(false)
				.texOffs(0, 113).mirror().addBox(-2.5F, -2.25F, -1.5F, 3, 5, 5, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-5, -7.0118F, -0.4942F, 0.0436F, 0, 0.1309F));
		PartDefinition rightForearm = rightArm.addOrReplaceChild("right_forearm", CubeListBuilder.create()
				.texOffs(24, 89).mirror().addBox(-1.5F, 0, -3, 2, 8, 3, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(0, 8, 3, -0.2618F, 0, 0));
		PartDefinition rightHand = rightForearm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-0.5F, 9, 1));
		rightHand.addOrReplaceChild("cube_r3", CubeListBuilder.create()
				.texOffs(42, 92).mirror().addBox(-0.5F, -1, -3, 2, 5, 5, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-0.5F, -0.7382F, -2.0058F, 0, 0, -0.4363F));

		PartDefinition leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(3, 23.9647F, 1.8651F));
		PartDefinition leftLegUpper = leftLeg.addOrReplaceChild("left_leg_upper", CubeListBuilder.create()
				.texOffs(0, 69).addBox(-2, -1, -3, 4, 13, 5, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0, -19.2266F, 0.3906F, -0.2488F, -0.194F, -0.0552F));
		PartDefinition leftForeleg = leftLegUpper.addOrReplaceChild("left_foreleg", CubeListBuilder.create()
				.texOffs(0, 88).addBox(-1.5F, 0, -1.5F, 3, 11, 3, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 8, 2, 0.1745F, 0, 0));
		leftForeleg.addOrReplaceChild("left_foot", CubeListBuilder.create()
				.texOffs(0, 104).addBox(-2, -1.75F, -3.75F, 4, 2, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 11, -1, 0.0873F, 0, 0.0873F));
		PartDefinition rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-3, 23.9413F, 1.8651F));
		PartDefinition rightLegUpper = rightLeg.addOrReplaceChild("right_leg_upper", CubeListBuilder.create()
				.texOffs(0, 69).mirror().addBox(-2, -1, -3, 4, 13, 5, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0, -19.2031F, 0.3906F, -0.2488F, 0.194F, 0.0552F));
		PartDefinition rightForeleg = rightLegUpper.addOrReplaceChild("right_foreleg", CubeListBuilder.create()
				.texOffs(0, 88).mirror().addBox(-1.5F, 0, -1.5F, 3, 11, 3, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(0, 8, 2, 0.1745F, 0, 0));
		rightForeleg.addOrReplaceChild("right_foot", CubeListBuilder.create()
				.texOffs(0, 104).mirror().addBox(-2, -1.75F, -3.75F, 4, 2, 5, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(0, 11, -1, 0.0873F, 0, -0.0873F));

		return LayerDefinition.create(mesh, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, null);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, AnimationContext animationContext) {
		setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, animationContext, false);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, AnimationContext animationContext, boolean jumpPhase) {
		root.getAllParts().forEach(ModelPart::resetPose);
		head.xRot = headPitch * Mth.DEG_TO_RAD;
		head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
		if (animationContext != null) {
			animationContext.update(entity, limbSwingAmount, jumpPhase);
			animateWalk(entity.isSprinting() ? DarkFormAnimation.RUN : DarkFormAnimation.WALK, limbSwing, limbSwingAmount, entity.onGround() ? 2 : 1, 1);
			animate(animationContext.idle, DarkFormAnimation.IDLE, ageInTicks);
			animate(animationContext.idleSneak, DarkFormAnimation.IDLE_SNEAK, ageInTicks);
			animate(animationContext.jump, DarkFormAnimation.JUMP, ageInTicks);
			animate(animationContext.fly, DarkFormAnimation.FLY, ageInTicks);
			animate(animationContext.leftAttack, DarkFormAnimation.LEFT_ATTACK, ageInTicks, 2);
			animate(animationContext.rightAttack, DarkFormAnimation.RIGHT_ATTACK, ageInTicks, 2);
			return;
		}
		if (entity instanceof DarkForm darkForm) {
			animateWalk(entity.isSprinting() ? DarkFormAnimation.RUN : DarkFormAnimation.WALK, limbSwing, limbSwingAmount, entity.onGround() ? 2 : 1, 1);
			animate(darkForm.idleAnimationState, DarkFormAnimation.IDLE, ageInTicks);
			animate(darkForm.idleSneakAnimationState, DarkFormAnimation.IDLE_SNEAK, ageInTicks);
			animate(darkForm.jumpAnimationState, DarkFormAnimation.JUMP, ageInTicks);
			animate(darkForm.flyAnimationState, DarkFormAnimation.FLY, ageInTicks);
			animate(darkForm.leftAttackAnimationState, DarkFormAnimation.LEFT_ATTACK, ageInTicks, 2);
			animate(darkForm.rightAttackAnimationState, DarkFormAnimation.RIGHT_ATTACK, ageInTicks, 2);
			return;
		}
		float walk = Math.min(limbSwingAmount, 1);
		leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 0.7F * walk - 0.25F;
		rightArm.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 0.7F * walk - 0.25F;
		float wing = Mth.sin(ageInTicks * (entity.onGround() ? 0.08F : 0.35F)) * (entity.onGround() ? 0.12F : 0.35F);
		leftWing1.zRot = -0.0873F + wing;
		rightWing1.zRot = 0.0873F - wing;
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void translateToHand(HumanoidArm side, PoseStack poseStack) {
		for (ModelPart part : side == HumanoidArm.LEFT ? leftHeldItemTranslations : rightHeldItemTranslations) {
			part.translateAndRotate(poseStack);
		}
		poseStack.translate(0.15 * (side == HumanoidArm.LEFT ? -1 : 1), -0.45, 0.2);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, buffer, packedLight, packedOverlay, color);
	}

	public void renderArm(HumanoidArm side, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		ModelPart arm = side == HumanoidArm.LEFT ? leftArm : rightArm;
		arm.render(poseStack, buffer, packedLight, packedOverlay, color);
	}

	public static final class AnimationContext {
		private final AnimationState idle = new AnimationState();
		private final AnimationState idleSneak = new AnimationState();
		private final AnimationState jump = new AnimationState();
		private final AnimationState fly = new AnimationState();
		private final AnimationState leftAttack = new AnimationState();
		private final AnimationState rightAttack = new AnimationState();
		private boolean wasSwinging = false;

		public void update(LivingEntity entity, float limbSwingAmount, boolean jumpPhase) {
			boolean onGround = entity.onGround();
			boolean moving = limbSwingAmount > 0.02F;
			idle.animateWhen(onGround && !entity.isCrouching() && !moving, entity.tickCount);
			idleSneak.animateWhen(onGround && entity.isCrouching(), entity.tickCount);
			jump.animateWhen(!onGround && jumpPhase, entity.tickCount);
			fly.animateWhen(!onGround && !jumpPhase, entity.tickCount);
			if (entity.swinging && !wasSwinging) {
				if (entity.swingingArm == InteractionHand.OFF_HAND) {
					leftAttack.start(entity.tickCount);
					rightAttack.stop();
				} else {
					rightAttack.start(entity.tickCount);
					leftAttack.stop();
				}
			}
			wasSwinging = entity.swinging;
			if (!entity.swinging) {
				leftAttack.stop();
				rightAttack.stop();
			}
		}
	}
}
