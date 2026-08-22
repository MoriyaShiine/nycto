package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.world.level.block.WildVegetationBlock;
import moriyashiine.nycto.common.references.NyctoBlockItemIds;
import moriyashiine.nycto.common.world.level.block.*;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.references.BlockItemId;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlock;
import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockType;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class NyctoBlocks {
	public static final Block VAMPIRE_ALTAR = registerBlock(NyctoBlockItemIds.VAMPIRE_ALTAR, VampireAltarBlock::new, ofFullCopy(Blocks.OAK_PLANKS)
			.noOcclusion());
	public static final Block WEREWOLF_ALTAR = registerBlock(NyctoBlockItemIds.WEREWOLF_ALTAR, WerewolfAltarBlock::new, ofFullCopy(Blocks.BONE_BLOCK)
			.noOcclusion());

	public static final Block OAK_COFFIN = registerCoffin(NyctoBlockItemIds.OAK_COFFIN, Blocks.OAK_PLANKS);
	public static final Block SPRUCE_COFFIN = registerCoffin(NyctoBlockItemIds.SPRUCE_COFFIN, Blocks.SPRUCE_PLANKS);
	public static final Block BIRCH_COFFIN = registerCoffin(NyctoBlockItemIds.BIRCH_COFFIN, Blocks.BIRCH_PLANKS);
	public static final Block JUNGLE_COFFIN = registerCoffin(NyctoBlockItemIds.JUNGLE_COFFIN, Blocks.JUNGLE_PLANKS);
	public static final Block ACACIA_COFFIN = registerCoffin(NyctoBlockItemIds.ACACIA_COFFIN, Blocks.ACACIA_PLANKS);
	public static final Block DARK_OAK_COFFIN = registerCoffin(NyctoBlockItemIds.DARK_OAK_COFFIN, Blocks.DARK_OAK_PLANKS);
	public static final Block PALE_OAK_COFFIN = registerCoffin(NyctoBlockItemIds.PALE_OAK_COFFIN, Blocks.PALE_OAK_PLANKS);
	public static final Block MANGROVE_COFFIN = registerCoffin(NyctoBlockItemIds.MANGROVE_COFFIN, Blocks.MANGROVE_PLANKS);
	public static final Block CHERRY_COFFIN = registerCoffin(NyctoBlockItemIds.CHERRY_COFFIN, Blocks.CHERRY_PLANKS);
	public static final Block BAMBOO_COFFIN = registerCoffin(NyctoBlockItemIds.BAMBOO_COFFIN, Blocks.BAMBOO_PLANKS);
	public static final Block CRIMSON_COFFIN = registerCoffin(NyctoBlockItemIds.CRIMSON_COFFIN, Blocks.CRIMSON_PLANKS);
	public static final Block WARPED_COFFIN = registerCoffin(NyctoBlockItemIds.WARPED_COFFIN, Blocks.WARPED_PLANKS);

	public static final Block BLOOD_FOUNTAIN = registerBlock(NyctoBlockItemIds.BLOOD_FOUNTAIN, BloodFountainBlock::new, ofFullCopy(Blocks.STONE_BRICKS));

	public static final Block GARLIC_WREATH = registerBlock(NyctoBlockItemIds.GARLIC_WREATH, GarlicWreathBlock::new, of()
			.noCollision()
			.strength(0.5F)
			.sound(SoundType.GRASS));
	public static final Block ACONITE_GARLAND = registerBlock(NyctoBlockItemIds.ACONITE_GARLAND, AconiteGarlandBlock::new, of()
			.noCollision()
			.strength(0.5F)
			.sound(SoundType.GRASS));

	public static final Block WILD_GARLIC = registerBlock(NyctoBlockItemIds.WILD_GARLIC, WildGarlicBlock::new, of()
			.noCollision()
			.instabreak()
			.sound(SoundType.CROP)
			.offsetType(BlockBehaviour.OffsetType.XYZ));
	public static final Block WILD_ACONITE = registerBlock(NyctoBlockItemIds.WILD_ACONITE, WildVegetationBlock::new, of()
			.noCollision()
			.instabreak()
			.sound(SoundType.CROP)
			.offsetType(BlockBehaviour.OffsetType.XYZ));

	public static final Block GARLIC = registerBlock(NyctoBlockItemIds.GARLIC, GarlicBlock::new, ofFullCopy(Blocks.CARROTS));
	public static final Block ACONITE = registerBlock(NyctoBlockItemIds.ACONITE, AconiteBlock::new, ofFullCopy(Blocks.CARROTS));

	public static final Block WOODEN_STAKE = registerBlock(NyctoBlockItemIds.WOODEN_STAKE, WoodenStakeBlock::new, of()
			.mapColor(MapColor.WOOD)
			.forceSolidOn()
			.instrument(NoteBlockInstrument.BASS)
			.noOcclusion()
			.sound(SoundType.WOOD)
			.strength(2)
			.pushReaction(PushReaction.DESTROY)
			.isRedstoneConductor(Blocks::never));
	public static final Block FIREBOMB = registerBlock(NyctoBlockItemIds.FIREBOMB, FirebombBlock::new, ofFullCopy(Blocks.FIRE));

	public static Block registerCoffin(BlockItemId id, Block base) {
		return registerBlock(id, CoffinBlock::new, ofFullCopy(base)
				.noOcclusion());
	}

	public static void init() {
		registerBlockType("vampire_altar", VampireAltarBlock.CODEC);
		registerBlockType("blood_fountain", BloodFountainBlock.CODEC);
		registerBlockType("wooden_stake", WoodenStakeBlock.CODEC);
		registerBlockType("firebomb", FirebombBlock.CODEC);
		FlammableBlockRegistry.getDefaultInstance().add(WILD_GARLIC, 60, 100);
		FlammableBlockRegistry.getDefaultInstance().add(WILD_ACONITE, 60, 100);
		FlammableBlockRegistry.getDefaultInstance().add(GARLIC_WREATH, 60, 100);
		FlammableBlockRegistry.getDefaultInstance().add(ACONITE_GARLAND, 60, 100);
		FlammableBlockRegistry.getDefaultInstance().add(WOODEN_STAKE, 5, 5);
	}
}
