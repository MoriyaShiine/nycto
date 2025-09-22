/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ModDamageTypes {
	public static final RegistryKey<DamageType> BLEED = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Nycto.id("bleed"));
	public static final RegistryKey<DamageType> SUN = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Nycto.id("sun"));
	public static final RegistryKey<DamageType> TOXIC_TOUCH = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Nycto.id("toxic_touch"));
}
