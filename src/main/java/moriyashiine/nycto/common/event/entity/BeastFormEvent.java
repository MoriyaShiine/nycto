/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.tag.ModBlockTags;
import moriyashiine.strawberrylib.api.event.ModifyDestroyProgressEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class BeastFormEvent implements ModifyDestroyProgressEvent {
	@Override
	public float modify(Player player, BlockState state, BlockGetter level, BlockPos pos) {
		return canHarvestAsBeast(player, state) ? 4 : 1;
	}

	public static boolean canHarvestAsBeast(Player player, BlockState state) {
		if (state.is(ModBlockTags.BEAST_MINEABLE) && !state.is(BlockTags.INCORRECT_FOR_WOODEN_TOOL)) {
			return NyctoAPI.isBeastForm(player);
		}
		return false;
	}
}
