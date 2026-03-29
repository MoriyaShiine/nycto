/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.misc;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
	@ModifyReturnValue(method = "canEntityWalkOnPowderSnow", at = @At("RETURN"))
	private static boolean nycto$powderSnowBoots(boolean original, Entity entity) {
		if (!original && entity instanceof LivingEntity living) {
			ItemStack feetStack = living.getItemBySlot(EquipmentSlot.FEET);
			if (feetStack.is(ModItems.VAMPIRE_BOOTS) || feetStack.is(ModItems.VAMPIRE_HUNTER_BOOTS) || feetStack.is(ModItems.WEREWOLF_HUNTER_BOOTS)) {
				return true;
			}
		}
		return original;
	}
}
