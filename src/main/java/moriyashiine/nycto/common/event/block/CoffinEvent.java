/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.block;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.util.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class CoffinEvent {
	public static class AllowBed implements EntitySleepEvents.AllowBed {
		@Override
		public EventResult allowBed(LivingEntity entity, BlockPos sleepingPos, BlockState state, boolean vanillaResult) {
			return entity.level().isDarkOutside() && entity.level().getBlockState(sleepingPos).is(ModBlockTags.COFFINS) ? EventResult.DENY : EventResult.PASS;
		}
	}

	public static class AllowSleeping implements EntitySleepEvents.AllowSleeping {
		@Override
		public Player.@Nullable BedSleepingProblem allowSleep(Player player, BlockPos sleepingPos) {
			if (player.level().isDarkOutside() && player.level().getBlockState(sleepingPos).is(ModBlockTags.COFFINS)) {
				player.sendOverlayMessage(Component.translatable("block." + Nycto.MOD_ID + ".coffin.no_sleep"));
				return Player.BedSleepingProblem.OTHER_PROBLEM;
			}
			return null;
		}
	}
}
