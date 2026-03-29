/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WanderingTrader.class)
public class WanderingTraderMixin {
	@ModifyExpressionValue(method = "lambda$registerGoals$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isDarkOutside()Z"))
	private boolean nycto$vampiricThrallPotion(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner();
	}

	@ModifyExpressionValue(method = "lambda$registerGoals$1", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isBrightOutside()Z"))
	private boolean nycto$vampiricThrallMilk(boolean original) {
		return original && !ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner();
	}
}
