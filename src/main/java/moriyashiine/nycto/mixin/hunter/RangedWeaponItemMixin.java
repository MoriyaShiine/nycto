/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.hunter;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.entity.mob.HunterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.RangedWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RangedWeaponItem.class)
public class RangedWeaponItemMixin {
	@ModifyExpressionValue(method = "getProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isInCreativeMode()Z"))
	private static boolean nycto$hunter(boolean original, @Local(argsOnly = true) LivingEntity shooter) {
		return original || shooter instanceof HunterEntity;
	}
}
