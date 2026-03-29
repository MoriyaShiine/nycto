/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.brew.client;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModPotions;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {
	@Unique
	private static final Identifier GARLIC_BREW = Nycto.id("garlic_brew");

	@Unique
	private static final Identifier SPLASH_GARLIC_BREW = Nycto.id("splash_garlic_brew");

	@Unique
	private static final Identifier LINGERING_GARLIC_BREW = Nycto.id("lingering_garlic_brew");

	@ModifyVariable(method = "appendItemLayers", at = @At("STORE"), name = "modelId")
	private Identifier nycto$brew(Identifier modelId, ItemStackRenderState output, ItemStack item) {
		if (item.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion().stream().anyMatch(potion -> potion == ModPotions.GARLIC || potion == ModPotions.LONG_GARLIC || potion == ModPotions.STRONG_GARLIC)) {
			if (item.is(Items.POTION)) {
				return GARLIC_BREW;
			} else if (item.is(Items.SPLASH_POTION)) {
				return SPLASH_GARLIC_BREW;
			} else if (item.is(Items.LINGERING_POTION)) {
				return LINGERING_GARLIC_BREW;
			}
		}
		return modelId;
	}
}
