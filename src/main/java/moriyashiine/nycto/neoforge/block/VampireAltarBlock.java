/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.block;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.menu.VampireAltarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VampireAltarBlock extends HorizontalDirectionalBlock {
	public static final MapCodec<VampireAltarBlock> CODEC = simpleCodec(VampireAltarBlock::new);

	private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 14, 15);
	private static final Component TITLE = Component.translatable("block.nycto.vampire_altar");

	public VampireAltarBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH));
	}

	@Override
	protected MapCodec<VampireAltarBlock> codec() {
		return CODEC;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected VoxelShape getShape(BlockState state, net.minecraft.world.level.BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (level.isClientSide()) {
			return ItemInteractionResult.SUCCESS;
		}
		if (!NyctoData.isVampire(player)) {
			player.sendSystemMessage(Component.translatable("block.nycto.vampire_altar.vampire_only"));
			return ItemInteractionResult.CONSUME;
		}
		openAltar(level, pos, player);
		return ItemInteractionResult.SUCCESS;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		}
		if (!NyctoData.isVampire(player)) {
			player.sendSystemMessage(Component.translatable("block.nycto.vampire_altar.vampire_only"));
			return InteractionResult.CONSUME;
		}
		openAltar(level, pos, player);
		return InteractionResult.SUCCESS;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING);
	}

	private static void openAltar(Level level, BlockPos pos, Player player) {
		if (player instanceof ServerPlayer serverPlayer) {
			serverPlayer.openMenu(new SimpleMenuProvider((containerId, inventory, ignored) -> new VampireAltarMenu(containerId, inventory, ContainerLevelAccess.create(level, pos)), TITLE));
		}
	}
}
