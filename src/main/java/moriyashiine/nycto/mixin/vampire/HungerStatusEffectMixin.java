/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.vampire;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.transformation.VampireTransformation;
import net.minecraft.entity.effect.HungerStatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HungerStatusEffect.class)
public class HungerStatusEffectMixin {
	@WrapOperation(method = "applyUpdateEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"))
	private void nycto$vampire(PlayerEntity instance, float exhaustion, Operation<Void> original) {
		if (NyctoAPI.isVampire(instance)) {
			exhaustion *= VampireTransformation.VAMPIRE_EXHAUSTION_MULTIPLIER * 24;
		}
		original.call(instance, exhaustion);
	}
}
