/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.internal;

import moriyashiine.nycto.common.init.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class GenerateLootEvent implements LootTableEvents.Modify {
	@Override
	public void modifyLootTable(ResourceKey<LootTable> resourceKey, LootTable.Builder builder, LootTableSource lootTableSource, HolderLookup.Provider provider) {
		if (resourceKey == BuiltInLootTables.ANCIENT_CITY || resourceKey == BuiltInLootTables.DESERT_PYRAMID || resourceKey == BuiltInLootTables.JUNGLE_TEMPLE || resourceKey == BuiltInLootTables.WOODLAND_MANSION) {
			builder.withPool(LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1))
					.when(LootItemRandomChanceCondition.randomChance(0.0625F))
					.add(
							LootItem.lootTableItem(ModItems.AMBROSIA_BOTTLE)
									.setWeight(1)
					)
			);
		}
	}
}
