/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class ModBannerPatternTags {
	public static final TagKey<BannerPattern> PATTERN_ITEM_VAMPIRE_BAT = TagKey.create(Registries.BANNER_PATTERN, Nycto.id("pattern_item/vampire_bat"));
	public static final TagKey<BannerPattern> PATTERN_ITEM_WOLF_SKULL = TagKey.create(Registries.BANNER_PATTERN, Nycto.id("pattern_item/wolf_skull"));
	public static final TagKey<BannerPattern> PATTERN_ITEM_HUNTERS_MARK = TagKey.create(Registries.BANNER_PATTERN, Nycto.id("pattern_item/hunters_mark"));
}
