/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.item.HunterContractItem;
import moriyashiine.nycto.api.world.item.TransformationCheckerBlockItem;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.tag.ModBannerPatternTags;
import moriyashiine.nycto.common.world.entity.monster.Hunter;
import moriyashiine.nycto.common.world.item.*;
import moriyashiine.strawberrylib.api.objects.records.ModifierTrio;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;

import java.util.List;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.*;

public class ModItems {
	public static CreativeModeTab TAB;

	public static final Item VAMPIRE_ALTAR = registerBlockItem("vampire_altar", ModBlocks.VAMPIRE_ALTAR);
	public static final Item WEREWOLF_ALTAR = registerBlockItem("werewolf_altar", ModBlocks.WEREWOLF_ALTAR);

	public static final Item OAK_COFFIN = registerCoffin("oak_coffin", ModBlocks.OAK_COFFIN);
	public static final Item SPRUCE_COFFIN = registerCoffin("spruce_coffin", ModBlocks.SPRUCE_COFFIN);
	public static final Item BIRCH_COFFIN = registerCoffin("birch_coffin", ModBlocks.BIRCH_COFFIN);
	public static final Item JUNGLE_COFFIN = registerCoffin("jungle_coffin", ModBlocks.JUNGLE_COFFIN);
	public static final Item ACACIA_COFFIN = registerCoffin("acacia_coffin", ModBlocks.ACACIA_COFFIN);
	public static final Item DARK_OAK_COFFIN = registerCoffin("dark_oak_coffin", ModBlocks.DARK_OAK_COFFIN);
	public static final Item PALE_OAK_COFFIN = registerCoffin("pale_oak_coffin", ModBlocks.PALE_OAK_COFFIN);
	public static final Item MANGROVE_COFFIN = registerCoffin("mangrove_coffin", ModBlocks.MANGROVE_COFFIN);
	public static final Item CHERRY_COFFIN = registerCoffin("cherry_coffin", ModBlocks.CHERRY_COFFIN);
	public static final Item BAMBOO_COFFIN = registerCoffin("bamboo_coffin", ModBlocks.BAMBOO_COFFIN);
	public static final Item CRIMSON_COFFIN = registerCoffin("crimson_coffin", ModBlocks.CRIMSON_COFFIN);
	public static final Item WARPED_COFFIN = registerCoffin("warped_coffin", ModBlocks.WARPED_COFFIN);

	public static final Item GARLIC_WREATH = registerBlockItem("garlic_wreath", ModBlocks.GARLIC_WREATH);
	public static final Item ACONITE_GARLAND = registerBlockItem("aconite_garland", ModBlocks.ACONITE_GARLAND);

	public static final Item WILD_GARLIC = registerBlockItem("wild_garlic", ModBlocks.WILD_GARLIC);
	public static final Item WILD_ACONITE = registerBlockItem("wild_aconite", ModBlocks.WILD_ACONITE);

	public static final Item VAMPIRE_UPGRADE_SMITHING_TEMPLATE = registerItem("vampire_upgrade_smithing_template", settings -> new SmithingTemplateItem(
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.vampire_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.base_slot_description"))),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.vampire_upgrade.additions_slot_description"))),
			List.of(Identifier.withDefaultNamespace("container/slot/helmet"), Identifier.withDefaultNamespace("container/slot/chestplate"), Identifier.withDefaultNamespace("container/slot/leggings"), Identifier.withDefaultNamespace("container/slot/boots")),
			List.of(Nycto.id("container/slot/blood_bottle"), Nycto.id("container/slot/vampire_blood_bottle")),
			settings));
	public static final Item VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE = registerItem("vampire_hunter_upgrade_smithing_template", settings -> new SmithingTemplateItem(
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.hunter_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.base_slot_description"))),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.hunter_upgrade.additions_slot_description"))),
			List.of(Identifier.withDefaultNamespace("container/slot/helmet"), Identifier.withDefaultNamespace("container/slot/chestplate"), Identifier.withDefaultNamespace("container/slot/leggings"), Identifier.withDefaultNamespace("container/slot/boots")),
			List.of(Identifier.withDefaultNamespace("container/slot/ingot")),
			settings));
	public static final Item WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE = registerItem("werewolf_hunter_upgrade_smithing_template", settings -> new SmithingTemplateItem(
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.hunter_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.base_slot_description"))),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.hunter_upgrade.additions_slot_description"))),
			List.of(Identifier.withDefaultNamespace("container/slot/helmet"), Identifier.withDefaultNamespace("container/slot/chestplate"), Identifier.withDefaultNamespace("container/slot/leggings"), Identifier.withDefaultNamespace("container/slot/boots")),
			List.of(Identifier.withDefaultNamespace("container/slot/ingot")),
			settings));

	public static final Item VAMPIRE_HELMET = registerItem("vampire_helmet", new Item.Properties().humanoidArmor(ModArmorMaterials.VAMPIRE, ArmorType.HELMET));
	public static final Item VAMPIRE_CHESTPLATE = registerItem("vampire_chestplate", CapeItem::new, new Item.Properties().humanoidArmor(ModArmorMaterials.VAMPIRE, ArmorType.CHESTPLATE).component(ModComponentTypes.SHOW_CAPE, true));
	public static final Item VAMPIRE_LEGGINGS = registerItem("vampire_leggings", new Item.Properties().humanoidArmor(ModArmorMaterials.VAMPIRE, ArmorType.LEGGINGS));
	public static final Item VAMPIRE_BOOTS = registerItem("vampire_boots", new Item.Properties().humanoidArmor(ModArmorMaterials.VAMPIRE, ArmorType.BOOTS));

	public static final Item VAMPIRE_HUNTER_HELMET = registerHunterArmor("vampire_hunter_helmet", ArmorType.HELMET, ModAttributes.VAMPIRE_RESISTANCE);
	public static final Item VAMPIRE_HUNTER_CHESTPLATE = registerHunterArmor("vampire_hunter_chestplate", ArmorType.CHESTPLATE, ModAttributes.VAMPIRE_RESISTANCE);
	public static final Item VAMPIRE_HUNTER_LEGGINGS = registerHunterArmor("vampire_hunter_leggings", ArmorType.LEGGINGS, ModAttributes.VAMPIRE_RESISTANCE);
	public static final Item VAMPIRE_HUNTER_BOOTS = registerHunterArmor("vampire_hunter_boots", ArmorType.BOOTS, ModAttributes.VAMPIRE_RESISTANCE);

	public static final Item WEREWOLF_HUNTER_HELMET = registerHunterArmor("werewolf_hunter_helmet", ArmorType.HELMET, ModAttributes.WEREWOLF_RESISTANCE);
	public static final Item WEREWOLF_HUNTER_CHESTPLATE = registerHunterArmor("werewolf_hunter_chestplate", ArmorType.CHESTPLATE, ModAttributes.WEREWOLF_RESISTANCE);
	public static final Item WEREWOLF_HUNTER_LEGGINGS = registerHunterArmor("werewolf_hunter_leggings", ArmorType.LEGGINGS, ModAttributes.WEREWOLF_RESISTANCE);
	public static final Item WEREWOLF_HUNTER_BOOTS = registerHunterArmor("werewolf_hunter_boots", ArmorType.BOOTS, ModAttributes.WEREWOLF_RESISTANCE);

	public static final Item VAMPIRIC_DAGGER = registerItem("vampiric_dagger", VampiricDaggerItem::new, properties()
			.component(ModComponentTypes.PLAYER_BLOOD, false)
			.component(ModComponentTypes.VAMPIRE_BLOOD, false)
			.component(ModComponentTypes.BLOOD_CHARGE, 0)
			.enchantable(20)
			.stacksTo(1));
	public static final Item HALBERD = registerHalberd("halberd");
	public static final Item GARLIC_COATED_HALBERD = registerHalberd("garlic_coated_halberd");
	public static final Item ACONITE_COATED_HALBERD = registerHalberd("aconite_coated_halberd");

	public static final Item WOODEN_STAKE = registerItem("wooden_stake", WoodenStakeItem::new);
	public static final Item ACONITE_ARROW = registerItem("aconite_arrow", AconiteArrowItem::new);
	public static final Item FIREBOMB = registerItem("firebomb", FirebombItem::new, properties().stacksTo(16));

	public static final Item BLOOD_BOTTLE = registerItem("blood_bottle", drink(ModConsumables.BLOOD_BOTTLE));
	public static final Item PLAYER_BLOOD_BOTTLE = registerItem("player_blood_bottle", PlayerBloodBottleItem::new, drink(ModConsumables.BLOOD_BOTTLE).overrideDescription(BLOOD_BOTTLE.getDescriptionId()).modelId(Nycto.id("blood_bottle")));
	public static final Item VAMPIRE_BLOOD_BOTTLE = registerItem("vampire_blood_bottle", drink(ModConsumables.VAMPIRE_BLOOD_BOTTLE));
	public static final Item PLAYER_VAMPIRE_BLOOD_BOTTLE = registerItem("player_vampire_blood_bottle", PlayerBloodBottleItem::new, drink(ModConsumables.VAMPIRE_BLOOD_BOTTLE).overrideDescription(VAMPIRE_BLOOD_BOTTLE.getDescriptionId()).modelId(Nycto.id("vampire_blood_bottle")));
	public static final Item AMBROSIA_BOTTLE = registerItem("ambrosia_bottle", properties()
			.rarity(Rarity.RARE)
			.food(ModFoods.AMBROSIA_BOTTLE, ModConsumables.AMBROSIA_BOTTLE)
			.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
			.craftRemainder(Items.GLASS_BOTTLE)
			.usingConvertsTo(Items.GLASS_BOTTLE)
			.stacksTo(16));

	public static final Item GARLIC = registerItem("garlic", settings -> new TransformationCheckerBlockItem(ModBlocks.GARLIC, settings, NyctoAPI::isVampire), properties().food(ModFoods.GARLIC));
	public static final Item GRILLED_GARLIC = registerItem("grilled_garlic", properties().food(ModFoods.GRILLED_GARLIC));
	public static final Item GARLIC_BREAD = registerItem("garlic_bread", properties().food(ModFoods.GARLIC_BREAD));

	public static final Item ACONITE_SEEDS = registerItem("aconite_seeds", settings -> new BlockItem(ModBlocks.ACONITE, settings));
	public static final Item ACONITE = registerItem("aconite", settings -> new TransformationCheckerBlockItem(ModBlocks.ACONITE, settings, NyctoAPI::isWerewolf));

	public static final Item HUNTER_CONTRACT = registerItem("hunter_contract");
	public static final Item VAMPIRE_HUNTER_CONTRACT = registerItem("vampire_hunter_contract", settings -> new HunterContractItem(settings, Hunter.HunterType.VAMPIRE));
	public static final Item WEREWOLF_HUNTER_CONTRACT = registerItem("werewolf_hunter_contract", settings -> new HunterContractItem(settings, Hunter.HunterType.WEREWOLF));

	public static final Item VAMPIRE_BAT_BANNER_PATTERN = registerItem("vampire_bat_banner_pattern", properties().stacksTo(1).delayedComponent(DataComponents.PROVIDES_BANNER_PATTERNS, context -> context.getOrThrow(ModBannerPatternTags.PATTERN_ITEM_VAMPIRE_BAT)));
	public static final Item WOLF_SKULL_BANNER_PATTERN = registerItem("wolf_skull_banner_pattern", properties().stacksTo(1).delayedComponent(DataComponents.PROVIDES_BANNER_PATTERNS, context -> context.getOrThrow(ModBannerPatternTags.PATTERN_ITEM_WOLF_SKULL)));
	public static final Item HUNTERS_MARK_BANNER_PATTERN = registerItem("hunters_mark_banner_pattern", properties().stacksTo(1).delayedComponent(DataComponents.PROVIDES_BANNER_PATTERNS, context -> context.getOrThrow(ModBannerPatternTags.PATTERN_ITEM_HUNTERS_MARK)));

	public static final Item VAMPIRE_SPAWN_EGG = registerItem("vampire_spawn_egg", SpawnEggItem::new, properties().spawnEgg(ModEntityTypes.VAMPIRE));
	public static final Item HUNTER_SPAWN_EGG = registerItem("hunter_spawn_egg", SpawnEggItem::new, properties().spawnEgg(ModEntityTypes.HUNTER));

	public static Item registerCoffin(String name, Block block) {
		return registerBlockItem(name, block, properties().stacksTo(1));
	}

	public static Item registerHunterArmor(String name, ArmorType type, Holder<Attribute> attribute) {
		AttributeModifier resistanceModifier = new AttributeModifier(Nycto.id("hunter_armor_resistance_" + type.getName()), 1, AttributeModifier.Operation.ADD_VALUE);
		return registerItem(name, type == ArmorType.HELMET ? MaskItem::new : Item::new, properties().humanoidArmor(ModArmorMaterials.HUNTER, type).attributes(ModArmorMaterials.HUNTER.createAttributes(type).withModifierAdded(attribute, resistanceModifier, EquipmentSlotGroup.bySlot(type.getSlot()))).component(ModComponentTypes.MASK_VISIBILITY, MaskVisibility.VISIBLE));
	}

	public static Item registerHalberd(String name) {
		AttributeModifier rangeModifier = new AttributeModifier(Nycto.id("halberd_entity_interaction_range"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		ModifierTrio modifier = new ModifierTrio(Attributes.ENTITY_INTERACTION_RANGE, rangeModifier, EquipmentSlotGroup.MAINHAND);
		return registerItem(name, settings -> new AxeItem(ModToolMaterials.HALBERD, 7, -3.2F, settings), editModifiers(ModItems::properties, modifier));
	}

	private static Item.Properties drink(Consumable consumable) {
		return properties()
				.component(DataComponents.CONSUMABLE, consumable)
				.craftRemainder(Items.GLASS_BOTTLE)
				.usingConvertsTo(Items.GLASS_BOTTLE)
				.stacksTo(16);
	}

	private static Item.Properties properties() {
		return new Item.Properties();
	}

	public static void init() {
		TAB = registerCreativeModeTab(FabricCreativeModeTab.builder().title(Component.translatable("itemGroup." + Nycto.MOD_ID)).icon(VAMPIRE_BLOOD_BOTTLE::getDefaultInstance).displayItems((_, output) -> {
			output.accept(VAMPIRE_ALTAR);
			output.accept(WEREWOLF_ALTAR);

			output.accept(OAK_COFFIN);
			output.accept(SPRUCE_COFFIN);
			output.accept(BIRCH_COFFIN);
			output.accept(JUNGLE_COFFIN);
			output.accept(ACACIA_COFFIN);
			output.accept(DARK_OAK_COFFIN);
			output.accept(PALE_OAK_COFFIN);
			output.accept(MANGROVE_COFFIN);
			output.accept(CHERRY_COFFIN);
			output.accept(BAMBOO_COFFIN);
			output.accept(CRIMSON_COFFIN);
			output.accept(WARPED_COFFIN);

			output.accept(GARLIC_WREATH);
			output.accept(ACONITE_GARLAND);

			output.accept(WILD_GARLIC);
			output.accept(WILD_ACONITE);

			output.accept(VAMPIRE_UPGRADE_SMITHING_TEMPLATE);
			output.accept(VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE);
			output.accept(WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE);

			output.accept(VAMPIRE_HELMET);
			output.accept(VAMPIRE_CHESTPLATE);
			output.accept(VAMPIRE_LEGGINGS);
			output.accept(VAMPIRE_BOOTS);
			output.accept(VAMPIRE_HUNTER_HELMET);
			output.accept(VAMPIRE_HUNTER_CHESTPLATE);
			output.accept(VAMPIRE_HUNTER_LEGGINGS);
			output.accept(VAMPIRE_HUNTER_BOOTS);
			output.accept(WEREWOLF_HUNTER_HELMET);
			output.accept(WEREWOLF_HUNTER_CHESTPLATE);
			output.accept(WEREWOLF_HUNTER_LEGGINGS);
			output.accept(WEREWOLF_HUNTER_BOOTS);

			output.accept(VAMPIRIC_DAGGER);
			output.accept(HALBERD);
			output.accept(GARLIC_COATED_HALBERD);
			output.accept(ACONITE_COATED_HALBERD);
			output.accept(WOODEN_STAKE);
			output.accept(ACONITE_ARROW);
			output.accept(FIREBOMB);

			output.accept(BLOOD_BOTTLE);
			output.accept(VAMPIRE_BLOOD_BOTTLE);
			output.accept(AMBROSIA_BOTTLE);

			output.accept(GARLIC);
			output.accept(GRILLED_GARLIC);
			output.accept(GARLIC_BREAD);

			output.accept(ACONITE_SEEDS);
			output.accept(ACONITE);

			output.accept(HUNTER_CONTRACT);
			output.accept(VAMPIRE_HUNTER_CONTRACT);
			output.accept(WEREWOLF_HUNTER_CONTRACT);

			output.accept(VAMPIRE_BAT_BANNER_PATTERN);
			output.accept(WOLF_SKULL_BANNER_PATTERN);
			output.accept(HUNTERS_MARK_BANNER_PATTERN);

			output.accept(VAMPIRE_SPAWN_EGG);
			output.accept(HUNTER_SPAWN_EGG);

			addPotions(output, ModPotions.GARLIC, ModPotions.LONG_GARLIC, ModPotions.STRONG_GARLIC);
			addPotions(output, ModPotions.WITHER, ModPotions.LONG_WITHER, ModPotions.STRONG_WITHER);
		}).build());
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(output -> {
			output.accept(VAMPIRE_SPAWN_EGG);
			output.accept(HUNTER_SPAWN_EGG);
		});

		CompostableRegistry.INSTANCE.add(WILD_GARLIC, 0.65F);
		CompostableRegistry.INSTANCE.add(WILD_ACONITE, 0.65F);
		CompostableRegistry.INSTANCE.add(GARLIC, 0.65F);
		CompostableRegistry.INSTANCE.add(GRILLED_GARLIC, 0.85F);
		CompostableRegistry.INSTANCE.add(GARLIC_BREAD, 1F);
		CompostableRegistry.INSTANCE.add(ACONITE_SEEDS, 0.3F);
		CompostableRegistry.INSTANCE.add(ACONITE, 0.65F);
	}

	@SafeVarargs
	private static void addPotions(CreativeModeTab.Output output, Holder<Potion>... potions) {
		for (Item item : new Item[]{Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION, Items.TIPPED_ARROW}) {
			for (Holder<Potion> potion : potions) {
				output.accept(PotionContents.createItemStack(item, potion));
			}
		}
	}
}
