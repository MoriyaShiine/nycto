/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockLootSubProvider extends FabricBlockLootSubProvider {
	public ModBlockLootSubProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate() {
		dropSelf(ModBlocks.VAMPIRE_ALTAR);
		dropSelf(ModBlocks.WEREWOLF_ALTAR);
		add(ModBlocks.OAK_COFFIN, createSinglePropConditionTable(ModBlocks.OAK_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(ModBlocks.SPRUCE_COFFIN, createSinglePropConditionTable(ModBlocks.SPRUCE_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(ModBlocks.BIRCH_COFFIN, createSinglePropConditionTable(ModBlocks.BIRCH_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(ModBlocks.JUNGLE_COFFIN, createSinglePropConditionTable(ModBlocks.JUNGLE_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(ModBlocks.ACACIA_COFFIN, createSinglePropConditionTable(ModBlocks.ACACIA_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(ModBlocks.DARK_OAK_COFFIN, createSinglePropConditionTable(ModBlocks.DARK_OAK_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(ModBlocks.PALE_OAK_COFFIN, createSinglePropConditionTable(ModBlocks.PALE_OAK_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(ModBlocks.MANGROVE_COFFIN, createSinglePropConditionTable(ModBlocks.MANGROVE_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(ModBlocks.CHERRY_COFFIN, createSinglePropConditionTable(ModBlocks.CHERRY_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(ModBlocks.BAMBOO_COFFIN, createSinglePropConditionTable(ModBlocks.BAMBOO_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(ModBlocks.CRIMSON_COFFIN, createSinglePropConditionTable(ModBlocks.CRIMSON_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(ModBlocks.WARPED_COFFIN, createSinglePropConditionTable(ModBlocks.WARPED_COFFIN, BedBlock.PART, BedPart.HEAD));
		dropSelf(ModBlocks.GARLIC_WREATH);
		dropSelf(ModBlocks.ACONITE_GARLAND);
		add(ModBlocks.WILD_GARLIC, createShearsDispatchTable(ModBlocks.WILD_GARLIC, applyExplosionDecay(ModBlocks.WILD_GARLIC, LootItem.lootTableItem(ModItems.GARLIC).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))));
		add(ModBlocks.WILD_ACONITE, LootTable.lootTable().pools(List.of(
				applyExplosionCondition(ModItems.WILD_ACONITE, LootPool.lootPool().add(LootItem.lootTableItem(ModItems.WILD_ACONITE))).when(hasShears()).build(),
				applyExplosionCondition(ModItems.ACONITE_SEEDS, LootPool.lootPool().add(LootItem.lootTableItem(ModItems.ACONITE_SEEDS))).when(hasShears().invert()).build(),
				applyExplosionCondition(ModItems.ACONITE, LootPool.lootPool().add(LootItem.lootTableItem(ModItems.ACONITE))).when(hasShears().invert()).build()
		)));
		add(ModBlocks.GARLIC, applyExplosionDecay(ModBlocks.GARLIC, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModItems.GARLIC))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.GARLIC).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))).add(LootItem.lootTableItem(ModItems.GARLIC).apply(ApplyBonusCount.addBonusBinomialDistributionCount(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.5714286F, 2))))));
		add(ModBlocks.ACONITE, createCropDrops(ModBlocks.ACONITE, ModItems.ACONITE, ModItems.ACONITE_SEEDS, LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.ACONITE).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))));
		add(ModBlocks.FIREBOMB, noDrop());
	}
}
