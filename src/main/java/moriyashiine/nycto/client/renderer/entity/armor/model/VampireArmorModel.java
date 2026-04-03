/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.armor.model;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModComponentTypes;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.joml.Quaternionf;

import java.util.Set;

public class VampireArmorModel<S extends HumanoidRenderState> extends HumanoidModel<S> {
	public static final ArmorModelSet<ModelLayerLocation> MODEL_LAYERS = new ArmorModelSet<>("helmet", "chestplate", "leggings", "boots").map(s -> new ModelLayerLocation(Nycto.id("vampire_armor"), s));

	private final ModelPart coatFlap;
	private final ModelPart cape;
	private final ModelPart capeMain;

	public VampireArmorModel(ModelPart root) {
		super(root);
		coatFlap = body.getChild("coat_flap");
		cape = body.getChild("cape");
		capeMain = cape.getChild("cape_main");
	}

	public static ArmorModelSet<LayerDefinition> createArmorMeshSet() {
		MeshDefinition head = createBaseArmorMesh();
		head.getRoot().retainPartsAndChildren(Set.of(PartNames.HEAD));
		MeshDefinition body = createBaseArmorMesh();
		body.getRoot().retainPartsAndChildren(Set.of(PartNames.BODY, PartNames.LEFT_ARM, PartNames.RIGHT_ARM));
		MeshDefinition legs = createBaseArmorMesh();
		legs.getRoot().retainPartsAndChildren(Set.of("left_leg_real", "right_leg_real"));
		MeshDefinition feet = createBaseArmorMesh();
		feet.getRoot().retainPartsAndChildren(Set.of(PartNames.LEFT_FOOT, PartNames.RIGHT_FOOT));
		ArmorModelSet<MeshDefinition> data = new ArmorModelSet<>(head, body, legs, feet);
		return data.map(d -> LayerDefinition.create(d, 128, 128));
	}

	private static MeshDefinition createBaseArmorMesh() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition head = root.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(66, 0).addBox(-4, -12, -4, 8, 6, 8, new CubeDeformation(0.55F)).texOffs(0, 112).addBox(-7, -5.5F, -7, 14, 1, 14, CubeDeformation.NONE), PartPose.ZERO);
		head.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(106, 86).addBox(-5, -0.25F, -2.25F, 10, 12, 1, new CubeDeformation(0.35F)).texOffs(74, 32).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.5F)).texOffs(0, 95).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.35F)), PartPose.ZERO);
		body.addOrReplaceChild("right_collar", CubeListBuilder.create().texOffs(0, 81).mirror().addBox(-5, -1, -1, 6, 4, 6, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-0.5F, -2.75F, 0.5F, -0.5672F, -0.1745F, -0.1745F));
		body.addOrReplaceChild("left_collar", CubeListBuilder.create().texOffs(0, 81).addBox(-1, -1, -1, 6, 4, 6, CubeDeformation.NONE), PartPose.offsetAndRotation(0.5F, -2.75F, 0.5F, -0.5672F, 0.1745F, 0.1745F));
		body.addOrReplaceChild("scarf_1", CubeListBuilder.create().texOffs(92, 1).addBox(-1.5F, 0.5F, -2.4F, 3, 4, 2, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, -0.3491F, 0, 0));
		body.addOrReplaceChild("scarf_2", CubeListBuilder.create().texOffs(102, 1).addBox(-1, 1.5F, -2.4F, 2, 4, 2, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, -0.1745F, 0, 0));
		PartDefinition cape = body.addOrReplaceChild("cape", CubeListBuilder.create().texOffs(63, 82).addBox(-5.5F, -1, -5, 11, 3, 6, new CubeDeformation(-0.1F)), PartPose.offset(0, 0, 2));
		PartDefinition cape_main = cape.addOrReplaceChild("cape_main", CubeListBuilder.create().texOffs(66, 93).addBox(-7.5F, -1, -1, 15, 14, 2, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, 0.0873F, 0, 0));
		PartDefinition cape_lower = cape_main.addOrReplaceChild("cape_lower", CubeListBuilder.create(), PartPose.offset(0, 12.5F, 0));
		cape_lower.addOrReplaceChild("cape_cube", CubeListBuilder.create().texOffs(66, 111).addBox(-8, 0, -1, 16, 9, 2, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, 0.1309F, 0, 0));

		body.addOrReplaceChild("coat_flap", CubeListBuilder.create().texOffs(72, 50).addBox(-4.5F, -0.8F, 0, 9, 9, 5, new CubeDeformation(0.35F)), PartPose.offset(0, 12.5F, -2.5F));

		root.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(98, 16).mirror().addBox(-1, -2, -2, 4, 12, 4, new CubeDeformation(0.32F)).mirror(false).texOffs(102, 37).mirror().addBox(-1.5F, 6.5F, -2.5F, 5, 2, 5, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(5, 2, 0));
		root.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(98, 16).addBox(-3, -2, -2, 4, 12, 4, new CubeDeformation(0.32F)).texOffs(102, 37).addBox(-3.5F, 6.5F, -2.5F, 5, 2, 5, new CubeDeformation(0.1F)), PartPose.offset(-5, 2, 0));

		PartDefinition leftLeg = root.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(1.9F, 12, 0));
		leftLeg.addOrReplaceChild("left_leg_real", CubeListBuilder.create().texOffs(58, 16).mirror().addBox(-2.1F, 0, -2, 4, 12, 4, new CubeDeformation(0.35F)).mirror(false), PartPose.ZERO);
		leftLeg.addOrReplaceChild(PartNames.LEFT_FOOT, CubeListBuilder.create().texOffs(79, 65).mirror().addBox(-2.1F, 0, -2, 4, 12, 4, new CubeDeformation(0.355F)).mirror(false).texOffs(107, 103).addBox(-2.6F, 6, -2.5F, 5, 2, 5, new CubeDeformation(0.2F)), PartPose.ZERO);

		PartDefinition rightLeg = root.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(-1.9F, 12, 0));
		rightLeg.addOrReplaceChild("right_leg_real", CubeListBuilder.create().texOffs(58, 16).addBox(-1.9F, 0, -2, 4, 12, 4, new CubeDeformation(0.35F)), PartPose.ZERO);
		rightLeg.addOrReplaceChild(PartNames.RIGHT_FOOT, CubeListBuilder.create().texOffs(79, 65).addBox(-1.9F, 0, -2, 4, 12, 4, new CubeDeformation(0.355F)).texOffs(107, 103).mirror().addBox(-2.4F, 6, -2.5F, 5, 2, 5, new CubeDeformation(0.2F)).mirror(false), PartPose.ZERO);

		return mesh;
	}

	@Override
	public void setupAnim(S state) {
		coatFlap.xRot = Math.max(leftLeg.xRot, rightLeg.xRot);
		if (state.isDiscrete) {
			coatFlap.xRot /= 2;
		}
		if (state instanceof AvatarRenderState avatarRenderState) {
			capeMain.rotateBy(
					new Quaternionf()
							.rotateY((float) Math.PI)
							.rotateX(-(6 + avatarRenderState.capeLean / 2 + avatarRenderState.capeFlap) * (float) (Math.PI / 180))
							.rotateZ(-avatarRenderState.capeLean2 / 2 * (float) (Math.PI / 180))
							.rotateY(-(180 - avatarRenderState.capeLean2 / 2) * (float) (Math.PI / 180))
			);
		}

		cape.visible = state.chestEquipment.getOrDefault(ModComponentTypes.SHOW_CAPE, false);
	}
}
