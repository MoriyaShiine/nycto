/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.level.block;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.level.block.AltarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WerewolfAltarBlock extends AltarBlock {
	private static final VoxelShape[] SHAPES = {box(1, 0, 0, 15, 8, 16), box(0, 0, 1, 16, 8, 15)};

	public WerewolfAltarBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPES[state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis() == Direction.Axis.Z ? 0 : 1];
	}

	@Override
	protected AbstractContainerMenu getScreenHandler(Level level, BlockPos pos, Inventory inventory, int containerId) {
		return null;
	}

	@Override
	protected boolean canUse(Player player) {
		return NyctoAPI.isWerewolf(player);
	}
}
