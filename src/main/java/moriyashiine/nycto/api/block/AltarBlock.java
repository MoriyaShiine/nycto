/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AltarBlock extends Block {
	private static final Text INVALID_TRANSFORMATION_TEXT = Text.translatable("message.nycto.altar.invalid_transformation");

	public AltarBlock(Settings settings) {
		super(settings);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!world.isClient()) {
			if (canUse(player)) {
				player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
			} else {
				player.sendMessage(INVALID_TRANSFORMATION_TEXT, true);
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	protected @Nullable NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		Text text = Text.translatable(getTranslationKey());
		return new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player) -> getScreenHandler(world, pos, playerInventory, syncId), text);
	}

	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.HORIZONTAL_FACING);
	}

	protected abstract ScreenHandler getScreenHandler(World world, BlockPos pos, PlayerInventory playerInventory, int syncId);

	protected abstract boolean canUse(PlayerEntity player);
}
