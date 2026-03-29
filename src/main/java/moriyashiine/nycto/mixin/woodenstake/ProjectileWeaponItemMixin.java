/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.woodenstake;

import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Predicate;

@Mixin(ProjectileWeaponItem.class)
public class ProjectileWeaponItemMixin {
	@Shadow
	@Mutable
	@Final
	public static Predicate<ItemStack> ARROW_OR_FIREWORK;

	static {
		ARROW_OR_FIREWORK = ARROW_OR_FIREWORK.or(stack -> stack.is(ModItems.WOODEN_STAKE));
	}
}
