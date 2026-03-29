/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypeTags {
	public static final TagKey<DamageType> BYPASSES_BLOOD_VEIL = TagKey.create(Registries.DAMAGE_TYPE, Nycto.id("bypasses_blood_veil"));
	public static final TagKey<DamageType> HALTS_VAMPIRE_REGENERATION = TagKey.create(Registries.DAMAGE_TYPE, Nycto.id("halts_vampire_regeneration"));
}
