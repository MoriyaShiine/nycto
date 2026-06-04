/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.block.BloodFountainBlock;
import moriyashiine.nycto.neoforge.block.CoffinBlock;
import moriyashiine.nycto.neoforge.block.FirebombBlock;
import moriyashiine.nycto.neoforge.block.VampireAltarBlock;
import moriyashiine.nycto.neoforge.block.WoodenStakeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NyctoBlocks {
	private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NyctoNeoForge.MOD_ID);

	public static final DeferredBlock<VampireAltarBlock> VAMPIRE_ALTAR = BLOCKS.registerBlock("vampire_altar", VampireAltarBlock::new,
			BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).strength(3, 6).noOcclusion());
	public static final DeferredBlock<BloodFountainBlock> BLOOD_FOUNTAIN = BLOCKS.registerBlock("blood_fountain", BloodFountainBlock::new,
			BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS).strength(3, 6).noOcclusion());
	public static final DeferredBlock<WoodenStakeBlock> WOODEN_STAKE = BLOCKS.registerBlock("wooden_stake", WoodenStakeBlock::new,
			BlockBehaviour.Properties.of().strength(2).sound(SoundType.WOOD).noOcclusion());
	public static final DeferredBlock<FirebombBlock> FIREBOMB = BLOCKS.registerBlock("firebomb", FirebombBlock::new,
			BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE).noLootTable());

	public static final DeferredBlock<CoffinBlock> OAK_COFFIN = registerCoffin("oak_coffin", Blocks.OAK_PLANKS);
	public static final DeferredBlock<CoffinBlock> SPRUCE_COFFIN = registerCoffin("spruce_coffin", Blocks.SPRUCE_PLANKS);
	public static final DeferredBlock<CoffinBlock> BIRCH_COFFIN = registerCoffin("birch_coffin", Blocks.BIRCH_PLANKS);
	public static final DeferredBlock<CoffinBlock> JUNGLE_COFFIN = registerCoffin("jungle_coffin", Blocks.JUNGLE_PLANKS);
	public static final DeferredBlock<CoffinBlock> ACACIA_COFFIN = registerCoffin("acacia_coffin", Blocks.ACACIA_PLANKS);
	public static final DeferredBlock<CoffinBlock> DARK_OAK_COFFIN = registerCoffin("dark_oak_coffin", Blocks.DARK_OAK_PLANKS);
	public static final DeferredBlock<CoffinBlock> PALE_OAK_COFFIN = registerCoffin("pale_oak_coffin", Blocks.OAK_PLANKS);
	public static final DeferredBlock<CoffinBlock> MANGROVE_COFFIN = registerCoffin("mangrove_coffin", Blocks.MANGROVE_PLANKS);
	public static final DeferredBlock<CoffinBlock> CHERRY_COFFIN = registerCoffin("cherry_coffin", Blocks.CHERRY_PLANKS);
	public static final DeferredBlock<CoffinBlock> BAMBOO_COFFIN = registerCoffin("bamboo_coffin", Blocks.BAMBOO_PLANKS);
	public static final DeferredBlock<CoffinBlock> CRIMSON_COFFIN = registerCoffin("crimson_coffin", Blocks.CRIMSON_PLANKS);
	public static final DeferredBlock<CoffinBlock> WARPED_COFFIN = registerCoffin("warped_coffin", Blocks.WARPED_PLANKS);

	private NyctoBlocks() {
	}

	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
	}

	private static DeferredBlock<CoffinBlock> registerCoffin(String name, Block planks) {
		return BLOCKS.registerBlock(name, CoffinBlock::new, BlockBehaviour.Properties.ofFullCopy(planks).sound(SoundType.WOOD).strength(2, 3).noOcclusion());
	}
}
