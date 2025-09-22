/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.woodenstake;

import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Predicate;

@Mixin(RangedWeaponItem.class)
public class RangedWeaponItemMixin {
	@Shadow
	@Mutable
	@Final
	public static Predicate<ItemStack> CROSSBOW_HELD_PROJECTILES;

	static {
		CROSSBOW_HELD_PROJECTILES = CROSSBOW_HELD_PROJECTILES.or(stack -> stack.isOf(ModItems.WOODEN_STAKE));
	}
}
