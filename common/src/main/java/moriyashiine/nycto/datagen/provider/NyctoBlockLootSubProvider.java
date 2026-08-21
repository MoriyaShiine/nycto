package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.init.NyctoBlocks;
import moriyashiine.nycto.common.init.NyctoItems;
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

public class NyctoBlockLootSubProvider extends FabricBlockLootSubProvider {
	public NyctoBlockLootSubProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate() {
		dropSelf(NyctoBlocks.VAMPIRE_ALTAR);
		dropSelf(NyctoBlocks.WEREWOLF_ALTAR);
		add(NyctoBlocks.OAK_COFFIN, createSinglePropConditionTable(NyctoBlocks.OAK_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(NyctoBlocks.SPRUCE_COFFIN, createSinglePropConditionTable(NyctoBlocks.SPRUCE_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(NyctoBlocks.BIRCH_COFFIN, createSinglePropConditionTable(NyctoBlocks.BIRCH_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(NyctoBlocks.JUNGLE_COFFIN, createSinglePropConditionTable(NyctoBlocks.JUNGLE_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(NyctoBlocks.ACACIA_COFFIN, createSinglePropConditionTable(NyctoBlocks.ACACIA_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(NyctoBlocks.DARK_OAK_COFFIN, createSinglePropConditionTable(NyctoBlocks.DARK_OAK_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(NyctoBlocks.PALE_OAK_COFFIN, createSinglePropConditionTable(NyctoBlocks.PALE_OAK_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(NyctoBlocks.MANGROVE_COFFIN, createSinglePropConditionTable(NyctoBlocks.MANGROVE_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(NyctoBlocks.CHERRY_COFFIN, createSinglePropConditionTable(NyctoBlocks.CHERRY_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(NyctoBlocks.BAMBOO_COFFIN, createSinglePropConditionTable(NyctoBlocks.BAMBOO_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(NyctoBlocks.CRIMSON_COFFIN, createSinglePropConditionTable(NyctoBlocks.CRIMSON_COFFIN, BedBlock.PART, BedPart.HEAD));
		add(NyctoBlocks.WARPED_COFFIN, createSinglePropConditionTable(NyctoBlocks.WARPED_COFFIN, BedBlock.PART, BedPart.HEAD));
		dropSelf(NyctoBlocks.BLOOD_FOUNTAIN);
		dropSelf(NyctoBlocks.GARLIC_WREATH);
		dropSelf(NyctoBlocks.ACONITE_GARLAND);
		add(NyctoBlocks.WILD_GARLIC, createShearsDispatchTable(NyctoBlocks.WILD_GARLIC, applyExplosionDecay(NyctoBlocks.WILD_GARLIC, LootItem.lootTableItem(NyctoItems.GARLIC).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))));
		add(NyctoBlocks.WILD_ACONITE, LootTable.lootTable().pools(List.of(
				applyExplosionCondition(NyctoItems.WILD_ACONITE, LootPool.lootPool().add(LootItem.lootTableItem(NyctoItems.WILD_ACONITE))).when(hasShears()).build(),
				applyExplosionCondition(NyctoItems.ACONITE_SEEDS, LootPool.lootPool().add(LootItem.lootTableItem(NyctoItems.ACONITE_SEEDS))).when(hasShears().invert()).build(),
				applyExplosionCondition(NyctoItems.ACONITE, LootPool.lootPool().add(LootItem.lootTableItem(NyctoItems.ACONITE))).when(hasShears().invert()).build()
		)));
		add(NyctoBlocks.GARLIC, applyExplosionDecay(NyctoBlocks.GARLIC, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(NyctoItems.GARLIC))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(NyctoBlocks.GARLIC).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))).add(LootItem.lootTableItem(NyctoItems.GARLIC).apply(ApplyBonusCount.addBonusBinomialDistributionCount(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.5714286F, 2))))));
		add(NyctoBlocks.ACONITE, createCropDrops(NyctoBlocks.ACONITE, NyctoItems.ACONITE, NyctoItems.ACONITE_SEEDS, LootItemBlockStatePropertyCondition.hasBlockStateProperties(NyctoBlocks.ACONITE).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))));
		dropSelf(NyctoBlocks.WOODEN_STAKE);
		add(NyctoBlocks.FIREBOMB, noDrop());
	}
}
