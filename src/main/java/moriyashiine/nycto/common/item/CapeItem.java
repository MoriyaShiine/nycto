/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.item;

import moriyashiine.nycto.common.init.ModComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public class CapeItem extends Item {
	public CapeItem(Item.Settings settings) {
		super(settings);
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT && stack.contains(ModComponentTypes.SHOW_CAPE)) {
			stack.set(ModComponentTypes.SHOW_CAPE, !stack.get(ModComponentTypes.SHOW_CAPE));
			if (player.getEntityWorld().isClient()) {
				player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1, 1);
			}
			return true;
		}
		return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
		MutableText icon = Text.literal("× ");
		Formatting formatting = Formatting.DARK_RED;
		if (stack.getOrDefault(ModComponentTypes.SHOW_CAPE, false)) {
			icon = Text.literal("✔ ");
			formatting = Formatting.DARK_GREEN;
		}
		textConsumer.accept(icon.append(Text.translatable("tooltip.nycto.show_cape")).formatted(formatting));
	}
}
