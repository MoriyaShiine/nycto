/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.armor.model;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.animal.equine.AbstractEquineModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HorseRenderState;

public class ThralledHorseHornsModel extends AbstractEquineModel<HorseRenderState> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Nycto.id("thralled_horse_horns"), "main");

	public ThralledHorseHornsModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition data = new MeshDefinition();
		PartDefinition root = data.getRoot();

		PartDefinition headParts = root.addOrReplaceChild("head_parts", CubeListBuilder.create(), PartPose.offsetAndRotation(0, 4, -12, (float) (Math.PI / 6), 0, 0));
		PartDefinition head = headParts.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0, 11, 5));
		headParts.addOrReplaceChild(PartNames.MANE, CubeListBuilder.create(), PartPose.ZERO);
		headParts.addOrReplaceChild(PartNames.UPPER_MOUTH, CubeListBuilder.create(), PartPose.ZERO);
		root.addOrReplaceChild(PartNames.LEFT_HIND_LEG, CubeListBuilder.create(), PartPose.offset(4, 14, 7));
		root.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, CubeListBuilder.create(), PartPose.offset(-4, 14, 7));
		root.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, CubeListBuilder.create(), PartPose.offset(4, 14, -10));
		root.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, CubeListBuilder.create(), PartPose.offset(-4, 14, -10));
		head.addOrReplaceChild(PartNames.LEFT_EAR, CubeListBuilder.create(), PartPose.ZERO);
		head.addOrReplaceChild(PartNames.RIGHT_EAR, CubeListBuilder.create(), PartPose.ZERO);
		body.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create(), PartPose.offsetAndRotation(0, -5, 2, (float) (Math.PI / 6), 0, 0));

		PartDefinition leftHorn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(58, 1).addBox(0, -4, 0, 1, 4, 1, CubeDeformation.NONE), PartPose.offsetAndRotation(1.25F, -11, 2.75F, -0.48F, 0, 0.0873F));
		PartDefinition rightHorn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(58, 1).mirror().addBox(-1, -4, 0, 1, 4, 1, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-1.25F, -11, 2.75F, -0.48F, 0, -0.0873F));
		PartDefinition middleHorn = head.addOrReplaceChild("middle_horn", CubeListBuilder.create(), PartPose.offset(0, -6.45F, 5));

		leftHorn.addOrReplaceChild("left_horn_tip", CubeListBuilder.create().texOffs(20, 26).addBox(0, -5, -1, 1, 5, 1, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0, -3.134F, 0.5F, -1.0472F, 0, 0));
		rightHorn.addOrReplaceChild("right_horn_tip", CubeListBuilder.create().texOffs(20, 26).mirror().addBox(-1, -5, -1, 1, 5, 1, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(0, -3.134F, 0.5F, -1.0472F, 0, 0));
		middleHorn.addOrReplaceChild("middle_horn_tip_1", CubeListBuilder.create().texOffs(17, 35).addBox(-0.5F, -4, -0.5F, 1, 4, 1, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-1, -4.634F, -3.75F, -0.0873F, 3.1416F, -0.0873F));
		middleHorn.addOrReplaceChild("middle_horn_tip_2", CubeListBuilder.create().texOffs(17, 35).addBox(-0.5F, -4, -0.5F, 1, 4, 1, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(1, -4.634F, -3.75F, -0.0873F, 3.1416F, 0.0873F));
		middleHorn.addOrReplaceChild("middle_horn_tip_3", CubeListBuilder.create().texOffs(17, 35).addBox(-0.5F, -4, -0.5F, 1, 4, 1, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0, -4.634F, -2.25F, 0, 3.1416F, 0));

		return LayerDefinition.create(data, 64, 64);
	}
}
