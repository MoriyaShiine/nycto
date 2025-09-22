/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

public class AconiteGarlandBlock extends Block {
	public static final VoxelShape[] WALL_SHAPES = {
			createCuboidShape(0, 0, 13, 16, 16, 16),
			createCuboidShape(0, 0, 0, 16, 16, 3),
			createCuboidShape(13, 0, 0, 16, 16, 16),
			createCuboidShape(0, 0, 0, 3, 16, 16)};
	private static final VoxelShape[] HANGING_SHAPES = {
			createCuboidShape(0, 1, 6, 16, 16, 10),
			createCuboidShape(6, 1, 0, 10, 16, 16)};

	public AconiteGarlandBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (state.get(Properties.HANGING)) {
			return HANGING_SHAPES[state.get(Properties.HORIZONTAL_FACING).getAxis() == Direction.Axis.Z ? 0 : 1];
		}
		return WALL_SHAPES[state.get(Properties.HORIZONTAL_FACING).getIndex() - 2];
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
		if (direction == Direction.UP || direction.getOpposite() == state.get(Properties.HORIZONTAL_FACING)) {
			if (!canPlaceAt(state, world, pos)) {
				return Blocks.AIR.getDefaultState();
			}
		}
		return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		if (ctx.getSide() == Direction.UP) {
			return null;
		}
		boolean hanging = ctx.getSide() == Direction.DOWN;
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, hanging ? ctx.getHorizontalPlayerFacing().getOpposite() : Direction.fromHorizontalQuarterTurns(ctx.getSide().getHorizontalQuarterTurns())).with(Properties.HANGING, hanging);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos offset = pos.up();
		if (state.get(Properties.HANGING)) {
			return world.getBlockState(offset).isSideSolid(world, offset, Direction.DOWN, SideShapeType.FULL);
		} else {
			for (Direction direction : Direction.Type.HORIZONTAL) {
				offset = pos.offset(direction);
				if (world.getBlockState(offset).isSideSolid(world, offset, direction, SideShapeType.FULL)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.HORIZONTAL_FACING, Properties.HANGING);
	}
}
