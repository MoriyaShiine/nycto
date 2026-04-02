/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
	public static final ResourceKey<DamageType> BLEED = ResourceKey.create(Registries.DAMAGE_TYPE, Nycto.id("bleed"));
	public static final ResourceKey<DamageType> SUN = ResourceKey.create(Registries.DAMAGE_TYPE, Nycto.id("sun"));
	public static final ResourceKey<DamageType> TOXIC_TOUCH = ResourceKey.create(Registries.DAMAGE_TYPE, Nycto.id("toxic_touch"));

	public static void bootstrap(BootstrapContext<DamageType> registry) {
		registry.register(BLEED, new DamageType("nycto.bleed", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0));
		registry.register(SUN, new DamageType("onFire", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0, DamageEffects.BURNING));
		registry.register(TOXIC_TOUCH, new DamageType("nycto.toxic_touch", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0));
	}
}
