/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.power.Power;
import moriyashiine.nycto.common.Nycto;
import net.minecraft.registry.tag.TagKey;

public class ModPowerTags {
	public static final TagKey<Power> VAMPIRE_CHOOSABLE = TagKey.of(NyctoRegistries.POWER_KEY, Nycto.id("vampire_choosable"));
}
