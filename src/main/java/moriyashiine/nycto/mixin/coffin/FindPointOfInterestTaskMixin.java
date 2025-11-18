/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.coffin;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.task.FindPointOfInterestTask;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(FindPointOfInterestTask.class)
public class FindPointOfInterestTaskMixin {
	@ModifyArg(method = "method_46885", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/poi/PointOfInterestStorage;getSortedTypesAndPositions(Ljava/util/function/Predicate;Ljava/util/function/Predicate;Lnet/minecraft/util/math/BlockPos;ILnet/minecraft/world/poi/PointOfInterestStorage$OccupationStatus;)Ljava/util/stream/Stream;"), index = 1)
	private static Predicate<BlockPos> nycto$coffin(Predicate<BlockPos> posPredicate, @Local(argsOnly = true) ServerWorld world, @Local(argsOnly = true) PathAwareEntity entity) {
		return posPredicate.and(pos -> {
			BlockState state = world.getBlockState(pos);
			if (state.isIn(BlockTags.BEDS)) {
				return NyctoAPI.isVampire(entity) == state.isIn(ModBlockTags.COFFINS);
			}
			return true;
		});
	}
}
