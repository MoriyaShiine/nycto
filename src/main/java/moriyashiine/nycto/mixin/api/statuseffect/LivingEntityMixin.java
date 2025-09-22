/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.api.statuseffect;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.api.statuseffect.EntityRemovableStatusEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "onStatusEffectUpgraded", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffect;onRemoved(Lnet/minecraft/entity/attribute/AttributeContainer;)V"))
	private void nycto$statusEffect(StatusEffectInstance effect, boolean reapplyEffect, Entity source, CallbackInfo ci) {
		if (effect.getEffectType().value() instanceof EntityRemovableStatusEffect removable) {
			removable.onRemoved((LivingEntity) (Object) this);
		}
	}

	@Inject(method = "onStatusEffectsRemoved", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffect;onRemoved(Lnet/minecraft/entity/attribute/AttributeContainer;)V"))
	private void nycto$statusEffect(Collection<StatusEffectInstance> effects, CallbackInfo ci, @Local StatusEffectInstance effect) {
		if (effect.getEffectType().value() instanceof EntityRemovableStatusEffect removable) {
			removable.onRemoved((LivingEntity) (Object) this);
		}
	}
}
