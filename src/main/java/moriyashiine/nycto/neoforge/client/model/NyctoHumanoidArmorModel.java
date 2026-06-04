/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.model;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public final class NyctoHumanoidArmorModel extends HumanoidModel<LivingEntity> {
	public static final ModelLayerLocation VAMPIRE_HELMET = layer("vampire_armor", "helmet");
	public static final ModelLayerLocation VAMPIRE_CHESTPLATE = layer("vampire_armor", "chestplate");
	public static final ModelLayerLocation VAMPIRE_LEGGINGS = layer("vampire_armor", "leggings");
	public static final ModelLayerLocation VAMPIRE_BOOTS = layer("vampire_armor", "boots");
	public static final ModelLayerLocation HUNTER_HELMET = layer("hunter_armor", "helmet");
	public static final ModelLayerLocation HUNTER_CHESTPLATE = layer("hunter_armor", "chestplate");
	public static final ModelLayerLocation HUNTER_LEGGINGS = layer("hunter_armor", "leggings");
	public static final ModelLayerLocation HUNTER_BOOTS = layer("hunter_armor", "boots");

	private final ModelPart coatFlap;

	public NyctoHumanoidArmorModel(ModelPart root) {
		super(root);
		coatFlap = body.hasChild("coat_flap") ? body.getChild("coat_flap") : null;
	}

	public void animateLooseCoat() {
		if (coatFlap != null) {
			coatFlap.xRot = Math.max(leftLeg.xRot, rightLeg.xRot);
		}
	}

	public static LayerDefinition createVampireHelmetLayer() {
		return LayerDefinition.create(createVampireMesh(EquipmentSlot.HEAD), 128, 128);
	}

	public static LayerDefinition createVampireChestplateLayer() {
		return LayerDefinition.create(createVampireMesh(EquipmentSlot.CHEST), 128, 128);
	}

	public static LayerDefinition createVampireLeggingsLayer() {
		return LayerDefinition.create(createVampireMesh(EquipmentSlot.LEGS), 128, 128);
	}

	public static LayerDefinition createVampireBootsLayer() {
		return LayerDefinition.create(createVampireMesh(EquipmentSlot.FEET), 128, 128);
	}

	public static LayerDefinition createHunterHelmetLayer() {
		return LayerDefinition.create(createHunterMesh(EquipmentSlot.HEAD), 128, 128);
	}

	public static LayerDefinition createHunterChestplateLayer() {
		return LayerDefinition.create(createHunterMesh(EquipmentSlot.CHEST), 128, 128);
	}

	public static LayerDefinition createHunterLeggingsLayer() {
		return LayerDefinition.create(createHunterMesh(EquipmentSlot.LEGS), 128, 128);
	}

	public static LayerDefinition createHunterBootsLayer() {
		return LayerDefinition.create(createHunterMesh(EquipmentSlot.FEET), 128, 128);
	}

	private static MeshDefinition createVampireMesh(EquipmentSlot slot) {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		addEmptyHumanoid(root);

		if (slot == EquipmentSlot.HEAD) {
			root.addOrReplaceChild("head", CubeListBuilder.create()
					.texOffs(66, 0).addBox(-4, -12, -4, 8, 6, 8, new CubeDeformation(0.55F))
					.texOffs(0, 112).addBox(-7, -5.5F, -7, 14, 1, 14, CubeDeformation.NONE), PartPose.ZERO);
		}
		if (slot == EquipmentSlot.CHEST) {
			PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
					.texOffs(106, 86).addBox(-5, -0.25F, -2.25F, 10, 12, 1, new CubeDeformation(0.35F))
					.texOffs(74, 32).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.5F))
					.texOffs(0, 95).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.35F)), PartPose.ZERO);
			body.addOrReplaceChild("right_collar", CubeListBuilder.create().texOffs(0, 81).mirror().addBox(-5, -1, -1, 6, 4, 6, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-0.5F, -2.75F, 0.5F, -0.5672F, -0.1745F, -0.1745F));
			body.addOrReplaceChild("left_collar", CubeListBuilder.create().texOffs(0, 81).addBox(-1, -1, -1, 6, 4, 6, CubeDeformation.NONE), PartPose.offsetAndRotation(0.5F, -2.75F, 0.5F, -0.5672F, 0.1745F, 0.1745F));
			body.addOrReplaceChild("scarf_1", CubeListBuilder.create().texOffs(92, 1).addBox(-1.5F, 0.5F, -2.4F, 3, 4, 2, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, -0.3491F, 0, 0));
			body.addOrReplaceChild("scarf_2", CubeListBuilder.create().texOffs(102, 1).addBox(-1, 1.5F, -2.4F, 2, 4, 2, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, -0.1745F, 0, 0));
			PartDefinition cape = body.addOrReplaceChild("cape", CubeListBuilder.create().texOffs(63, 82).addBox(-5.5F, -1, -5, 11, 3, 6, new CubeDeformation(-0.1F)), PartPose.offset(0, 0, 2));
			PartDefinition capeMain = cape.addOrReplaceChild("cape_main", CubeListBuilder.create().texOffs(66, 93).addBox(-7.5F, -1, -1, 15, 14, 2, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, 0.0873F, 0, 0));
			PartDefinition capeLower = capeMain.addOrReplaceChild("cape_lower", CubeListBuilder.create(), PartPose.offset(0, 12.5F, 0));
			capeLower.addOrReplaceChild("cape_cube", CubeListBuilder.create().texOffs(66, 111).addBox(-8, 0, -1, 16, 9, 2, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, 0.1309F, 0, 0));
			body.addOrReplaceChild("coat_flap", CubeListBuilder.create().texOffs(72, 50).addBox(-4.5F, -0.8F, 0, 9, 9, 5, new CubeDeformation(0.35F)), PartPose.offset(0, 12.5F, -2.5F));
			root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(98, 16).mirror().addBox(-1, -2, -2, 4, 12, 4, new CubeDeformation(0.32F)).mirror(false).texOffs(102, 37).mirror().addBox(-1.5F, 6.5F, -2.5F, 5, 2, 5, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(5, 2, 0));
			root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(98, 16).addBox(-3, -2, -2, 4, 12, 4, new CubeDeformation(0.32F)).texOffs(102, 37).addBox(-3.5F, 6.5F, -2.5F, 5, 2, 5, new CubeDeformation(0.1F)), PartPose.offset(-5, 2, 0));
		}
		if (slot == EquipmentSlot.LEGS) {
			root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(58, 16).mirror().addBox(-2.1F, 0, -2, 4, 12, 4, new CubeDeformation(0.35F)).mirror(false), PartPose.offset(1.9F, 12, 0));
			root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(58, 16).addBox(-1.9F, 0, -2, 4, 12, 4, new CubeDeformation(0.35F)), PartPose.offset(-1.9F, 12, 0));
		}
		if (slot == EquipmentSlot.FEET) {
			root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(79, 65).mirror().addBox(-2.1F, 0, -2, 4, 12, 4, new CubeDeformation(0.355F)).mirror(false).texOffs(107, 103).addBox(-2.6F, 6, -2.5F, 5, 2, 5, new CubeDeformation(0.2F)), PartPose.offset(1.9F, 12, 0));
			root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(79, 65).addBox(-1.9F, 0, -2, 4, 12, 4, new CubeDeformation(0.355F)).texOffs(107, 103).mirror().addBox(-2.4F, 6, -2.5F, 5, 2, 5, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(-1.9F, 12, 0));
		}
		return mesh;
	}

	private static MeshDefinition createHunterMesh(EquipmentSlot slot) {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		addEmptyHumanoid(root);

		if (slot == EquipmentSlot.HEAD) {
			PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
					.texOffs(65, 0).addBox(-4, -8, -4, 8, 8, 8, new CubeDeformation(0.55F))
					.texOffs(0, 64).addBox(-7, -8.2F, -11, 14, 3, 18, CubeDeformation.NONE)
					.texOffs(4, 108).addBox(-6, -9, -6, 12, 4, 6, new CubeDeformation(0.01F))
					.texOffs(28, 102).addBox(-2, -9, -8, 4, 4, 2, CubeDeformation.NONE)
					.texOffs(0, 118).addBox(-7, -9, 0, 14, 4, 6, CubeDeformation.NONE), PartPose.ZERO);
			head.addOrReplaceChild("cocked_hat_1", CubeListBuilder.create().texOffs(56, 88).addBox(-8, -33, 0.3F, 14, 4, 6, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 24, 0, 0, 1.8326F, 0));
			head.addOrReplaceChild("cocked_hat_2", CubeListBuilder.create().texOffs(56, 88).addBox(-6, -33, 0.3F, 14, 4, 6, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 24, 0, 0, -1.8326F, 0));
			PartDefinition hatFlower = head.addOrReplaceChild("hat_flower", CubeListBuilder.create().texOffs(101, 105).addBox(6.5F, 0, -2, 0, 4, 3, CubeDeformation.NONE), PartPose.offset(-1, -5, 3));
			hatFlower.addOrReplaceChild("hat_flower_cube", CubeListBuilder.create().texOffs(100, 65).addBox(-1, -9, -6, 0, 8, 7, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.75F, 2.5F, -2, -0.3491F, 0, 0));
			PartDefinition mask = head.addOrReplaceChild("mask", CubeListBuilder.create(), PartPose.ZERO);
			mask.addOrReplaceChild("normal_mask", CubeListBuilder.create().texOffs(60, 112).addBox(-4, -5, -4, 8, 5, 8, new CubeDeformation(0.55F)), PartPose.ZERO);
		}
		if (slot == EquipmentSlot.CHEST) {
			PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
					.texOffs(74, 32).addBox(-4, 0, -2, 8, 12, 4, new CubeDeformation(0.35F))
					.texOffs(100, 55).addBox(-4.5F, 10, -2.5F, 9, 3, 5, new CubeDeformation(0.1F))
					.texOffs(106, 86).addBox(-5, 0, -2.25F, 10, 12, 1, new CubeDeformation(0.35F)), PartPose.ZERO);
			body.addOrReplaceChild("chest_belt", CubeListBuilder.create().texOffs(100, 51).addBox(-5, -2, -3.5F, 3, 3, 1, CubeDeformation.NONE).texOffs(100, 51).addBox(-5, 7, -3.5F, 3, 3, 1, CubeDeformation.NONE).texOffs(99, 32).addBox(-5, -2, -2.5F, 3, 14, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 0, 0, 0, 0, -0.5672F));
			body.addOrReplaceChild("coat_flap", CubeListBuilder.create().texOffs(74, 48).addBox(-4, 0.2F, 0.5F, 8, 8, 4, new CubeDeformation(0.45F)), PartPose.offset(0, 12.5F, -2.5F));
			PartDefinition chestFlowers = body.addOrReplaceChild("chest_flowers", CubeListBuilder.create().texOffs(115, 16).addBox(-1.25F, 0, -0.75F, 4, 5, 0, CubeDeformation.NONE).texOffs(115, 23).addBox(-8.25F, -6.75F, 4.76F, 4, 5, 0, CubeDeformation.NONE).texOffs(115, 23).addBox(-6.25F, -3.75F, -0.49F, 4, 5, 0, CubeDeformation.NONE).texOffs(102, 80).addBox(-5.5F, -8, -0.75F, 5, 6, 0, CubeDeformation.NONE), PartPose.offset(2, 10, -2.25F));
			chestFlowers.addOrReplaceChild("chest_flowers_cube", CubeListBuilder.create().texOffs(115, 23).addBox(-2, -0.0019F, -0.9872F, 4, 5, 0, CubeDeformation.NONE), PartPose.offsetAndRotation(-3, -3.75F, 5.75F, 0.0436F, 0, 0));
			root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(98, 16).mirror().addBox(-1, -2, -2, 4, 12, 4, new CubeDeformation(0.32F)).mirror(false), PartPose.offset(5, 2, 0));
			root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(98, 16).addBox(-3, -2, -2, 4, 12, 4, new CubeDeformation(0.32F)), PartPose.offset(-5, 2, 0));
		}
		if (slot == EquipmentSlot.LEGS) {
			root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(58, 16).mirror().addBox(-2.1F, 0, -2, 4, 12, 4, new CubeDeformation(0.35F)).mirror(false), PartPose.offset(1.9F, 12, 0));
			root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(58, 16).addBox(-1.9F, 0, -2, 4, 12, 4, new CubeDeformation(0.35F)), PartPose.offset(-1.9F, 12, 0));
		}
		if (slot == EquipmentSlot.FEET) {
			root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(79, 65).mirror().addBox(-2.1F, 0, -2, 4, 12, 4, new CubeDeformation(0.36F)).mirror(false), PartPose.offset(1.9F, 12, 0));
			root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(79, 65).addBox(-1.9F, 0, -2, 4, 12, 4, new CubeDeformation(0.36F)), PartPose.offset(-1.9F, 12, 0));
		}
		return mesh;
	}

	private static void addEmptyHumanoid(PartDefinition root) {
		root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
		root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
		root.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5, 2, 0));
		root.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5, 2, 0));
		root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12, 0));
		root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12, 0));
	}

	private static ModelLayerLocation layer(String model, String part) {
		return new ModelLayerLocation(NyctoNeoForge.id(model), part);
	}
}
