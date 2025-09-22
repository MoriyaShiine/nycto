/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.block.WildCropBlock;
import moriyashiine.nycto.common.block.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.sound.BlockSoundGroup;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlock;
import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockType;
import static net.minecraft.block.AbstractBlock.Settings.copy;
import static net.minecraft.block.AbstractBlock.Settings.create;

public class ModBlocks {
	public static final Block VAMPIRE_ALTAR = registerBlock("vampire_altar", VampireAltarBlock::new, copy(Blocks.OAK_PLANKS).nonOpaque());
	public static final Block WEREWOLF_ALTAR = registerBlock("werewolf_altar", WerewolfAltarBlock::new, copy(Blocks.BONE_BLOCK).nonOpaque());

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

	public static final Block GARLIC_WREATH = registerBlock("garlic_wreath", GarlicWreathBlock::new, create().noCollision().strength(0.5F).sounds(BlockSoundGroup.GRASS));
	public static final Block ACONITE_GARLAND = registerBlock("aconite_garland", AconiteGarlandBlock::new, create().noCollision().strength(0.5F).sounds(BlockSoundGroup.GRASS));

	public static final Block WILD_GARLIC = registerBlock("wild_garlic", WildGarlicBlock::new, create().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XYZ));
	public static final Block WILD_ACONITE = registerBlock("wild_aconite", WildCropBlock::new, create().noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).offset(AbstractBlock.OffsetType.XYZ));

	public static final Block GARLIC = registerBlock("garlic", GarlicBlock::new, copy(Blocks.CARROTS));
	public static final Block ACONITE = registerBlock("aconite", AconiteBlock::new, copy(Blocks.CARROTS));

	public static final Block FIREBOMB = registerBlock("firebomb", FirebombBlock::new, copy(Blocks.FIRE));

	public static Block registerCoffin(String name, Block base) {
		return registerBlock(name, CoffinBlock::new, copy(base).nonOpaque());
	}

	public static void init() {
		registerBlockType("vampire_altar", VampireAltarBlock.CODEC);
		registerBlockType("firebomb", FirebombBlock.CODEC);
		FireBlock fire = (FireBlock) Blocks.FIRE;
		fire.registerFlammableBlock(WILD_GARLIC, 60, 100);
		fire.registerFlammableBlock(WILD_ACONITE, 60, 100);
		fire.registerFlammableBlock(GARLIC_WREATH, 60, 100);
		fire.registerFlammableBlock(ACONITE_GARLAND, 60, 100);
	}
}
