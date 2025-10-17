/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.brain.task;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.ai.brain.task.PanicTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PanicTask.class)
public class PanicTaskMixin {
	@ModifyReturnValue(method = "shouldKeepRunning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/VillagerEntity;J)Z", at = @At("RETURN"))
	private boolean nycto$vampiricThrall(boolean original, ServerWorld world, VillagerEntity villager) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(villager).hasOwner();
	}

	@Inject(method = "run(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/VillagerEntity;J)V", at = @At("HEAD"), cancellable = true)
	private void nycto$vampiricThrall(ServerWorld world, VillagerEntity villager, long l, CallbackInfo ci) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(villager).hasOwner()) {
			ci.cancel();
		}
	}
}
