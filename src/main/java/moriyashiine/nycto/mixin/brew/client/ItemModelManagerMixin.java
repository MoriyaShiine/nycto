/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.brew.client;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModPotions;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemModelManager.class)
public class ItemModelManagerMixin {
	@Unique
	private static final Identifier GARLIC_BREW = Nycto.id("garlic_brew");

	@Unique
	private static final Identifier SPLASH_GARLIC_BREW = Nycto.id("splash_garlic_brew");

	@Unique
	private static final Identifier LINGERING_GARLIC_BREW = Nycto.id("lingering_garlic_brew");

	@ModifyVariable(method = "update", at = @At("STORE"))
	private Identifier nycto$brew(Identifier value, ItemRenderState renderState, ItemStack stack) {
		if (stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).potion().stream().anyMatch(potion -> potion == ModPotions.GARLIC || potion == ModPotions.LONG_GARLIC || potion == ModPotions.STRONG_GARLIC)) {
			if (stack.isOf(Items.POTION)) {
				return GARLIC_BREW;
			} else if (stack.isOf(Items.SPLASH_POTION)) {
				return SPLASH_GARLIC_BREW;
			} else if (stack.isOf(Items.LINGERING_POTION)) {
				return LINGERING_GARLIC_BREW;
			}
		}
		return value;
	}
}
