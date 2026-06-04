/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.model;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;

public final class BloodBarrierModel<T extends LivingEntity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(NyctoNeoForge.id("blood_barrier"), "main");

	private final ModelPart root;

	public BloodBarrierModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		PartDefinition ring = root.addOrReplaceChild("ring", CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-11, -2, -11, 22, 3, 22, CubeDeformation.NONE), PartPose.offset(0, 23, 0));
		ring.addOrReplaceChild("ring_flat", CubeListBuilder.create()
				.texOffs(-33, 26)
				.addBox(-19, -1, -19, 38, 0, 38, CubeDeformation.NONE), PartPose.ZERO);
		return LayerDefinition.create(mesh, 128, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(com.mojang.blaze3d.vertex.PoseStack poseStack, com.mojang.blaze3d.vertex.VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
