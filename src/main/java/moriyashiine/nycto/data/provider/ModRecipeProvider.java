/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
	public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
		return new RecipeGenerator(wrapperLookup, recipeExporter) {
			@Override
			public void generate() {
				offerStonecuttingRecipe(RecipeCategory.DECORATIONS, Items.SKELETON_SKULL, Items.BONE_BLOCK);
				createShaped(RecipeCategory.DECORATIONS, ModItems.VAMPIRE_ALTAR).input('C', ItemTags.CANDLES).input('W', ItemTags.PLANKS).input('B', ModItemTags.USABLE_BLOOD_BOTTLES).input('I', ConventionalItemTags.COPPER_INGOTS).pattern("C C").pattern("WBW").pattern("IWI").criterion("has_blood_bottle", conditionsFromTag(ModItemTags.USABLE_BLOOD_BOTTLES)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.WEREWOLF_ALTAR).input('S', Items.SKELETON_SKULL).input('B', Items.BONE).input('E', Items.ENDER_PEARL).input('C', ItemTags.CANDLES).input('I', ConventionalItemTags.COPPER_INGOTS).pattern(" S ").pattern("BEB").pattern("CIC").criterion("has_skeleton_skull", conditionsFromItem(Items.SKELETON_SKULL)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.OAK_COFFIN).input('P', Items.OAK_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.SPRUCE_COFFIN).input('P', Items.SPRUCE_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.BIRCH_COFFIN).input('P', Items.BIRCH_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.JUNGLE_COFFIN).input('P', Items.JUNGLE_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.ACACIA_COFFIN).input('P', Items.ACACIA_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.DARK_OAK_COFFIN).input('P', Items.DARK_OAK_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.PALE_OAK_COFFIN).input('P', Items.PALE_OAK_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.MANGROVE_COFFIN).input('P', Items.MANGROVE_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.CHERRY_COFFIN).input('P', Items.CHERRY_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.BAMBOO_COFFIN).input('P', Items.BAMBOO_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.CRIMSON_COFFIN).input('P', Items.CRIMSON_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.WARPED_COFFIN).input('P', Items.WARPED_PLANKS).input('B', ItemTags.BEDS).pattern("PPP").pattern("PBP").pattern("PPP").criterion("has_bed", conditionsFromTag(ItemTags.BEDS)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.GARLIC_WREATH).input('S', Items.STRING).input('A', Items.ALLIUM).input('G', ModItems.GARLIC).pattern("SAS").pattern("GSG").pattern(" G ").criterion("has_garlic", conditionsFromItem(ModItems.GARLIC)).offerTo(exporter);
				createShaped(RecipeCategory.DECORATIONS, ModItems.ACONITE_GARLAND).input('S', Items.STRING).input('C', ConventionalItemTags.COCOA_BEAN_CROPS).input('A', ModItems.ACONITE).pattern("SCS").pattern("ASA").pattern(" A ").criterion("has_aconite", conditionsFromItem(ModItems.ACONITE)).offerTo(exporter);
				createShaped(RecipeCategory.COMBAT, ModItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE).input('I', ConventionalItemTags.IRON_INGOTS).input('B', ModItemTags.USABLE_BLOOD_BOTTLES).input('D', ConventionalItemTags.DIAMOND_GEMS).pattern("III").pattern("IBI").pattern("IDI").criterion("has_blood_bottle", conditionsFromTag(ModItemTags.USABLE_BLOOD_BOTTLES)).offerTo(exporter);
				createShaped(RecipeCategory.COMBAT, ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE).input('I', ConventionalItemTags.IRON_INGOTS).input('G', ModItems.GARLIC_WREATH).input('D', ConventionalItemTags.DIAMOND_GEMS).pattern("III").pattern("IGI").pattern("IDI").criterion("has_gold_ingot", conditionsFromTag(ConventionalItemTags.GOLD_INGOTS)).offerTo(exporter);
				createShaped(RecipeCategory.COMBAT, ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE).input('I', ConventionalItemTags.IRON_INGOTS).input('A', ModItems.ACONITE_GARLAND).input('D', ConventionalItemTags.DIAMOND_GEMS).pattern("III").pattern("IAI").pattern("IDI").criterion("has_gold_ingot", conditionsFromTag(ConventionalItemTags.GOLD_INGOTS)).offerTo(exporter);
				offerVampireUpgradeRecipe(Items.LEATHER_HELMET, ModItems.VAMPIRE_HELMET);
				offerVampireUpgradeRecipe(Items.LEATHER_CHESTPLATE, ModItems.VAMPIRE_CHESTPLATE);
				offerVampireUpgradeRecipe(Items.LEATHER_LEGGINGS, ModItems.VAMPIRE_LEGGINGS);
				offerVampireUpgradeRecipe(Items.LEATHER_BOOTS, ModItems.VAMPIRE_BOOTS);
				offerHunterUpgradeRecipe(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_HELMET, ModItems.VAMPIRE_HUNTER_HELMET);
				offerHunterUpgradeRecipe(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_CHESTPLATE, ModItems.VAMPIRE_HUNTER_CHESTPLATE);
				offerHunterUpgradeRecipe(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_LEGGINGS, ModItems.VAMPIRE_HUNTER_LEGGINGS);
				offerHunterUpgradeRecipe(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_BOOTS, ModItems.VAMPIRE_HUNTER_BOOTS);
				offerHunterUpgradeRecipe(ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_HELMET, ModItems.WEREWOLF_HUNTER_HELMET);
				offerHunterUpgradeRecipe(ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_CHESTPLATE, ModItems.WEREWOLF_HUNTER_CHESTPLATE);
				offerHunterUpgradeRecipe(ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_LEGGINGS, ModItems.WEREWOLF_HUNTER_LEGGINGS);
				offerHunterUpgradeRecipe(ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Items.LEATHER_BOOTS, ModItems.WEREWOLF_HUNTER_BOOTS);
				createShaped(RecipeCategory.COMBAT, ModItems.VAMPIRIC_DAGGER).input('I', ConventionalItemTags.IRON_INGOTS).input('F', Items.FLINT).input('B', Items.GLASS_BOTTLE).pattern("  I").pattern(" F ").pattern("B  ").criterion("has_iron_ingot", conditionsFromTag(ConventionalItemTags.IRON_INGOTS)).offerTo(exporter);
				createShaped(RecipeCategory.COMBAT, ModItems.HALBERD).input('G', ConventionalItemTags.GOLD_INGOTS).input('I', ConventionalItemTags.IRON_INGOTS).input('A', Items.IRON_AXE).input('S', Items.STICK).pattern(" GI").pattern(" AI").pattern("S  ").criterion("has_iron_ingot", conditionsFromTag(ConventionalItemTags.IRON_INGOTS)).offerTo(exporter);
				createShapeless(RecipeCategory.COMBAT, ModItems.GARLIC_COATED_HALBERD).input(ModItems.HALBERD).input(ModItems.GARLIC_WREATH).criterion("has_halberd", conditionsFromItem(ModItems.HALBERD)).offerTo(exporter);
				createShapeless(RecipeCategory.COMBAT, ModItems.ACONITE_COATED_HALBERD).input(ModItems.HALBERD).input(ModItems.ACONITE_GARLAND).criterion("has_halberd", conditionsFromItem(ModItems.HALBERD)).offerTo(exporter);
				createShaped(RecipeCategory.COMBAT, ModItems.WOODEN_STAKE, 4).input('A', Items.ARROW).input('L', ItemTags.LOGS).pattern(" A ").pattern("ALA").pattern(" A ").criterion("has_log", conditionsFromTag(ItemTags.LOGS)).offerTo(exporter);
				createShaped(RecipeCategory.COMBAT, ModItems.ACONITE_ARROW, 4).input('A', Items.ARROW).input('P', ModItems.ACONITE).pattern(" A ").pattern("APA").pattern(" A ").criterion("has_aconite", conditionsFromItem(ModItems.ACONITE)).offerTo(exporter);
				createShapeless(RecipeCategory.COMBAT, ModItems.FIREBOMB).input(Items.GLASS_BOTTLE).input(Items.BLAZE_POWDER).input(ConventionalItemTags.GUNPOWDERS).criterion("has_blaze_powder", conditionsFromItem(Items.BLAZE_POWDER)).offerTo(exporter);
				CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(ModItems.GARLIC), RecipeCategory.FOOD, ModItems.GRILLED_GARLIC, 0.35F, 200).criterion("has_garlic", conditionsFromItem(ModItems.GARLIC)).offerTo(exporter);
				offerFoodCookingRecipe("smoking", RecipeSerializer.SMOKING, SmokingRecipe::new, 100, ModItems.GARLIC, ModItems.GRILLED_GARLIC, 0.35F);
				offerFoodCookingRecipe("campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 600, ModItems.GARLIC, ModItems.GRILLED_GARLIC, 0.35F);
				createShapeless(RecipeCategory.FOOD, ModItems.GARLIC_BREAD).input(Items.BREAD).input(ModItems.GRILLED_GARLIC).criterion("has_grilled_garlic", conditionsFromItem(ModItems.GRILLED_GARLIC)).offerTo(exporter);
				createShapeless(RecipeCategory.COMBAT, ModItems.VAMPIRE_HUNTER_CONTRACT).input(ModItems.HUNTER_CONTRACT).input(ModItems.GARLIC).criterion("has_contract", conditionsFromItem(ModItems.HUNTER_CONTRACT)).offerTo(exporter);
				createShapeless(RecipeCategory.COMBAT, ModItems.WEREWOLF_HUNTER_CONTRACT).input(ModItems.HUNTER_CONTRACT).input(ModItems.ACONITE).criterion("has_contract", conditionsFromItem(ModItems.HUNTER_CONTRACT)).offerTo(exporter);
			}

			private void offerVampireUpgradeRecipe(Item input, Item result) {
				SmithingTransformRecipeJsonBuilder.create(
								Ingredient.ofItem(ModItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.ofItem(input),
								ingredientFromTag(ModItemTags.USABLE_BLOOD_BOTTLES),
								RecipeCategory.COMBAT,
								result
						)
						.criterion("has_blood_bottle", conditionsFromTag(ModItemTags.USABLE_BLOOD_BOTTLES))
						.offerTo(exporter, RecipeGenerator.getItemPath(result) + "_smithing");
			}

			private void offerHunterUpgradeRecipe(Item template, Item input, Item result) {
				SmithingTransformRecipeJsonBuilder.create(
								Ingredient.ofItem(template),
								Ingredient.ofItem(input),
								ingredientFromTag(ConventionalItemTags.GOLD_INGOTS),
								RecipeCategory.COMBAT,
								result
						)
						.criterion("has_gold_ingot", conditionsFromTag(ConventionalItemTags.GOLD_INGOTS))
						.offerTo(exporter, RecipeGenerator.getItemPath(result) + "_smithing");
			}
		};
	}

	@Override
	public String getName() {
		return Nycto.MOD_ID + "_recipes";
	}
}
