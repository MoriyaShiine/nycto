/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.tag.ModItemTags;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WolfEntity.class)
public class WolfEntityMixin {
	@ModifyArg(method = "isBreedingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
	private TagKey<Item> nycto$vampiricThrall(TagKey<Item> tag) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner()) {
			return ModItemTags.USABLE_BLOOD_BOTTLES;
		}
		return tag;
	}
}
