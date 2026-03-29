/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.woodenstake.client;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {
	@Unique
	private static final Identifier CROSSBOW_WOODEN_STAKE = Nycto.id("crossbow_wooden_stake");

	@ModifyVariable(method = "appendItemLayers", at = @At("STORE"), name = "modelId")
	private Identifier nycto$woodenStake(Identifier modelId, ItemStackRenderState output, ItemStack item) {
		if (item.is(Items.CROSSBOW) && item.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).contains(ModItems.WOODEN_STAKE)) {
			return CROSSBOW_WOODEN_STAKE;
		}
		return modelId;
	}
}
