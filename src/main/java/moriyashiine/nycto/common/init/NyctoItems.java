package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.item.HunterContractItem;
import moriyashiine.nycto.api.world.item.TransformationCheckerBlockItem;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.references.NyctoBlockItemIds;
import moriyashiine.nycto.common.references.NyctoItemIds;
import moriyashiine.nycto.common.tag.NyctoBannerPatternTags;
import moriyashiine.nycto.common.world.item.*;
import moriyashiine.strawberrylib.api.objects.records.ModifierTrio;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;

import java.util.List;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.*;

public class NyctoItems {
	public static CreativeModeTab TAB;

	public static final Item VAMPIRE_ALTAR = registerBlockItem(NyctoBlockItemIds.VAMPIRE_ALTAR, NyctoBlocks.VAMPIRE_ALTAR);
	public static final Item WEREWOLF_ALTAR = registerBlockItem(NyctoBlockItemIds.WEREWOLF_ALTAR, NyctoBlocks.WEREWOLF_ALTAR);

	public static final Item OAK_COFFIN = registerCoffin(NyctoBlockItemIds.OAK_COFFIN, NyctoBlocks.OAK_COFFIN);
	public static final Item SPRUCE_COFFIN = registerCoffin(NyctoBlockItemIds.SPRUCE_COFFIN, NyctoBlocks.SPRUCE_COFFIN);
	public static final Item BIRCH_COFFIN = registerCoffin(NyctoBlockItemIds.BIRCH_COFFIN, NyctoBlocks.BIRCH_COFFIN);
	public static final Item JUNGLE_COFFIN = registerCoffin(NyctoBlockItemIds.JUNGLE_COFFIN, NyctoBlocks.JUNGLE_COFFIN);
	public static final Item ACACIA_COFFIN = registerCoffin(NyctoBlockItemIds.ACACIA_COFFIN, NyctoBlocks.ACACIA_COFFIN);
	public static final Item DARK_OAK_COFFIN = registerCoffin(NyctoBlockItemIds.DARK_OAK_COFFIN, NyctoBlocks.DARK_OAK_COFFIN);
	public static final Item PALE_OAK_COFFIN = registerCoffin(NyctoBlockItemIds.PALE_OAK_COFFIN, NyctoBlocks.PALE_OAK_COFFIN);
	public static final Item MANGROVE_COFFIN = registerCoffin(NyctoBlockItemIds.MANGROVE_COFFIN, NyctoBlocks.MANGROVE_COFFIN);
	public static final Item CHERRY_COFFIN = registerCoffin(NyctoBlockItemIds.CHERRY_COFFIN, NyctoBlocks.CHERRY_COFFIN);
	public static final Item BAMBOO_COFFIN = registerCoffin(NyctoBlockItemIds.BAMBOO_COFFIN, NyctoBlocks.BAMBOO_COFFIN);
	public static final Item CRIMSON_COFFIN = registerCoffin(NyctoBlockItemIds.CRIMSON_COFFIN, NyctoBlocks.CRIMSON_COFFIN);
	public static final Item WARPED_COFFIN = registerCoffin(NyctoBlockItemIds.WARPED_COFFIN, NyctoBlocks.WARPED_COFFIN);

	public static final Item BLOOD_FOUNTAIN = registerBlockItem(NyctoBlockItemIds.BLOOD_FOUNTAIN, NyctoBlocks.BLOOD_FOUNTAIN);

	public static final Item GARLIC_WREATH = registerBlockItem(NyctoBlockItemIds.GARLIC_WREATH, NyctoBlocks.GARLIC_WREATH);
	public static final Item ACONITE_GARLAND = registerBlockItem(NyctoBlockItemIds.ACONITE_GARLAND, NyctoBlocks.ACONITE_GARLAND);

	public static final Item WILD_GARLIC = registerBlockItem(NyctoBlockItemIds.WILD_GARLIC, NyctoBlocks.WILD_GARLIC);
	public static final Item WILD_ACONITE = registerBlockItem(NyctoBlockItemIds.WILD_ACONITE, NyctoBlocks.WILD_ACONITE);

	public static final Item VAMPIRE_UPGRADE_SMITHING_TEMPLATE = registerItem(NyctoItemIds.VAMPIRE_UPGRADE_SMITHING_TEMPLATE, properties -> new SmithingTemplateItem(
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.vampire_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.base_slot_description"))),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.vampire_upgrade.additions_slot_description"))),
			List.of(Identifier.withDefaultNamespace("container/slot/helmet"), Identifier.withDefaultNamespace("container/slot/chestplate"), Identifier.withDefaultNamespace("container/slot/leggings"), Identifier.withDefaultNamespace("container/slot/boots")),
			List.of(Nycto.id("container/slot/blood_bottle"), Nycto.id("container/slot/vampire_blood_bottle")),
			properties));
	public static final Item VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE = registerItem(NyctoItemIds.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, properties -> new SmithingTemplateItem(
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.hunter_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.base_slot_description"))),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.hunter_upgrade.additions_slot_description"))),
			List.of(Identifier.withDefaultNamespace("container/slot/helmet"), Identifier.withDefaultNamespace("container/slot/chestplate"), Identifier.withDefaultNamespace("container/slot/leggings"), Identifier.withDefaultNamespace("container/slot/boots")),
			List.of(Identifier.withDefaultNamespace("container/slot/ingot")),
			properties));
	public static final Item WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE = registerItem(NyctoItemIds.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, properties -> new SmithingTemplateItem(
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.hunter_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.generic_upgrade.base_slot_description"))),
			Component.translatable(Util.makeDescriptionId("item", Nycto.id("smithing_template.hunter_upgrade.additions_slot_description"))),
			List.of(Identifier.withDefaultNamespace("container/slot/helmet"), Identifier.withDefaultNamespace("container/slot/chestplate"), Identifier.withDefaultNamespace("container/slot/leggings"), Identifier.withDefaultNamespace("container/slot/boots")),
			List.of(Identifier.withDefaultNamespace("container/slot/ingot")),
			properties));

	public static final Item VAMPIRE_HELMET = registerItem(NyctoItemIds.VAMPIRE_HELMET, properties()
			.humanoidArmor(NyctoArmorMaterials.VAMPIRE, ArmorType.HELMET));
	public static final Item VAMPIRE_CHESTPLATE = registerItem(NyctoItemIds.VAMPIRE_CHESTPLATE, CapeItem::new, properties()
			.humanoidArmor(NyctoArmorMaterials.VAMPIRE, ArmorType.CHESTPLATE)
			.component(NyctoDataComponents.SHOW_CAPE, true));
	public static final Item VAMPIRE_LEGGINGS = registerItem(NyctoItemIds.VAMPIRE_LEGGINGS, properties()
			.humanoidArmor(NyctoArmorMaterials.VAMPIRE, ArmorType.LEGGINGS));
	public static final Item VAMPIRE_BOOTS = registerItem(NyctoItemIds.VAMPIRE_BOOTS, properties()
			.humanoidArmor(NyctoArmorMaterials.VAMPIRE, ArmorType.BOOTS));

	public static final Item VAMPIRE_HUNTER_HELMET = registerHunterArmor(NyctoItemIds.VAMPIRE_HUNTER_HELMET, ArmorType.HELMET, NyctoAttributes.VAMPIRE_RESISTANCE);
	public static final Item VAMPIRE_HUNTER_CHESTPLATE = registerHunterArmor(NyctoItemIds.VAMPIRE_HUNTER_CHESTPLATE, ArmorType.CHESTPLATE, NyctoAttributes.VAMPIRE_RESISTANCE);
	public static final Item VAMPIRE_HUNTER_LEGGINGS = registerHunterArmor(NyctoItemIds.VAMPIRE_HUNTER_LEGGINGS, ArmorType.LEGGINGS, NyctoAttributes.VAMPIRE_RESISTANCE);
	public static final Item VAMPIRE_HUNTER_BOOTS = registerHunterArmor(NyctoItemIds.VAMPIRE_HUNTER_BOOTS, ArmorType.BOOTS, NyctoAttributes.VAMPIRE_RESISTANCE);
	public static final Item VAMPIRE_HUNTER_WOLF_ARMOR = registerHunterArmor(NyctoItemIds.VAMPIRE_HUNTER_WOLF_ARMOR, ArmorType.BODY, NyctoAttributes.VAMPIRE_RESISTANCE);

	public static final Item WEREWOLF_HUNTER_HELMET = registerHunterArmor(NyctoItemIds.WEREWOLF_HUNTER_HELMET, ArmorType.HELMET, NyctoAttributes.WEREWOLF_RESISTANCE);
	public static final Item WEREWOLF_HUNTER_CHESTPLATE = registerHunterArmor(NyctoItemIds.WEREWOLF_HUNTER_CHESTPLATE, ArmorType.CHESTPLATE, NyctoAttributes.WEREWOLF_RESISTANCE);
	public static final Item WEREWOLF_HUNTER_LEGGINGS = registerHunterArmor(NyctoItemIds.WEREWOLF_HUNTER_LEGGINGS, ArmorType.LEGGINGS, NyctoAttributes.WEREWOLF_RESISTANCE);
	public static final Item WEREWOLF_HUNTER_BOOTS = registerHunterArmor(NyctoItemIds.WEREWOLF_HUNTER_BOOTS, ArmorType.BOOTS, NyctoAttributes.WEREWOLF_RESISTANCE);
	public static final Item WEREWOLF_HUNTER_WOLF_ARMOR = registerHunterArmor(NyctoItemIds.WEREWOLF_HUNTER_WOLF_ARMOR, ArmorType.BODY, NyctoAttributes.WEREWOLF_RESISTANCE);

	public static final Item VAMPIRIC_DAGGER = registerItem(NyctoItemIds.VAMPIRIC_DAGGER, VampiricDaggerItem::new);
	public static final Item HALBERD = registerHalberd(NyctoItemIds.HALBERD);
	public static final Item GARLIC_COATED_HALBERD = registerHalberd(NyctoItemIds.GARLIC_COATED_HALBERD);
	public static final Item ACONITE_COATED_HALBERD = registerHalberd(NyctoItemIds.ACONITE_COATED_HALBERD);

	public static final Item WOODEN_STAKE = registerItem(NyctoBlockItemIds.WOODEN_STAKE.item(), properties -> new WoodenStakeItem(NyctoBlocks.WOODEN_STAKE, properties), properties()
			.useBlockDescriptionPrefix()
			.attributes(ToolMaterial.WOOD.createToolAttributes(WoodenStakeItem.DAMAGE, -3.6F)));
	public static final Item FIREBOMB = registerItem(NyctoBlockItemIds.FIREBOMB.item(), FirebombItem::new, properties()
			.useBlockDescriptionPrefix()
			.stacksTo(16));

	public static final Item BLOOD_BOTTLE = registerItem(NyctoItemIds.BLOOD_BOTTLE, drink(NyctoConsumables.BLOOD_BOTTLE));
	public static final Item PLAYER_BLOOD_BOTTLE = registerItem(NyctoItemIds.PLAYER_BLOOD_BOTTLE, PlayerBloodBottleItem::new, drink(NyctoConsumables.BLOOD_BOTTLE)
			.overrideDescription(BLOOD_BOTTLE.getDescriptionId()).modelId(Nycto.id("blood_bottle")));
	public static final Item VAMPIRE_BLOOD_BOTTLE = registerItem(NyctoItemIds.VAMPIRE_BLOOD_BOTTLE, drink(NyctoConsumables.VAMPIRE_BLOOD_BOTTLE));
	public static final Item PLAYER_VAMPIRE_BLOOD_BOTTLE = registerItem(NyctoItemIds.PLAYER_VAMPIRE_BLOOD_BOTTLE, PlayerBloodBottleItem::new, drink(NyctoConsumables.VAMPIRE_BLOOD_BOTTLE)
			.overrideDescription(VAMPIRE_BLOOD_BOTTLE.getDescriptionId()).modelId(Nycto.id("vampire_blood_bottle")));
	public static final Item AMBROSIA_BOTTLE = registerItem(NyctoItemIds.AMBROSIA_BOTTLE, properties()
			.rarity(Rarity.RARE)
			.food(NyctoFoods.AMBROSIA_BOTTLE, NyctoConsumables.AMBROSIA_BOTTLE)
			.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
			.craftRemainder(Items.GLASS_BOTTLE)
			.usingConvertsTo(Items.GLASS_BOTTLE)
			.stacksTo(16));

	public static final Item GARLIC = registerItem(NyctoBlockItemIds.GARLIC.item(), properties -> new TransformationCheckerBlockItem(NyctoBlocks.GARLIC, properties, NyctoAPI::isVampire), properties()
			.food(NyctoFoods.GARLIC));
	public static final Item GRILLED_GARLIC = registerItem(NyctoItemIds.GRILLED_GARLIC, properties()
			.food(NyctoFoods.GRILLED_GARLIC));
	public static final Item GARLIC_BREAD = registerItem(NyctoItemIds.GARLIC_BREAD, properties()
			.food(NyctoFoods.GARLIC_BREAD));

	public static final Item ACONITE_SEEDS = registerItem(NyctoBlockItemIds.ACONITE.item(), properties -> new BlockItem(NyctoBlocks.ACONITE, properties));
	public static final Item ACONITE = registerItem(NyctoItemIds.ACONITE, properties -> new TransformationCheckerBlockItem(NyctoBlocks.ACONITE, properties, NyctoAPI::isWerewolf));

	public static final Item HUNTER_CONTRACT = registerItem(NyctoItemIds.HUNTER_CONTRACT);
	public static final Item VAMPIRE_HUNTER_CONTRACT = registerItem(NyctoItemIds.VAMPIRE_HUNTER_CONTRACT, properties -> new HunterContractItem(properties, NyctoHunterTypes.VAMPIRE));
	public static final Item WEREWOLF_HUNTER_CONTRACT = registerItem(NyctoItemIds.WEREWOLF_HUNTER_CONTRACT, properties -> new HunterContractItem(properties, NyctoHunterTypes.WEREWOLF));

	public static final Item VAMPIRE_BAT_BANNER_PATTERN = registerItem(NyctoItemIds.VAMPIRE_BAT_BANNER_PATTERN, properties()
			.stacksTo(1)
			.delayedComponent(DataComponents.PROVIDES_BANNER_PATTERNS, context -> context.getOrThrow(NyctoBannerPatternTags.PATTERN_ITEM_VAMPIRE_BAT)));
	public static final Item WOLF_SKULL_BANNER_PATTERN = registerItem(NyctoItemIds.WOLF_SKULL_BANNER_PATTERN, properties()
			.stacksTo(1)
			.delayedComponent(DataComponents.PROVIDES_BANNER_PATTERNS, context -> context.getOrThrow(NyctoBannerPatternTags.PATTERN_ITEM_WOLF_SKULL)));
	public static final Item HUNTERS_MARK_BANNER_PATTERN = registerItem(NyctoItemIds.HUNTERS_MARK_BANNER_PATTERN, properties()
			.stacksTo(1)
			.delayedComponent(DataComponents.PROVIDES_BANNER_PATTERNS, context -> context.getOrThrow(NyctoBannerPatternTags.PATTERN_ITEM_HUNTERS_MARK)));

	public static final Item VAMPIRE_SPAWN_EGG = registerItem(NyctoItemIds.VAMPIRE_SPAWN_EGG, SpawnEggItem::new, properties()
			.spawnEgg(NyctoEntityTypes.VAMPIRE));
	public static final Item HUNTER_SPAWN_EGG = registerItem(NyctoItemIds.HUNTER_SPAWN_EGG, SpawnEggItem::new, properties()
			.spawnEgg(NyctoEntityTypes.HUNTER));

	public static Item registerCoffin(BlockItemId id, Block block) {
		return registerBlockItem(id, block, properties()
				.stacksTo(1));
	}

	public static Item registerHunterArmor(ResourceKey<Item> key, ArmorType type, Holder<Attribute> attribute) {
		boolean body = type == ArmorType.BODY;
		boolean mask = type == ArmorType.HELMET;
		AttributeModifier resistanceModifier = new AttributeModifier(Nycto.id("hunter_armor_resistance_" + type.getName()), body ? 4 : 1, AttributeModifier.Operation.ADD_VALUE);
		Item.Properties properties = properties();
		if (body) {
			properties.wolfArmor(ArmorMaterials.ARMADILLO_SCUTE);
			properties.attributes(ArmorMaterials.ARMADILLO_SCUTE.createAttributes(type).withModifierAdded(attribute, resistanceModifier, EquipmentSlotGroup.bySlot(type.getSlot())));
		} else {
			properties.humanoidArmor(NyctoArmorMaterials.HUNTER, type);
			properties.attributes(NyctoArmorMaterials.HUNTER.createAttributes(type).withModifierAdded(attribute, resistanceModifier, EquipmentSlotGroup.bySlot(type.getSlot())));
		}
		if (mask) {
			properties.component(NyctoDataComponents.MASK_VISIBILITY, MaskVisibility.VISIBLE);
		}
		return registerItem(key, mask ? MaskItem::new : Item::new, properties);
	}

	public static Item registerHalberd(ResourceKey<Item> key) {
		AttributeModifier entityInteractionRangeModifier = new AttributeModifier(Nycto.id("halberd_entity_interaction_range"), 0.5, AttributeModifier.Operation.ADD_VALUE);
		ModifierTrio modifier = new ModifierTrio(Attributes.ENTITY_INTERACTION_RANGE, entityInteractionRangeModifier, EquipmentSlotGroup.MAINHAND);
		return registerItem(key, settings -> new AxeItem(ToolMaterial.DIAMOND, 5, -3.2F, settings), editModifiers(NyctoItems::properties, modifier));
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

			output.accept(BLOOD_FOUNTAIN);

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
			output.accept(VAMPIRE_HUNTER_WOLF_ARMOR);
			output.accept(WEREWOLF_HUNTER_HELMET);
			output.accept(WEREWOLF_HUNTER_CHESTPLATE);
			output.accept(WEREWOLF_HUNTER_LEGGINGS);
			output.accept(WEREWOLF_HUNTER_BOOTS);
			output.accept(WEREWOLF_HUNTER_WOLF_ARMOR);

			output.accept(VAMPIRIC_DAGGER);
			output.accept(HALBERD);
			output.accept(GARLIC_COATED_HALBERD);
			output.accept(ACONITE_COATED_HALBERD);
			output.accept(WOODEN_STAKE);
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

			addPotions(output, NyctoPotions.WITHER, NyctoPotions.LONG_WITHER, NyctoPotions.STRONG_WITHER);
			addPotions(output, NyctoPotions.GARLIC, NyctoPotions.LONG_GARLIC, NyctoPotions.STRONG_GARLIC);
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
