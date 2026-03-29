/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.model;

import moriyashiine.nycto.client.renderer.entity.state.VampireRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class VampireModel extends HumanoidModel<VampireRenderState> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(Nycto.id("vampire"), "main");

	private final ModelPart arms;

	public VampireModel(ModelPart root) {
		super(root);
		arms = root.getChild(PartNames.ARMS);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition head = root.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4, -10, -4, 8, 10, 8, CubeDeformation.NONE), PartPose.offset(0, 0, 0));
		root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(16, 20).addBox(-4, 0, -3, 8, 12, 6, CubeDeformation.NONE), PartPose.offset(0, 0, 0));
		PartDefinition leftArm = root.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(36, 46).addBox(-1, -2, -2, 3, 12, 4, CubeDeformation.NONE).texOffs(50, 46).addBox(-1, -2, -2, 3, 12, 4, new CubeDeformation(0.25F)), PartPose.offset(5, 2, 0));
		PartDefinition rightArm = root.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(36, 46).addBox(-2, -2, -2, 3, 12, 4, CubeDeformation.NONE).texOffs(50, 46).addBox(-2, -2, -2, 3, 12, 4, new CubeDeformation(0.25F)), PartPose.offset(-5, 2, 0));
		root.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(0, 22).addBox(-2, 0, -2, 4, 12, 4, CubeDeformation.NONE), PartPose.offset(2, 12, 0));
		root.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(0, 22).addBox(-2, 0, -2, 4, 12, 4, CubeDeformation.NONE), PartPose.offset(-2, 12, 0));
		root.addOrReplaceChild(PartNames.ARMS, CubeListBuilder.create().texOffs(44, 22).addBox(4, -2, -2, 3, 8, 4, CubeDeformation.NONE).texOffs(44, 22).addBox(-7, -2, -2, 3, 8, 4, CubeDeformation.NONE).texOffs(40, 38).addBox(-4, 2, -2, 8, 4, 4, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 3, -1, -0.75F, 0, 0));
		head.addOrReplaceChild(PartNames.LEFT_EAR, CubeListBuilder.create().texOffs(34, 0).addBox(0, -3.5F, 0, 0, 4, 6, CubeDeformation.NONE), PartPose.offsetAndRotation(-4, -2, 0, 0.48F, -0.3927F, -0.0873F));
		head.addOrReplaceChild(PartNames.RIGHT_EAR, CubeListBuilder.create().texOffs(34, 0).addBox(0, -3.5F, 0, 0, 4, 6, CubeDeformation.NONE), PartPose.offsetAndRotation(4, -2, 0, 0.48F, 0.3927F, 0.0873F));
		head.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.ZERO);
		head.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(24, 0).addBox(-1, 0.2F, -2, 2, 4, 2, CubeDeformation.NONE), PartPose.offset(0, -3.2F, -4.1F));

		root.addOrReplaceChild("robe", CubeListBuilder.create().texOffs(0, 39).addBox(-4, 0, -3, 8, 19, 6, new CubeDeformation(0.25F)), PartPose.offset(0, 0, 0));
		leftArm.addOrReplaceChild("left_claw", CubeListBuilder.create().texOffs(48, 0).addBox(-2.25F, -1, -2.5F, 3, 4, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(1, 10, 0, 0, 0, 0.1309F));
		rightArm.addOrReplaceChild("right_claw", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-0.75F, -1, -2.5F, 3, 4, 5, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-1.25F, 10, 0, 0, 0, -0.1309F));

		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(VampireRenderState state) {
		super.setupAnim(state);
		leftArm.visible = rightArm.visible = state.attacking;
		arms.visible = !state.attacking;
	}
}
