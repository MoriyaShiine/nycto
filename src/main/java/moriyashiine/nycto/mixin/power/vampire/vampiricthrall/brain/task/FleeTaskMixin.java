/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.brain.task;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.ai.brain.task.FleeTask;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FleeTask.class)
public class FleeTaskMixin {
	@ModifyReturnValue(method = "shouldRun(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/mob/PathAwareEntity;)Z", at = @At("RETURN"))
	private boolean nycto$vampiricThrall(boolean original, @Local(argsOnly = true) PathAwareEntity entity) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(entity).hasOwner();
	}
}
