/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.screenhandler.VampireAltarScreenHandler;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerScreenHandlerType;

public class ModScreenHandlerTypes {
	public static final ScreenHandlerType<VampireAltarScreenHandler> VAMPIRE_ALTAR = registerScreenHandlerType("vampire_altar", new ScreenHandlerType<>(VampireAltarScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

	public static void init() {
	}
}
