/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.model;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class BloodBarrierModel extends EntityModel<LivingEntityRenderState> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(Nycto.id("blood_barrier"), "main");

	public BloodBarrierModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		PartDefinition ring = root.addOrReplaceChild("ring", CubeListBuilder.create().texOffs(0, 0).addBox(-11, -2, -11, 22, 3, 22, CubeDeformation.NONE), PartPose.offset(0, 23, 0));
		ring.addOrReplaceChild("ring_flat", CubeListBuilder.create().texOffs(-33, 26).addBox(-19, -1, -19, 38, 0, 38, CubeDeformation.NONE), PartPose.offset(0, 0, 0));
		return LayerDefinition.create(mesh, 128, 64);
	}
}
