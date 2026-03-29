/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.power.vampire.vampiricthrall.mobspecific;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.tag.ModItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Wolf.class)
public class WolfMixin {
	@ModifyArg(method = "isFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/tags/TagKey;)Z"))
	private TagKey<Item> nycto$vampiricThrall(TagKey<Item> tag) {
		if (ModEntityComponents.VAMPIRIC_THRALL.get(this).hasOwner()) {
			return ModItemTags.USABLE_BLOOD_BOTTLES;
		}
		return tag;
	}
}
