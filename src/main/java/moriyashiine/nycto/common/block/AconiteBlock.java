/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.block;

import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class AconiteBlock extends CropBlock {
	private static final VoxelShape[] AGE_TO_SHAPE = {createCuboidShape(0, 0, 0, 16, 3, 16), createCuboidShape(0, 0, 0, 16, 6, 16), createCuboidShape(0, 0, 0, 16, 10, 16), createCuboidShape(0, 0, 0, 16, 14, 16)};

	public AconiteBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return AGE_TO_SHAPE[state.get(getAgeProperty())];
	}

	@Override
	protected ItemConvertible getSeedsItem() {
		return ModItems.ACONITE_SEEDS;
	}

	@Override
	public IntProperty getAgeProperty() {
		return Properties.AGE_3;
	}

	@Override
	public int getMaxAge() {
		return 3;
	}

	@Override
	protected int getGrowthAmount(World world) {
		return super.getGrowthAmount(world) / 3;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt(3) != 0) {
			super.randomTick(state, world, pos, random);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(getAgeProperty());
	}
}
