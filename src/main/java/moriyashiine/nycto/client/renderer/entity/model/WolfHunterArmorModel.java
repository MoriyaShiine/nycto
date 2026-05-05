/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.renderer.entity.model;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.client.model.animal.wolf.AdultWolfModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class WolfHunterArmorModel extends AdultWolfModel {
	public static final ModelLayerLocation VAMPIRE_HUNTER_LAYER = new ModelLayerLocation(Nycto.id("wolf_vampire_hunter"), "main");
	public static final ModelLayerLocation WEREWOLF_HUNTER_LAYER = new ModelLayerLocation(Nycto.id("wolf_werewolf_hunter"), "main");

	public WolfHunterArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createVampireHunterBodyLayer() {
		MeshDefinition mesh = AdultWolfModel.createBodyLayer(new CubeDeformation(0.2F));
		PartDefinition root = mesh.getRoot();
		root.getChild("body").addOrReplaceChild("body_armour", CubeListBuilder.create().texOffs(39, 42).addBox(-3.5F, -12.25F, -2.5F, 7, 10, 8, new CubeDeformation(-0.2F)), PartPose.offset(0, 10, -2));
		PartDefinition upper_body = root.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4, -8, -3, 8, 6, 7, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 14, 2, (float) (Math.PI / 2), 0, 0));
		PartDefinition upper_body_armor = upper_body.addOrReplaceChild("upper_body_armor", CubeListBuilder.create().texOffs(0, 48).addBox(-5, -17, -2, 8, 6, 8, new CubeDeformation(0.2F)), PartPose.offset(1, 10, -2));
		PartDefinition flowers = upper_body_armor.addOrReplaceChild("flowers", CubeListBuilder.create().texOffs(53, 8).addBox(-2, -1.75F, -11.25F, 3, 0, 5, CubeDeformation.NONE)
				.texOffs(53, 8).addBox(2, -1.75F, -10.25F, 3, 0, 5, CubeDeformation.NONE)
				.texOffs(53, 8).mirror().addBox(-6, -1.75F, -10.25F, 3, 0, 5, CubeDeformation.NONE).mirror(false)
				.texOffs(1, 36).addBox(-5, -2, -7.5F, 9, 2, 8, CubeDeformation.NONE), PartPose.offset(-0.5F, -15.5F, 6));
		flowers.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-2.5F, 0, -2.5F, 5, 0, 5, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-4.5F, -2.5F, -7.5F, -0.3927F, 0, -0.3491F));
		flowers.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 0).addBox(-2.5F, 0, -2.5F, 5, 0, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(3.5F, -2.5F, -7.5F, -0.3927F, 0, 0.3491F));
		flowers.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(-2.5F, 0, -2.5F, 5, 0, 5, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-5.5F, -1.5F, -3.5F, -0.3927F, 0, -1.309F));
		flowers.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(52, 0).addBox(-2.5F, 0, -2.5F, 5, 0, 5, CubeDeformation.NONE), PartPose.offsetAndRotation(4.5F, -1.5F, -3.5F, -0.3927F, 0, 1.309F));
		return LayerDefinition.create(mesh, 128, 64);
	}

	public static LayerDefinition createWerewolfHunterBodyLayer() {
		MeshDefinition mesh = AdultWolfModel.createBodyLayer(new CubeDeformation(0.2F));
		PartDefinition root = mesh.getRoot();
		PartDefinition body_armor = root.getChild("body").addOrReplaceChild("body_armour", CubeListBuilder.create().texOffs(39, 42).addBox(-3.5F, -12.5F, -2.5F, 7, 10, 8, new CubeDeformation(-0.2F)).texOffs(57, 6).addBox(0, -16, 5.1F, 0, 14, 4, CubeDeformation.NONE), PartPose.offset(0, 10, -2));
		PartDefinition upper_body = root.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4, -8, -3, 8, 6, 7, CubeDeformation.NONE), PartPose.offsetAndRotation(0, 14, 2, (float) (Math.PI / 2), 0, 0));
		PartDefinition upper_body_armor = upper_body.addOrReplaceChild("upper_body_armor", CubeListBuilder.create().texOffs(0, 48).addBox(-5, -17, -2, 8, 6, 8, new CubeDeformation(0.2F)), PartPose.offset(1, 10, -2));
		upper_body_armor.addOrReplaceChild("collar", CubeListBuilder.create().texOffs(58, 1).addBox(-5.5F, -17.25F, -1.5F, 9, 1, 8, CubeDeformation.NONE).texOffs(85, 0).addBox(-7.5F, -16.75F, -3.5F, 13, 0, 12, CubeDeformation.NONE), PartPose.offset(0, -0.5F, 0));
		PartDefinition flowers = body_armor.addOrReplaceChild("flowers", CubeListBuilder.create(), PartPose.offset(3.5F, -12.5F, 2.25F));
		flowers.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 30).mirror().addBox(0, -6, -6, 0, 10, 7, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(-7.25F, 2, 0, 0, 0.1309F, -0.0873F));
		flowers.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 30).addBox(0, -6, -6, 0, 10, 7, CubeDeformation.NONE), PartPose.offsetAndRotation(0.25F, 2, 0, 0, -0.1309F, 0.0873F));
		return LayerDefinition.create(mesh, 128, 64);
	}
}
