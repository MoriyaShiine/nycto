/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.level.block;

import moriyashiine.nycto.common.component.level.AuraComponent;
import moriyashiine.nycto.common.init.ModLevelComponents;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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

public class GarlicWreathBlock extends Block {
	private static final VoxelShape BOTTOM_SHAPE = box(0, 0, 0, 16, 3, 16);

	public GarlicWreathBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (state.getValue(BlockStateProperties.DOWN)) {
			return BOTTOM_SHAPE;
		}
		return AconiteGarlandBlock.WALL_SHAPES[state.getValue(BlockStateProperties.HORIZONTAL_FACING).get3DDataValue() - 2];
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
		if (directionToNeighbour == Direction.DOWN || directionToNeighbour.getOpposite() == state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
			if (!canSurvive(state, level, pos)) {
				return Blocks.AIR.defaultBlockState();
			}
		}
		return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		if (context.getClickedFace() == Direction.DOWN) {
			return null;
		}
		boolean down = context.getClickedFace() == Direction.UP;
		return super.getStateForPlacement(context).setValue(BlockStateProperties.HORIZONTAL_FACING, down ? context.getHorizontalDirection().getOpposite() : Direction.from2DDataValue(context.getClickedFace().get2DDataValue())).setValue(BlockStateProperties.DOWN, down);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos offset = pos.below();
		if (state.getValue(BlockStateProperties.DOWN)) {
			return level.getBlockState(offset).isFaceSturdy(level, offset, Direction.UP, SupportType.FULL);
		}
		Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
		offset = pos.relative(direction);
		return level.getBlockState(offset).isFaceSturdy(level, offset, direction, SupportType.FULL);
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
		if (entity instanceof LivingEntity living && living.hurtTime == 0 && NyctoUtil.affectedByHurtsVampiresTag(entity)) {
			NyctoUtil.hurtWithToxicTouch(living, 1);
		}
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		if (!level.isClientSide()) {
			AuraComponent auraComponent = ModLevelComponents.AURA.get(level);
			auraComponent.getGarlicWreaths().add(pos);
			auraComponent.sync();
		}
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
		AuraComponent auraComponent = ModLevelComponents.AURA.get(level);
		auraComponent.getGarlicWreaths().remove(pos);
		auraComponent.sync();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.DOWN);
	}
}
