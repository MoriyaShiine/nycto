/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.init;

import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.api.transformation.Transformation;
import moriyashiine.nycto.common.Nycto;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class NyctoRegistries {
	public static final RegistryKey<Registry<Transformation>> TRANSFORMATION_KEY = RegistryKey.ofRegistry(Nycto.id("transformation"));
	public static final Registry<Transformation> TRANSFORMATION = FabricRegistryBuilder.createSimple(TRANSFORMATION_KEY).buildAndRegister();

	public static final RegistryKey<Registry<Power>> POWER_KEY = RegistryKey.ofRegistry(Nycto.id("power"));
	public static final Registry<Power> POWER = FabricRegistryBuilder.createSimple(POWER_KEY).buildAndRegister();

	public static void init() {
		// fields initialized during mod init
	}
}
