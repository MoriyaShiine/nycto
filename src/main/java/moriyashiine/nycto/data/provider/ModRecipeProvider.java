/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.tag.ModItemTags;
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

public class ModRecipeProvider extends FabricRecipeProvider {
	public static final ResourceKey<Recipe<?>> BLOOD_EXTRACTION = ResourceKey.create(Registries.RECIPE, Nycto.id("blood_extraction"));

	public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
		return new RecipeProvider(registries, output) {
			@Override
			public void buildRecipes() {
				stonecutterResultFromBase(RecipeCategory.DECORATIONS, Items.SKELETON_SKULL, Items.BONE_BLOCK);
				shaped(RecipeCategory.DECORATIONS, ModItems.VAMPIRE_ALTAR).define('C', ItemTags.CANDLES).define('W', ItemTags.PLANKS).define('B', ModItemTags.USABLE_BLOOD_BOTTLES).define('I', ConventionalItemTags.COPPER_INGOTS).pattern("C C").pattern("WBW").pattern("IWI").unlockedBy("has_blood_bottle", has(ModItemTags.USABLE_BLOOD_BOTTLES)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.WEREWOLF_ALTAR).define('S', Items.SKELETON_SKULL).define('B', Items.BONE).define('E', Items.ENDER_PEARL).define('C', ItemTags.CANDLES).define('I', ConventionalItemTags.COPPER_INGOTS).pattern(" S ").pattern("BEB").pattern("CIC").unlockedBy("has_skeleton_skull", has(Items.SKELETON_SKULL)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.OAK_COFFIN).define('P', Items.OAK_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.SPRUCE_COFFIN).define('P', Items.SPRUCE_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.BIRCH_COFFIN).define('P', Items.BIRCH_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.JUNGLE_COFFIN).define('P', Items.JUNGLE_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.ACACIA_COFFIN).define('P', Items.ACACIA_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.DARK_OAK_COFFIN).define('P', Items.DARK_OAK_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.PALE_OAK_COFFIN).define('P', Items.PALE_OAK_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.MANGROVE_COFFIN).define('P', Items.MANGROVE_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.CHERRY_COFFIN).define('P', Items.CHERRY_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.BAMBOO_COFFIN).define('P', Items.BAMBOO_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.CRIMSON_COFFIN).define('P', Items.CRIMSON_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.WARPED_COFFIN).define('P', Items.WARPED_PLANKS).define('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").unlockedBy("has_bed", has(ItemTags.BEDS)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.GARLIC_WREATH).define('S', Items.STRING).define('A', Items.ALLIUM).define('G', ModItems.GARLIC).pattern("SAS").pattern("GSG").pattern(" G ").unlockedBy("has_garlic", has(ModItems.GARLIC)).save(output);
				shaped(RecipeCategory.DECORATIONS, ModItems.ACONITE_GARLAND).define('S', Items.STRING).define('C', ConventionalItemTags.COCOA_BEAN_CROPS).define('A', ModItems.ACONITE).pattern("SCS").pattern("ASA").pattern(" A ").unlockedBy("has_aconite", has(ModItems.ACONITE)).save(output);
				shaped(RecipeCategory.COMBAT, ModItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE).define('I', ConventionalItemTags.IRON_INGOTS).define('B', ModItemTags.USABLE_BLOOD_BOTTLES).define('D', ConventionalItemTags.DIAMOND_GEMS).pattern("III").pattern("IBI").pattern("IDI").unlockedBy("has_blood_bottle", has(ModItemTags.USABLE_BLOOD_BOTTLES)).save(output);
				shaped(RecipeCategory.COMBAT, ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE).define('I', ConventionalItemTags.IRON_INGOTS).define('G', ModItems.GARLIC_WREATH).define('D', ConventionalItemTags.DIAMOND_GEMS).pattern("III").pattern("IGI").pattern("IDI").unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS)).save(output);
				shaped(RecipeCategory.COMBAT, ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE).define('I', ConventionalItemTags.IRON_INGOTS).define('A', ModItems.ACONITE_GARLAND).define('D', ConventionalItemTags.DIAMOND_GEMS).pattern("III").pattern("IAI").pattern("IDI").unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS)).save(output);
				vampireSmithing(Items.LEATHER_HELMET, ModItems.VAMPIRE_HELMET);
				vampireSmithing(Items.LEATHER_CHESTPLATE, ModItems.VAMPIRE_CHESTPLATE);
				vampireSmithing(Items.LEATHER_LEGGINGS, ModItems.VAMPIRE_LEGGINGS);
				vampireSmithing(Items.LEATHER_BOOTS, ModItems.VAMPIRE_BOOTS);
				hunterSmithing(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_HELMET, ModItems.VAMPIRE_HUNTER_HELMET);
				hunterSmithing(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_CHESTPLATE, ModItems.VAMPIRE_HUNTER_CHESTPLATE);
				hunterSmithing(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_LEGGINGS, ModItems.VAMPIRE_HUNTER_LEGGINGS);
				hunterSmithing(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_BOOTS, ModItems.VAMPIRE_HUNTER_BOOTS);
				hunterSmithing(ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_HELMET, ModItems.WEREWOLF_HUNTER_HELMET);
				hunterSmithing(ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_CHESTPLATE, ModItems.WEREWOLF_HUNTER_CHESTPLATE);
				hunterSmithing(ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_LEGGINGS, ModItems.WEREWOLF_HUNTER_LEGGINGS);
				hunterSmithing(ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_BOOTS, ModItems.WEREWOLF_HUNTER_BOOTS);
				shaped(RecipeCategory.COMBAT, ModItems.VAMPIRIC_DAGGER).define('I', ConventionalItemTags.IRON_INGOTS).define('F', Items.FLINT).define('B', Items.GLASS_BOTTLE).pattern("  I").pattern(" F ").pattern("B  ").unlockedBy("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS)).save(output);
				shaped(RecipeCategory.COMBAT, ModItems.HALBERD).define('G', ConventionalItemTags.GOLD_INGOTS).define('I', ConventionalItemTags.IRON_INGOTS).define('A', Items.IRON_AXE).define('S', Items.STICK).pattern(" GI").pattern(" AI").pattern("S  ").unlockedBy("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS)).save(output);
				TransmuteRecipeBuilder.transmute(RecipeCategory.COMBAT, of(ModItems.HALBERD), of(ModItems.GARLIC_WREATH), ModItems.GARLIC_COATED_HALBERD).unlockedBy("has_halberd", has(ModItems.HALBERD)).save(output);
				TransmuteRecipeBuilder.transmute(RecipeCategory.COMBAT, of(ModItems.HALBERD), of(ModItems.ACONITE_GARLAND), ModItems.ACONITE_COATED_HALBERD).unlockedBy("has_halberd", has(ModItems.HALBERD)).save(output);
				shaped(RecipeCategory.COMBAT, ModItems.WOODEN_STAKE, 4).define('A', Items.ARROW).define('L', ItemTags.LOGS).pattern(" A ").pattern("ALA").pattern(" A ").unlockedBy("has_log", has(ItemTags.LOGS)).save(output);
				shaped(RecipeCategory.COMBAT, ModItems.ACONITE_ARROW, 4).define('A', Items.ARROW).define('P', ModItems.ACONITE).pattern(" A ").pattern("APA").pattern(" A ").unlockedBy("has_aconite", has(ModItems.ACONITE)).save(output);
				shapeless(RecipeCategory.COMBAT, ModItems.FIREBOMB).requires(Items.GLASS_BOTTLE).requires(Items.BLAZE_POWDER).requires(ConventionalItemTags.GUNPOWDERS).unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER)).save(output);
				SimpleCookingRecipeBuilder.smelting(of(ModItems.GARLIC), RecipeCategory.FOOD, CookingBookCategory.FOOD, ModItems.GRILLED_GARLIC, 0.35F, 200).unlockedBy("has_garlic", has(ModItems.GARLIC)).save(output);
				simpleCookingRecipe("smoking", SmokingRecipe::new, 100, ModItems.GARLIC, ModItems.GRILLED_GARLIC, 0.35F);
				simpleCookingRecipe("campfire_cooking", CampfireCookingRecipe::new, 600, ModItems.GARLIC, ModItems.GRILLED_GARLIC, 0.35F);
				shapeless(RecipeCategory.FOOD, ModItems.GARLIC_BREAD).requires(Items.BREAD).requires(ModItems.GRILLED_GARLIC).unlockedBy("has_grilled_garlic", has(ModItems.GRILLED_GARLIC)).save(output);
				shapeless(RecipeCategory.COMBAT, ModItems.VAMPIRE_HUNTER_CONTRACT).requires(ModItems.HUNTER_CONTRACT).requires(ModItems.GARLIC).unlockedBy("has_contract", has(ModItems.HUNTER_CONTRACT)).save(output);
				shapeless(RecipeCategory.COMBAT, ModItems.WEREWOLF_HUNTER_CONTRACT).requires(ModItems.HUNTER_CONTRACT).requires(ModItems.ACONITE).unlockedBy("has_contract", has(ModItems.HUNTER_CONTRACT)).save(output);

				SpecialRecipeBuilder.special(BloodExtractionRecipe::new).save(output, BLOOD_EXTRACTION);
				SpecialRecipeBuilder.special(FoodPoisoningRecipe::new).save(output, Nycto.id("food_poisoning").toString());
			}

			private void vampireSmithing(Item base, Item result) {
				SmithingTransformRecipeBuilder.smithing(
								of(ModItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE),
								of(base),
								tag(ModItemTags.USABLE_BLOOD_BOTTLES),
								RecipeCategory.COMBAT,
								result
						)
						.unlocks("has_blood_bottle", has(ModItemTags.USABLE_BLOOD_BOTTLES))
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
