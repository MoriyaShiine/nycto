/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModDamageTypeTags {
	public static final TagKey<DamageType> BYPASSES_BLOOD_VEIL = TagKey.of(RegistryKeys.DAMAGE_TYPE, Nycto.id("bypasses_blood_veil"));
	public static final TagKey<DamageType> HALTS_VAMPIRE_REGENERATION = TagKey.of(RegistryKeys.DAMAGE_TYPE, Nycto.id("halts_vampire_regeneration"));
}
