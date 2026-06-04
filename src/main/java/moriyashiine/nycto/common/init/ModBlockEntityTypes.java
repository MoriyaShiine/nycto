/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.world.level.block.entity.BloodFountainBlockEntity;
import moriyashiine.nycto.common.world.level.block.entity.CoffinBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes {
	public static final BlockEntityType<CoffinBlockEntity> COFFIN = registerBlockEntityType("coffin", BlockEntityType.Builder.of(CoffinBlockEntity::new,
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
	).build(null));
	public static final BlockEntityType<BloodFountainBlockEntity> BLOOD_FOUNTAIN = registerBlockEntityType("blood_fountain", BlockEntityType.Builder.of(BloodFountainBlockEntity::new,
			ModBlocks.BLOOD_FOUNTAIN
	).build(null));

	private static <T extends BlockEntityType<?>> T registerBlockEntityType(String name, T type) {
		ModRegistration.register(ModRegistration.BLOCK_ENTITY_TYPES, name, type);
		return type;
	}

	public static void init() {
	}
}
