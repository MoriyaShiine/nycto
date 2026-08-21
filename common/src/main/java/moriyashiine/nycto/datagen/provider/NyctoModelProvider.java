package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.client.renderer.item.properties.conditional.FullDaggerProperty;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.NyctoBlocks;
import moriyashiine.nycto.common.init.NyctoItems;
import moriyashiine.nycto.common.world.level.block.BloodFountainBlock;
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
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
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

public class NyctoModelProvider extends FabricModelProvider {
	private static final TextureSlot BASE = TextureSlot.create("base");
	private static final TextureSlot EXTRA = TextureSlot.create("extra");

	private static final ModelTemplate COFFIN = new ModelTemplate(Optional.of(Nycto.id("block/template_coffin")), Optional.empty(), BASE, TextureSlot.PARTICLE);
	private static final ModelTemplate COFFIN_CLOSED = new ModelTemplate(Optional.of(Nycto.id("block/template_coffin_closed")), Optional.empty(), BASE, TextureSlot.PARTICLE);

	private static final ModelTemplate HALBERD_IN_HAND = new ModelTemplate(Optional.of(Nycto.id("item/template_halberd_in_hand")), Optional.empty(), BASE, EXTRA);

	public NyctoModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generators) {
		createWithExistingModel(generators, NyctoBlocks.VAMPIRE_ALTAR, BlockModelGenerators.ROTATION_HORIZONTAL_FACING);
		createWithExistingModel(generators, NyctoBlocks.WEREWOLF_ALTAR, BlockModelGenerators.ROTATION_HORIZONTAL_FACING);
		createCoffin(generators, NyctoBlocks.OAK_COFFIN, new Material(Nycto.id("block/coffin_oak")), Blocks.OAK_PLANKS);
		createCoffin(generators, NyctoBlocks.SPRUCE_COFFIN, new Material(Nycto.id("block/coffin_spruce")), Blocks.SPRUCE_PLANKS);
		createCoffin(generators, NyctoBlocks.BIRCH_COFFIN, new Material(Nycto.id("block/coffin_birch")), Blocks.BIRCH_PLANKS);
		createCoffin(generators, NyctoBlocks.JUNGLE_COFFIN, new Material(Nycto.id("block/coffin_jungle")), Blocks.JUNGLE_PLANKS);
		createCoffin(generators, NyctoBlocks.ACACIA_COFFIN, new Material(Nycto.id("block/coffin_acacia")), Blocks.ACACIA_PLANKS);
		createCoffin(generators, NyctoBlocks.DARK_OAK_COFFIN, new Material(Nycto.id("block/coffin_dark_oak")), Blocks.DARK_OAK_PLANKS);
		createCoffin(generators, NyctoBlocks.PALE_OAK_COFFIN, new Material(Nycto.id("block/coffin_pale_oak")), Blocks.PALE_OAK_PLANKS);
		createCoffin(generators, NyctoBlocks.MANGROVE_COFFIN, new Material(Nycto.id("block/coffin_mangrove")), Blocks.MANGROVE_PLANKS);
		createCoffin(generators, NyctoBlocks.CHERRY_COFFIN, new Material(Nycto.id("block/coffin_cherry")), Blocks.CHERRY_PLANKS);
		createCoffin(generators, NyctoBlocks.BAMBOO_COFFIN, new Material(Nycto.id("block/coffin_bamboo")), Blocks.BAMBOO_PLANKS);
		createCoffin(generators, NyctoBlocks.CRIMSON_COFFIN, new Material(Nycto.id("block/coffin_crimson")), Blocks.CRIMSON_PLANKS);
		createCoffin(generators, NyctoBlocks.WARPED_COFFIN, new Material(Nycto.id("block/coffin_warped")), Blocks.WARPED_PLANKS);
		createBloodFountain(generators);
		createGarlicWreath(generators);
		createAconiteGarland(generators);
		generators.createCrossBlockWithDefaultItem(NyctoBlocks.WILD_GARLIC, BlockModelGenerators.PlantType.NOT_TINTED);
		generators.createCrossBlockWithDefaultItem(NyctoBlocks.WILD_ACONITE, BlockModelGenerators.PlantType.NOT_TINTED);
		generators.createCropBlock(NyctoBlocks.GARLIC, BlockStateProperties.AGE_3, 0, 1, 2, 3);
		SLibDataUtils.createCropCrossBlock(generators, NyctoBlocks.ACONITE, BlockStateProperties.AGE_3, 0, 1, 2, 3);
		createWithExistingModel(generators, NyctoBlocks.WOODEN_STAKE, BlockModelGenerators.ROTATION_HORIZONTAL_FACING);
		createFirebomb(generators);
	}

	@Override
	public void generateItemModels(ItemModelGenerators generators) {
		generators.generateFlatItem(NyctoItems.VAMPIRE_ALTAR, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.WEREWOLF_ALTAR, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.GARLIC_WREATH, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.ACONITE_GARLAND, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_UPGRADE_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_HUNTER_UPGRADE_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.WEREWOLF_HUNTER_UPGRADE_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_HELMET, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_CHESTPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_LEGGINGS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_BOOTS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_HUNTER_HELMET, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_HUNTER_CHESTPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_HUNTER_LEGGINGS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_HUNTER_BOOTS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_HUNTER_WOLF_ARMOR, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.WEREWOLF_HUNTER_HELMET, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.WEREWOLF_HUNTER_CHESTPLATE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.WEREWOLF_HUNTER_LEGGINGS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.WEREWOLF_HUNTER_BOOTS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.WEREWOLF_HUNTER_WOLF_ARMOR, ModelTemplates.FLAT_ITEM);
		generateWithFull(generators, NyctoItems.VAMPIRIC_DAGGER);
		generateHalberd(generators, NyctoItems.HALBERD, new Material(Nycto.id("item/halberd_in_hand")), new Material(Nycto.id("item/empty")));
		generateHalberd(generators, NyctoItems.GARLIC_COATED_HALBERD, new Material(Nycto.id("item/halberd_in_hand_coated")), new Material(Nycto.id("item/halberd_in_hand_extra_garlic")));
		generateHalberd(generators, NyctoItems.ACONITE_COATED_HALBERD, new Material(Nycto.id("item/halberd_in_hand_coated")), new Material(Nycto.id("item/halberd_in_hand_extra_aconite")));
		generators.generateFlatItem(NyctoItems.WOODEN_STAKE, ModelTemplates.FLAT_HANDHELD_ITEM);
		ModelTemplates.CROSSBOW.create(Nycto.id("item/crossbow_wooden_stake"), TextureMapping.layer0(new Material(Nycto.id("item/crossbow_wooden_stake"))), generators.modelOutput);
		generators.generateFlatItem(NyctoItems.FIREBOMB, ModelTemplates.FLAT_ITEM);
		generateTintedBottle(generators, NyctoItems.BLOOD_BOTTLE, 0xFF7F0000);
		generators.generateFlatItem(NyctoItems.VAMPIRE_BLOOD_BOTTLE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.AMBROSIA_BOTTLE, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.GRILLED_GARLIC, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.GARLIC_BREAD, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.ACONITE_SEEDS, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.HUNTER_CONTRACT, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_HUNTER_CONTRACT, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.WEREWOLF_HUNTER_CONTRACT, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_BAT_BANNER_PATTERN, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.WOLF_SKULL_BANNER_PATTERN, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.HUNTERS_MARK_BANNER_PATTERN, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.VAMPIRE_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(NyctoItems.HUNTER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);

		ModelTemplates.FLAT_ITEM.create(Nycto.id("item/garlic_brew"), TextureMapping.layer0(new Material(Nycto.id("item/garlic_brew"))), generators.modelOutput);
		ModelTemplates.FLAT_ITEM.create(Nycto.id("item/splash_garlic_brew"), TextureMapping.layer0(new Material(Nycto.id("item/splash_garlic_brew"))), generators.modelOutput);
		ModelTemplates.FLAT_ITEM.create(Nycto.id("item/lingering_garlic_brew"), TextureMapping.layer0(new Material(Nycto.id("item/lingering_garlic_brew"))), generators.modelOutput);
	}

	public static void createCoffin(BlockModelGenerators generators, Block block, Material base, Block particle) {
		Material particleId = TextureMapping.getBlockTexture(particle);
		COFFIN.create(block, TextureMapping.singleSlot(BASE, base).put(TextureSlot.PARTICLE, particleId), generators.modelOutput);
		COFFIN_CLOSED.createWithSuffix(block, "_closed", TextureMapping.singleSlot(BASE, base).put(TextureSlot.PARTICLE, particleId), generators.modelOutput);
		generators.blockStateOutput.accept(
				MultiVariantGenerator.dispatch(block)
						.with(PropertyDispatch.initial(BlockStateProperties.BED_PART, BlockStateProperties.OCCUPIED).generate((part, occupied) -> {
							StringBuilder stringBuilder = new StringBuilder();
							if (part == BedPart.HEAD) {
								return BlockModelGenerators.plainVariant(TextureMapping.getBlockTexture(particle).sprite());
							}
							if (occupied) {
								stringBuilder.append("_closed");
							}
							return BlockModelGenerators.plainVariant(TextureMapping.getBlockTexture(block, stringBuilder.toString()).sprite());
						}))
						.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT)
		);
	}


	public static void generateHalberd(ItemModelGenerators generators, Item item, Material baseId, Material extraId) {
		ItemModel.Unbaked normal = ItemModelUtils.plainModel(generators.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ITEM));
		ItemModel.Unbaked inHand = ItemModelUtils.plainModel(HALBERD_IN_HAND.create(ModelLocationUtils.getModelLocation(item, "_in_hand"), new TextureMapping().put(BASE, baseId).put(EXTRA, extraId), generators.modelOutput));
		generators.itemModelOutput.accept(item, ItemModelGenerators.createFlatModelDispatch(normal, inHand));
	}

	public static void generateWithFull(ItemModelGenerators generators, Item item) {
		ItemModel.Unbaked main = ItemModelUtils.plainModel(generators.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ITEM));
		ItemModel.Unbaked full = ItemModelUtils.plainModel(generators.createFlatItemModel(item, "_full", ModelTemplates.FLAT_HANDHELD_ITEM));
		generators.generateBooleanDispatch(item, new FullDaggerProperty(), full, main);
	}

	public static void generateTintedBottle(ItemModelGenerators generators, Item item, int color) {
		Identifier identifier = generators.generateLayeredItem(item, new Material(ModelLocationUtils.decorateItemModelLocation("potion_overlay")), new Material(ModelLocationUtils.getModelLocation(Items.POTION)));
		generators.itemModelOutput.accept(item, ItemModelUtils.tintedModel(identifier, new Constant(color)));
	}

	@SafeVarargs
	public static void createWithExistingModel(BlockModelGenerators generators, Block block, PropertyDispatch<VariantMutator>... mutators) {
		MultiVariant model = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block));
		MultiVariantGenerator dispatch = MultiVariantGenerator.dispatch(block, model);
		for (PropertyDispatch<VariantMutator> mutator : mutators) {
			dispatch = dispatch.with(mutator);
		}
		generators.blockStateOutput.accept(dispatch);
	}

	private static void createBloodFountain(BlockModelGenerators generators) {
		MultiVariantGenerator.Empty dispatch = MultiVariantGenerator.dispatch(NyctoBlocks.BLOOD_FOUNTAIN);
		MultiVariant empty = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(NyctoBlocks.BLOOD_FOUNTAIN));
		MultiVariant empty_locked = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(NyctoBlocks.BLOOD_FOUNTAIN, "_locked"));
		MultiVariant blood = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(NyctoBlocks.BLOOD_FOUNTAIN, "_blood"));
		MultiVariant blood_locked = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(NyctoBlocks.BLOOD_FOUNTAIN, "_blood_locked"));
		MultiVariant ambrosia = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(NyctoBlocks.BLOOD_FOUNTAIN, "_ambrosia"));
		MultiVariant ambrosia_locked = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(NyctoBlocks.BLOOD_FOUNTAIN, "_ambrosia_locked"));
		generators.blockStateOutput.accept(dispatch
				.with(PropertyDispatch.initial(BlockStateProperties.LOCKED, BloodFountainBlock.FILL_STATE)
						.select(false, BloodFountainBlock.FillState.EMPTY, empty)
						.select(true, BloodFountainBlock.FillState.EMPTY, empty_locked)
						.select(false, BloodFountainBlock.FillState.BLOOD, blood)
						.select(true, BloodFountainBlock.FillState.BLOOD, blood_locked)
						.select(false, BloodFountainBlock.FillState.AMBROSIA, ambrosia)
						.select(true, BloodFountainBlock.FillState.AMBROSIA, ambrosia_locked))
				.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
	}

	private static void createGarlicWreath(BlockModelGenerators generators) {
		MultiVariantGenerator.Empty dispatch = MultiVariantGenerator.dispatch(NyctoBlocks.GARLIC_WREATH);
		MultiVariant side = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(NyctoBlocks.GARLIC_WREATH, "_side"));
		MultiVariant down = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(NyctoBlocks.GARLIC_WREATH, "_down"));
		generators.blockStateOutput.accept(dispatch.with(BlockModelGenerators.createBooleanModelDispatch(BlockStateProperties.DOWN, down, side)).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
	}

	private static void createAconiteGarland(BlockModelGenerators generators) {
		MultiVariantGenerator.Empty dispatch = MultiVariantGenerator.dispatch(NyctoBlocks.ACONITE_GARLAND);
		MultiVariant side = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(NyctoBlocks.ACONITE_GARLAND, "_side"));
		MultiVariant hanging = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(NyctoBlocks.ACONITE_GARLAND, "_hanging"));
		generators.blockStateOutput.accept(dispatch.with(BlockModelGenerators.createBooleanModelDispatch(BlockStateProperties.HANGING, hanging, side)).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
	}

	private static void createFirebomb(BlockModelGenerators generators) {
		MultiVariant normal = generators.createFloorFireModels(NyctoBlocks.FIREBOMB);
		MultiVariant variant = generators.createSideFireModels(NyctoBlocks.FIREBOMB);
		generators.blockStateOutput
				.accept(
						MultiPartGenerator.multiPart(NyctoBlocks.FIREBOMB)
								.with(normal)
								.with(variant)
								.with(variant.with(BlockModelGenerators.Y_ROT_90))
								.with(variant.with(BlockModelGenerators.Y_ROT_180))
								.with(variant.with(BlockModelGenerators.Y_ROT_270))
				);
	}
}
