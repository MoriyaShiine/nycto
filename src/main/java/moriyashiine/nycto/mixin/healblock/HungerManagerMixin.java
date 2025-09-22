/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.healblock;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
	@ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;canFoodHeal()Z"))
	private boolean nycto$healBlock(boolean original, ServerPlayerEntity player) {
		return original && !ModEntityComponents.HEAL_BLOCK.get(player).isHealingBlocked();
	}
}
