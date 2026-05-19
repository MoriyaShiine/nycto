/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.NyctoItems;
import moriyashiine.nycto.common.tag.NyctoItemTags;
import moriyashiine.nycto.common.world.item.crafting.BloodExtractionRecipe;
import moriyashiine.nycto.common.world.item.crafting.FoodPoisoningRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SmokingRecipe;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.item.crafting.Ingredient.of;

public class NyctoRecipeProvider extends FabricRecipeProvider {
	public static final ResourceKey<Recipe<?>> BLOOD_EXTRACTION = ResourceKey.create(Registries.RECIPE, Nycto.id("blood_extraction"));

	public NyctoRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
		return new RecipeProvider(registries, output) {
			@Override
			public void buildRecipes() {
				stonecutterResultFromBase(RecipeCategory.DECORATIONS, Items.SKELETON_SKULL, Items.BONE_BLOCK);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.VAMPIRE_ALTAR).define('C', ItemTags.CANDLES).define('W', ItemTags.PLANKS).define('B', NyctoItemTags.USABLE_BLOOD_BOTTLES).define('I', ConventionalItemTags.COPPER_INGOTS).pattern("C C").pattern("WBW").pattern("IWI").unlockedBy("has_blood_bottle", has(NyctoItemTags.USABLE_BLOOD_BOTTLES)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.WEREWOLF_ALTAR).define('S', Items.SKELETON_SKULL).define('B', Items.BONE).define('E', Items.ENDER_PEARL).define('C', ItemTags.CANDLES).define('I', ConventionalItemTags.COPPER_INGOTS).pattern(" S ").pattern("BEB").pattern("CIC").unlockedBy("has_skeleton_skull", has(Items.SKELETON_SKULL)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.OAK_COFFIN).define('P', Items.OAK_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.SPRUCE_COFFIN).define('P', Items.SPRUCE_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.BIRCH_COFFIN).define('P', Items.BIRCH_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.JUNGLE_COFFIN).define('P', Items.JUNGLE_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.ACACIA_COFFIN).define('P', Items.ACACIA_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.DARK_OAK_COFFIN).define('P', Items.DARK_OAK_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.PALE_OAK_COFFIN).define('P', Items.PALE_OAK_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.MANGROVE_COFFIN).define('P', Items.MANGROVE_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.CHERRY_COFFIN).define('P', Items.CHERRY_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.BAMBOO_COFFIN).define('P', Items.BAMBOO_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.CRIMSON_COFFIN).define('P', Items.CRIMSON_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.WARPED_COFFIN).define('P', Items.WARPED_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.BLOOD_FOUNTAIN).define('S', Items.STONE_BRICK_SLAB).define('B', Items.GLASS_BOTTLE).pattern(" S ").pattern(" B ").pattern("SSS").unlockedBy("has_bottle", has(Items.GLASS_BOTTLE)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.GARLIC_WREATH).define('S', Items.STRING).define('A', Items.ALLIUM).define('G', NyctoItems.GARLIC).pattern("SAS").pattern("GSG").pattern(" G ").unlockedBy("has_garlic", has(NyctoItems.GARLIC)).save(output);
				shaped(RecipeCategory.DECORATIONS, NyctoItems.ACONITE_GARLAND).define('S', Items.STRING).define('C', ConventionalItemTags.COCOA_BEAN_CROPS).define('A', NyctoItems.ACONITE).pattern("SCS").pattern("ASA").pattern(" A ").unlockedBy("has_aconite", has(NyctoItems.ACONITE)).save(output);
				shaped(RecipeCategory.COMBAT, NyctoItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE).define('I', ConventionalItemTags.IRON_INGOTS).define('B', NyctoItemTags.USABLE_BLOOD_BOTTLES).define('D', ConventionalItemTags.DIAMOND_GEMS).pattern("III").pattern("IBI").pattern("IDI").unlockedBy("has_blood_bottle", has(NyctoItemTags.USABLE_BLOOD_BOTTLES)).save(output);
				shaped(RecipeCategory.COMBAT, NyctoItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE).define('I', ConventionalItemTags.IRON_INGOTS).define('G', NyctoItems.GARLIC_WREATH).define('D', ConventionalItemTags.DIAMOND_GEMS).pattern("III").pattern("IGI").pattern("IDI").unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS)).save(output);
				shaped(RecipeCategory.COMBAT, NyctoItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE).define('I', ConventionalItemTags.IRON_INGOTS).define('A', NyctoItems.ACONITE_GARLAND).define('D', ConventionalItemTags.DIAMOND_GEMS).pattern("III").pattern("IAI").pattern("IDI").unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS)).save(output);
				vampireSmithing(Items.LEATHER_HELMET, NyctoItems.VAMPIRE_HELMET);
				vampireSmithing(Items.LEATHER_CHESTPLATE, NyctoItems.VAMPIRE_CHESTPLATE);
				vampireSmithing(Items.LEATHER_LEGGINGS, NyctoItems.VAMPIRE_LEGGINGS);
				vampireSmithing(Items.LEATHER_BOOTS, NyctoItems.VAMPIRE_BOOTS);
				hunterSmithing(NyctoItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_HELMET, NyctoItems.VAMPIRE_HUNTER_HELMET);
				hunterSmithing(NyctoItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_CHESTPLATE, NyctoItems.VAMPIRE_HUNTER_CHESTPLATE);
				hunterSmithing(NyctoItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_LEGGINGS, NyctoItems.VAMPIRE_HUNTER_LEGGINGS);
				hunterSmithing(NyctoItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_BOOTS, NyctoItems.VAMPIRE_HUNTER_BOOTS);
				hunterSmithing(NyctoItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.WOLF_ARMOR, NyctoItems.VAMPIRE_HUNTER_WOLF_ARMOR);
				hunterSmithing(NyctoItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_HELMET, NyctoItems.WEREWOLF_HUNTER_HELMET);
				hunterSmithing(NyctoItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_CHESTPLATE, NyctoItems.WEREWOLF_HUNTER_CHESTPLATE);
				hunterSmithing(NyctoItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_LEGGINGS, NyctoItems.WEREWOLF_HUNTER_LEGGINGS);
				hunterSmithing(NyctoItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_BOOTS, NyctoItems.WEREWOLF_HUNTER_BOOTS);
				hunterSmithing(NyctoItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.WOLF_ARMOR, NyctoItems.WEREWOLF_HUNTER_WOLF_ARMOR);
				shaped(RecipeCategory.COMBAT, NyctoItems.VAMPIRIC_DAGGER).define('I', ConventionalItemTags.IRON_INGOTS).define('F', Items.FLINT).define('B', Items.GLASS_BOTTLE).pattern("  I").pattern(" F ").pattern("B  ").unlockedBy("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS)).save(output);
				shaped(RecipeCategory.COMBAT, NyctoItems.HALBERD).define('G', ConventionalItemTags.GOLD_INGOTS).define('I', ConventionalItemTags.IRON_INGOTS).define('A', Items.DIAMOND_AXE).define('S', Items.STICK).pattern(" GI").pattern(" AI").pattern("S  ").unlockedBy("has_diamond", has(ItemTags.DIAMOND_TOOL_MATERIALS)).save(output);
				TransmuteRecipeBuilder.transmute(RecipeCategory.COMBAT, of(NyctoItems.HALBERD), of(NyctoItems.GARLIC_WREATH), NyctoItems.GARLIC_COATED_HALBERD).unlockedBy("has_halberd", has(NyctoItems.HALBERD)).save(output);
				TransmuteRecipeBuilder.transmute(RecipeCategory.COMBAT, of(NyctoItems.HALBERD), of(NyctoItems.ACONITE_GARLAND), NyctoItems.ACONITE_COATED_HALBERD).unlockedBy("has_halberd", has(NyctoItems.HALBERD)).save(output);
				shaped(RecipeCategory.COMBAT, NyctoItems.WOODEN_STAKE, 4).define('A', Items.ARROW).define('L', ItemTags.LOGS).pattern(" A ").pattern("ALA").pattern(" A ").unlockedBy("has_log", has(ItemTags.LOGS)).save(output);
				shapeless(RecipeCategory.COMBAT, NyctoItems.FIREBOMB).requires(Items.GLASS_BOTTLE).requires(Items.BLAZE_POWDER).requires(ConventionalItemTags.GUNPOWDERS).unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER)).save(output);
				SimpleCookingRecipeBuilder.smelting(of(NyctoItems.GARLIC), RecipeCategory.FOOD, CookingBookCategory.FOOD, NyctoItems.GRILLED_GARLIC, 0.35F, 200).unlockedBy("has_garlic", has(NyctoItems.GARLIC)).save(output);
				simpleCookingRecipe("smoking", SmokingRecipe::new, 100, NyctoItems.GARLIC, NyctoItems.GRILLED_GARLIC, 0.35F);
				simpleCookingRecipe("campfire_cooking", CampfireCookingRecipe::new, 600, NyctoItems.GARLIC, NyctoItems.GRILLED_GARLIC, 0.35F);
				shapeless(RecipeCategory.FOOD, NyctoItems.GARLIC_BREAD).requires(Items.BREAD).requires(NyctoItems.GRILLED_GARLIC).unlockedBy("has_grilled_garlic", has(NyctoItems.GRILLED_GARLIC)).save(output);
				shapeless(RecipeCategory.COMBAT, NyctoItems.VAMPIRE_HUNTER_CONTRACT).requires(NyctoItems.HUNTER_CONTRACT).requires(NyctoItems.GARLIC).unlockedBy("has_contract", has(NyctoItems.HUNTER_CONTRACT)).save(output);
				shapeless(RecipeCategory.COMBAT, NyctoItems.WEREWOLF_HUNTER_CONTRACT).requires(NyctoItems.HUNTER_CONTRACT).requires(NyctoItems.ACONITE).unlockedBy("has_contract", has(NyctoItems.HUNTER_CONTRACT)).save(output);
				shapeless(RecipeCategory.MISC, NyctoItems.VAMPIRE_BAT_BANNER_PATTERN).requires(Items.PAPER).requires(NyctoItems.BLOOD_BOTTLE).unlockedBy("has_blood_bottle", has(NyctoItems.BLOOD_BOTTLE)).save(output);
				shapeless(RecipeCategory.MISC, NyctoItems.WOLF_SKULL_BANNER_PATTERN).requires(Items.PAPER).requires(NyctoItems.ACONITE).unlockedBy("has_aconite_placeholder", has(NyctoItems.ACONITE)).save(output);
				shapeless(RecipeCategory.MISC, NyctoItems.HUNTERS_MARK_BANNER_PATTERN).requires(Items.PAPER).requires(NyctoItems.HUNTER_CONTRACT).unlockedBy("has_contract", has(NyctoItems.HUNTER_CONTRACT)).save(output);

				SpecialRecipeBuilder.special(BloodExtractionRecipe::new).save(output, BLOOD_EXTRACTION);
				SpecialRecipeBuilder.special(FoodPoisoningRecipe::new).save(output, Nycto.id("food_poisoning").toString());
			}

			private void vampireSmithing(Item base, Item result) {
				SmithingTransformRecipeBuilder.smithing(
								of(NyctoItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE),
								of(base),
								tag(NyctoItemTags.USABLE_BLOOD_BOTTLES),
								RecipeCategory.COMBAT,
								result
						)
						.unlocks("has_blood_bottle", has(NyctoItemTags.USABLE_BLOOD_BOTTLES))
						.save(output, getItemName(result) + "_smithing");
			}

			private void hunterSmithing(Item template, Item base, Item result) {
				SmithingTransformRecipeBuilder.smithing(
								of(template),
								of(base),
								tag(ConventionalItemTags.GOLD_INGOTS),
								RecipeCategory.COMBAT,
								result
						)
						.unlocks("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS))
						.save(output, getItemName(result) + "_smithing");
			}
		};
	}

	@Override
	public String getName() {
		return Nycto.MOD_ID + "_recipes";
	}
}
