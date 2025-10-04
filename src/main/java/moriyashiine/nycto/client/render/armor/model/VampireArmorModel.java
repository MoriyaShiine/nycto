/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.armor.model;

import moriyashiine.nycto.client.render.armor.VampireArmorRenderer;
import moriyashiine.nycto.client.util.NyctoClientUtil;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerSkinType;

public class VampireArmorModel<S extends BipedEntityRenderState> extends BipedEntityModel<S> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Nycto.id("vampire_armor"), "main");

	public final ModelPart bodyReal;
	public final ModelPart coatFlap;
	public final ModelPart underCoat;
	public final ModelPart leftArmThick;
	public final ModelPart leftArmThin;
	public final ModelPart rightArmThick;
	public final ModelPart rightArmThin;
	public final ModelPart leftLegReal;
	public final ModelPart rightLegReal;
	public final ModelPart leftFoot;
	public final ModelPart rightFoot;

	public VampireArmorModel(ModelPart root) {
		super(root);
		bodyReal = root.getChild(EntityModelPartNames.BODY).getChild("body_real");
		coatFlap = root.getChild(EntityModelPartNames.BODY).getChild("body_real").getChild("coat_flap");
		underCoat = root.getChild(EntityModelPartNames.BODY).getChild("under_coat");
		leftArmThick = root.getChild(EntityModelPartNames.LEFT_ARM).getChild("left_arm_thick");
		leftArmThin = root.getChild(EntityModelPartNames.LEFT_ARM).getChild("left_arm_thin");
		rightArmThick = root.getChild(EntityModelPartNames.RIGHT_ARM).getChild("right_arm_thick");
		rightArmThin = root.getChild(EntityModelPartNames.RIGHT_ARM).getChild("right_arm_thin");
		leftLegReal = root.getChild(EntityModelPartNames.LEFT_LEG).getChild("left_leg_real");
		rightLegReal = root.getChild(EntityModelPartNames.RIGHT_LEG).getChild("right_leg_real");
		leftFoot = root.getChild(EntityModelPartNames.LEFT_LEG).getChild("left_foot");
		rightFoot = root.getChild(EntityModelPartNames.RIGHT_LEG).getChild("right_foot");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(75, 111).cuboid(-4, -9, -4, 8, 3, 8, new Dilation(0.55F)).uv(39, 111).cuboid(-6, -5.5F, -6, 12, 0, 12, Dilation.NONE), ModelTransform.NONE);
		head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);

		ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.NONE);
		ModelPartData bodyReal = body.addChild("body_real", ModelPartBuilder.create().uv(74, 16).cuboid(-4, 0, -2, 8, 12, 4, new Dilation(0.33F)).uv(74, 32).cuboid(-4, 0, -2, 8, 12, 4, new Dilation(0.35F)), ModelTransform.NONE);
		bodyReal.addChild("coat_flap", ModelPartBuilder.create().uv(74, 48).cuboid(-4, 0.2F, 0.5F, 8, 6, 4, new Dilation(0.45F)), ModelTransform.origin(0, 12.5F, -2.5F));
		body.addChild("under_coat", ModelPartBuilder.create().uv(74, 16).cuboid(-4, -24, -2, 8, 12, 4, new Dilation(0.33F)), ModelTransform.origin(0, 24, 0));

		ModelPartData leftArm = root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(5, 2, 0));
		leftArm.addChild("left_arm_thick", ModelPartBuilder.create().uv(98, 16).mirrored().cuboid(3, -24, -2, 4, 12, 4, new Dilation(0.32F)).mirrored(false), ModelTransform.origin(-4, 22, 0));
		leftArm.addChild("left_arm_thin", ModelPartBuilder.create().uv(114, 16).mirrored().cuboid(3, -24, -2, 3, 12, 4, new Dilation(0.32F)).mirrored(false), ModelTransform.origin(-4, 22, 0));

		ModelPartData rightArm = root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(-5, 2, 0));
		rightArm.addChild("right_arm_thick", ModelPartBuilder.create().uv(98, 16).cuboid(-7, -24, -2, 4, 12, 4, new Dilation(0.32F)), ModelTransform.origin(4, 22, 0));
		rightArm.addChild("right_arm_thin", ModelPartBuilder.create().uv(114, 16).cuboid(-6, -24, -2, 3, 12, 4, new Dilation(0.32F)), ModelTransform.origin(4, 22, 0));

		ModelPartData leftLeg = root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(1.9F, 12, 0));
		leftLeg.addChild("left_leg_real", ModelPartBuilder.create().uv(58, 16).mirrored().cuboid(-2.1F, 0, -2, 4, 12, 4, new Dilation(0.35F)).mirrored(false), ModelTransform.NONE);
		leftLeg.addChild("left_foot", ModelPartBuilder.create().uv(98, 33).mirrored().cuboid(-2.1F, 10, -3, 4, 2, 5, new Dilation(0.36F)).mirrored(false), ModelTransform.NONE);

		ModelPartData rightLeg = root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(-1.9F, 12, 0));
		rightLeg.addChild("right_leg_real", ModelPartBuilder.create().uv(58, 16).cuboid(-1.9F, 0, -2, 4, 12, 4, new Dilation(0.35F)), ModelTransform.NONE);
		rightLeg.addChild("right_foot", ModelPartBuilder.create().uv(98, 33).cuboid(-1.9F, 10, -3, 4, 2, 5, new Dilation(0.36F)), ModelTransform.NONE);

		return TexturedModelData.of(data, 128, 128);
	}

	@Override
	public void setAngles(S state) {
		super.setAngles(state);
		coatFlap.pitch = Math.max(leftLeg.pitch, rightLeg.pitch);
		if (state.sneaking) {
			coatFlap.pitch /= 3;
		}

		boolean slim = state instanceof PlayerEntityRenderState playerState && playerState.skinTextures.model() == PlayerSkinType.SLIM;
		leftArmThick.visible = rightArmThick.visible = !slim;
		leftArmThin.visible = rightArmThin.visible = slim;

		head.visible = NyctoClientUtil.isArmorRenderer(state, EquipmentSlot.HEAD, r -> r instanceof VampireArmorRenderer);
		bodyReal.visible = leftArm.visible = rightArm.visible = NyctoClientUtil.isArmorRenderer(state, EquipmentSlot.CHEST, r -> r instanceof VampireArmorRenderer);
		leftLegReal.visible = rightLegReal.visible = underCoat.visible = NyctoClientUtil.isArmorRenderer(state, EquipmentSlot.LEGS, r -> r instanceof VampireArmorRenderer);
		leftFoot.visible = rightFoot.visible = NyctoClientUtil.isArmorRenderer(state, EquipmentSlot.FEET, r -> r instanceof VampireArmorRenderer);

	}
}
