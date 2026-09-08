package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.item.trading.VillagerTrades;

public class NyctoVillagerTrades {
	public static final ResourceKey<VillagerTrade> CLERIC_5_HUNTER_CONTRACT = createKey("cleric_5_hunter_contract");

	public static ResourceKey<VillagerTrade> createKey(String name) {
		return ResourceKey.create(Registries.VILLAGER_TRADE, Nycto.id(name));
	}

	public static void bootstrap(BootstrapContext<VillagerTrade> registry) {
		VillagerTrades.register(registry, CLERIC_5_HUNTER_CONTRACT, VillagerTrade.builder(new TradeCost(Items.EMERALD, 8), new ItemStackTemplate(NyctoItems.HUNTER_CONTRACT), 8, 1, 0.05F).build());
	}
}
