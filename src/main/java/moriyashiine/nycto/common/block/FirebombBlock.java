/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class FirebombBlock extends AbstractFireBlock {
	public static final MapCodec<FirebombBlock> CODEC = createCodec(FirebombBlock::new);

	public FirebombBlock(Settings settings) {
		super(settings, 1);
	}

	@Override
	protected MapCodec<? extends AbstractFireBlock> getCodec() {
		return CODEC;
	}

	@Override
	public boolean isFlammable(BlockState state) {
		return false;
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
		return canPlaceAt(state, world, pos) ? getDefaultState() : Blocks.AIR.getDefaultState();
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.down()).isSolid();
	}

	@Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		super.onBlockAdded(state, world, pos, oldState, notify);
		if (!world.getBlockState(pos.down()).isIn(world.getDimension().infiniburn())) {
			world.scheduleBlockTick(pos, this, world.getRandom().nextBetween(40, 100));
		}
	}

	@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.removeBlock(pos, false);
	}
}
