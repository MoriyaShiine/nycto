/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.entity.model;

import moriyashiine.nycto.client.render.entity.state.HunterEntityRenderState;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class HunterEntityModel extends IllagerEntityModel<HunterEntityRenderState> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(Nycto.id("hunter"), "main");

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart coatFlap;

	public HunterEntityModel(ModelPart root) {
		super(root);
		head = root.getChild(EntityModelPartNames.HEAD);
		body = root.getChild(EntityModelPartNames.BODY);
		leftArm = root.getChild(EntityModelPartNames.LEFT_ARM);
		rightArm = root.getChild(EntityModelPartNames.RIGHT_ARM);
		leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);
		rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
		coatFlap = root.getChild(EntityModelPartNames.BODY).getChild("body_armor").getChild("coat_flap");
	}

	public static TexturedModelData getHunterTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4, -10, -4, 8, 10, 8, Dilation.NONE), ModelTransform.NONE);
		ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(18, 22).cuboid(-4, 0, -2, 8, 12, 4, Dilation.NONE), ModelTransform.NONE);
		root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 46).cuboid(-1, -2, -2, 4, 12, 4, Dilation.NONE).uv(98, 16).mirrored().cuboid(-1, -2, -2, 4, 12, 4, new Dilation(0.32F)).mirrored(false), ModelTransform.origin(5, 2, 0));
		root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 46).cuboid(-3, -2, -2, 4, 12, 4, Dilation.NONE).uv(98, 16).cuboid(-3, -2, -2, 4, 12, 4, new Dilation(0.32F)), ModelTransform.origin(-5, 2, 0));
		root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 22).cuboid(-2, 0, -2, 4, 12, 4, Dilation.NONE).uv(79, 65).mirrored().cuboid(-2.1F, 0, -2, 4, 12, 4, new Dilation(0.32F)).mirrored(false).uv(58, 16).mirrored().cuboid(-2.1F, 0, -2, 4, 12, 4, new Dilation(0.3F)).mirrored(false), ModelTransform.origin(2, 12, 0));
		root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 22).cuboid(-2, 0, -2, 4, 12, 4, Dilation.NONE).uv(79, 65).cuboid(-1.9F, 0, -2, 4, 12, 4, new Dilation(0.32F)).uv(58, 16).cuboid(-1.9F, 0, -2, 4, 12, 4, new Dilation(0.3F)), ModelTransform.origin(-2, 12, 0));
		root.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create(), ModelTransform.NONE);
		head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);
		head.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create().uv(24, 0).cuboid(-1, -1.3F, -1, 2, 4, 2, Dilation.NONE), ModelTransform.origin(0, -1.7F, -5.1F));

		ModelPartData headArmor = head.addChild("head_armor", ModelPartBuilder.create().uv(65, 0).cuboid(-4, -8, -4, 8, 8, 8, new Dilation(0.55F)).uv(0, 64).cuboid(-7, -9.2F, -11, 14, 3, 18, Dilation.NONE).uv(4, 108).cuboid(-6, -10, -6, 12, 4, 6, new Dilation(0.01F)).uv(28, 102).cuboid(-2, -10, -8, 4, 4, 2, Dilation.NONE).uv(0, 118).cuboid(-7, -10, 0, 14, 4, 6, Dilation.NONE), ModelTransform.NONE);
		headArmor.addChild("cocked_hat_1", ModelPartBuilder.create().uv(56, 88).cuboid(-8, -33, 0.3F, 14, 4, 6, Dilation.NONE), ModelTransform.of(0, 23, 0, 0, 1.8326F, 0));
		headArmor.addChild("cocked_hat_2", ModelPartBuilder.create().uv(56, 88).cuboid(-6, -33, 0.3F, 14, 4, 6, Dilation.NONE), ModelTransform.of(0, 23, 0, 0, -1.8326F, 0));
		ModelPartData hatFlower = headArmor.addChild("hat_flower", ModelPartBuilder.create().uv(101, 105).cuboid(6.5F, -1, -2, 0, 4, 3, Dilation.NONE), ModelTransform.origin(-1, -5, 3));
		hatFlower.addChild("hat_flower_cube", ModelPartBuilder.create().uv(100, 65).cuboid(-1, -9, -6, 0, 8, 7, Dilation.NONE), ModelTransform.of(-2.75F, 1.5F, -2, -0.3491F, 0, 0));

		ModelPartData bodyArmor = body.addChild("body_armor", ModelPartBuilder.create().uv(74, 32).cuboid(-4, 0, -2, 8, 12, 4, new Dilation(0.35F)).uv(100, 55).cuboid(-4.5F, 10, -2.5F, 9, 3, 5, new Dilation(0.1F)).uv(106, 86).cuboid(-5, 0, -2.25F, 10, 12, 1, new Dilation(0.35F)), ModelTransform.NONE);
		bodyArmor.addChild("chest_belt", ModelPartBuilder.create().uv(100, 51).cuboid(-5, -2, -3.5F, 3, 3, 1, Dilation.NONE).uv(100, 51).cuboid(-5, 7, -3.5F, 3, 3, 1, Dilation.NONE).uv(99, 32).cuboid(-5, -2, -2.5F, 3, 14, 5, Dilation.NONE), ModelTransform.of(0, 0, 0, 0, 0, -0.5672F));
		bodyArmor.addChild("coat_flap", ModelPartBuilder.create().uv(74, 48).cuboid(-4, 0.2F, 0.5F, 8, 8, 4, new Dilation(0.35F)), ModelTransform.origin(0, 12.5F, -2.5F));
		ModelPartData chestFlowers = bodyArmor.addChild("chest_flowers", ModelPartBuilder.create().uv(115, 16).cuboid(-1.25F, 0, -0.75F, 4, 5, 0, Dilation.NONE).uv(115, 23).cuboid(-8.25F, -6.75F, 4.76F, 4, 5, 0, Dilation.NONE).uv(115, 23).cuboid(-6.25F, -3.75F, -0.49F, 4, 5, 0, Dilation.NONE).uv(102, 80).cuboid(-5.5F, -8, -0.75F, 5, 6, 0, Dilation.NONE), ModelTransform.origin(2, 10, -2.25F));
		chestFlowers.addChild("chest_flowers_cube", ModelPartBuilder.create().uv(115, 23).cuboid(-2, -0.0019F, -0.9872F, 4, 5, 0, Dilation.NONE), ModelTransform.of(-3, -3.75F, 5.75F, 0.0436F, 0, 0));
		body.addChild("scarf", ModelPartBuilder.create().uv(34, 0).cuboid(-1.5F, 0.5F, -2.4F, 3, 4, 2, Dilation.NONE), ModelTransform.of(0, 0.25F, 0, -0.3491F, 0, 0));

		root.addChild("robe", ModelPartBuilder.create().uv(0, 39).cuboid(-4, 0, -3, 8, 19, 6, new Dilation(0.25F)), ModelTransform.NONE);

		return TexturedModelData.of(data, 128, 128);
	}

	@Override
	public void setAngles(HunterEntityRenderState state) {
		super.setAngles(state);
		float handSwingProgress = state.handSwingProgress;
		if (handSwingProgress > 0) {
			Arm arm = state.illagerMainArm;
			ModelPart modelPart = arm == Arm.RIGHT ? rightArm : leftArm;
			body.yaw = MathHelper.sin(MathHelper.sqrt(handSwingProgress) * (float) (Math.PI * 2)) * 0.2F;
			if (arm == Arm.LEFT) {
				body.yaw *= -1;
			}
			rightArm.originZ = MathHelper.sin(body.yaw) * 5 * state.ageScale;
			rightArm.originX = -MathHelper.cos(body.yaw) * 5 * state.ageScale;
			leftArm.originZ = -MathHelper.sin(body.yaw) * 5 * state.ageScale;
			leftArm.originX = MathHelper.cos(body.yaw) * 5 * state.ageScale;
			rightArm.yaw = rightArm.yaw + body.yaw;
			leftArm.yaw = leftArm.yaw + body.yaw;
			leftArm.pitch = leftArm.pitch + body.yaw;
			modelPart.pitch -= MathHelper.sin((float) ((1 - Math.pow(1 - handSwingProgress, 3)) * Math.PI)) * 1.2F + MathHelper.sin(handSwingProgress * (float) Math.PI) * -(head.pitch - 0.7F) * 0.75F;
			modelPart.yaw = modelPart.yaw + body.yaw * 2;
			modelPart.roll = modelPart.roll + MathHelper.sin(handSwingProgress * (float) Math.PI) * -0.4F;
		}
		coatFlap.pitch = Math.max(leftLeg.pitch, rightLeg.pitch);
	}
}
