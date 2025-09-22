/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.vampire;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SuspiciousStewEffectsComponent.class)
public class SuspiciousStewEffectsComponentMixin {
	@WrapWithCondition(method = "onConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z"))
	private boolean nycto$vampire(LivingEntity instance, StatusEffectInstance effect) {
		return effect.getEffectType() != StatusEffects.SATURATION || !NyctoAPI.isVampire(instance);
	}
}
