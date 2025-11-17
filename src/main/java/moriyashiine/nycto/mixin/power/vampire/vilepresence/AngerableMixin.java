/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vilepresence;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.power.vampire.weakness.VilePresencePower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Angerable.class)
public interface AngerableMixin {
	@ModifyReturnValue(method = "shouldAngerAt", at = @At(value = "RETURN", ordinal = 2))
	private boolean nycto$vilePresence(boolean original, LivingEntity entity) {
		return original || ((Object) this instanceof Entity self && entity instanceof PlayerEntity player && VilePresencePower.isAffected(self, player));
	}
}
