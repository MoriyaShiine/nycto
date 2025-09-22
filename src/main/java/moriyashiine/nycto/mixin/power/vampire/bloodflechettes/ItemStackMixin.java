/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.bloodflechettes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.entity.projectile.BloodFlechetteEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@ModifyReturnValue(method = "takesDamageFrom", at = @At("RETURN"))
	private boolean nycto$bloodFlechettes(boolean original, DamageSource source) {
		return original && !(source.getSource() instanceof BloodFlechetteEntity);
	}
}
