/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.block;

import moriyashiine.nycto.common.component.world.AuraComponent;
import moriyashiine.nycto.common.init.ModWorldComponents;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

public class GarlicWreathBlock extends Block {
	private static final VoxelShape BOTTOM_SHAPE = createCuboidShape(0, 0, 0, 16, 3, 16);

	public GarlicWreathBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (state.get(Properties.DOWN)) {
			return BOTTOM_SHAPE;
		}
		return AconiteGarlandBlock.WALL_SHAPES[state.get(Properties.HORIZONTAL_FACING).getIndex() - 2];
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
		if (direction == Direction.DOWN || direction.getOpposite() == state.get(Properties.HORIZONTAL_FACING)) {
			if (!canPlaceAt(state, world, pos)) {
				return Blocks.AIR.getDefaultState();
			}
		}
		return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		if (ctx.getSide() == Direction.DOWN) {
			return null;
		}
		boolean down = ctx.getSide() == Direction.UP;
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, down ? ctx.getHorizontalPlayerFacing().getOpposite() : Direction.fromHorizontalQuarterTurns(ctx.getSide().getHorizontalQuarterTurns())).with(Properties.DOWN, down);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos offset = pos.down();
		if (state.get(Properties.DOWN)) {
			return world.getBlockState(offset).isSideSolid(world, offset, Direction.UP, SideShapeType.FULL);
		}
		Direction direction = state.get(Properties.HORIZONTAL_FACING).getOpposite();
		offset = pos.offset(direction);
		return world.getBlockState(offset).isSideSolid(world, offset, direction, SideShapeType.FULL);
	}

	@Override
	protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler, boolean bl) {
		if (entity instanceof LivingEntity living && living.hurtTime == 0 && NyctoUtil.affectedByHurtsVampiresTag(entity)) {
			NyctoUtil.damageWithToxicTouch(living, 1);
		}
	}

	@Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!world.isClient()) {
			AuraComponent auraComponent = ModWorldComponents.AURA.get(world);
			auraComponent.getGarlicWreaths().add(pos);
			auraComponent.sync();
		}
	}

	@Override
	protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
		AuraComponent auraComponent = ModWorldComponents.AURA.get(world);
		auraComponent.getGarlicWreaths().remove(pos);
		auraComponent.sync();
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.HORIZONTAL_FACING, Properties.DOWN);
	}
}
