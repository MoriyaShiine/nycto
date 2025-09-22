/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModItemTags {
	public static final TagKey<Item> COFFINS = TagKey.of(RegistryKeys.ITEM, Nycto.id("coffins"));

	public static final TagKey<Item> BEAST_UNEQUIPPABLE = TagKey.of(RegistryKeys.ITEM, Nycto.id("beast_unequippable"));
	public static final TagKey<Item> HURTS_VAMPIRES = TagKey.of(RegistryKeys.ITEM, Nycto.id("hurts_vampires"));
	public static final TagKey<Item> SAFE_EDIBLES = TagKey.of(RegistryKeys.ITEM, Nycto.id("safe_edibles"));
	public static final TagKey<Item> USABLE_BLOOD_BOTTLES = TagKey.of(RegistryKeys.ITEM, Nycto.id("usable_blood_bottles"));
	public static final TagKey<Item> VAMPIRE_WEAKNESSES = TagKey.of(RegistryKeys.ITEM, Nycto.id("vampire_weaknesses"));

	public static final TagKey<Item> VAMPIRE_ARMOR = TagKey.of(RegistryKeys.ITEM, Nycto.id("vampire_armor"));
	public static final TagKey<Item> VAMPIRE_HUNTER_ARMOR = TagKey.of(RegistryKeys.ITEM, Nycto.id("vampire_hunter_armor"));
	public static final TagKey<Item> WEREWOLF_HUNTER_ARMOR = TagKey.of(RegistryKeys.ITEM, Nycto.id("werewolf_hunter_armor"));
	public static final TagKey<Item> REPAIRS_VAMPIRE_ARMOR = TagKey.of(RegistryKeys.ITEM, Nycto.id("repairs_vampire_armor"));
	public static final TagKey<Item> REPAIRS_HUNTER_ARMOR = TagKey.of(RegistryKeys.ITEM, Nycto.id("repairs_hunter_armor"));

	public static final TagKey<Item> HALBERD_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, Nycto.id("halberd_tool_materials"));

	public static final TagKey<Item> WEAK_VAMPIRE_ALTAR_UPGRADES = TagKey.of(RegistryKeys.ITEM, Nycto.id("weak_vampire_altar_upgrades"));
	public static final TagKey<Item> AVERAGE_VAMPIRE_ALTAR_UPGRADES = TagKey.of(RegistryKeys.ITEM, Nycto.id("average_vampire_altar_upgrades"));
	public static final TagKey<Item> STRONG_VAMPIRE_ALTAR_UPGRADES = TagKey.of(RegistryKeys.ITEM, Nycto.id("strong_vampire_altar_upgrades"));
}
