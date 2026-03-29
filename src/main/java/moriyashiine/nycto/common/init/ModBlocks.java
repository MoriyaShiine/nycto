/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.world.level.block.WildVegetationBlock;
import moriyashiine.nycto.common.world.level.block.*;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlock;
import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockType;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class ModBlocks {
	public static final Block VAMPIRE_ALTAR = registerBlock("vampire_altar", VampireAltarBlock::new, ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
	public static final Block WEREWOLF_ALTAR = registerBlock("werewolf_altar", WerewolfAltarBlock::new, ofFullCopy(Blocks.BONE_BLOCK).noOcclusion());

	public static final Block OAK_COFFIN = registerCoffin("oak_coffin", Blocks.OAK_PLANKS);
	public static final Block SPRUCE_COFFIN = registerCoffin("spruce_coffin", Blocks.SPRUCE_PLANKS);
	public static final Block BIRCH_COFFIN = registerCoffin("birch_coffin", Blocks.BIRCH_PLANKS);
	public static final Block JUNGLE_COFFIN = registerCoffin("jungle_coffin", Blocks.JUNGLE_PLANKS);
	public static final Block ACACIA_COFFIN = registerCoffin("acacia_coffin", Blocks.ACACIA_PLANKS);
	public static final Block DARK_OAK_COFFIN = registerCoffin("dark_oak_coffin", Blocks.DARK_OAK_PLANKS);
	public static final Block PALE_OAK_COFFIN = registerCoffin("pale_oak_coffin", Blocks.PALE_OAK_PLANKS);
	public static final Block MANGROVE_COFFIN = registerCoffin("mangrove_coffin", Blocks.MANGROVE_PLANKS);
	public static final Block CHERRY_COFFIN = registerCoffin("cherry_coffin", Blocks.CHERRY_PLANKS);
	public static final Block BAMBOO_COFFIN = registerCoffin("bamboo_coffin", Blocks.BAMBOO_PLANKS);
	public static final Block CRIMSON_COFFIN = registerCoffin("crimson_coffin", Blocks.CRIMSON_PLANKS);
	public static final Block WARPED_COFFIN = registerCoffin("warped_coffin", Blocks.WARPED_PLANKS);

	public static final Block GARLIC_WREATH = registerBlock("garlic_wreath", GarlicWreathBlock::new, of().noCollision().strength(0.5F).sound(SoundType.GRASS));
	public static final Block ACONITE_GARLAND = registerBlock("aconite_garland", AconiteGarlandBlock::new, of().noCollision().strength(0.5F).sound(SoundType.GRASS));

	public static final Block WILD_GARLIC = registerBlock("wild_garlic", WildGarlicBlock::new, of().noCollision().instabreak().sound(SoundType.CROP).offsetType(BlockBehaviour.OffsetType.XYZ));
	public static final Block WILD_ACONITE = registerBlock("wild_aconite", WildVegetationBlock::new, of().noCollision().instabreak().sound(SoundType.CROP).offsetType(BlockBehaviour.OffsetType.XYZ));

	public static final Block GARLIC = registerBlock("garlic", GarlicBlock::new, ofFullCopy(Blocks.CARROTS));
	public static final Block ACONITE = registerBlock("aconite", AconiteBlock::new, ofFullCopy(Blocks.CARROTS));

	public static final Block FIREBOMB = registerBlock("firebomb", FirebombBlock::new, ofFullCopy(Blocks.FIRE));

	public static Block registerCoffin(String name, Block base) {
		return registerBlock(name, CoffinBlock::new, ofFullCopy(base).noOcclusion());
	}

	public static void init() {
		registerBlockType("vampire_altar", VampireAltarBlock.CODEC);
		registerBlockType("firebomb", FirebombBlock.CODEC);
		FlammableBlockRegistry.getDefaultInstance().add(WILD_GARLIC, 60, 100);
		FlammableBlockRegistry.getDefaultInstance().add(WILD_ACONITE, 60, 100);
		FlammableBlockRegistry.getDefaultInstance().add(GARLIC_WREATH, 60, 100);
		FlammableBlockRegistry.getDefaultInstance().add(ACONITE_GARLAND, 60, 100);
	}
}
