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

public class MaskItem extends Item {
	public MaskItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack self, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess carriedItem) {
		if (clickAction == ClickAction.SECONDARY) {
			MaskVisibility maskVisibility = self.get(ModComponentTypes.MASK_VISIBILITY);
			if (maskVisibility != null) {
				self.set(ModComponentTypes.MASK_VISIBILITY, maskVisibility.cycle());
				if (player.level().isClientSide()) {
					player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1, 1);
				}
				return true;
			}
		}
		return super.overrideOtherStackedOnMe(self, other, slot, clickAction, player, carriedItem);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
		MutableComponent icon = Component.literal("× ");
		ChatFormatting formatting = ChatFormatting.DARK_RED;
		MaskVisibility maskVisibility = itemStack.get(ModComponentTypes.MASK_VISIBILITY);
		if (maskVisibility != null) {
			switch (maskVisibility) {
				case VISIBLE -> {
					icon = Component.literal("✔ ");
					formatting = ChatFormatting.DARK_GREEN;
				}
				case REDUCED -> {
					icon = Component.literal("- ");
					formatting = ChatFormatting.GOLD;
				}
				case REMOVED -> {
				}
			}
			builder.accept(icon.append(Component.translatable("tooltip.nycto.mask_visibility", Component.translatable(maskVisibility.getTranslationKey()))).withStyle(formatting));
		}
	}
}
