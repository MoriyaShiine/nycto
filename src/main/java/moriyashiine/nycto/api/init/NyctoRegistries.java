/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.init;

import moriyashiine.nycto.api.world.power.Power;
import moriyashiine.nycto.api.world.transformation.Transformation;
import moriyashiine.nycto.common.Nycto;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class NyctoRegistries {
	public static final ResourceKey<Registry<Transformation>> TRANSFORMATION_KEY = ResourceKey.createRegistryKey(Nycto.id("transformation"));
	public static final Registry<Transformation> TRANSFORMATION = FabricRegistryBuilder.create(TRANSFORMATION_KEY).buildAndRegister();

	public static final ResourceKey<Registry<Power>> POWER_KEY = ResourceKey.createRegistryKey(Nycto.id("power"));
	public static final Registry<Power> POWER = FabricRegistryBuilder.create(POWER_KEY).buildAndRegister();

	public static void init() {
		// fields initialized during mod init
	}
}
