/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.mistform;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin extends EntityMixin {
	@ModifyReturnValue(method = "getArmorVisibility", at = @At("RETURN"))
	protected float nycto$mistForm(float original) {
		return original;
	}
}
