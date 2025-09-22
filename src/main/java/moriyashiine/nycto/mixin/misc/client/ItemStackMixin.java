/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.misc.client;

import moriyashiine.nycto.client.event.ItemDescriptionsEvent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Inject(method = "appendAttributeModifiersTooltip", at = @At("HEAD"))
	private void nycto$itemDescriptions(Consumer<Text> textConsumer, TooltipDisplayComponent displayComponent, @Nullable PlayerEntity player, CallbackInfo ci) {
		for (ItemDescriptionsEvent.TooltipAdder adder : ItemDescriptionsEvent.ARMOR_SET_BONUSES) {
			adder.getTooltip((ItemStack) (Object) this).ifPresent(texts -> texts.forEach(textConsumer));
		}
	}
}
