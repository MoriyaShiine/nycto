/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.client.renderer.item.properties.conditional.FullDaggerProperty;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.strawberrylib.api.module.SLibDataUtils;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
	private static final TextureSlot BASE = TextureSlot.create("base");
	private static final TextureSlot EXTRA = TextureSlot.create("extra");

	private static final ModelTemplate COFFIN = new ModelTemplate(Optional.of(Nycto.id("block/template_coffin")), Optional.empty(), BASE, TextureSlot.PARTICLE);
	private static final ModelTemplate COFFIN_CLOSED = new ModelTemplate(Optional.of(Nycto.id("block/template_coffin_closed")), Optional.empty(), BASE, TextureSlot.PARTICLE);

	private static final ModelTemplate HALBERD_IN_HAND = new ModelTemplate(Optional.of(Nycto.id("item/template_halberd_in_hand")), Optional.empty(), BASE, EXTRA);

	public ModModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generators) {
		registerCoffin(generators, ModBlocks.OAK_COFFIN, new Material(Nycto.id("block/coffin_oak")), Blocks.OAK_PLANKS, Blocks.OAK_SIGN);
		registerCoffin(generators, ModBlocks.SPRUCE_COFFIN, new Material(Nycto.id("block/coffin_spruce")), Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SIGN);
		registerCoffin(generators, ModBlocks.BIRCH_COFFIN, new Material(Nycto.id("block/coffin_birch")), Blocks.BIRCH_PLANKS, Blocks.BIRCH_SIGN);
		registerCoffin(generators, ModBlocks.JUNGLE_COFFIN, new Material(Nycto.id("block/coffin_jungle")), Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_SIGN);
		registerCoffin(generators, ModBlocks.ACACIA_COFFIN, new Material(Nycto.id("block/coffin_acacia")), Blocks.ACACIA_PLANKS, Blocks.ACACIA_SIGN);
		registerCoffin(generators, ModBlocks.DARK_OAK_COFFIN, new Material(Nycto.id("block/coffin_dark_oak")), Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_SIGN);
		registerCoffin(generators, ModBlocks.PALE_OAK_COFFIN, new Material(Nycto.id("block/coffin_pale_oak")), Blocks.PALE_OAK_PLANKS, Blocks.PALE_OAK_SIGN);
		registerCoffin(generators, ModBlocks.MANGROVE_COFFIN, new Material(Nycto.id("block/coffin_mangrove")), Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_SIGN);
		registerCoffin(generators, ModBlocks.CHERRY_COFFIN, new Material(Nycto.id("block/coffin_cherry")), Blocks.CHERRY_PLANKS, Blocks.CHERRY_SIGN);
		registerCoffin(generators, ModBlocks.BAMBOO_COFFIN, new Material(Nycto.id("block/coffin_bamboo")), Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_SIGN);
		registerCoffin(generators, ModBlocks.CRIMSON_COFFIN, new Material(Nycto.id("block/coffin_crimson")), Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_SIGN);
		registerCoffin(generators, ModBlocks.WARPED_COFFIN, new Material(Nycto.id("block/coffin_warped")), Blocks.WARPED_PLANKS, Blocks.WARPED_SIGN);
		generators.createCrossBlockWithDefaultItem(ModBlocks.WILD_GARLIC, BlockModelGenerators.PlantType.NOT_TINTED);
		generators.createCrossBlockWithDefaultItem(ModBlocks.WILD_ACONITE, BlockModelGenerators.PlantType.NOT_TINTED);
		generators.createCropBlock(ModBlocks.GARLIC, BlockStateProperties.AGE_3, 0, 1, 2, 3);
		SLibDataUtils.createCropCrossBlock(generators, ModBlocks.ACONITE, BlockStateProperties.AGE_3, 0, 1, 2, 3);
		registerFirebomb(generators);
	}

	@Override
	public void generateItemModels(ItemModelGenerators generators) {
		generators.generateFlatItem(ModItems.VAMPIRE_ALTAR, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.WEREWOLF_ALTAR, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.GARLIC_WREATH, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.ACONITE_GARLAND, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_HELMET, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_CHESTPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_LEGGINGS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_BOOTS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_HUNTER_HELMET, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_HUNTER_CHESTPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_HUNTER_LEGGINGS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_HUNTER_BOOTS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.WEREWOLF_HUNTER_HELMET, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.WEREWOLF_HUNTER_CHESTPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.WEREWOLF_HUNTER_LEGGINGS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.WEREWOLF_HUNTER_BOOTS, ModelTemplates.FLAT_ITEM);
		registerWithFullCondition(generators, ModItems.VAMPIRIC_DAGGER);
		registerHalberd(generators, ModItems.HALBERD, new Material(Nycto.id("item/halberd_in_hand")), new Material(Nycto.id("item/empty")));
		registerHalberd(generators, ModItems.GARLIC_COATED_HALBERD, new Material(Nycto.id("item/halberd_in_hand_coated")), new Material(Nycto.id("item/halberd_in_hand_extra_garlic")));
		registerHalberd(generators, ModItems.ACONITE_COATED_HALBERD, new Material(Nycto.id("item/halberd_in_hand_coated")), new Material(Nycto.id("item/halberd_in_hand_extra_aconite")));
		generators.generateFlatItem(ModItems.WOODEN_STAKE, ModelTemplates.FLAT_HANDHELD_ITEM);
		ModelTemplates.CROSSBOW.create(Nycto.id("item/crossbow_wooden_stake"), TextureMapping.layer0(new Material(Nycto.id("item/crossbow_wooden_stake"))), generators.modelOutput);
		generators.generateFlatItem(ModItems.ACONITE_ARROW, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.FIREBOMB, ModelTemplates.FLAT_ITEM);
		registerTintedBottle(generators, ModItems.BLOOD_BOTTLE, 0xFF7F0000);
		generators.generateFlatItem(ModItems.VAMPIRE_BLOOD_BOTTLE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.AMBROSIA_BOTTLE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.GRILLED_GARLIC, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.GARLIC_BREAD, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.ACONITE_SEEDS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.HUNTER_CONTRACT, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_HUNTER_CONTRACT, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.WEREWOLF_HUNTER_CONTRACT, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.VAMPIRE_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.HUNTER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);

		ModelTemplates.FLAT_ITEM.create(Nycto.id("item/garlic_brew"), TextureMapping.layer0(new Material(Nycto.id("item/garlic_brew"))), generators.modelOutput);
		ModelTemplates.FLAT_ITEM.create(Nycto.id("item/splash_garlic_brew"), TextureMapping.layer0(new Material(Nycto.id("item/splash_garlic_brew"))), generators.modelOutput);
		ModelTemplates.FLAT_ITEM.create(Nycto.id("item/lingering_garlic_brew"), TextureMapping.layer0(new Material(Nycto.id("item/lingering_garlic_brew"))), generators.modelOutput);
	}

	public static void registerCoffin(BlockModelGenerators generators, Block block, Material base, Block particle, Block headModel) {
		Material particleId = TextureMapping.getBlockTexture(particle);
		COFFIN.create(block, TextureMapping.singleSlot(BASE, base).put(TextureSlot.PARTICLE, particleId), generators.modelOutput);
		COFFIN_CLOSED.createWithSuffix(block, "_closed", TextureMapping.singleSlot(BASE, base).put(TextureSlot.PARTICLE, particleId), generators.modelOutput);
		generators.blockStateOutput.accept(
				MultiVariantGenerator.dispatch(block)
						.with(PropertyDispatch.initial(BlockStateProperties.BED_PART, BlockStateProperties.OCCUPIED).generate((part, occupied) -> {
							StringBuilder stringBuilder = new StringBuilder();
							if (part == BedPart.HEAD) {
								return BlockModelGenerators.plainVariant(TextureMapping.getBlockTexture(headModel).sprite());
							}
							if (occupied) {
								stringBuilder.append("_closed");
							}
							return BlockModelGenerators.plainVariant(TextureMapping.getBlockTexture(block, stringBuilder.toString()).sprite());
						}))
						.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT)
		);
	}


	public static void registerHalberd(ItemModelGenerators generators, Item item, Material baseId, Material extraId) {
		ItemModel.Unbaked normal = ItemModelUtils.plainModel(generators.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ITEM));
		ItemModel.Unbaked inHand = ItemModelUtils.plainModel(HALBERD_IN_HAND.create(ModelLocationUtils.getModelLocation(item, "_in_hand"), new TextureMapping().put(BASE, baseId).put(EXTRA, extraId), generators.modelOutput));
		generators.itemModelOutput.accept(item, ItemModelGenerators.createFlatModelDispatch(normal, inHand));
	}

	public static void registerWithFullCondition(ItemModelGenerators generators, Item item) {
		ItemModel.Unbaked main = ItemModelUtils.plainModel(generators.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ITEM));
		ItemModel.Unbaked full = ItemModelUtils.plainModel(generators.createFlatItemModel(item, "_full", ModelTemplates.FLAT_HANDHELD_ITEM));
		generators.generateBooleanDispatch(item, new FullDaggerProperty(), full, main);
	}

	public static void registerTintedBottle(ItemModelGenerators generators, Item item, int color) {
		Identifier identifier = generators.generateLayeredItem(item, new Material(ModelLocationUtils.decorateItemModelLocation("potion_overlay")), new Material(ModelLocationUtils.getModelLocation(Items.POTION)));
		generators.itemModelOutput.accept(item, ItemModelUtils.tintedModel(identifier, new Constant(color)));
	}

	private static void registerFirebomb(BlockModelGenerators generators) {
		MultiVariant weightedVariant = generators.createFloorFireModels(ModBlocks.FIREBOMB);
		MultiVariant weightedVariant2 = generators.createSideFireModels(ModBlocks.FIREBOMB);
		generators.blockStateOutput
				.accept(
						MultiPartGenerator.multiPart(ModBlocks.FIREBOMB)
								.with(weightedVariant)
								.with(weightedVariant2)
								.with(weightedVariant2.with(BlockModelGenerators.Y_ROT_90))
								.with(weightedVariant2.with(BlockModelGenerators.Y_ROT_180))
								.with(weightedVariant2.with(BlockModelGenerators.Y_ROT_270))
				);
	}
}
