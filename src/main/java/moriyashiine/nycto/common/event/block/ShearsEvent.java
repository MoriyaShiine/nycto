/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.block;

import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.strawberrylib.api.event.ModifyDestroyProgressEvent;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class ShearsEvent implements ModifyDestroyProgressEvent {
	@Override
	public float modify(Player player, BlockState state, BlockGetter level, BlockPos pos) {
		if (player.getMainHandItem().is(ConventionalItemTags.SHEAR_TOOLS)) {
			if (state.is(ModBlocks.GARLIC_WREATH) || state.is(ModBlocks.ACONITE_GARLAND)) {
				return 5;
			}
		}
		return 1;
	}
}
