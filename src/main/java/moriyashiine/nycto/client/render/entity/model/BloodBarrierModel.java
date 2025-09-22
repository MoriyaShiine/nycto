/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.model;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public class BloodBarrierModel extends EntityModel<LivingEntityRenderState> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Nycto.id("blood_barrier"), "main");

	public BloodBarrierModel(ModelPart root) {
		super(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();
		ModelPartData ring = root.addChild("ring", ModelPartBuilder.create().uv(0, 0).cuboid(-11, -2, -11, 22, 3, 22, Dilation.NONE), ModelTransform.origin(0, 23, 0));
		ring.addChild("ring_flat", ModelPartBuilder.create().uv(-33, 26).cuboid(-19, -1, -19, 38, 0, 38, Dilation.NONE), ModelTransform.origin(0, 0, 0));
		return TexturedModelData.of(data, 128, 64);
	}
}
