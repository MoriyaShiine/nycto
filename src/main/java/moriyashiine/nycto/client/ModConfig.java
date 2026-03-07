/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
	@Entry(category = "client")
	public static boolean vampireChargeJump = true;
	@Entry(category = "client")
	public static boolean vampireStepHeight = true;

	// Hunter balance
	@Entry(category = "server", min = 1, max = 20)
	public static int hunterMaxHeat = 5;
	@Entry(category = "server", min = 0, max = 20)
	public static int hunterMaxSpawns = 3;
	@Entry(category = "server")
	public static boolean weaponsBypassBloodVeil = true;
	@Entry(category = "server")
	public static boolean weaponsHaltRegeneration = true;

	// Sun balance
	@Entry(category = "server")
	public static boolean sunDamageEnabled = true;
	@Entry(category = "server")
	public static boolean sunBypassesBloodVeil = true;
	@Entry(category = "server")
	public static boolean sunHaltsRegeneration = true;

	// Fire balance
	@Entry(category = "server")
	public static boolean fireBypassesBloodVeil = true;
	@Entry(category = "server")
	public static boolean fireHaltsRegeneration = true;
}
