package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.init.NyctoItems;
import moriyashiine.nycto.common.tag.NyctoBlockTags;
import moriyashiine.nycto.common.tag.NyctoItemTags;
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

public class NyctoItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {
	public NyctoItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, @Nullable BlockTagsProvider blockTagsProvider) {
		super(output, registriesFuture, blockTagsProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		copy(NyctoBlockTags.COFFINS, NyctoItemTags.COFFINS);

		valueLookupBuilder(NyctoItemTags.BEAST_UNEQUIPPABLE)
				.forceAddTag(ItemTags.DURABILITY_ENCHANTABLE)
				.add(NyctoItems.WOODEN_STAKE);
		valueLookupBuilder(NyctoItemTags.HURTS_VAMPIRES)
				.addTag(NyctoItemTags.VAMPIRE_HUNTER_ARMOR)
				.add(NyctoItems.GARLIC_WREATH)
				.add(NyctoItems.WILD_GARLIC)
				.add(NyctoItems.GARLIC_COATED_HALBERD)
				.add(NyctoItems.GARLIC)
				.add(NyctoItems.GRILLED_GARLIC)
				.add(NyctoItems.GARLIC_BREAD);
		valueLookupBuilder(NyctoItemTags.USABLE_BLOOD_BOTTLES)
				.add(NyctoItems.BLOOD_BOTTLE)
				.add(NyctoItems.VAMPIRE_BLOOD_BOTTLE);
		valueLookupBuilder(NyctoItemTags.VAMPIRE_WEAKNESSES)
				.add(NyctoItems.GARLIC_COATED_HALBERD)
				.add(NyctoItems.WOODEN_STAKE);

		valueLookupBuilder(NyctoItemTags.VAMPIRE_ARMOR)
				.add(NyctoItems.VAMPIRE_HELMET)
				.add(NyctoItems.VAMPIRE_CHESTPLATE)
				.add(NyctoItems.VAMPIRE_LEGGINGS)
				.add(NyctoItems.VAMPIRE_BOOTS);
		valueLookupBuilder(NyctoItemTags.VAMPIRE_HUNTER_ARMOR)
				.add(NyctoItems.VAMPIRE_HUNTER_HELMET)
				.add(NyctoItems.VAMPIRE_HUNTER_CHESTPLATE)
				.add(NyctoItems.VAMPIRE_HUNTER_LEGGINGS)
				.add(NyctoItems.VAMPIRE_HUNTER_BOOTS)
				.add(NyctoItems.VAMPIRE_HUNTER_WOLF_ARMOR);
		valueLookupBuilder(NyctoItemTags.WEREWOLF_HUNTER_ARMOR)
				.add(NyctoItems.WEREWOLF_HUNTER_HELMET)
				.add(NyctoItems.WEREWOLF_HUNTER_CHESTPLATE)
				.add(NyctoItems.WEREWOLF_HUNTER_LEGGINGS)
				.add(NyctoItems.WEREWOLF_HUNTER_BOOTS)
				.add(NyctoItems.WEREWOLF_HUNTER_WOLF_ARMOR);
		valueLookupBuilder(NyctoItemTags.REPAIRS_VAMPIRE_ARMOR)
				.forceAddTag(ItemTags.REPAIRS_IRON_ARMOR);
		valueLookupBuilder(NyctoItemTags.REPAIRS_HUNTER_ARMOR)
				.forceAddTag(ItemTags.REPAIRS_IRON_ARMOR);

		valueLookupBuilder(NyctoItemTags.WEAK_VAMPIRE_ALTAR_UPGRADES)
				.forceAddTag(ConventionalItemTags.AMETHYST_GEMS)
				.forceAddTag(ConventionalItemTags.GOLD_INGOTS)
				.forceAddTag(ConventionalItemTags.LAPIS_GEMS);
		valueLookupBuilder(NyctoItemTags.AVERAGE_VAMPIRE_ALTAR_UPGRADES)
				.forceAddTag(ConventionalItemTags.DIAMOND_GEMS)
				.forceAddTag(ConventionalItemTags.ENDER_PEARLS)
				.add(Items.GOLDEN_APPLE);
		valueLookupBuilder(NyctoItemTags.STRONG_VAMPIRE_ALTAR_UPGRADES)
				.add(Items.ENDER_EYE)
				.add(Items.GHAST_TEAR)
				.add(Items.NETHERITE_SCRAP);

		valueLookupBuilder(ConventionalItemTags.FOODS)
				.add(NyctoItems.GARLIC)
				.add(NyctoItems.GRILLED_GARLIC)
				.add(NyctoItems.GARLIC_BREAD);
		valueLookupBuilder(ConventionalItemTags.WOLF_ARMORS)
				.add(NyctoItems.VAMPIRE_HUNTER_WOLF_ARMOR)
				.add(NyctoItems.WEREWOLF_HUNTER_WOLF_ARMOR);

		valueLookupBuilder(ItemTags.BEDS)
				.addTag(NyctoItemTags.COFFINS);
		valueLookupBuilder(ItemTags.HEAD_ARMOR)
				.add(NyctoItems.VAMPIRE_HELMET)
				.add(NyctoItems.VAMPIRE_HUNTER_HELMET)
				.add(NyctoItems.WEREWOLF_HUNTER_HELMET);
		valueLookupBuilder(ItemTags.CHEST_ARMOR)
				.add(NyctoItems.VAMPIRE_CHESTPLATE)
				.add(NyctoItems.VAMPIRE_HUNTER_CHESTPLATE)
				.add(NyctoItems.WEREWOLF_HUNTER_CHESTPLATE);
		valueLookupBuilder(ItemTags.LEG_ARMOR)
				.add(NyctoItems.VAMPIRE_LEGGINGS)
				.add(NyctoItems.VAMPIRE_HUNTER_LEGGINGS)
				.add(NyctoItems.WEREWOLF_HUNTER_LEGGINGS);
		valueLookupBuilder(ItemTags.FOOT_ARMOR)
				.add(NyctoItems.VAMPIRE_BOOTS)
				.add(NyctoItems.VAMPIRE_HUNTER_BOOTS)
				.add(NyctoItems.WEREWOLF_HUNTER_BOOTS);
		valueLookupBuilder(ItemTags.FREEZE_IMMUNE_WEARABLES)
				.addTag(NyctoItemTags.VAMPIRE_ARMOR)
				.addTag(NyctoItemTags.VAMPIRE_HUNTER_ARMOR)
				.addTag(NyctoItemTags.WEREWOLF_HUNTER_ARMOR);
		valueLookupBuilder(ItemTags.SWORDS)
				.add(NyctoItems.VAMPIRIC_DAGGER);
		valueLookupBuilder(ItemTags.AXES)
				.add(NyctoItems.HALBERD)
				.add(NyctoItems.GARLIC_COATED_HALBERD)
				.add(NyctoItems.ACONITE_COATED_HALBERD);
		valueLookupBuilder(ItemTags.LOOM_PATTERNS)
				.add(NyctoItems.VAMPIRE_BAT_BANNER_PATTERN)
				.add(NyctoItems.WOLF_SKULL_BANNER_PATTERN)
				.add(NyctoItems.HUNTERS_MARK_BANNER_PATTERN);

		valueLookupBuilder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("enchancement", "cannot_automatically_consume")))
				.add(NyctoItems.AMBROSIA_BOTTLE);

		valueLookupBuilder(ItemTags.TRIMMABLE_ARMOR)
				.removeTag(NyctoItemTags.VAMPIRE_ARMOR)
				.removeTag(NyctoItemTags.VAMPIRE_HUNTER_ARMOR)
				.removeTag(NyctoItemTags.WEREWOLF_HUNTER_ARMOR);
	}
}
