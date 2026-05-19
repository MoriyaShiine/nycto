/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.world.level.block.entity.BloodFountainBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockEntityType;

public class NyctoBlockEntityTypes {
	public static final BlockEntityType<BloodFountainBlockEntity> BLOOD_FOUNTAIN = registerBlockEntityType("blood_fountain", FabricBlockEntityTypeBuilder.create(BloodFountainBlockEntity::new,
			NyctoBlocks.BLOOD_FOUNTAIN
	));

	public static void init() {
	}
}
