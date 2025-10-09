/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
	@Entry
	public static boolean spawnVampires = true;

	@Entry(category = "client")
	public static boolean vampireChargeJump = true;
	@Entry(category = "client")
	public static boolean vampireStepHeight = true;
}
