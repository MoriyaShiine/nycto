/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.world.level.block.WildVegetationBlock;
import moriyashiine.nycto.common.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class ModBlocks {
	public static final Block VAMPIRE_ALTAR = registerBlock("vampire_altar", VampireAltarBlock::new, ofFullCopy(Blocks.OAK_PLANKS)
			.noOcclusion());
	public static final Block WEREWOLF_ALTAR = registerBlock("werewolf_altar", WerewolfAltarBlock::new, ofFullCopy(Blocks.BONE_BLOCK)
			.noOcclusion());

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

	public static final Block BLOOD_FOUNTAIN = registerBlock("blood_fountain", BloodFountainBlock::new, ofFullCopy(Blocks.STONE_BRICKS));

	public static final Block GARLIC_WREATH = registerBlock("garlic_wreath", GarlicWreathBlock::new, of()
			.noCollision()
			.strength(0.5F)
			.sound(SoundType.GRASS));
	public static final Block ACONITE_GARLAND = registerBlock("aconite_garland", AconiteGarlandBlock::new, of()
			.noCollision()
			.strength(0.5F)
			.sound(SoundType.GRASS));

	public static final Block WILD_GARLIC = registerBlock("wild_garlic", WildGarlicBlock::new, of()
			.noCollision()
			.instabreak()
			.sound(SoundType.CROP)
			.offsetType(BlockBehaviour.OffsetType.XYZ));
	public static final Block WILD_ACONITE = registerBlock("wild_aconite", WildVegetationBlock::new, of()
			.noCollision()
			.instabreak()
			.sound(SoundType.CROP)
			.offsetType(BlockBehaviour.OffsetType.XYZ));

	public static final Block GARLIC = registerBlock("garlic", GarlicBlock::new, ofFullCopy(Blocks.CARROTS));
	public static final Block ACONITE = registerBlock("aconite", AconiteBlock::new, ofFullCopy(Blocks.CARROTS));

	public static final Block WOODEN_STAKE = registerBlock("wooden_stake", WoodenStakeBlock::new, of()
			.mapColor(MapColor.WOOD)
			.forceSolidOn()
			.instrument(NoteBlockInstrument.BASS)
			.noOcclusion()
			.sound(SoundType.WOOD)
			.strength(2)
			.pushReaction(PushReaction.DESTROY)
			.isRedstoneConductor(Blocks::never));
	public static final Block FIREBOMB = registerBlock("firebomb", FirebombBlock::new, ofFullCopy(Blocks.FIRE));

	public static Block registerCoffin(String name, Block base) {
		return registerBlock(name, CoffinBlock::new, ofFullCopy(base)
				.noOcclusion());
	}

	private static <T extends Block> T registerBlock(String name, BlockFactory<T> factory, BlockBehaviour.Properties properties) {
		T block = factory.create(properties);
		ModRegistration.register(ModRegistration.BLOCKS, name, block);
		return block;
	}

	public static void init() {
		FireBlock fire = (FireBlock) Blocks.FIRE;
		fire.setFlammable(WILD_GARLIC, 60, 100);
		fire.setFlammable(WILD_ACONITE, 60, 100);
		fire.setFlammable(GARLIC_WREATH, 60, 100);
		fire.setFlammable(ACONITE_GARLAND, 60, 100);
		fire.setFlammable(WOODEN_STAKE, 5, 5);
	}

	@FunctionalInterface
	private interface BlockFactory<T extends Block> {
		T create(BlockBehaviour.Properties properties);
	}
}
