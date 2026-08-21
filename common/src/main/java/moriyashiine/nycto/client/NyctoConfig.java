package moriyashiine.nycto.client;

import eu.midnightdust.lib.config.MidnightConfig;

public class NyctoConfig extends MidnightConfig {
	@Entry(category = "client")
	public static boolean vampireChargeJump = true;
	@Entry(category = "client")
	public static boolean vampireStepHeight = true;
}
