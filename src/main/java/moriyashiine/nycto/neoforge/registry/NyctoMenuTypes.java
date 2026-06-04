/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.menu.VampireAltarMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NyctoMenuTypes {
	private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, NyctoNeoForge.MOD_ID);

	public static final DeferredHolder<MenuType<?>, MenuType<VampireAltarMenu>> VAMPIRE_ALTAR = MENU_TYPES.register("vampire_altar",
			() -> new MenuType<>(VampireAltarMenu::new, FeatureFlags.VANILLA_SET));

	private NyctoMenuTypes() {
	}

	public static void register(IEventBus bus) {
		MENU_TYPES.register(bus);
	}
}
