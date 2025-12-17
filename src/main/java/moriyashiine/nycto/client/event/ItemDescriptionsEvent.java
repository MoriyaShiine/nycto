/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.*;

public class ItemDescriptionsEvent implements ItemTooltipCallback {
	public static final Set<TooltipAdder> ARMOR_SET_BONUSES = new HashSet<>();

	private static final MinecraftClient client = MinecraftClient.getInstance();

	static {
		ARMOR_SET_BONUSES.add(stack -> getArmorSetText(stack, ModItemTags.VAMPIRE_ARMOR, ItemDescriptionsEvent.VAMPIRE_ARMOR_BONUSES));
		ARMOR_SET_BONUSES.add(stack -> getArmorSetText(stack, ModItemTags.VAMPIRE_HUNTER_ARMOR, ItemDescriptionsEvent.VAMPIRE_HUNTER_ARMOR_BONUSES));
	}

	private static final Text WEAKNESS_TEXT = Text.translatable("tooltip.nycto.in_weakness_tag").formatted(Formatting.ITALIC, Formatting.RED);

	private static final Text ARMOR_SET_DESCRIPTION = Text.translatable("tooltip.nycto.armor_set.desc").formatted(Formatting.GOLD);
	private static final List<Text> VAMPIRE_ARMOR_BONUSES = List.of(
			Text.translatable("tooltip.nycto.vampire_armor.bonus_1").formatted(Formatting.GRAY),
			Text.translatable("tooltip.nycto.vampire_armor.bonus_2").formatted(Formatting.GRAY),
			Text.translatable("tooltip.nycto.vampire_armor.bonus_3").formatted(Formatting.GRAY),
			Text.translatable("tooltip.nycto.vampire_armor.bonus_4").formatted(Formatting.GRAY)
	);
	private static final List<Text> VAMPIRE_HUNTER_ARMOR_BONUSES = List.of(
			Text.translatable("tooltip.nycto.vampire_hunter_armor.bonus_1").formatted(Formatting.GRAY),
			Text.translatable("tooltip.nycto.vampire_hunter_armor.bonus_2").formatted(Formatting.GRAY),
			Text.translatable("tooltip.nycto.vampire_hunter_armor.bonus_3").formatted(Formatting.GRAY),
			Text.translatable("tooltip.nycto.vampire_hunter_armor.bonus_4").formatted(Formatting.GRAY)
	);

	@Override
	public void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> lines) {
		addTooltip(lines, getWeaknessText(stack));

		// todo remove when werewolves are real
		if (stack.isOf(ModItems.WEREWOLF_ALTAR)) {
			addTooltip(lines, Optional.of(List.of(Text.literal("[WIP] Werewolves are not implemented! This block is just decoration for now.").formatted(Formatting.GRAY))));
		}
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private static void addTooltip(List<Text> lines, Optional<List<Text>> tooltips) {
		tooltips.ifPresent(texts -> lines.addAll(1, texts));
	}

	private static Optional<List<Text>> getArmorSetText(ItemStack stack, TagKey<Item> tagKey, List<Text> bonuses) {
		if (stack.isIn(tagKey)) {
			List<Text> lines = new ArrayList<>();
			lines.add(ScreenTexts.EMPTY);
			lines.add(ARMOR_SET_DESCRIPTION);
			int equipped = NyctoUtil.getEquippedArmorPieces(client.player, tagKey);
			for (int i = 0; i < bonuses.size(); i++) {
				MutableText bonus = bonuses.get(i).copy();
				if (equipped > i) {
					bonus.formatted(Formatting.DARK_GREEN);
				}
				lines.add(bonus);
			}
			return Optional.of(lines);
		}
		return Optional.empty();
	}

	private static Optional<List<Text>> getWeaknessText(ItemStack stack) {
		if (stack.isIn(ModItemTags.HURTS_VAMPIRES) && NyctoAPI.isVampire(client.player)) {
			return Optional.of(List.of(WEAKNESS_TEXT));
		}
		return Optional.empty();
	}

	public interface TooltipAdder {
		Optional<List<Text>> getTooltip(ItemStack stack);
	}
}
