package moriyashiine.nycto.api.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public abstract class AltarBlock extends Block implements SimpleWaterloggedBlock {
	private static final Component INVALID_TRANSFORMATION_TEXT = Component.translatable("message.nycto.altar.invalid_transformation");

	public AltarBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState()
				.setValue(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
				.setValue(BlockStateProperties.WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER))
				.setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (!level.isClientSide()) {
			if (canUse(player)) {
				player.openMenu(state.getMenuProvider(level, pos));
			} else {
				player.sendOverlayMessage(INVALID_TRANSFORMATION_TEXT);
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		Component component = Component.translatable(getDescriptionId());
		return new SimpleMenuProvider((containerId, inventory, _) -> getScreenHandler(level, pos, inventory, containerId), component);
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.WATERLOGGED, BlockStateProperties.HORIZONTAL_FACING);
	}

	protected abstract AbstractContainerMenu getScreenHandler(Level level, BlockPos pos, Inventory inventory, int containerId);

	protected abstract boolean canUse(Player player);
}
