/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.IllagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IllagerEntity.class)
public class IllagerEntityMixin {
	@ModifyExpressionValue(method = "isInSameTeam", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
	private boolean nycto$vampiricThrall(boolean original, Entity other) {
		if (original) {
			if (ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner() || ModEntityComponents.VAMPIRIC_THRALL.get(other).hasOwner()) {
				return false;
			}
		}
		return original;
	}
}
