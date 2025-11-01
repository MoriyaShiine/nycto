/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.misc;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
	@ModifyReturnValue(method = "canWalkOnPowderSnow", at = @At("RETURN"))
	private static boolean nycto$powderSnowBoots(boolean original, Entity entity) {
		if (!original && entity instanceof LivingEntity living) {
			ItemStack equippedBoots = living.getEquippedStack(EquipmentSlot.FEET);
			if (equippedBoots.isOf(ModItems.VAMPIRE_BOOTS) || equippedBoots.isOf(ModItems.VAMPIRE_HUNTER_BOOTS) || equippedBoots.isOf(ModItems.WEREWOLF_HUNTER_BOOTS)) {
				return true;
			}
		}
		return original;
	}
}
