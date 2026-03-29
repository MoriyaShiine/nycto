/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.item;

import moriyashiine.nycto.common.init.ModComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class CapeItem extends Item {
	public CapeItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack self, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess carriedItem) {
		if (clickAction == ClickAction.SECONDARY && self.has(ModComponentTypes.SHOW_CAPE)) {
			self.set(ModComponentTypes.SHOW_CAPE, !self.get(ModComponentTypes.SHOW_CAPE));
			if (player.level().isClientSide()) {
				player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1, 1);
			}
			return true;
		}
		return super.overrideOtherStackedOnMe(self, other, slot, clickAction, player, carriedItem);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
		MutableComponent icon = Component.literal("× ");
		ChatFormatting formatting = ChatFormatting.DARK_RED;
		if (itemStack.getOrDefault(ModComponentTypes.SHOW_CAPE, false)) {
			icon = Component.literal("✔ ");
			formatting = ChatFormatting.DARK_GREEN;
		}
		builder.accept(icon.append(Component.translatable("tooltip.nycto.show_cape")).withStyle(formatting));
	}
}
