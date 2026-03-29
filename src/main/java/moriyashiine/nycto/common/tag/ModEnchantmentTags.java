/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantmentTags {
	public static final TagKey<Enchantment> BYPASSES_BLOOD_VEIL = TagKey.create(Registries.ENCHANTMENT, Nycto.id("bypasses_blood_veil"));
}
