/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.block;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CoffinEvent {
	public static class AllowBed implements EntitySleepEvents.AllowBed {
		@Override
		public ActionResult allowBed(LivingEntity entity, BlockPos sleepingPos, BlockState state, boolean vanillaResult) {
			return entity.getWorld().isNight() && entity.getWorld().getBlockState(sleepingPos).isIn(ModBlockTags.COFFINS) ? ActionResult.FAIL : ActionResult.PASS;
		}
	}

	public static class AllowSleeping implements EntitySleepEvents.AllowSleeping {
		@Override
		public PlayerEntity.@Nullable SleepFailureReason allowSleep(PlayerEntity player, BlockPos sleepingPos) {
			if (player.getWorld().isNight() && player.getWorld().getBlockState(sleepingPos).isIn(ModBlockTags.COFFINS)) {
				player.sendMessage(Text.translatable("block." + Nycto.MOD_ID + ".coffin.no_sleep"), true);
				return PlayerEntity.SleepFailureReason.OTHER_PROBLEM;
			}
			return null;
		}
	}

	public static class AllowSleepTime implements EntitySleepEvents.AllowSleepTime {
		@Override
		public ActionResult allowSleepTime(PlayerEntity player, BlockPos sleepingPos, boolean vanillaResult) {
			return player.getWorld().isDay() && player.getWorld().getBlockState(sleepingPos).isIn(ModBlockTags.COFFINS) ? ActionResult.SUCCESS : ActionResult.PASS;
		}
	}
}
