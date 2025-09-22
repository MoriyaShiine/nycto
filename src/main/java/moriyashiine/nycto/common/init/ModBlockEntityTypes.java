/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.block.entity.CoffinBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockEntityType;

public class ModBlockEntityTypes {
	public static final BlockEntityType<CoffinBlockEntity> COFFIN = registerBlockEntityType("coffin", FabricBlockEntityTypeBuilder.create(CoffinBlockEntity::new,
			ModBlocks.OAK_COFFIN,
			ModBlocks.SPRUCE_COFFIN,
			ModBlocks.BIRCH_COFFIN,
			ModBlocks.JUNGLE_COFFIN,
			ModBlocks.ACACIA_COFFIN,
			ModBlocks.DARK_OAK_COFFIN,
			ModBlocks.PALE_OAK_COFFIN,
			ModBlocks.MANGROVE_COFFIN,
			ModBlocks.CHERRY_COFFIN,
			ModBlocks.BAMBOO_COFFIN,
			ModBlocks.CRIMSON_COFFIN,
			ModBlocks.WARPED_COFFIN
	));

	public static void init() {
	}
}
