/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.tag.ModBlockTags;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.strawberrylib.api.module.tag.SLibItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {
	public ModItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, @Nullable BlockTagsProvider blockTagsProvider) {
		super(output, registriesFuture, blockTagsProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		copy(ModBlockTags.COFFINS, ModItemTags.COFFINS);

		valueLookupBuilder(ModItemTags.BEAST_UNEQUIPPABLE)
				.forceAddTag(ItemTags.DURABILITY_ENCHANTABLE)
				.add(ModItems.WOODEN_STAKE);
		valueLookupBuilder(ModItemTags.HURTS_VAMPIRES)
				.addTag(ModItemTags.VAMPIRE_HUNTER_ARMOR)
				.add(ModItems.GARLIC_WREATH)
				.add(ModItems.WILD_GARLIC)
				.add(ModItems.GARLIC_COATED_HALBERD)
				.add(ModItems.GARLIC)
				.add(ModItems.GRILLED_GARLIC)
				.add(ModItems.GARLIC_BREAD);
		valueLookupBuilder(ModItemTags.USABLE_BLOOD_BOTTLES)
				.add(ModItems.BLOOD_BOTTLE)
				.add(ModItems.VAMPIRE_BLOOD_BOTTLE);
		valueLookupBuilder(ModItemTags.VAMPIRE_WEAKNESSES)
				.add(ModItems.GARLIC_COATED_HALBERD)
				.add(ModItems.WOODEN_STAKE);

		valueLookupBuilder(ModItemTags.VAMPIRE_ARMOR)
				.add(ModItems.VAMPIRE_HELMET)
				.add(ModItems.VAMPIRE_CHESTPLATE)
				.add(ModItems.VAMPIRE_LEGGINGS)
				.add(ModItems.VAMPIRE_BOOTS);
		valueLookupBuilder(ModItemTags.VAMPIRE_HUNTER_ARMOR)
				.add(ModItems.VAMPIRE_HUNTER_HELMET)
				.add(ModItems.VAMPIRE_HUNTER_CHESTPLATE)
				.add(ModItems.VAMPIRE_HUNTER_LEGGINGS)
				.add(ModItems.VAMPIRE_HUNTER_BOOTS);
		valueLookupBuilder(ModItemTags.WEREWOLF_HUNTER_ARMOR)
				.add(ModItems.WEREWOLF_HUNTER_HELMET)
				.add(ModItems.WEREWOLF_HUNTER_CHESTPLATE)
				.add(ModItems.WEREWOLF_HUNTER_LEGGINGS)
				.add(ModItems.WEREWOLF_HUNTER_BOOTS);
		valueLookupBuilder(ModItemTags.REPAIRS_VAMPIRE_ARMOR)
				.forceAddTag(ItemTags.REPAIRS_IRON_ARMOR);
		valueLookupBuilder(ModItemTags.REPAIRS_HUNTER_ARMOR)
				.forceAddTag(ItemTags.REPAIRS_IRON_ARMOR);

		valueLookupBuilder(ModItemTags.HALBERD_TOOL_MATERIALS)
				.forceAddTag(ItemTags.IRON_TOOL_MATERIALS);

		valueLookupBuilder(ModItemTags.WEAK_VAMPIRE_ALTAR_UPGRADES)
				.forceAddTag(ConventionalItemTags.AMETHYST_GEMS)
				.forceAddTag(ConventionalItemTags.GOLD_INGOTS)
				.forceAddTag(ConventionalItemTags.LAPIS_GEMS);
		valueLookupBuilder(ModItemTags.AVERAGE_VAMPIRE_ALTAR_UPGRADES)
				.forceAddTag(ConventionalItemTags.DIAMOND_GEMS)
				.forceAddTag(ConventionalItemTags.ENDER_PEARLS)
				.add(Items.GOLDEN_APPLE);
		valueLookupBuilder(ModItemTags.STRONG_VAMPIRE_ALTAR_UPGRADES)
				.add(Items.GHAST_TEAR)
				.add(Items.MAGMA_CREAM);

		valueLookupBuilder(SLibItemTags.UNTRIMMABLE_ARMOR)
				.addTag(ModItemTags.VAMPIRE_ARMOR)
				.addTag(ModItemTags.VAMPIRE_HUNTER_ARMOR)
				.addTag(ModItemTags.WEREWOLF_HUNTER_ARMOR);

		valueLookupBuilder(ConventionalItemTags.FOODS)
				.add(ModItems.GARLIC)
				.add(ModItems.GRILLED_GARLIC)
				.add(ModItems.GARLIC_BREAD);

		valueLookupBuilder(ItemTags.BEDS)
				.addTag(ModItemTags.COFFINS);
		valueLookupBuilder(ItemTags.HEAD_ARMOR)
				.add(ModItems.VAMPIRE_HELMET)
				.add(ModItems.VAMPIRE_HUNTER_HELMET)
				.add(ModItems.WEREWOLF_HUNTER_HELMET);
		valueLookupBuilder(ItemTags.CHEST_ARMOR)
				.add(ModItems.VAMPIRE_CHESTPLATE)
				.add(ModItems.VAMPIRE_HUNTER_CHESTPLATE)
				.add(ModItems.WEREWOLF_HUNTER_CHESTPLATE);
		valueLookupBuilder(ItemTags.LEG_ARMOR)
				.add(ModItems.VAMPIRE_LEGGINGS)
				.add(ModItems.VAMPIRE_HUNTER_LEGGINGS)
				.add(ModItems.WEREWOLF_HUNTER_LEGGINGS);
		valueLookupBuilder(ItemTags.FOOT_ARMOR)
				.add(ModItems.VAMPIRE_BOOTS)
				.add(ModItems.VAMPIRE_HUNTER_BOOTS)
				.add(ModItems.WEREWOLF_HUNTER_BOOTS);
		valueLookupBuilder(ItemTags.SWORDS)
				.add(ModItems.VAMPIRIC_DAGGER);
		valueLookupBuilder(ItemTags.AXES)
				.add(ModItems.HALBERD)
				.add(ModItems.GARLIC_COATED_HALBERD)
				.add(ModItems.ACONITE_COATED_HALBERD);
		valueLookupBuilder(ItemTags.ARROWS)
				.add(ModItems.ACONITE_ARROW);
		valueLookupBuilder(ItemTags.LOOM_PATTERNS)
				.add(ModItems.VAMPIRE_BAT_BANNER_PATTERN)
				.add(ModItems.WOLF_SKULL_BANNER_PATTERN)
				.add(ModItems.HUNTERS_MARK_BANNER_PATTERN);

		valueLookupBuilder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("enchancement", "cannot_automatically_consume")))
				.add(ModItems.AMBROSIA_BOTTLE);
	}
}
