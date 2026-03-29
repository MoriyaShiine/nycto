/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.model;

import moriyashiine.nycto.client.renderer.entity.state.HunterRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class HunterModel extends IllagerModel<HunterRenderState> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(Nycto.id("hunter"), "main");

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart coatFlap;

	public HunterModel(ModelPart root) {
		super(root);
		head = root.getChild(PartNames.HEAD);
		body = root.getChild(PartNames.BODY);
		leftArm = root.getChild(PartNames.LEFT_ARM);
		rightArm = root.getChild(PartNames.RIGHT_ARM);
		leftLeg = root.getChild(PartNames.LEFT_LEG);
		rightLeg = root.getChild(PartNames.RIGHT_LEG);
		coatFlap = root.getChild(PartNames.BODY).getChild("body_armor").getChild("coat_flap");
	}

	public static LayerDefinition createHunterBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition head = root.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4, -10, -4, 8, 10, 8, CubeDeformation.NONE), PartPose.ZERO);
		PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(18, 22).addBox(-4, 0, -2, 8, 12, 4, CubeDeformation.NONE), PartPose.ZERO);
		root.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(40, 46).addBox(-1, -2, -2, 4, 12, 4, CubeDeformation.NONE).texOffs(98, 16).mirror().addBox(-1, -2, -2, 4, 12, 4, new CubeDeformation(0.32F)).mirror(false), PartPose.offset(5, 2, 0));
		root.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(40, 46).addBox(-3, -2, -2, 4, 12, 4, CubeDeformation.NONE).texOffs(98, 16).addBox(-3, -2, -2, 4, 12, 4, new CubeDeformation(0.32F)), PartPose.offset(-5, 2, 0));
		root.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(0, 22).addBox(-2, 0, -2, 4, 12, 4, CubeDeformation.NONE).texOffs(79, 65).mirror().addBox(-2.1F, 0, -2, 4, 12, 4, new CubeDeformation(0.32F)).mirror(false).texOffs(58, 16).mirror().addBox(-2.1F, 0, -2, 4, 12, 4, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(2, 12, 0));
		root.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(0, 22).addBox(-2, 0, -2, 4, 12, 4, CubeDeformation.NONE).texOffs(79, 65).addBox(-1.9F, 0, -2, 4, 12, 4, new CubeDeformation(0.32F)).texOffs(58, 16).addBox(-1.9F, 0, -2, 4, 12, 4, new CubeDeformation(0.3F)), PartPose.offset(-2, 12, 0));
		root.addOrReplaceChild(PartNames.ARMS, CubeListBuilder.create(), PartPose.ZERO);
		head.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.ZERO);
		head.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(24, 0).addBox(-1, -1.3F, -1, 2, 4, 2, CubeDeformation.NONE), PartPose.offset(0, -1.7F, -5.1F));

		PartDefinition headArmor = head.addOrReplaceChild("head_armor", CubeListBuilder.create().texOffs(65, 0).addBox(-4, -8, -4, 8, 8, 8, new CubeDeformation(0.55F)).texOffs(0, 64).addBox(-7, -9.2F, -11, 14, 3, 18, CubeDeformation.NONE).texOffs(4, 108).addBox(-6, -10, -6, 12, 4, 6, new CubeDeformation(0.01F)).texOffs(28, 102).addBox(-2, -10, -8, 4, 4, 2, CubeDeformation.NONE).texOffs(0, 118).addBox(-7, -10, 0, 14, 4, 6, CubeDeformation.NONE), PartPose.ZERO);
		headArmor.addOrReplaceChild("cocked_hat_1", CubeListBuilder.create().texOffs(56, 88).addBox(-8, -33, 0.3F, 14, 4, 6, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 23, 0, 0, 1.8326F, 0));
		headArmor.addOrReplaceChild("cocked_hat_2", CubeListBuilder.create().texOffs(56, 88).addBox(-6, -33, 0.3F, 14, 4, 6, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 23, 0, 0, -1.8326F, 0));
		PartDefinition hatFlower = headArmor.addOrReplaceChild("hat_flower", CubeListBuilder.create().texOffs(101, 105).addBox(6.5F, -1, -2, 0, 4, 3, CubeDeformation.NONE), PartPose.offset(-1, -5, 3));
		hatFlower.addOrReplaceChild("hat_flower_cube", CubeListBuilder.create().texOffs(100, 65).addBox(-1, -9, -6, 0, 8, 7, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.75F, 1.5F, -2, -0.3491F, 0, 0));

		PartDefinition bodyArmor = body.addOrReplaceChild("body_armor", CubeListBuilder.create().texOffs(74, 32).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.35F)).texOffs(100, 55).addBox(-4.5F, 10, -2.5F, 9, 3, 5, new CubeDeformation(0.1F)).texOffs(106, 86).addBox(-5, 0, -2.25F, 10, 12, 1, new CubeDeformation(0.35F)), PartPose.ZERO);
		bodyArmor.addOrReplaceChild("chest_belt", CubeListBuilder.create().texOffs(100, 51).addBox(-5, -2, -3.5F, 3, 3, 1, CubeDeformation.NONE).texOffs(100, 51).addBox(-5, 7, -3.5F, 3, 3, 1, CubeDeformation.NONE).texOffs(99, 32).addBox(-5, -2, -2.5F, 3, 14, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, 0, 0, -0.5672F));
		bodyArmor.addOrReplaceChild("coat_flap", CubeListBuilder.create().texOffs(74, 48).addBox(-4, 0.2F, 0.5F, 8, 8, 4, new CubeDeformation(0.35F)), PartPose.offset(0, 12.5F, -2.5F));
		PartDefinition chestFlowers = bodyArmor.addOrReplaceChild("chest_flowers", CubeListBuilder.create().texOffs(115, 16).addBox(-1.25F, 0, -0.75F, 4, 5, 0, CubeDeformation.NONE).texOffs(115, 23).addBox(-8.25F, -6.75F, 4.76F, 4, 5, 0, CubeDeformation.NONE).texOffs(115, 23).addBox(-6.25F, -3.75F, -0.49F, 4, 5, 0, CubeDeformation.NONE).texOffs(102, 80).addBox(-5.5F, -8, -0.75F, 5, 6, 0, CubeDeformation.NONE), PartPose.offset(2, 10, -2.25F));
		chestFlowers.addOrReplaceChild("chest_flowers_cube", CubeListBuilder.create().texOffs(115, 23).addBox(-2, -0.0019F, -0.9872F, 4, 5, 0, CubeDeformation.NONE), PartPose.offsetAndRotation(-3, -3.75F, 5.75F, 0.0436F, 0, 0));
		body.addOrReplaceChild("scarf", CubeListBuilder.create().texOffs(34, 0).addBox(-1.5F, 0.5F, -2.4F, 3, 4, 2, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0.25F, 0, -0.3491F, 0, 0));

		root.addOrReplaceChild("robe", CubeListBuilder.create().texOffs(0, 39).addBox(-4, 0, -3, 8, 19, 6, new CubeDeformation(0.25F)), PartPose.ZERO);

		return LayerDefinition.create(mesh, 128, 128);
	}

	@Override
	public void setupAnim(HunterRenderState state) {
		super.setupAnim(state);
		float attackAnim = state.attackAnim;
		if (attackAnim > 0) {
			HumanoidArm arm = state.mainArm;
			ModelPart armPart = arm == HumanoidArm.RIGHT ? rightArm : leftArm;
			body.yRot = Mth.sin(Mth.sqrt(attackAnim) * (float) (Math.PI * 2)) * 0.2F;
			if (arm == HumanoidArm.LEFT) {
				body.yRot *= -1;
			}
			rightArm.z = Mth.sin(body.yRot) * 5 * state.ageScale;
			rightArm.x = -Mth.cos(body.yRot) * 5 * state.ageScale;
			leftArm.z = -Mth.sin(body.yRot) * 5 * state.ageScale;
			leftArm.x = Mth.cos(body.yRot) * 5 * state.ageScale;
			rightArm.yRot = rightArm.yRot + body.yRot;
			leftArm.yRot = leftArm.yRot + body.yRot;
			leftArm.xRot = leftArm.xRot + body.yRot;
			armPart.xRot -= Mth.sin((float) ((1 - Math.pow(1 - attackAnim, 3)) * Math.PI)) * 1.2F + Mth.sin(attackAnim * (float) Math.PI) * -(head.xRot - 0.7F) * 0.75F;
			armPart.yRot = armPart.yRot + body.yRot * 2;
			armPart.zRot = armPart.zRot + Mth.sin(attackAnim * (float) Math.PI) * -0.4F;
		}
		coatFlap.xRot = Math.max(leftLeg.xRot, rightLeg.xRot);
	}
}
