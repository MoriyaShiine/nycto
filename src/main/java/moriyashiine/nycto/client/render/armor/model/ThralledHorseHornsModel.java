/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.armor.model;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AbstractHorseEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.HorseEntityRenderState;

public class ThralledHorseHornsModel extends AbstractHorseEntityModel<HorseEntityRenderState> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Nycto.id("thralled_horse_horns"), "main");

	public ThralledHorseHornsModel(ModelPart root) {
		super(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData headParts = root.addChild("head_parts", ModelPartBuilder.create(), ModelTransform.of(0, 4, -12, (float) (Math.PI / 6), 0, 0));
		ModelPartData head = headParts.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.NONE);
		ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0, 11, 5));
		headParts.addChild(EntityModelPartNames.MANE, ModelPartBuilder.create(), ModelTransform.NONE);
		headParts.addChild(EntityModelPartNames.UPPER_MOUTH, ModelPartBuilder.create(), ModelTransform.NONE);
		root.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create(), ModelTransform.origin(4, 14, 7));
		root.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create(), ModelTransform.origin(-4, 14, 7));
		root.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create(), ModelTransform.origin(4, 14, -10));
		root.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create(), ModelTransform.origin(-4, 14, -10));
		head.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create(), ModelTransform.NONE);
		head.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create(), ModelTransform.NONE);
		body.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create(), ModelTransform.of(0, -5, 2, (float) (Math.PI / 6), 0, 0));

		ModelPartData leftHorn = head.addChild("left_horn", ModelPartBuilder.create().uv(58, 1).cuboid(0, -4, 0, 1, 4, 1, Dilation.NONE), ModelTransform.of(1.25F, -11, 2.75F, -0.48F, 0, 0.0873F));
		ModelPartData rightHorn = head.addChild("right_horn", ModelPartBuilder.create().uv(58, 1).mirrored().cuboid(-1, -4, 0, 1, 4, 1, Dilation.NONE).mirrored(false), ModelTransform.of(-1.25F, -11, 2.75F, -0.48F, 0, -0.0873F));
		ModelPartData middleHorn = head.addChild("middle_horn", ModelPartBuilder.create(), ModelTransform.origin(0, -6.45F, 5));

		leftHorn.addChild("left_horn_tip", ModelPartBuilder.create().uv(20, 26).cuboid(0, -5, -1, 1, 5, 1, new Dilation(-0.01F)), ModelTransform.of(0, -3.134F, 0.5F, -1.0472F, 0, 0));
		rightHorn.addChild("right_horn_tip", ModelPartBuilder.create().uv(20, 26).mirrored().cuboid(-1, -5, -1, 1, 5, 1, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(0, -3.134F, 0.5F, -1.0472F, 0, 0));
		middleHorn.addChild("middle_horn_tip_1", ModelPartBuilder.create().uv(17, 35).cuboid(-0.5F, -4, -0.5F, 1, 4, 1, new Dilation(-0.01F)), ModelTransform.of(-1, -4.634F, -3.75F, -0.0873F, 3.1416F, -0.0873F));
		middleHorn.addChild("middle_horn_tip_2", ModelPartBuilder.create().uv(17, 35).cuboid(-0.5F, -4, -0.5F, 1, 4, 1, new Dilation(-0.01F)), ModelTransform.of(1, -4.634F, -3.75F, -0.0873F, 3.1416F, 0.0873F));
		middleHorn.addChild("middle_horn_tip_3", ModelPartBuilder.create().uv(17, 35).cuboid(-0.5F, -4, -0.5F, 1, 4, 1, new Dilation(-0.01F)), ModelTransform.of(0, -4.634F, -2.25F, 0, 3.1416F, 0));

		return TexturedModelData.of(data, 64, 64);
	}
}
