/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.model;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.horse.Horse;

public final class ThralledHorseHornsModel extends HorseModel<Horse> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(NyctoNeoForge.id("thralled_horse_horns"), "main");

	public ThralledHorseHornsModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0, 11, 5));
		PartDefinition headParts = root.addOrReplaceChild("head_parts", CubeListBuilder.create(), PartPose.offsetAndRotation(0, 4, -12, (float) (Math.PI / 6), 0, 0));
		PartDefinition head = headParts.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
		headParts.addOrReplaceChild("mane", CubeListBuilder.create(), PartPose.ZERO);
		headParts.addOrReplaceChild("upper_mouth", CubeListBuilder.create(), PartPose.ZERO);
		headParts.addOrReplaceChild("left_saddle_mouth", CubeListBuilder.create(), PartPose.ZERO);
		headParts.addOrReplaceChild("right_saddle_mouth", CubeListBuilder.create(), PartPose.ZERO);
		headParts.addOrReplaceChild("left_saddle_line", CubeListBuilder.create(), PartPose.ZERO);
		headParts.addOrReplaceChild("right_saddle_line", CubeListBuilder.create(), PartPose.ZERO);
		headParts.addOrReplaceChild("head_saddle", CubeListBuilder.create(), PartPose.ZERO);
		headParts.addOrReplaceChild("mouth_saddle_wrap", CubeListBuilder.create(), PartPose.ZERO);
		body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.ZERO);
		body.addOrReplaceChild("saddle", CubeListBuilder.create(), PartPose.ZERO);
		root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(4, 14, 7));
		root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-4, 14, 7));
		root.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(4, 14, -12));
		root.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-4, 14, -12));
		root.addOrReplaceChild("left_hind_baby_leg", CubeListBuilder.create(), PartPose.offset(4, 14, 7));
		root.addOrReplaceChild("right_hind_baby_leg", CubeListBuilder.create(), PartPose.offset(-4, 14, 7));
		root.addOrReplaceChild("left_front_baby_leg", CubeListBuilder.create(), PartPose.offset(4, 14, -12));
		root.addOrReplaceChild("right_front_baby_leg", CubeListBuilder.create(), PartPose.offset(-4, 14, -12));
		PartDefinition leftHorn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(58, 1).addBox(0, -4, 0, 1, 4, 1, CubeDeformation.NONE), PartPose.offsetAndRotation(1.25F, -11, 2.75F, -0.48F, 0, 0.0873F));
		PartDefinition rightHorn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(58, 1).mirror().addBox(-1, -4, 0, 1, 4, 1, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-1.25F, -11, 2.75F, -0.48F, 0, -0.0873F));
		PartDefinition middleHorn = head.addOrReplaceChild("middle_horn", CubeListBuilder.create(), PartPose.offset(0, -6.45F, 5));
		leftHorn.addOrReplaceChild("left_horn_tip", CubeListBuilder.create().texOffs(20, 26).addBox(0, -5, -1, 1, 5, 1, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0, -3.134F, 0.5F, -1.0472F, 0, 0));
		rightHorn.addOrReplaceChild("right_horn_tip", CubeListBuilder.create().texOffs(20, 26).mirror().addBox(-1, -5, -1, 1, 5, 1, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(0, -3.134F, 0.5F, -1.0472F, 0, 0));
		middleHorn.addOrReplaceChild("middle_horn_tip_1", CubeListBuilder.create().texOffs(17, 35).addBox(-0.5F, -4, -0.5F, 1, 4, 1, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-1, -4.634F, -3.75F, -0.0873F, 3.1416F, -0.0873F));
		middleHorn.addOrReplaceChild("middle_horn_tip_2", CubeListBuilder.create().texOffs(17, 35).addBox(-0.5F, -4, -0.5F, 1, 4, 1, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(1, -4.634F, -3.75F, -0.0873F, 3.1416F, 0.0873F));
		middleHorn.addOrReplaceChild("middle_horn_tip_3", CubeListBuilder.create().texOffs(17, 35).addBox(-0.5F, -4, -0.5F, 1, 4, 1, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0, -4.634F, -2.25F, 0, 3.1416F, 0));
		return LayerDefinition.create(mesh, 64, 64);
	}
}
