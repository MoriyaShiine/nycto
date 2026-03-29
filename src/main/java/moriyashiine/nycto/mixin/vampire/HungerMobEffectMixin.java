/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.vampire;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.world.transformation.VampireTransformation;
import net.minecraft.world.effect.HungerMobEffect;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HungerMobEffect.class)
public class HungerMobEffectMixin {
	@WrapOperation(method = "applyEffectTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"))
	private void nycto$vampire(Player instance, float amount, Operation<Void> original) {
		if (NyctoAPI.isVampire(instance)) {
			amount *= VampireTransformation.VAMPIRE_EXHAUSTION_MULTIPLIER * 24;
		}
		original.call(instance, amount);
	}
}
