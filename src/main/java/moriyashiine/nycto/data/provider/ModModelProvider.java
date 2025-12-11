/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.client.render.item.property.bool.FullDaggerProperty;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.strawberrylib.api.module.SLibDataUtils;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.tint.ConstantTintSource;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
	private static final TextureKey BASE = TextureKey.of("base");
	private static final TextureKey EXTRA = TextureKey.of("extra");

	private static final Model COFFIN = new Model(Optional.of(Nycto.id("block/template_coffin")), Optional.empty(), BASE, TextureKey.PARTICLE);
	private static final Model COFFIN_CLOSED = new Model(Optional.of(Nycto.id("block/template_coffin_closed")), Optional.empty(), BASE, TextureKey.PARTICLE);

	private static final Model HALBERD_IN_HAND = new Model(Optional.of(Nycto.id("item/template_halberd_in_hand")), Optional.empty(), BASE, EXTRA);

	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator generator) {
		registerCoffin(generator, ModBlocks.OAK_COFFIN, Nycto.id("block/coffin_oak"), Blocks.OAK_PLANKS, Blocks.OAK_SIGN);
		registerCoffin(generator, ModBlocks.SPRUCE_COFFIN, Nycto.id("block/coffin_spruce"), Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SIGN);
		registerCoffin(generator, ModBlocks.BIRCH_COFFIN, Nycto.id("block/coffin_birch"), Blocks.BIRCH_PLANKS, Blocks.BIRCH_SIGN);
		registerCoffin(generator, ModBlocks.JUNGLE_COFFIN, Nycto.id("block/coffin_jungle"), Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_SIGN);
		registerCoffin(generator, ModBlocks.ACACIA_COFFIN, Nycto.id("block/coffin_acacia"), Blocks.ACACIA_PLANKS, Blocks.ACACIA_SIGN);
		registerCoffin(generator, ModBlocks.DARK_OAK_COFFIN, Nycto.id("block/coffin_dark_oak"), Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_SIGN);
		registerCoffin(generator, ModBlocks.PALE_OAK_COFFIN, Nycto.id("block/coffin_pale_oak"), Blocks.PALE_OAK_PLANKS, Blocks.PALE_OAK_SIGN);
		registerCoffin(generator, ModBlocks.MANGROVE_COFFIN, Nycto.id("block/coffin_mangrove"), Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_SIGN);
		registerCoffin(generator, ModBlocks.CHERRY_COFFIN, Nycto.id("block/coffin_cherry"), Blocks.CHERRY_PLANKS, Blocks.CHERRY_SIGN);
		registerCoffin(generator, ModBlocks.BAMBOO_COFFIN, Nycto.id("block/coffin_bamboo"), Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_SIGN);
		registerCoffin(generator, ModBlocks.CRIMSON_COFFIN, Nycto.id("block/coffin_crimson"), Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_SIGN);
		registerCoffin(generator, ModBlocks.WARPED_COFFIN, Nycto.id("block/coffin_warped"), Blocks.WARPED_PLANKS, Blocks.WARPED_SIGN);
		generator.registerTintableCross(ModBlocks.WILD_GARLIC, BlockStateModelGenerator.CrossType.NOT_TINTED);
		generator.registerTintableCross(ModBlocks.WILD_ACONITE, BlockStateModelGenerator.CrossType.NOT_TINTED);
		generator.registerCrop(ModBlocks.GARLIC, Properties.AGE_3, 0, 1, 2, 3);
		SLibDataUtils.generateCropCross(generator, ModBlocks.ACONITE, Properties.AGE_3, 0, 1, 2, 3);
		registerFirebomb(generator);
	}

	@Override
	public void generateItemModels(ItemModelGenerator generator) {
		generator.register(ModItems.VAMPIRE_ALTAR, Models.GENERATED);
		generator.register(ModItems.WEREWOLF_ALTAR, Models.GENERATED);
		generator.register(ModItems.GARLIC_WREATH, Models.GENERATED);
		generator.register(ModItems.ACONITE_GARLAND, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
		generator.register(ModItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_HELMET, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_CHESTPLATE, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_LEGGINGS, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_BOOTS, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_HUNTER_HELMET, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_HUNTER_CHESTPLATE, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_HUNTER_LEGGINGS, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_HUNTER_BOOTS, Models.GENERATED);
		generator.register(ModItems.WEREWOLF_HUNTER_HELMET, Models.GENERATED);
		generator.register(ModItems.WEREWOLF_HUNTER_CHESTPLATE, Models.GENERATED);
		generator.register(ModItems.WEREWOLF_HUNTER_LEGGINGS, Models.GENERATED);
		generator.register(ModItems.WEREWOLF_HUNTER_BOOTS, Models.GENERATED);
		registerWithFullCondition(generator, ModItems.VAMPIRIC_DAGGER);
		registerHalberd(generator, ModItems.HALBERD, null, null);
		registerHalberd(generator, ModItems.GARLIC_COATED_HALBERD, Nycto.id("item/halberd_in_hand_coated"), Nycto.id("item/halberd_in_hand_extra_garlic"));
		registerHalberd(generator, ModItems.ACONITE_COATED_HALBERD, Nycto.id("item/halberd_in_hand_coated"), Nycto.id("item/halberd_in_hand_extra_aconite"));
		generator.register(ModItems.WOODEN_STAKE, Models.HANDHELD);
		Models.CROSSBOW.upload(Nycto.id("item/crossbow_wooden_stake"), TextureMap.layer0(Nycto.id("item/crossbow_wooden_stake")), generator.modelCollector);
		generator.register(ModItems.ACONITE_ARROW, Models.GENERATED);
		generator.register(ModItems.FIREBOMB, Models.GENERATED);
		registerTintedBottle(generator, ModItems.BLOOD_BOTTLE, 0xFF7F0000);
		generator.register(ModItems.VAMPIRE_BLOOD_BOTTLE, Models.GENERATED);
		generator.register(ModItems.AMBROSIA_BOTTLE, Models.GENERATED);
		generator.register(ModItems.GRILLED_GARLIC, Models.GENERATED);
		generator.register(ModItems.GARLIC_BREAD, Models.GENERATED);
		generator.register(ModItems.ACONITE_SEEDS, Models.GENERATED);
		generator.register(ModItems.HUNTER_CONTRACT, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_HUNTER_CONTRACT, Models.GENERATED);
		generator.register(ModItems.WEREWOLF_HUNTER_CONTRACT, Models.GENERATED);
		generator.register(ModItems.VAMPIRE_SPAWN_EGG, Models.GENERATED);
		generator.register(ModItems.HUNTER_SPAWN_EGG, Models.GENERATED);

		Models.GENERATED.upload(Nycto.id("item/garlic_brew"), TextureMap.layer0(Nycto.id("item/garlic_brew")), generator.modelCollector);
		Models.GENERATED.upload(Nycto.id("item/splash_garlic_brew"), TextureMap.layer0(Nycto.id("item/splash_garlic_brew")), generator.modelCollector);
		Models.GENERATED.upload(Nycto.id("item/lingering_garlic_brew"), TextureMap.layer0(Nycto.id("item/lingering_garlic_brew")), generator.modelCollector);
	}

	public static void registerCoffin(BlockStateModelGenerator generator, Block block, Identifier base, Block particle, Block headModel) {
		Identifier particleId = TextureMap.getId(particle);
		COFFIN.upload(block, TextureMap.of(BASE, base).put(TextureKey.PARTICLE, particleId), generator.modelCollector);
		COFFIN_CLOSED.upload(block, "_closed", TextureMap.of(BASE, base).put(TextureKey.PARTICLE, particleId), generator.modelCollector);
		generator.blockStateCollector.accept(
				VariantsBlockModelDefinitionCreator.of(block)
						.with(BlockStateVariantMap.models(Properties.BED_PART, Properties.OCCUPIED).generate((part, occupied) -> {
							StringBuilder stringBuilder = new StringBuilder();
							if (part == BedPart.HEAD) {
								return BlockStateModelGenerator.createWeightedVariant(TextureMap.getId(headModel));
							}
							if (occupied) {
								stringBuilder.append("_closed");
							}
							return BlockStateModelGenerator.createWeightedVariant(TextureMap.getSubId(block, stringBuilder.toString()));
						}))
						.apply(BlockStateModelGenerator.SOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS)
		);
	}


	public static void registerHalberd(ItemModelGenerator generator, Item item, @Nullable Identifier baseId, @Nullable Identifier extraId) {
		if (baseId == null) {
			baseId = Nycto.id("item/halberd_in_hand");
		}
		if (extraId == null) {
			extraId = Nycto.id("item/empty");
		}
		ItemModel.Unbaked normal = ItemModels.basic(generator.upload(item, Models.HANDHELD));
		ItemModel.Unbaked inHand = ItemModels.basic(HALBERD_IN_HAND.upload(ModelIds.getItemSubModelId(item, "_in_hand"), new TextureMap().put(BASE, baseId).put(EXTRA, extraId), generator.modelCollector));
		generator.output.accept(item, ItemModelGenerator.createModelWithInHandVariant(normal, inHand));
	}

	public static void registerWithFullCondition(ItemModelGenerator generator, Item item) {
		ItemModel.Unbaked main = ItemModels.basic(generator.upload(item, Models.HANDHELD));
		ItemModel.Unbaked full = ItemModels.basic(generator.registerSubModel(item, "_full", Models.HANDHELD));
		generator.registerCondition(item, new FullDaggerProperty(), full, main);
	}

	public static void registerTintedBottle(ItemModelGenerator generator, Item item, int color) {
		Identifier identifier = generator.uploadTwoLayers(item, ModelIds.getMinecraftNamespacedItem("potion_overlay"), ModelIds.getItemModelId(Items.POTION));
		generator.output.accept(item, ItemModels.tinted(identifier, new ConstantTintSource(color)));
	}

	private static void registerFirebomb(BlockStateModelGenerator generator) {
		WeightedVariant weightedVariant = generator.getFireFloorModels(ModBlocks.FIREBOMB);
		WeightedVariant weightedVariant2 = generator.getFireSideModels(ModBlocks.FIREBOMB);
		generator.blockStateCollector
				.accept(
						MultipartBlockModelDefinitionCreator.create(ModBlocks.FIREBOMB)
								.with(weightedVariant)
								.with(weightedVariant2)
								.with(weightedVariant2.apply(BlockStateModelGenerator.ROTATE_Y_90))
								.with(weightedVariant2.apply(BlockStateModelGenerator.ROTATE_Y_180))
								.with(weightedVariant2.apply(BlockStateModelGenerator.ROTATE_Y_270))
				);
	}
}
