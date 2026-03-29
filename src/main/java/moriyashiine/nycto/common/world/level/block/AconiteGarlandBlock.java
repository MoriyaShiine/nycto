/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class AconiteGarlandBlock extends Block {
	public static final VoxelShape[] WALL_SHAPES = {
			box(0, 0, 13, 16, 16, 16),
			box(0, 0, 0, 16, 16, 3),
			box(13, 0, 0, 16, 16, 16),
			box(0, 0, 0, 3, 16, 16)};
	private static final VoxelShape[] HANGING_SHAPES = {
			box(0, 1, 6, 16, 16, 10),
			box(6, 1, 0, 10, 16, 16)};

	public AconiteGarlandBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (state.getValue(BlockStateProperties.HANGING)) {
			return HANGING_SHAPES[state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis() == Direction.Axis.Z ? 0 : 1];
		}
		return WALL_SHAPES[state.getValue(BlockStateProperties.HORIZONTAL_FACING).get3DDataValue() - 2];
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
		if (directionToNeighbour == Direction.UP || directionToNeighbour.getOpposite() == state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
			if (!canSurvive(state, level, pos)) {
				return Blocks.AIR.defaultBlockState();
			}
		}
		return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		if (context.getClickedFace() == Direction.UP) {
			return null;
		}
		boolean hanging = context.getClickedFace() == Direction.DOWN;
		return super.getStateForPlacement(context).setValue(BlockStateProperties.HORIZONTAL_FACING, hanging ? context.getHorizontalDirection().getOpposite() : Direction.from2DDataValue(context.getClickedFace().get2DDataValue())).setValue(BlockStateProperties.HANGING, hanging);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos offset = pos.above();
		if (state.getValue(BlockStateProperties.HANGING)) {
			return level.getBlockState(offset).isFaceSturdy(level, offset, Direction.DOWN, SupportType.FULL);
		}
		Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
		offset = pos.relative(direction);
		return level.getBlockState(offset).isFaceSturdy(level, offset, direction, SupportType.FULL);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HANGING);
	}
}
