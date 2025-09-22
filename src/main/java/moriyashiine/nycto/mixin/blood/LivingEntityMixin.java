/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.blood;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@ModifyReturnValue(method = "canHaveStatusEffect", at = @At("RETURN"))
	private boolean nycto$blood(boolean original, StatusEffectInstance effect) {
		return original && !(effect.getEffectType() == StatusEffects.REGENERATION && ModEntityComponents.BLOOD.get(this).criticalBlood());
	}
}
