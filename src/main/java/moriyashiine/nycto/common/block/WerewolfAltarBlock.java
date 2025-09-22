/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.block;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.block.AltarBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class WerewolfAltarBlock extends AltarBlock {
	private static final VoxelShape[] SHAPES = {createCuboidShape(1, 0, 0, 15, 8, 16), createCuboidShape(0, 0, 1, 16, 8, 15)};

	public WerewolfAltarBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPES[state.get(Properties.HORIZONTAL_FACING).getAxis() == Direction.Axis.Z ? 0 : 1];
	}

	@Override
	protected ScreenHandler getScreenHandler(World world, BlockPos pos, PlayerInventory playerInventory, int syncId) {
		return null;
	}

	@Override
	protected boolean canUse(PlayerEntity player) {
		return NyctoAPI.isWerewolf(player);
	}
}
