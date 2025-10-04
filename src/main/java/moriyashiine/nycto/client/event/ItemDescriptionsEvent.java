/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.init.ModPotions;
import moriyashiine.nycto.common.item.VampiricDaggerItem;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.*;
import java.util.function.Predicate;

public class ItemDescriptionsEvent implements ItemTooltipCallback {
	public static final Set<TooltipAdder> ARMOR_SET_BONUSES = new HashSet<>();

	private static final MinecraftClient client = MinecraftClient.getInstance();

	static {
		ARMOR_SET_BONUSES.add(stack -> getArmorSetText(stack, ModItemTags.VAMPIRE_ARMOR, ItemDescriptionsEvent.VAMPIRE_ARMOR_BONUSES));
		ARMOR_SET_BONUSES.add(stack -> getArmorSetText(stack, ModItemTags.VAMPIRE_HUNTER_ARMOR, ItemDescriptionsEvent.VAMPIRE_HUNTER_ARMOR_BONUSES));
	}

	private static final Text HOLD_LEFT_SHEFT_TEXT = Text.translatable("tooltip.nycto.hold_left_shift", Text.translatable("key.keyboard.left.shift").formatted(Formatting.GOLD)).formatted(Formatting.GRAY);
	private static final Text HOLDING_PLAYER_BLOOD_TEXT = Text.translatable("tooltip.nycto.holding_player_blood").formatted(Formatting.ITALIC, Formatting.GRAY);
	private static final Text HOLDING_VAMPIRE_BLOOD_TEXT = Text.translatable("tooltip.nycto.holding_vampire_blood").formatted(Formatting.ITALIC, Formatting.GRAY);

	private static final Text WEAKNESS_TEXT = Text.translatable("tooltip.nycto.in_weakness_tag").formatted(Formatting.ITALIC, Formatting.RED);

	private static final List<Text> VAMPIRIC_DAGGER_DESCRIPTIONS = List.of(
			Text.translatable("tooltip.nycto.vampiric_dagger.desc.0").formatted(Formatting.GRAY),
			Text.translatable("tooltip.nycto.vampiric_dagger.desc.1").formatted(Formatting.GRAY)
	);
	private static final List<Text> GARLIC_BREW_DESCRIPTIONS = List.of(
			Text.translatable("tooltip.nycto.garlic_brew.desc.0").formatted(Formatting.GRAY),
			Text.translatable("tooltip.nycto.garlic_brew.desc.1").formatted(Formatting.GRAY)
	);

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
		addTooltip(lines, getVampiricDaggerText(stack));
		addTooltip(lines, getDescriptionText(stack, GARLIC_BREW_DESCRIPTIONS, s -> s.isOf(Items.POTION) && s.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).potion().stream().anyMatch(potion -> potion == ModPotions.GARLIC || potion == ModPotions.LONG_GARLIC || potion == ModPotions.STRONG_GARLIC)));
		addTooltip(lines, getWeaknessText(stack));
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

	private static Optional<List<Text>> getVampiricDaggerText(ItemStack stack) {
		Optional<List<Text>> description = getDescriptionText(stack, VAMPIRIC_DAGGER_DESCRIPTIONS, s -> s.getItem() instanceof VampiricDaggerItem);
		description.ifPresent(lines -> {
			boolean playerBlood = stack.getOrDefault(ModComponentTypes.PLAYER_BLOOD, false);
			boolean vampireBlood = stack.getOrDefault(ModComponentTypes.VAMPIRE_BLOOD, false);
			if (!playerBlood && !vampireBlood) {
				lines.removeLast();
			}
			if (playerBlood) {
				lines.add(HOLDING_PLAYER_BLOOD_TEXT);
			}
			if (vampireBlood) {
				lines.add(HOLDING_VAMPIRE_BLOOD_TEXT);
			}
		});
		return description;
	}

	private static Optional<List<Text>> getDescriptionText(ItemStack stack, List<Text> descriptions, Predicate<ItemStack> predicate) {
		if (predicate.test(stack)) {
			List<Text> lines = new ArrayList<>();
			if (InputUtil.isKeyPressed(client.getWindow(), InputUtil.GLFW_KEY_LEFT_SHIFT)) {
				lines.addAll(descriptions);
			} else {
				lines.add(HOLD_LEFT_SHEFT_TEXT);
			}
			lines.add(ScreenTexts.EMPTY);
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
