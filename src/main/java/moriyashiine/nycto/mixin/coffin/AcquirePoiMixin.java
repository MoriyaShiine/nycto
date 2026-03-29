/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.coffin;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AcquirePoi;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(AcquirePoi.class)
public class AcquirePoiMixin {
	@ModifyArg(method = "lambda$create$3", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/village/poi/PoiManager;findAllClosestFirstWithType(Ljava/util/function/Predicate;Ljava/util/function/Predicate;Lnet/minecraft/core/BlockPos;ILnet/minecraft/world/entity/ai/village/poi/PoiManager$Occupancy;)Ljava/util/stream/Stream;"), index = 1)
	private static Predicate<BlockPos> nycto$coffin(Predicate<BlockPos> posPredicate, @Local(argsOnly = true) ServerLevel level, @Local(argsOnly = true) PathfinderMob body) {
		return posPredicate.and(pos -> {
			BlockState state = level.getBlockState(pos);
			if (state.is(BlockTags.BEDS)) {
				return NyctoAPI.isVampire(body) == state.is(ModBlockTags.COFFINS);
			}
			return true;
		});
	}
}
