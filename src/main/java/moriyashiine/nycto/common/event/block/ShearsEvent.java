/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.block;

import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.strawberrylib.api.event.ModifyBlockBreakingSpeedEvent;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ShearsEvent implements ModifyBlockBreakingSpeedEvent {
	@Override
	public float modify(float breakSpeed, PlayerEntity player, BlockState state, BlockView world, BlockPos pos) {
		if (player.getMainHandStack().isIn(ConventionalItemTags.SHEAR_TOOLS)) {
			if (state.isOf(ModBlocks.GARLIC_WREATH) || state.isOf(ModBlocks.ACONITE_GARLAND)) {
				return 5;
			}
		}
		return 1;
	}
}
