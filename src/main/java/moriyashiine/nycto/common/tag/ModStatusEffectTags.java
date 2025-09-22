/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModStatusEffectTags {
	public static final TagKey<StatusEffect> INFECTION = TagKey.of(RegistryKeys.STATUS_EFFECT, Nycto.id("infection"));
}
