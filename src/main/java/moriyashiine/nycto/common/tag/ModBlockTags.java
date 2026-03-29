/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
	public static final TagKey<Block> BEAST_MINEABLE = TagKey.create(Registries.BLOCK, Nycto.id("beast_mineable"));
	public static final TagKey<Block> COFFINS = TagKey.create(Registries.BLOCK, Nycto.id("coffins"));
	public static final TagKey<Block> HURTS_VAMPIRES = TagKey.create(Registries.BLOCK, Nycto.id("hurts_vampires"));
	public static final TagKey<Block> MIST_FORM_UNPASSABLE = TagKey.create(Registries.BLOCK, Nycto.id("mist_form_unpassable"));
}
