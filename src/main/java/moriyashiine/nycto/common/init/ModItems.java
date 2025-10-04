/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.item.HunterContractItem;
import moriyashiine.nycto.api.item.TransformationCheckerBlockItem;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.entity.mob.HunterEntity;
import moriyashiine.nycto.common.item.*;
import moriyashiine.strawberrylib.api.objects.records.ModifierTrio;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;

import java.util.List;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.*;

public class ModItems {
	public static ItemGroup GROUP;

	public static final Item VAMPIRE_ALTAR = registerBlockItem("vampire_altar", ModBlocks.VAMPIRE_ALTAR);
	public static final Item WEREWOLF_ALTAR = registerBlockItem("werewolf_altar", ModBlocks.WEREWOLF_ALTAR);

	public static final Item OAK_COFFIN = registerBlockItem("oak_coffin", ModBlocks.OAK_COFFIN);
	public static final Item SPRUCE_COFFIN = registerBlockItem("spruce_coffin", ModBlocks.SPRUCE_COFFIN);
	public static final Item BIRCH_COFFIN = registerBlockItem("birch_coffin", ModBlocks.BIRCH_COFFIN);
	public static final Item JUNGLE_COFFIN = registerBlockItem("jungle_coffin", ModBlocks.JUNGLE_COFFIN);
	public static final Item ACACIA_COFFIN = registerBlockItem("acacia_coffin", ModBlocks.ACACIA_COFFIN);
	public static final Item DARK_OAK_COFFIN = registerBlockItem("dark_oak_coffin", ModBlocks.DARK_OAK_COFFIN);
	public static final Item PALE_OAK_COFFIN = registerBlockItem("pale_oak_coffin", ModBlocks.PALE_OAK_COFFIN);
	public static final Item MANGROVE_COFFIN = registerBlockItem("mangrove_coffin", ModBlocks.MANGROVE_COFFIN);
	public static final Item CHERRY_COFFIN = registerBlockItem("cherry_coffin", ModBlocks.CHERRY_COFFIN);
	public static final Item BAMBOO_COFFIN = registerBlockItem("bamboo_coffin", ModBlocks.BAMBOO_COFFIN);
	public static final Item CRIMSON_COFFIN = registerBlockItem("crimson_coffin", ModBlocks.CRIMSON_COFFIN);
	public static final Item WARPED_COFFIN = registerBlockItem("warped_coffin", ModBlocks.WARPED_COFFIN);

	public static final Item GARLIC_WREATH = registerBlockItem("garlic_wreath", ModBlocks.GARLIC_WREATH);
	public static final Item ACONITE_GARLAND = registerBlockItem("aconite_garland", ModBlocks.ACONITE_GARLAND);

	public static final Item WILD_GARLIC = registerBlockItem("wild_garlic", ModBlocks.WILD_GARLIC);
	public static final Item WILD_ACONITE = registerBlockItem("wild_aconite", ModBlocks.WILD_ACONITE);

	public static final Item VAMPIRE_UPGRADE_SMITHING_TEMPLATE = registerItem("vampire_upgrade_smithing_template", settings -> new SmithingTemplateItem(
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.generic_upgrade.applies_to"))).formatted(Formatting.BLUE),
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.vampire_upgrade.ingredients"))).formatted(Formatting.BLUE),
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.generic_upgrade.base_slot_description"))),
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.vampire_upgrade.additions_slot_description"))),
			List.of(Identifier.ofVanilla("container/slot/helmet"), Identifier.ofVanilla("container/slot/chestplate"), Identifier.ofVanilla("container/slot/leggings"), Identifier.ofVanilla("container/slot/boots")),
			List.of(Nycto.id("container/slot/blood_bottle"), Nycto.id("container/slot/vampire_blood_bottle")),
			settings));
	public static final Item VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE = registerItem("vampire_hunter_upgrade_smithing_template", settings -> new SmithingTemplateItem(
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.generic_upgrade.applies_to"))).formatted(Formatting.BLUE),
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.hunter_upgrade.ingredients"))).formatted(Formatting.BLUE),
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.generic_upgrade.base_slot_description"))),
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.hunter_upgrade.additions_slot_description"))),
			List.of(Identifier.ofVanilla("container/slot/helmet"), Identifier.ofVanilla("container/slot/chestplate"), Identifier.ofVanilla("container/slot/leggings"), Identifier.ofVanilla("container/slot/boots")),
			List.of(Identifier.ofVanilla("container/slot/ingot")),
			settings));
	public static final Item WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE = registerItem("werewolf_hunter_upgrade_smithing_template", settings -> new SmithingTemplateItem(
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.generic_upgrade.applies_to"))).formatted(Formatting.BLUE),
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.hunter_upgrade.ingredients"))).formatted(Formatting.BLUE),
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.generic_upgrade.base_slot_description"))),
			Text.translatable(Util.createTranslationKey("item", Nycto.id("smithing_template.hunter_upgrade.additions_slot_description"))),
			List.of(Identifier.ofVanilla("container/slot/helmet"), Identifier.ofVanilla("container/slot/chestplate"), Identifier.ofVanilla("container/slot/leggings"), Identifier.ofVanilla("container/slot/boots")),
			List.of(Identifier.ofVanilla("container/slot/ingot")),
			settings));

	public static final Item VAMPIRE_HELMET = registerItem("vampire_helmet", new Item.Settings().armor(ModArmorMaterials.VAMPIRE, EquipmentType.HELMET));
	public static final Item VAMPIRE_CHESTPLATE = registerItem("vampire_chestplate", new Item.Settings().armor(ModArmorMaterials.VAMPIRE, EquipmentType.CHESTPLATE));
	public static final Item VAMPIRE_LEGGINGS = registerItem("vampire_leggings", new Item.Settings().armor(ModArmorMaterials.VAMPIRE, EquipmentType.LEGGINGS));
	public static final Item VAMPIRE_BOOTS = registerItem("vampire_boots", new Item.Settings().armor(ModArmorMaterials.VAMPIRE, EquipmentType.BOOTS));

	public static final Item VAMPIRE_HUNTER_HELMET = registerHunterArmor("vampire_hunter_helmet", EquipmentType.HELMET, ModEntityAttributes.VAMPIRE_RESISTANCE);
	public static final Item VAMPIRE_HUNTER_CHESTPLATE = registerHunterArmor("vampire_hunter_chestplate", EquipmentType.CHESTPLATE, ModEntityAttributes.VAMPIRE_RESISTANCE);
	public static final Item VAMPIRE_HUNTER_LEGGINGS = registerHunterArmor("vampire_hunter_leggings", EquipmentType.LEGGINGS, ModEntityAttributes.VAMPIRE_RESISTANCE);
	public static final Item VAMPIRE_HUNTER_BOOTS = registerHunterArmor("vampire_hunter_boots", EquipmentType.BOOTS, ModEntityAttributes.VAMPIRE_RESISTANCE);

	public static final Item WEREWOLF_HUNTER_HELMET = registerHunterArmor("werewolf_hunter_helmet", EquipmentType.HELMET, ModEntityAttributes.WEREWOLF_RESISTANCE);
	public static final Item WEREWOLF_HUNTER_CHESTPLATE = registerHunterArmor("werewolf_hunter_chestplate", EquipmentType.CHESTPLATE, ModEntityAttributes.WEREWOLF_RESISTANCE);
	public static final Item WEREWOLF_HUNTER_LEGGINGS = registerHunterArmor("werewolf_hunter_leggings", EquipmentType.LEGGINGS, ModEntityAttributes.WEREWOLF_RESISTANCE);
	public static final Item WEREWOLF_HUNTER_BOOTS = registerHunterArmor("werewolf_hunter_boots", EquipmentType.BOOTS, ModEntityAttributes.WEREWOLF_RESISTANCE);

	public static final Item VAMPIRIC_DAGGER = registerItem("vampiric_dagger", VampiricDaggerItem::new, settings()
			.component(ModComponentTypes.PLAYER_BLOOD, false)
			.component(ModComponentTypes.VAMPIRE_BLOOD, false)
			.component(ModComponentTypes.BLOOD_CHARGE, 0)
			.enchantable(20)
			.maxCount(1));
	public static final Item HALBERD = registerHalberd("halberd");
	public static final Item GARLIC_COATED_HALBERD = registerHalberd("garlic_coated_halberd");
	public static final Item ACONITE_COATED_HALBERD = registerHalberd("aconite_coated_halberd");

	public static final Item WOODEN_STAKE = registerItem("wooden_stake", WoodenStakeItem::new);
	public static final Item ACONITE_ARROW = registerItem("aconite_arrow", AconiteArrowItem::new);
	public static final Item FIREBOMB = registerItem("firebomb", FirebombItem::new, settings().maxCount(16));

	public static final Item BLOOD_BOTTLE = registerItem("blood_bottle", drink(ModConsumableComponents.BLOOD_BOTTLE));
	public static final Item PLAYER_BLOOD_BOTTLE = registerItem("player_blood_bottle", PlayerBloodBottleItem::new, drink(ModConsumableComponents.BLOOD_BOTTLE).translationKey(BLOOD_BOTTLE.getTranslationKey()).modelId(Nycto.id("blood_bottle")));
	public static final Item VAMPIRE_BLOOD_BOTTLE = registerItem("vampire_blood_bottle", drink(ModConsumableComponents.VAMPIRE_BLOOD_BOTTLE));
	public static final Item PLAYER_VAMPIRE_BLOOD_BOTTLE = registerItem("player_vampire_blood_bottle", PlayerBloodBottleItem::new, drink(ModConsumableComponents.VAMPIRE_BLOOD_BOTTLE).translationKey(VAMPIRE_BLOOD_BOTTLE.getTranslationKey()).modelId(Nycto.id("vampire_blood_bottle")));
	public static final Item AMBROSIA_BOTTLE = registerItem("ambrosia_bottle", settings()
			.rarity(Rarity.RARE)
			.food(ModFoodComponents.AMBROSIA_BOTTLE, ModConsumableComponents.AMBROSIA_BOTTLE)
			.component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
			.recipeRemainder(Items.GLASS_BOTTLE)
			.useRemainder(Items.GLASS_BOTTLE)
			.maxCount(16));

	public static final Item GARLIC = registerItem("garlic", settings -> new TransformationCheckerBlockItem(ModBlocks.GARLIC, settings, NyctoAPI::isVampire), settings().food(ModFoodComponents.GARLIC));
	public static final Item GRILLED_GARLIC = registerItem("grilled_garlic", settings().food(ModFoodComponents.GRILLED_GARLIC));
	public static final Item GARLIC_BREAD = registerItem("garlic_bread", settings().food(ModFoodComponents.GARLIC_BREAD));

	public static final Item ACONITE_SEEDS = registerItem("aconite_seeds", settings -> new BlockItem(ModBlocks.ACONITE, settings));
	public static final Item ACONITE = registerItem("aconite", settings -> new TransformationCheckerBlockItem(ModBlocks.ACONITE, settings, NyctoAPI::isWerewolf));

	public static final Item HUNTER_CONTRACT = registerItem("hunter_contract");
	public static final Item VAMPIRE_HUNTER_CONTRACT = registerItem("vampire_hunter_contract", settings -> new HunterContractItem(settings, HunterEntity.HunterType.VAMPIRE));
	public static final Item WEREWOLF_HUNTER_CONTRACT = registerItem("werewolf_hunter_contract", settings -> new HunterContractItem(settings, HunterEntity.HunterType.WEREWOLF));

	public static final Item VAMPIRE_SPAWN_EGG = registerItem("vampire_spawn_egg", SpawnEggItem::new, settings().spawnEgg(ModEntityTypes.VAMPIRE));
	public static final Item HUNTER_SPAWN_EGG = registerItem("hunter_spawn_egg", SpawnEggItem::new, settings().spawnEgg(ModEntityTypes.HUNTER));

	public static Item registerHunterArmor(String name, EquipmentType type, RegistryEntry<EntityAttribute> attribute) {
		EntityAttributeModifier resistanceModifier = new EntityAttributeModifier(Nycto.id("hunter_armor_resistance_" + type.getName()), 1, EntityAttributeModifier.Operation.ADD_VALUE);
		return registerItem(name, settings().armor(ModArmorMaterials.HUNTER, type).attributeModifiers(ModArmorMaterials.HUNTER.createAttributeModifiers(type).with(attribute, resistanceModifier, AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot()))));
	}

	public static Item registerHalberd(String name) {
		EntityAttributeModifier rangeModifier = new EntityAttributeModifier(Nycto.id("halberd_entity_interaction_range"), 0.5, EntityAttributeModifier.Operation.ADD_VALUE);
		ModifierTrio modifier = new ModifierTrio(EntityAttributes.ENTITY_INTERACTION_RANGE, rangeModifier, AttributeModifierSlot.MAINHAND);
		return registerItem(name, settings -> new AxeItem(ModToolMaterials.HALBERD, 7, -3.2F, settings), editModifiers(ModItems::settings, modifier));
	}

	private static Item.Settings drink(ConsumableComponent consumableComponent) {
		return settings()
				.component(DataComponentTypes.CONSUMABLE, consumableComponent)
				.recipeRemainder(Items.GLASS_BOTTLE)
				.useRemainder(Items.GLASS_BOTTLE)
				.maxCount(16);
	}

	private static Item.Settings settings() {
		return new Item.Settings();
	}

	public static void init() {
		GROUP = registerItemGroup(FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + Nycto.MOD_ID)).icon(VAMPIRE_BLOOD_BOTTLE::getDefaultStack).entries((displayContext, entries) -> {
			entries.add(VAMPIRE_ALTAR);
			entries.add(WEREWOLF_ALTAR);

			entries.add(OAK_COFFIN);
			entries.add(SPRUCE_COFFIN);
			entries.add(BIRCH_COFFIN);
			entries.add(JUNGLE_COFFIN);
			entries.add(ACACIA_COFFIN);
			entries.add(DARK_OAK_COFFIN);
			entries.add(PALE_OAK_COFFIN);
			entries.add(MANGROVE_COFFIN);
			entries.add(CHERRY_COFFIN);
			entries.add(BAMBOO_COFFIN);
			entries.add(CRIMSON_COFFIN);
			entries.add(WARPED_COFFIN);

			entries.add(GARLIC_WREATH);
			entries.add(ACONITE_GARLAND);

			entries.add(WILD_GARLIC);
			entries.add(WILD_ACONITE);

			entries.add(VAMPIRE_UPGRADE_SMITHING_TEMPLATE);
			entries.add(VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE);
			entries.add(WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE);

			entries.add(VAMPIRE_HELMET);
			entries.add(VAMPIRE_CHESTPLATE);
			entries.add(VAMPIRE_LEGGINGS);
			entries.add(VAMPIRE_BOOTS);
			entries.add(VAMPIRE_HUNTER_HELMET);
			entries.add(VAMPIRE_HUNTER_CHESTPLATE);
			entries.add(VAMPIRE_HUNTER_LEGGINGS);
			entries.add(VAMPIRE_HUNTER_BOOTS);
			entries.add(WEREWOLF_HUNTER_HELMET);
			entries.add(WEREWOLF_HUNTER_CHESTPLATE);
			entries.add(WEREWOLF_HUNTER_LEGGINGS);
			entries.add(WEREWOLF_HUNTER_BOOTS);

			entries.add(VAMPIRIC_DAGGER);
			entries.add(HALBERD);
			entries.add(GARLIC_COATED_HALBERD);
			entries.add(ACONITE_COATED_HALBERD);
			entries.add(WOODEN_STAKE);
			entries.add(ACONITE_ARROW);
			entries.add(FIREBOMB);

			entries.add(BLOOD_BOTTLE);
			entries.add(VAMPIRE_BLOOD_BOTTLE);
			entries.add(AMBROSIA_BOTTLE);

			entries.add(GARLIC);
			entries.add(GRILLED_GARLIC);
			entries.add(GARLIC_BREAD);

			entries.add(ACONITE_SEEDS);
			entries.add(ACONITE);

			entries.add(HUNTER_CONTRACT);
			entries.add(VAMPIRE_HUNTER_CONTRACT);
			entries.add(WEREWOLF_HUNTER_CONTRACT);

			entries.add(VAMPIRE_SPAWN_EGG);
			entries.add(HUNTER_SPAWN_EGG);

			addPotions(entries, ModPotions.GARLIC, ModPotions.LONG_GARLIC, ModPotions.STRONG_GARLIC);
			addPotions(entries, ModPotions.WITHER, ModPotions.LONG_WITHER, ModPotions.STRONG_WITHER);
		}).build());
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
			entries.add(VAMPIRE_SPAWN_EGG);
			entries.add(HUNTER_SPAWN_EGG);
		});

		CompostingChanceRegistry.INSTANCE.add(WILD_GARLIC, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(WILD_ACONITE, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(GARLIC, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(GRILLED_GARLIC, 0.85F);
		CompostingChanceRegistry.INSTANCE.add(GARLIC_BREAD, 1F);
		CompostingChanceRegistry.INSTANCE.add(ACONITE_SEEDS, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(ACONITE, 0.65F);
	}

	@SafeVarargs
	private static void addPotions(ItemGroup.Entries entries, RegistryEntry<Potion>... potions) {
		for (Item item : new Item[]{Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION, Items.TIPPED_ARROW}) {
			for (RegistryEntry<Potion> potion : potions) {
				entries.add(PotionContentsComponent.createStack(item, potion));
			}
		}
	}
}
