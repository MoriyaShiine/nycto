/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.render.armor.model;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModComponentTypes;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.EquipmentModelData;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.joml.Quaternionf;

import java.util.Set;

public class VampireArmorModel<S extends BipedEntityRenderState> extends BipedEntityModel<S> {
	public static final EquipmentModelData<EntityModelLayer> MODEL_LAYERS = new EquipmentModelData<>("helmet", "chestplate", "leggings", "boots").map(s -> new EntityModelLayer(Nycto.id("vampire_armor"), s));

	public final ModelPart coatFlap;
	public final ModelPart cape;
	public final ModelPart capeMain;

	public VampireArmorModel(ModelPart root) {
		super(root);
		coatFlap = body.getChild("coat_flap");
		cape = body.getChild("cape");
		capeMain = cape.getChild("cape_main");
	}

	public static EquipmentModelData<TexturedModelData> getTexturedModelData() {
		ModelData head = getModelData();
		head.getRoot().resetChildrenExcept(Set.of(EntityModelPartNames.HEAD));
		ModelData body = getModelData();
		body.getRoot().resetChildrenExcept(Set.of(EntityModelPartNames.BODY, EntityModelPartNames.LEFT_ARM, EntityModelPartNames.RIGHT_ARM));
		ModelData legs = getModelData();
		legs.getRoot().resetChildrenExcept(Set.of("left_leg_real", "right_leg_real"));
		ModelData feet = getModelData();
		feet.getRoot().resetChildrenExcept(Set.of(EntityModelPartNames.LEFT_FOOT, EntityModelPartNames.RIGHT_FOOT));
		EquipmentModelData<ModelData> data = new EquipmentModelData<>(head, body, legs, feet);
		return data.map(d -> TexturedModelData.of(d, 128, 128));
	}

	private static ModelData getModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(66, 0).cuboid(-4, -12, -4, 8, 6, 8, new Dilation(0.55F)).uv(0, 112).cuboid(-7, -5.5F, -7, 14, 1, 14, Dilation.NONE), ModelTransform.NONE);
		head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);

		ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(106, 86).cuboid(-5, -0.25F, -2.25F, 10, 12, 1, new Dilation(0.35F)).uv(74, 32).cuboid(-4, 0, -2, 8, 12, 4, new Dilation(0.5F)).uv(0, 95).cuboid(-4, 0, -2, 8, 12, 4, new Dilation(0.35F)), ModelTransform.NONE);
		body.addChild("right_collar", ModelPartBuilder.create().uv(0, 81).mirrored().cuboid(-5, -1, -1, 6, 4, 6, Dilation.NONE).mirrored(false), ModelTransform.of(-0.5F, -2.75F, 0.5F, -0.5672F, -0.1745F, -0.1745F));
		body.addChild("left_collar", ModelPartBuilder.create().uv(0, 81).cuboid(-1, -1, -1, 6, 4, 6, Dilation.NONE), ModelTransform.of(0.5F, -2.75F, 0.5F, -0.5672F, 0.1745F, 0.1745F));
		body.addChild("scarf_1", ModelPartBuilder.create().uv(92, 1).cuboid(-1.5F, 0.5F, -2.4F, 3, 4, 2, Dilation.NONE), ModelTransform.of(0, 0, 0, -0.3491F, 0, 0));
		body.addChild("scarf_2", ModelPartBuilder.create().uv(102, 1).cuboid(-1, 1.5F, -2.4F, 2, 4, 2, Dilation.NONE), ModelTransform.of(0, 0, 0, -0.1745F, 0, 0));
		ModelPartData cape = body.addChild("cape", ModelPartBuilder.create().uv(63, 82).cuboid(-5.5F, -1, -5, 11, 3, 6, new Dilation(-0.1F)), ModelTransform.origin(0, 0, 2));
		ModelPartData cape_main = cape.addChild("cape_main", ModelPartBuilder.create().uv(66, 93).cuboid(-7.5F, -1, -1, 15, 14, 2, Dilation.NONE), ModelTransform.of(0, 0, 0, 0.0873F, 0, 0));
		ModelPartData cape_lower = cape_main.addChild("cape_lower", ModelPartBuilder.create(), ModelTransform.origin(0, 12.5F, 0));
		cape_lower.addChild("cape_cube", ModelPartBuilder.create().uv(66, 111).cuboid(-8, 0, -1, 16, 9, 2, Dilation.NONE), ModelTransform.of(0, 0, 0, 0.1309F, 0, 0));

		body.addChild("coat_flap", ModelPartBuilder.create().uv(72, 50).cuboid(-4.5F, -0.8F, 0, 9, 9, 5, new Dilation(0.35F)), ModelTransform.origin(0, 12.5F, -2.5F));

		root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(98, 16).mirrored().cuboid(0, -2, -2, 4, 12, 4, new Dilation(0.32F)).mirrored(false).uv(102, 37).mirrored().cuboid(-0.5F, 6.5F, -2.5F, 5, 2, 5, new Dilation(0.1F)).mirrored(false), ModelTransform.origin(5, 2, 0));
		root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(98, 16).cuboid(-4, -2, -2, 4, 12, 4, new Dilation(0.32F)).uv(102, 37).cuboid(-4.5F, 6.5F, -2.5F, 5, 2, 5, new Dilation(0.1F)), ModelTransform.origin(-5, 2, 0));

		ModelPartData leftLeg = root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(1.9F, 12, 0));
		leftLeg.addChild("left_leg_real", ModelPartBuilder.create().uv(58, 16).mirrored().cuboid(-2.1F, 0, -2, 4, 12, 4, new Dilation(0.35F)).mirrored(false), ModelTransform.NONE);
		leftLeg.addChild(EntityModelPartNames.LEFT_FOOT, ModelPartBuilder.create().uv(79, 65).mirrored().cuboid(-2.1F, 0, -2, 4, 12, 4, new Dilation(0.355F)).mirrored(false).uv(107, 103).cuboid(-2.6F, 6, -2.5F, 5, 2, 5, new Dilation(0.2F)), ModelTransform.NONE);

		ModelPartData rightLeg = root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(-1.9F, 12, 0));
		rightLeg.addChild("right_leg_real", ModelPartBuilder.create().uv(58, 16).cuboid(-1.9F, 0, -2, 4, 12, 4, new Dilation(0.35F)), ModelTransform.NONE);
		rightLeg.addChild(EntityModelPartNames.RIGHT_FOOT, ModelPartBuilder.create().uv(79, 65).cuboid(-1.9F, 0, -2, 4, 12, 4, new Dilation(0.355F)).uv(107, 103).mirrored().cuboid(-2.4F, 6, -2.5F, 5, 2, 5, new Dilation(0.2F)).mirrored(false), ModelTransform.NONE);

		return data;
	}

	@Override
	public void setAngles(S state) {
		coatFlap.pitch = Math.max(leftLeg.pitch, rightLeg.pitch);
		if (state.sneaking) {
			coatFlap.pitch /= 2;
		}
		if (state instanceof PlayerEntityRenderState playerRenderState) {
			capeMain.rotate(
					new Quaternionf()
							.rotateY((float) Math.PI)
							.rotateX(-(6 + playerRenderState.field_53537 / 2 + playerRenderState.field_53536) * (float) (Math.PI / 180))
							.rotateZ(-playerRenderState.field_53538 / 2 * (float) (Math.PI / 180))
							.rotateY(-(180 - playerRenderState.field_53538 / 2) * (float) (Math.PI / 180))
			);
		}

		cape.visible = state.equippedChestStack.getOrDefault(ModComponentTypes.SHOW_CAPE, false);
	}
}
