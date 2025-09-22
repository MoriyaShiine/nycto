/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.data.provider;

import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.BedBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Properties;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
	public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		addDrop(ModBlocks.VAMPIRE_ALTAR);
		addDrop(ModBlocks.WEREWOLF_ALTAR);
		addDrop(ModBlocks.OAK_COFFIN, dropsWithProperty(ModBlocks.OAK_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.SPRUCE_COFFIN, dropsWithProperty(ModBlocks.SPRUCE_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.BIRCH_COFFIN, dropsWithProperty(ModBlocks.BIRCH_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.JUNGLE_COFFIN, dropsWithProperty(ModBlocks.JUNGLE_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.ACACIA_COFFIN, dropsWithProperty(ModBlocks.ACACIA_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.DARK_OAK_COFFIN, dropsWithProperty(ModBlocks.DARK_OAK_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.PALE_OAK_COFFIN, dropsWithProperty(ModBlocks.PALE_OAK_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.MANGROVE_COFFIN, dropsWithProperty(ModBlocks.MANGROVE_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.CHERRY_COFFIN, dropsWithProperty(ModBlocks.CHERRY_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.BAMBOO_COFFIN, dropsWithProperty(ModBlocks.BAMBOO_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.CRIMSON_COFFIN, dropsWithProperty(ModBlocks.CRIMSON_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.WARPED_COFFIN, dropsWithProperty(ModBlocks.WARPED_COFFIN, BedBlock.PART, BedPart.HEAD));
		addDrop(ModBlocks.GARLIC_WREATH);
		addDrop(ModBlocks.ACONITE_GARLAND);
		addDrop(ModBlocks.WILD_GARLIC, dropsWithShears(ModBlocks.WILD_GARLIC, applyExplosionDecay(ModBlocks.WILD_GARLIC, ItemEntry.builder(ModItems.GARLIC).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))))));
		addDrop(ModBlocks.WILD_ACONITE, LootTable.builder().pools(List.of(
				addSurvivesExplosionCondition(ModItems.WILD_ACONITE, LootPool.builder().with(ItemEntry.builder(ModItems.WILD_ACONITE))).conditionally(createWithShearsCondition()).build(),
				addSurvivesExplosionCondition(ModItems.ACONITE_SEEDS, LootPool.builder().with(ItemEntry.builder(ModItems.ACONITE_SEEDS))).conditionally(createWithShearsCondition().invert()).build(),
				addSurvivesExplosionCondition(ModItems.ACONITE, LootPool.builder().with(ItemEntry.builder(ModItems.ACONITE))).conditionally(createWithShearsCondition().invert()).build()
		)));
		addDrop(ModBlocks.GARLIC, applyExplosionDecay(ModBlocks.GARLIC, LootTable.builder().pool(LootPool.builder().with(ItemEntry.builder(ModItems.GARLIC))).pool(LootPool.builder().conditionally(BlockStatePropertyLootCondition.builder(ModBlocks.GARLIC).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, 3))).with(ItemEntry.builder(ModItems.GARLIC).apply(ApplyBonusLootFunction.binomialWithBonusCount(registries.getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.5714286F, 2))))));
		addDrop(ModBlocks.ACONITE, cropDrops(ModBlocks.ACONITE, ModItems.ACONITE, ModItems.ACONITE_SEEDS, BlockStatePropertyLootCondition.builder(ModBlocks.ACONITE).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, 3))));
		addDrop(ModBlocks.FIREBOMB, dropsNothing());
	}
}
