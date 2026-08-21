package moriyashiine.nycto.datagen.provider;

import moriyashiine.nycto.common.init.NyctoEntityTypes;
import moriyashiine.nycto.common.init.NyctoItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

public class NyctoEntityLootSubProvider extends FabricEntityLootSubProvider {
	public NyctoEntityLootSubProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate() {
		add(NyctoEntityTypes.VAMPIRE, LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(NyctoItems.VAMPIRE_BLOOD_BOTTLE))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));
	}
}
