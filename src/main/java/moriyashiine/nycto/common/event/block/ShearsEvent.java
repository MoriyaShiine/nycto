/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.block;

import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.strawberrylib.api.event.ModifyDestroySpeedEvent;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class ShearsEvent implements ModifyDestroySpeedEvent {
	@Override
	public float modify(Player player, ItemStack stack, Level level, BlockState state, @Nullable BlockPos pos) {
		if (stack.is(ConventionalItemTags.SHEAR_TOOLS)) {
			if (state.is(ModBlocks.GARLIC_WREATH) || state.is(ModBlocks.ACONITE_GARLAND)) {
				return 5;
			}
		}
		return 1;
	}
}
