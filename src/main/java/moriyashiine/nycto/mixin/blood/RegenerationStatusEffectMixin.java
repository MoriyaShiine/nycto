/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.blood;

import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.RegenerationStatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RegenerationStatusEffect.class)
public class RegenerationStatusEffectMixin {
	@Inject(method = "applyUpdateEffect", at = @At("HEAD"))
	private void nycto$blood(ServerWorld world, LivingEntity entity, int amplifier, CallbackInfoReturnable<Boolean> cir) {
		if (ModEntityComponents.BLOOD.get(entity).criticalBlood()) {
			entity.removeStatusEffect(StatusEffects.REGENERATION);
		}
	}
}
