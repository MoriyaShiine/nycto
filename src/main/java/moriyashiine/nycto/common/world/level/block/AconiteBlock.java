/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.level.block;

import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AconiteBlock extends CropBlock {
	private static final VoxelShape[] AGE_TO_SHAPE = {box(0, 0, 0, 16, 3, 16), box(0, 0, 0, 16, 6, 16), box(0, 0, 0, 16, 10, 16), box(0, 0, 0, 16, 14, 16)};

	public AconiteBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return AGE_TO_SHAPE[state.getValue(getAgeProperty())];
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ModItems.ACONITE_SEEDS;
	}

	@Override
	public IntegerProperty getAgeProperty() {
		return BlockStateProperties.AGE_3;
	}

	@Override
	public int getMaxAge() {
		return 3;
	}

	@Override
	protected int getBonemealAgeIncrease(Level level) {
		return super.getBonemealAgeIncrease(level) / 3;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (random.nextInt(3) != 0) {
			super.randomTick(state, level, pos, random);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(getAgeProperty());
	}
}
