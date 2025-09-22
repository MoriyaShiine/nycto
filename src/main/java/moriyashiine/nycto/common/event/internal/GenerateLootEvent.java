/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.internal;

import moriyashiine.nycto.common.init.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

public class GenerateLootEvent implements LootTableEvents.Modify {
	@Override
	public void modifyLootTable(RegistryKey<LootTable> registryKey, LootTable.Builder builder, LootTableSource lootTableSource, RegistryWrapper.WrapperLookup wrapperLookup) {
		if (registryKey == LootTables.ANCIENT_CITY_CHEST || registryKey == LootTables.DESERT_PYRAMID_CHEST || registryKey == LootTables.JUNGLE_TEMPLE_CHEST || registryKey == LootTables.WOODLAND_MANSION_CHEST) {
			builder.pool(LootPool.builder()
					.rolls(ConstantLootNumberProvider.create(1))
					.conditionally(RandomChanceLootCondition.builder(0.0625F))
					.with(
							ItemEntry.builder(ModItems.AMBROSIA_BOTTLE)
									.weight(1)
					)
			);
		}
	}
}
