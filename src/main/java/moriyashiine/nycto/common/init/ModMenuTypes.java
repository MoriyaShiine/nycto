/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.world.inventory.VampireAltarMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
	public static final MenuType<VampireAltarMenu> VAMPIRE_ALTAR = registerMenuType("vampire_altar", new MenuType<>(VampireAltarMenu::new, FeatureFlags.VANILLA_SET));

	private static <T extends MenuType<?>> T registerMenuType(String name, T menuType) {
		ModRegistration.register(ModRegistration.MENU_TYPES, name, menuType);
		return menuType;
	}

	public static void init() {
	}
}
