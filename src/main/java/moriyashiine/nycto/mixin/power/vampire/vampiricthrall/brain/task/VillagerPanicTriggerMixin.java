/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.brain.task;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.VillagerPanicTrigger;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerPanicTrigger.class)
public class VillagerPanicTriggerMixin {
	@ModifyReturnValue(method = "canStillUse(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/villager/Villager;J)Z", at = @At("RETURN"))
	private boolean nycto$vampiricThrall(boolean original, ServerLevel level, Villager body) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(body).hasOwner();
	}

	@Inject(method = "start(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/villager/Villager;J)V", at = @At("HEAD"), cancellable = true)
	private void nycto$vampiricThrall(ServerLevel level, Villager body, long timestamp, CallbackInfo ci) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(body).hasOwner()) {
			ci.cancel();
		}
	}
}
