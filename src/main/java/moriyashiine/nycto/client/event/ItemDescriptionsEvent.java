/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.*;

public class ItemDescriptionsEvent implements ItemTooltipCallback {
	public static final Set<TooltipAdder> ARMOR_SET_BONUSES = new HashSet<>();

	private static final Minecraft client = Minecraft.getInstance();

	static {
		ARMOR_SET_BONUSES.add(stack -> getArmorSetText(stack, ModItemTags.VAMPIRE_ARMOR, ItemDescriptionsEvent.VAMPIRE_ARMOR_BONUSES));
		ARMOR_SET_BONUSES.add(stack -> getArmorSetText(stack, ModItemTags.VAMPIRE_HUNTER_ARMOR, ItemDescriptionsEvent.VAMPIRE_HUNTER_ARMOR_BONUSES));
	}

	private static final Component WEAKNESS_TEXT = Component.translatable("tooltip.nycto.in_weakness_tag").withStyle(ChatFormatting.ITALIC, ChatFormatting.RED);

	private static final Component ARMOR_SET_DESCRIPTION = Component.translatable("tooltip.nycto.armor_set.desc").withStyle(ChatFormatting.GOLD);
	private static final List<Component> VAMPIRE_ARMOR_BONUSES = List.of(
			Component.translatable("tooltip.nycto.vampire_armor.bonus_1").withStyle(ChatFormatting.GRAY),
			Component.translatable("tooltip.nycto.vampire_armor.bonus_2").withStyle(ChatFormatting.GRAY),
			Component.translatable("tooltip.nycto.vampire_armor.bonus_3").withStyle(ChatFormatting.GRAY),
			Component.translatable("tooltip.nycto.vampire_armor.bonus_4").withStyle(ChatFormatting.GRAY)
	);
	private static final List<Component> VAMPIRE_HUNTER_ARMOR_BONUSES = List.of(
			Component.translatable("tooltip.nycto.vampire_hunter_armor.bonus_1").withStyle(ChatFormatting.GRAY),
			Component.translatable("tooltip.nycto.vampire_hunter_armor.bonus_2").withStyle(ChatFormatting.GRAY),
			Component.translatable("tooltip.nycto.vampire_hunter_armor.bonus_3").withStyle(ChatFormatting.GRAY),
			Component.translatable("tooltip.nycto.vampire_hunter_armor.bonus_4").withStyle(ChatFormatting.GRAY)
	);

	@Override
	public void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag, List<Component> lines) {
		addTooltip(lines, getWeaknessText(stack));

		// todo remove when werewolves are real
		if (stack.is(ModItems.WEREWOLF_ALTAR)) {
			addTooltip(lines, Optional.of(List.of(Component.literal("[WIP] Werewolves are not implemented! This block is just decoration for now.").withStyle(ChatFormatting.GRAY))));
		}
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private static void addTooltip(List<Component> lines, Optional<List<Component>> tooltips) {
		tooltips.ifPresent(texts -> lines.addAll(1, texts));
	}

	private static Optional<List<Component>> getArmorSetText(ItemStack stack, TagKey<Item> tagKey, List<Component> bonuses) {
		if (stack.is(tagKey)) {
			List<Component> lines = new ArrayList<>();
			lines.add(CommonComponents.EMPTY);
			lines.add(ARMOR_SET_DESCRIPTION);
			int equipped = NyctoUtil.getEquippedArmorPieces(client.player, tagKey);
			for (int i = 0; i < bonuses.size(); i++) {
				MutableComponent bonus = bonuses.get(i).copy();
				if (equipped > i) {
					bonus.withStyle(ChatFormatting.DARK_GREEN);
				}
				lines.add(bonus);
			}
			return Optional.of(lines);
		}
		return Optional.empty();
	}

	private static Optional<List<Component>> getWeaknessText(ItemStack stack) {
		if (stack.is(ModItemTags.HURTS_VAMPIRES) && NyctoAPI.isVampire(client.player)) {
			return Optional.of(List.of(WEAKNESS_TEXT));
		}
		return Optional.empty();
	}

	public interface TooltipAdder {
		Optional<List<Component>> getTooltip(ItemStack stack);
	}
}
