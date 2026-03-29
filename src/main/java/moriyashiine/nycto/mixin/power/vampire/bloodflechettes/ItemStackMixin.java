/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.bloodflechettes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.world.entity.projectile.arrow.BloodFlechette;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@ModifyReturnValue(method = "canBeHurtBy", at = @At("RETURN"))
	private boolean nycto$bloodFlechettes(boolean original, DamageSource source) {
		return original && !(source.getDirectEntity() instanceof BloodFlechette);
	}
}
