/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.vampire;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SuspiciousStewEffects.class)
public class SuspiciousStewEffectsMixin {
	@SuppressWarnings("WrapWithConditionTargetsNonVoid")
	@WrapWithCondition(method = "onConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"))
	private boolean nycto$vampire(LivingEntity instance, MobEffectInstance newEffect) {
		return newEffect.getEffect() != MobEffects.SATURATION || !NyctoAPI.isVampire(instance);
	}
}
