/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.armor.model;

import moriyashiine.nycto.client.render.armor.HunterArmorRenderer;
import moriyashiine.nycto.client.util.NyctoClientUtil;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.EquipmentSlot;

public class HunterArmorModel<S extends BipedEntityRenderState> extends BipedEntityModel<S> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Nycto.id("hunter_armor"), "main");

	public final ModelPart coatFlap;
	public final ModelPart leftLegReal;
	public final ModelPart rightLegReal;
	public final ModelPart leftFoot;
	public final ModelPart rightFoot;

	public HunterArmorModel(ModelPart root) {
		super(root);
		coatFlap = root.getChild(EntityModelPartNames.BODY).getChild("coat_flap");
		leftLegReal = root.getChild(EntityModelPartNames.LEFT_LEG).getChild("left_leg_real");
		rightLegReal = root.getChild(EntityModelPartNames.RIGHT_LEG).getChild("right_leg_real");
		leftFoot = root.getChild(EntityModelPartNames.LEFT_LEG).getChild("left_foot");
		rightFoot = root.getChild(EntityModelPartNames.RIGHT_LEG).getChild("right_foot");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(65, 0).cuboid(-4, -8, -4, 8, 8, 8, new Dilation(0.55F)).uv(0, 64).cuboid(-7, -8.2F, -11, 14, 3, 18, Dilation.NONE).uv(4, 108).cuboid(-6, -9, -6, 12, 4, 6, new Dilation(0.01F)).uv(28, 102).cuboid(-2, -9, -8, 4, 4, 2, Dilation.NONE).uv(0, 118).cuboid(-7, -9, 0, 14, 4, 6, Dilation.NONE), ModelTransform.NONE);
		head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);
		head.addChild("cocked_hat_1", ModelPartBuilder.create().uv(56, 88).cuboid(-8, -33, 0.3F, 14, 4, 6, Dilation.NONE), ModelTransform.of(0, 24, 0, 0, 1.8326F, 0));
		head.addChild("cocked_hat_2", ModelPartBuilder.create().uv(56, 88).cuboid(-6, -33, 0.3F, 14, 4, 6, Dilation.NONE), ModelTransform.of(0, 24, 0, 0, -1.8326F, 0));
		ModelPartData hatFlower = head.addChild("hat_flower", ModelPartBuilder.create().uv(101, 105).cuboid(6.5F, 0, -2, 0, 4, 3, Dilation.NONE), ModelTransform.origin(-1, -5, 3));
		hatFlower.addChild("hat_flower_cube", ModelPartBuilder.create().uv(100, 65).cuboid(-1, -9, -6, 0, 8, 7, Dilation.NONE), ModelTransform.of(-2.75F, 2.5F, -2, -0.3491F, 0, 0));

		ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(74, 32).cuboid(-4, 0, -2, 8, 12, 4, new Dilation(0.35F)).uv(100, 55).cuboid(-4.5F, 10, -2.5F, 9, 3, 5, new Dilation(0.1F)).uv(106, 86).cuboid(-5, 0, -2.25F, 10, 12, 1, new Dilation(0.35F)), ModelTransform.NONE);
		body.addChild("chest_belt", ModelPartBuilder.create().uv(100, 51).cuboid(-5, -2, -3.5F, 3, 3, 1, Dilation.NONE).uv(100, 51).cuboid(-5, 7, -3.5F, 3, 3, 1, Dilation.NONE).uv(99, 32).cuboid(-5, -2, -2.5F, 3, 14, 5, Dilation.NONE), ModelTransform.of(0, 0, 0, 0, 0, -0.5672F));
		body.addChild("coat_flap", ModelPartBuilder.create().uv(74, 48).cuboid(-4, 0.2F, 0.5F, 8, 8, 4, new Dilation(0.45F)), ModelTransform.origin(0, 12.5F, -2.5F));
		ModelPartData chestFlowers = body.addChild("chest_flowers", ModelPartBuilder.create().uv(115, 16).cuboid(-1.25F, 0, -0.75F, 4, 5, 0, Dilation.NONE).uv(115, 23).cuboid(-8.25F, -6.75F, 4.76F, 4, 5, 0, Dilation.NONE).uv(115, 23).cuboid(-6.25F, -3.75F, -0.49F, 4, 5, 0, Dilation.NONE).uv(102, 80).cuboid(-5.5F, -8, -0.75F, 5, 6, 0, Dilation.NONE), ModelTransform.origin(2, 10, -2.25F));
		chestFlowers.addChild("chest_flowers_cube", ModelPartBuilder.create().uv(115, 23).cuboid(-2, -0.0019F, -0.9872F, 4, 5, 0, Dilation.NONE), ModelTransform.of(-3, -3.75F, 5.75F, 0.0436F, 0, 0));

		root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(98, 16).mirrored().cuboid(-1, -2, -2, 4, 12, 4, new Dilation(0.32F)).mirrored(false), ModelTransform.origin(5, 2, 0));
		root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(98, 16).cuboid(-3, -2, -2, 4, 12, 4, new Dilation(0.32F)), ModelTransform.origin(-5, 2, 0));

		ModelPartData leftLeg = root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(1.9F, 12, 0));
		leftLeg.addChild("left_leg_real", ModelPartBuilder.create().uv(58, 16).mirrored().cuboid(-2.1F, 0, -2, 4, 12, 4, new Dilation(0.35F)).mirrored(false), ModelTransform.NONE);
		leftLeg.addChild("left_foot", ModelPartBuilder.create().uv(79, 65).mirrored().cuboid(-2.1F, 0, -2, 4, 12, 4, new Dilation(0.36F)).mirrored(false), ModelTransform.NONE);

		ModelPartData rightLeg = root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(-1.9F, 12, 0));
		rightLeg.addChild("right_leg_real", ModelPartBuilder.create().uv(58, 16).cuboid(-1.9F, 0, -2, 4, 12, 4, new Dilation(0.35F)), ModelTransform.NONE);
		rightLeg.addChild("right_foot", ModelPartBuilder.create().uv(79, 65).cuboid(-1.9F, 0, -2, 4, 12, 4, new Dilation(0.36F)), ModelTransform.NONE);

		return TexturedModelData.of(data, 128, 128);
	}

	@Override
	public void setAngles(S state) {
		super.setAngles(state);
		coatFlap.pitch = Math.max(leftLeg.pitch, rightLeg.pitch);
		if (state.sneaking) {
			coatFlap.pitch /= 3;
		}

		head.visible = NyctoClientUtil.isArmorRenderer(state, EquipmentSlot.HEAD, r -> r instanceof HunterArmorRenderer);
		body.visible = leftArm.visible = rightArm.visible = NyctoClientUtil.isArmorRenderer(state, EquipmentSlot.CHEST, r -> r instanceof HunterArmorRenderer);
		leftLegReal.visible = rightLegReal.visible = NyctoClientUtil.isArmorRenderer(state, EquipmentSlot.LEGS, r -> r instanceof HunterArmorRenderer);
		leftFoot.visible = rightFoot.visible = NyctoClientUtil.isArmorRenderer(state, EquipmentSlot.FEET, r -> r instanceof HunterArmorRenderer);
	}
}
