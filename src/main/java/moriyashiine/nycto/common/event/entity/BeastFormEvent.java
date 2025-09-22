/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.tag.ModBlockTags;
import moriyashiine.strawberrylib.api.event.ModifyBlockBreakingSpeedEvent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class BeastFormEvent implements ModifyBlockBreakingSpeedEvent {
	@Override
	public float modify(float breakSpeed, PlayerEntity player, BlockState state, BlockView world, BlockPos pos) {
		return canHarvestAsBeast(player, state) ? 4 : 1;
	}

	public static boolean canHarvestAsBeast(PlayerEntity player, BlockState state) {
		if (state.isIn(ModBlockTags.BEAST_MINEABLE) && !state.isIn(BlockTags.INCORRECT_FOR_WOODEN_TOOL)) {
			return NyctoAPI.isBeastForm(player);
		}
		return false;
	}
}
