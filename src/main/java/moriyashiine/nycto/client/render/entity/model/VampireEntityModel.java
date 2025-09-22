/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.model;

import moriyashiine.nycto.client.render.entity.state.VampireEntityRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class VampireEntityModel extends BipedEntityModel<VampireEntityRenderState> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Nycto.id("vampire"), "main");

	private final ModelPart arms;

	public VampireEntityModel(ModelPart root) {
		super(root);
		arms = root.getChild(EntityModelPartNames.ARMS);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4, -10, -4, 8, 10, 8, Dilation.NONE), ModelTransform.origin(0, 0, 0));
		root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 20).cuboid(-4, 0, -3, 8, 12, 6, Dilation.NONE), ModelTransform.origin(0, 0, 0));
		ModelPartData leftArm = root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(36, 46).cuboid(-1, -2, -2, 3, 12, 4, Dilation.NONE).uv(50, 46).cuboid(-1, -2, -2, 3, 12, 4, new Dilation(0.25F)), ModelTransform.origin(5, 2, 0));
		ModelPartData rightArm = root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(36, 46).cuboid(-2, -2, -2, 3, 12, 4, Dilation.NONE).uv(50, 46).cuboid(-2, -2, -2, 3, 12, 4, new Dilation(0.25F)), ModelTransform.origin(-5, 2, 0));
		root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 22).cuboid(-2, 0, -2, 4, 12, 4, Dilation.NONE), ModelTransform.origin(2, 12, 0));
		root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 22).cuboid(-2, 0, -2, 4, 12, 4, Dilation.NONE), ModelTransform.origin(-2, 12, 0));
		root.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(44, 22).cuboid(4, -2, -2, 3, 8, 4, Dilation.NONE).uv(44, 22).cuboid(-7, -2, -2, 3, 8, 4, Dilation.NONE).uv(40, 38).cuboid(-4, 2, -2, 8, 4, 4, Dilation.NONE), ModelTransform.of(0, 3, -1, -0.75F, 0, 0));
		head.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create().uv(34, 0).cuboid(0, -3.5F, 0, 0, 4, 6, Dilation.NONE), ModelTransform.of(-4, -2, 0, 0.48F, -0.3927F, -0.0873F));
		head.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create().uv(34, 0).cuboid(0, -3.5F, 0, 0, 4, 6, Dilation.NONE), ModelTransform.of(4, -2, 0, 0.48F, 0.3927F, 0.0873F));
		head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);
		head.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create().uv(24, 0).cuboid(-1, 0.2F, -2, 2, 4, 2, Dilation.NONE), ModelTransform.origin(0, -3.2F, -4.1F));

		root.addChild("robe", ModelPartBuilder.create().uv(0, 39).cuboid(-4, 0, -3, 8, 19, 6, new Dilation(0.25F)), ModelTransform.origin(0, 0, 0));
		leftArm.addChild("left_claw", ModelPartBuilder.create().uv(48, 0).cuboid(-2.25F, -1, -2.5F, 3, 4, 5, Dilation.NONE), ModelTransform.of(1, 10, 0, 0, 0, 0.1309F));
		rightArm.addChild("right_claw", ModelPartBuilder.create().uv(48, 0).mirrored().cuboid(-0.75F, -1, -2.5F, 3, 4, 5, Dilation.NONE).mirrored(false), ModelTransform.of(-1.25F, 10, 0, 0, 0, -0.1309F));

		return TexturedModelData.of(data, 64, 64);
	}

	@Override
	public void setAngles(VampireEntityRenderState state) {
		super.setAngles(state);
		leftArm.visible = rightArm.visible = state.attacking;
		arms.visible = !state.attacking;
	}
}
