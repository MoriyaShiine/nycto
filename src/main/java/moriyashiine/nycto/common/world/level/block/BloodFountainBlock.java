/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.level.block;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.common.init.ModBlockEntityTypes;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.world.item.consumeeffects.FillBloodConsumeEffect;
import moriyashiine.nycto.common.world.level.block.entity.BloodFountainBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.Locale;

public class BloodFountainBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
	public static final EnumProperty<FillState> FILL_STATE = EnumProperty.create("fill_state", FillState.class);

	public static final MapCodec<BloodFountainBlock> CODEC = simpleCodec(BloodFountainBlock::new);

	private static final VoxelShape SHAPE = Shapes.or(column(14, 0, 6), column(3, 6, 14), column(6, 14, 16));

	public BloodFountainBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState()
				.setValue(BlockStateProperties.WATERLOGGED, false)
				.setValue(BlockStateProperties.LOCKED, false));
	}

	@Override
	public MapCodec<BloodFountainBlock> codec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
		return new BloodFountainBlockEntity(worldPosition, blockState);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
		return createTickerHelper(type, ModBlockEntityTypes.BLOOD_FOUNTAIN, level.isClientSide() || blockState.getValue(BlockStateProperties.LOCKED) ? null : BloodFountainBlockEntity::serverTick);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
				.setValue(BlockStateProperties.WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER))
				.setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection());
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return false;
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (player.isShiftKeyDown()) {
			if (!level.isClientSide()) {
				level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, state.getValue(BlockStateProperties.LOCKED) ? ModSoundEvents.BLOOD_FOUNTAIN_UNLOCK : ModSoundEvents.BLOOD_FOUNTAIN_LOCK, SoundSource.BLOCKS, 1, 1);
				level.setBlockAndUpdate(pos, state.cycle(BlockStateProperties.LOCKED));
			}
			return InteractionResult.SUCCESS;
		}
		return super.useWithoutItem(state, level, pos, player, hitResult);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (itemStack.has(DataComponents.CONSUMABLE) && itemStack.get(DataComponents.CONSUMABLE).onConsumeEffects().stream().anyMatch(effect -> effect instanceof FillBloodConsumeEffect) && level.getBlockEntity(pos) instanceof BloodFountainBlockEntity blockEntity) {
			ItemStack copy = itemStack.copyWithCount(1);
			if (blockEntity.insertBottle(copy)) {
				if (!level.isClientSide()) {
					level.playSound(null, pos, ModSoundEvents.BLOOD_BOTTLE_DRINK.value(), SoundSource.BLOCKS, 1, 1);
					itemStack.consume(1, player);
					if (!player.isCreative() && copy.has(DataComponents.USE_REMAINDER)) {
						player.handleExtraItemsCreatedOnUse(copy.get(DataComponents.USE_REMAINDER).convertInto().create());
					}
				}
				return InteractionResult.SUCCESS;
			}
		}
		return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.WATERLOGGED, BlockStateProperties.LOCKED, BlockStateProperties.HORIZONTAL_FACING, FILL_STATE);
	}

	public enum FillState implements StringRepresentable {
		EMPTY, BLOOD, AMBROSIA;

		@Override
		public String getSerializedName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
}
