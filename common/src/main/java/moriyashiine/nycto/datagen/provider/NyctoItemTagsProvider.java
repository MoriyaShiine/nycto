package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.references.NyctoBlockItemIds;
import moriyashiine.nycto.common.references.NyctoItemIds;
import moriyashiine.nycto.common.tag.NyctoBlockTags;
import moriyashiine.nycto.common.tag.NyctoItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.ItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class NyctoItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {
	public NyctoItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, @Nullable BlockTagsProvider blockTagsProvider) {
		super(output, registriesFuture, blockTagsProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		copy(NyctoBlockTags.COFFINS, NyctoItemTags.COFFINS);

		builder(NyctoItemTags.BEAST_UNEQUIPPABLE)
				.add(NyctoBlockItemIds.WOODEN_STAKE)
				.forceAddTag(ItemTags.DURABILITY_ENCHANTABLE);
		builder(NyctoItemTags.HURTS_VAMPIRES)
				.addTag(NyctoItemTags.VAMPIRE_HUNTER_ARMOR)
				.add(NyctoBlockItemIds.GARLIC_WREATH)
				.add(NyctoBlockItemIds.WILD_GARLIC)
				.add(NyctoItemIds.GARLIC_COATED_HALBERD)
				.add(NyctoBlockItemIds.GARLIC)
				.add(NyctoItemIds.GRILLED_GARLIC)
				.add(NyctoItemIds.GARLIC_BREAD);
		builder(NyctoItemTags.USABLE_BLOOD_BOTTLES)
				.add(NyctoItemIds.BLOOD_BOTTLE)
				.add(NyctoItemIds.VAMPIRE_BLOOD_BOTTLE);
		builder(NyctoItemTags.VAMPIRE_WEAKNESSES)
				.add(NyctoItemIds.GARLIC_COATED_HALBERD)
				.add(NyctoBlockItemIds.WOODEN_STAKE);

		builder(NyctoItemTags.VAMPIRE_ARMOR)
				.add(NyctoItemIds.VAMPIRE_HELMET)
				.add(NyctoItemIds.VAMPIRE_CHESTPLATE)
				.add(NyctoItemIds.VAMPIRE_LEGGINGS)
				.add(NyctoItemIds.VAMPIRE_BOOTS);
		builder(NyctoItemTags.VAMPIRE_HUNTER_ARMOR)
				.add(NyctoItemIds.VAMPIRE_HUNTER_HELMET)
				.add(NyctoItemIds.VAMPIRE_HUNTER_CHESTPLATE)
				.add(NyctoItemIds.VAMPIRE_HUNTER_LEGGINGS)
				.add(NyctoItemIds.VAMPIRE_HUNTER_BOOTS)
				.add(NyctoItemIds.VAMPIRE_HUNTER_WOLF_ARMOR);
		builder(NyctoItemTags.WEREWOLF_HUNTER_ARMOR)
				.add(NyctoItemIds.WEREWOLF_HUNTER_HELMET)
				.add(NyctoItemIds.WEREWOLF_HUNTER_CHESTPLATE)
				.add(NyctoItemIds.WEREWOLF_HUNTER_LEGGINGS)
				.add(NyctoItemIds.WEREWOLF_HUNTER_BOOTS)
				.add(NyctoItemIds.WEREWOLF_HUNTER_WOLF_ARMOR);
		builder(NyctoItemTags.REPAIRS_VAMPIRE_ARMOR)
				.forceAddTag(ItemTags.REPAIRS_IRON_ARMOR);
		builder(NyctoItemTags.REPAIRS_HUNTER_ARMOR)
				.forceAddTag(ItemTags.REPAIRS_IRON_ARMOR);

		builder(NyctoItemTags.WEAK_VAMPIRE_ALTAR_UPGRADES)
				.forceAddTag(ConventionalItemTags.AMETHYST_GEMS)
				.forceAddTag(ConventionalItemTags.GOLD_INGOTS)
				.forceAddTag(ConventionalItemTags.LAPIS_GEMS);
		builder(NyctoItemTags.AVERAGE_VAMPIRE_ALTAR_UPGRADES)
				.forceAddTag(ConventionalItemTags.DIAMOND_GEMS)
				.forceAddTag(ConventionalItemTags.ENDER_PEARLS)
				.add(ItemIds.GOLDEN_APPLE);
		builder(NyctoItemTags.STRONG_VAMPIRE_ALTAR_UPGRADES)
				.add(ItemIds.ENDER_EYE)
				.add(ItemIds.GHAST_TEAR)
				.add(ItemIds.NETHERITE_SCRAP);

		builder(ConventionalItemTags.FOODS)
				.add(NyctoBlockItemIds.GARLIC)
				.add(NyctoItemIds.GRILLED_GARLIC)
				.add(NyctoItemIds.GARLIC_BREAD);
		builder(ConventionalItemTags.WOLF_ARMORS)
				.add(NyctoItemIds.VAMPIRE_HUNTER_WOLF_ARMOR)
				.add(NyctoItemIds.WEREWOLF_HUNTER_WOLF_ARMOR);

		builder(ItemTags.BEDS)
				.addTag(NyctoItemTags.COFFINS);
		builder(ItemTags.HEAD_ARMOR)
				.add(NyctoItemIds.VAMPIRE_HELMET)
				.add(NyctoItemIds.VAMPIRE_HUNTER_HELMET)
				.add(NyctoItemIds.WEREWOLF_HUNTER_HELMET);
		builder(ItemTags.CHEST_ARMOR)
				.add(NyctoItemIds.VAMPIRE_CHESTPLATE)
				.add(NyctoItemIds.VAMPIRE_HUNTER_CHESTPLATE)
				.add(NyctoItemIds.WEREWOLF_HUNTER_CHESTPLATE);
		builder(ItemTags.LEG_ARMOR)
				.add(NyctoItemIds.VAMPIRE_LEGGINGS)
				.add(NyctoItemIds.VAMPIRE_HUNTER_LEGGINGS)
				.add(NyctoItemIds.WEREWOLF_HUNTER_LEGGINGS);
		builder(ItemTags.FOOT_ARMOR)
				.add(NyctoItemIds.VAMPIRE_BOOTS)
				.add(NyctoItemIds.VAMPIRE_HUNTER_BOOTS)
				.add(NyctoItemIds.WEREWOLF_HUNTER_BOOTS);
		builder(ItemTags.FREEZE_IMMUNE_WEARABLES)
				.addTag(NyctoItemTags.VAMPIRE_ARMOR)
				.addTag(NyctoItemTags.VAMPIRE_HUNTER_ARMOR)
				.addTag(NyctoItemTags.WEREWOLF_HUNTER_ARMOR);
		builder(ItemTags.SWORDS)
				.add(NyctoItemIds.VAMPIRIC_DAGGER);
		builder(ItemTags.AXES)
				.add(NyctoItemIds.HALBERD)
				.add(NyctoItemIds.GARLIC_COATED_HALBERD)
				.add(NyctoItemIds.ACONITE_COATED_HALBERD);
		builder(ItemTags.LOOM_PATTERNS)
				.add(NyctoItemIds.VAMPIRE_BAT_BANNER_PATTERN)
				.add(NyctoItemIds.WOLF_SKULL_BANNER_PATTERN)
				.add(NyctoItemIds.HUNTERS_MARK_BANNER_PATTERN);

		builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("enchancement", "cannot_automatically_consume")))
				.add(NyctoItemIds.AMBROSIA_BOTTLE);

		builder(ItemTags.TRIMMABLE_ARMOR)
				.removeTag(NyctoItemTags.VAMPIRE_ARMOR)
				.removeTag(NyctoItemTags.VAMPIRE_HUNTER_ARMOR)
				.removeTag(NyctoItemTags.WEREWOLF_HUNTER_ARMOR);
	}
}
