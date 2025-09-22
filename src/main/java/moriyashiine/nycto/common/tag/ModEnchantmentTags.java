/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModEnchantmentTags {
	public static final TagKey<Enchantment> BYPASSES_BLOOD_VEIL = TagKey.of(RegistryKeys.ENCHANTMENT, Nycto.id("bypasses_blood_veil"));
}
