/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
	public static final ResourceKey<DamageType> BLEED = ModRegistration.key(Registries.DAMAGE_TYPE, "bleed");
	public static final ResourceKey<DamageType> SUN = ModRegistration.key(Registries.DAMAGE_TYPE, "sun");
	public static final ResourceKey<DamageType> TOXIC_TOUCH = ModRegistration.key(Registries.DAMAGE_TYPE, "toxic_touch");
	public static final ResourceKey<DamageType> WOODEN_STAKE_FALL = ModRegistration.key(Registries.DAMAGE_TYPE, "wooden_stake");

	public static void bootstrap(BootstrapContext<DamageType> registry) {
		registry.register(BLEED, new DamageType("nycto.bleed", 0));
		registry.register(SUN, new DamageType("onFire", 0, DamageEffects.BURNING));
		registry.register(TOXIC_TOUCH, new DamageType("nycto.toxic_touch", 0));
		registry.register(WOODEN_STAKE_FALL, new DamageType("nycto.wooden_stake_fall", 0));
	}
}
