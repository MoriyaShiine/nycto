/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModBlockTags {
	public static final TagKey<Block> BEAST_MINEABLE = TagKey.of(RegistryKeys.BLOCK, Nycto.id("beast_mineable"));
	public static final TagKey<Block> COFFINS = TagKey.of(RegistryKeys.BLOCK, Nycto.id("coffins"));
	public static final TagKey<Block> HURTS_VAMPIRES = TagKey.of(RegistryKeys.BLOCK, Nycto.id("hurts_vampires"));
	public static final TagKey<Block> MIST_FORM_UNPASSABLE = TagKey.of(RegistryKeys.BLOCK, Nycto.id("mist_form_unpassable"));
}
