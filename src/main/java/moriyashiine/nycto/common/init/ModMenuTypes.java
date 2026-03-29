/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.world.inventory.VampireAltarMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerMenuType;

public class ModMenuTypes {
	public static final MenuType<VampireAltarMenu> VAMPIRE_ALTAR = registerMenuType("vampire_altar", new MenuType<>(VampireAltarMenu::new, FeatureFlags.VANILLA_SET));

	public static void init() {
	}
}
