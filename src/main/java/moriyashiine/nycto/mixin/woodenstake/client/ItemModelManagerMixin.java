/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.woodenstake.client;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
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
	private static final Identifier CROSSBOW_WOODEN_STAKE = Nycto.id("crossbow_wooden_stake");

	@ModifyVariable(method = "update", at = @At("STORE"))
	private Identifier nycto$woodenStake(Identifier value, ItemRenderState renderState, ItemStack stack) {
		if (stack.isOf(Items.CROSSBOW) && stack.getOrDefault(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT).contains(ModItems.WOODEN_STAKE)) {
			return CROSSBOW_WOODEN_STAKE;
		}
		return value;
	}
}
