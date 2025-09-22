/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.SaturationStatusEffect;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SaturationStatusEffect.class)
public class SaturationStatusEffectMixin {
	@Inject(method = "applyUpdateEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;add(IF)V"))
	private void nycto$vampire(ServerWorld world, LivingEntity entity, int amplifier, CallbackInfoReturnable<Boolean> cir) {
		if (NyctoAPI.isVampire(entity)) {
			ModEntityComponents.BLOOD.get(entity).fill(amplifier + 1);
		}
	}
}
