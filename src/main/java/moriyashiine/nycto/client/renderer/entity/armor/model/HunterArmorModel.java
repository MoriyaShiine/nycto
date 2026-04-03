/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.armor.model;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.world.item.MaskVisibility;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

import java.util.Set;

public class HunterArmorModel<S extends HumanoidRenderState> extends HumanoidModel<S> {
	public static final ArmorModelSet<ModelLayerLocation> MODEL_LAYERS = new ArmorModelSet<>("helmet", "chestplate", "leggings", "boots").map(s -> new ModelLayerLocation(Nycto.id("hunter_armor"), s));

	private final ModelPart normalMask, smallMask;
	private final ModelPart coatFlap;

	public HunterArmorModel(ModelPart root) {
		super(root);
		normalMask = root.getChild(PartNames.HEAD).getChild("mask").getChild("normal_mask");
		smallMask = root.getChild(PartNames.HEAD).getChild("mask").getChild("small_mask");
		coatFlap = root.getChild(PartNames.BODY).getChild("coat_flap");
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

		PartDefinition head = root.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(65, 0).addBox(-4, -8, -4, 8, 8, 8, new CubeDeformation(0.55F)).texOffs(0, 64).addBox(-7, -8.2F, -11, 14, 3, 18, CubeDeformation.NONE).texOffs(4, 108).addBox(-6, -9, -6, 12, 4, 6, new CubeDeformation(0.01F)).texOffs(28, 102).addBox(-2, -9, -8, 4, 4, 2, CubeDeformation.NONE).texOffs(0, 118).addBox(-7, -9, 0, 14, 4, 6, CubeDeformation.NONE), PartPose.ZERO);
		head.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.ZERO);
		head.addOrReplaceChild("cocked_hat_1", CubeListBuilder.create().texOffs(56, 88).addBox(-8, -33, 0.3F, 14, 4, 6, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 24, 0, 0, 1.8326F, 0));
		head.addOrReplaceChild("cocked_hat_2", CubeListBuilder.create().texOffs(56, 88).addBox(-6, -33, 0.3F, 14, 4, 6, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 24, 0, 0, -1.8326F, 0));
		PartDefinition hatFlower = head.addOrReplaceChild("hat_flower", CubeListBuilder.create().texOffs(101, 105).addBox(6.5F, 0, -2, 0, 4, 3, CubeDeformation.NONE), PartPose.offset(-1, -5, 3));
		hatFlower.addOrReplaceChild("hat_flower_cube", CubeListBuilder.create().texOffs(100, 65).addBox(-1, -9, -6, 0, 8, 7, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.75F, 2.5F, -2, -0.3491F, 0, 0));
		PartDefinition mask = head.addOrReplaceChild("mask", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		mask.addOrReplaceChild("normal_mask", CubeListBuilder.create().texOffs(60, 112).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.55F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		mask.addOrReplaceChild("small_mask", CubeListBuilder.create().texOffs(60, 112).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.55F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(74, 32).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.35F)).texOffs(100, 55).addBox(-4.5F, 10, -2.5F, 9, 3, 5, new CubeDeformation(0.1F)).texOffs(106, 86).addBox(-5, 0, -2.25F, 10, 12, 1, new CubeDeformation(0.35F)), PartPose.ZERO);
		body.addOrReplaceChild("chest_belt", CubeListBuilder.create().texOffs(100, 51).addBox(-5, -2, -3.5F, 3, 3, 1, CubeDeformation.NONE).texOffs(100, 51).addBox(-5, 7, -3.5F, 3, 3, 1, CubeDeformation.NONE).texOffs(99, 32).addBox(-5, -2, -2.5F, 3, 14, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, 0, 0, -0.5672F));
		body.addOrReplaceChild("coat_flap", CubeListBuilder.create().texOffs(74, 48).addBox(-4, 0.2F, 0.5F, 8, 8, 4, new CubeDeformation(0.45F)), PartPose.offset(0, 12.5F, -2.5F));
		PartDefinition chestFlowers = body.addOrReplaceChild("chest_flowers", CubeListBuilder.create().texOffs(115, 16).addBox(-1.25F, 0, -0.75F, 4, 5, 0, CubeDeformation.NONE).texOffs(115, 23).addBox(-8.25F, -6.75F, 4.76F, 4, 5, 0, CubeDeformation.NONE).texOffs(115, 23).addBox(-6.25F, -3.75F, -0.49F, 4, 5, 0, CubeDeformation.NONE).texOffs(102, 80).addBox(-5.5F, -8, -0.75F, 5, 6, 0, CubeDeformation.NONE), PartPose.offset(2, 10, -2.25F));
		chestFlowers.addOrReplaceChild("chest_flowers_cube", CubeListBuilder.create().texOffs(115, 23).addBox(-2, -0.0019F, -0.9872F, 4, 5, 0, CubeDeformation.NONE), PartPose.offsetAndRotation(-3, -3.75F, 5.75F, 0.0436F, 0, 0));

		root.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(98, 16).mirror().addBox(-1, -2, -2, 4, 12, 4, new CubeDeformation(0.32F)).mirror(false), PartPose.offset(5, 2, 0));
		root.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(98, 16).addBox(-3, -2, -2, 4, 12, 4, new CubeDeformation(0.32F)), PartPose.offset(-5, 2, 0));

		PartDefinition leftLeg = root.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(1.9F, 12, 0));
		leftLeg.addOrReplaceChild("left_leg_real", CubeListBuilder.create().texOffs(58, 16).mirror().addBox(-2.1F, 0, -2, 4, 12, 4, new CubeDeformation(0.35F)).mirror(false), PartPose.ZERO);
		leftLeg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(79, 65).mirror().addBox(-2.1F, 0, -2, 4, 12, 4, new CubeDeformation(0.36F)).mirror(false), PartPose.ZERO);

		PartDefinition rightLeg = root.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(-1.9F, 12, 0));
		rightLeg.addOrReplaceChild("right_leg_real", CubeListBuilder.create().texOffs(58, 16).addBox(-1.9F, 0, -2, 4, 12, 4, new CubeDeformation(0.35F)), PartPose.ZERO);
		rightLeg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(79, 65).addBox(-1.9F, 0, -2, 4, 12, 4, new CubeDeformation(0.36F)), PartPose.ZERO);

		return mesh;
	}

	@Override
	public void setupAnim(S state) {
		MaskVisibility maskVisibility = state.headEquipment.get(ModComponentTypes.MASK_VISIBILITY);
		if (maskVisibility != null) {
			switch (maskVisibility) {
				case VISIBLE -> {
					normalMask.visible = true;
					smallMask.visible = false;
				}
				case REDUCED -> {
					normalMask.visible = false;
					smallMask.visible = true;
				}
				case REMOVED -> {
					normalMask.visible = false;
					smallMask.visible = false;
				}
			}
		}
		coatFlap.xRot = Math.max(leftLeg.xRot, rightLeg.xRot);
		if (state.isDiscrete) {
			coatFlap.xRot /= 2;
		}
	}
}
