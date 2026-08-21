package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.world.level.block.entity.BloodFountainBlockEntity;
import moriyashiine.nycto.common.world.level.block.entity.CoffinBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockEntityType;

public class NyctoBlockEntityTypes {
	public static final BlockEntityType<CoffinBlockEntity> COFFIN = registerBlockEntityType("coffin", FabricBlockEntityTypeBuilder.create(CoffinBlockEntity::new,
			NyctoBlocks.OAK_COFFIN,
			NyctoBlocks.SPRUCE_COFFIN,
			NyctoBlocks.BIRCH_COFFIN,
			NyctoBlocks.JUNGLE_COFFIN,
			NyctoBlocks.ACACIA_COFFIN,
			NyctoBlocks.DARK_OAK_COFFIN,
			NyctoBlocks.PALE_OAK_COFFIN,
			NyctoBlocks.MANGROVE_COFFIN,
			NyctoBlocks.CHERRY_COFFIN,
			NyctoBlocks.BAMBOO_COFFIN,
			NyctoBlocks.CRIMSON_COFFIN,
			NyctoBlocks.WARPED_COFFIN
	));
	public static final BlockEntityType<BloodFountainBlockEntity> BLOOD_FOUNTAIN = registerBlockEntityType("blood_fountain", FabricBlockEntityTypeBuilder.create(BloodFountainBlockEntity::new,
			NyctoBlocks.BLOOD_FOUNTAIN
	));

	public static void init() {
	}
}
