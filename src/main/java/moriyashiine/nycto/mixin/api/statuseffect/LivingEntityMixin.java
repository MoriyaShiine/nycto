/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.api.statuseffect;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.api.world.effect.EntityRemovableMobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "onEffectUpdated", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;removeAttributeModifiers(Lnet/minecraft/world/entity/ai/attributes/AttributeMap;)V"))
	private void nycto$statusEffect(MobEffectInstance effect, boolean doRefreshAttributes, Entity source, CallbackInfo ci) {
		if (effect.getEffect().value() instanceof EntityRemovableMobEffect removable) {
			removable.onEffectRemoved((LivingEntity) (Object) this);
		}
	}

	@Inject(method = "onEffectsRemoved", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;removeAttributeModifiers(Lnet/minecraft/world/entity/ai/attributes/AttributeMap;)V"))
	private void nycto$statusEffect(Collection<MobEffectInstance> effects, CallbackInfo ci, @Local(name = "effect") MobEffectInstance effect) {
		if (effect.getEffect().value() instanceof EntityRemovableMobEffect removable) {
			removable.onEffectRemoved((LivingEntity) (Object) this);
		}
	}
}
