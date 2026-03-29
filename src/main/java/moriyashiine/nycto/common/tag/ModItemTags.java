/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
	public static final TagKey<Item> COFFINS = TagKey.create(Registries.ITEM, Nycto.id("coffins"));

	public static final TagKey<Item> BEAST_UNEQUIPPABLE = TagKey.create(Registries.ITEM, Nycto.id("beast_unequippable"));
	public static final TagKey<Item> HURTS_VAMPIRES = TagKey.create(Registries.ITEM, Nycto.id("hurts_vampires"));
	public static final TagKey<Item> SAFE_EDIBLES = TagKey.create(Registries.ITEM, Nycto.id("safe_edibles"));
	public static final TagKey<Item> USABLE_BLOOD_BOTTLES = TagKey.create(Registries.ITEM, Nycto.id("usable_blood_bottles"));
	public static final TagKey<Item> VAMPIRE_WEAKNESSES = TagKey.create(Registries.ITEM, Nycto.id("vampire_weaknesses"));

	public static final TagKey<Item> VAMPIRE_ARMOR = TagKey.create(Registries.ITEM, Nycto.id("vampire_armor"));
	public static final TagKey<Item> VAMPIRE_HUNTER_ARMOR = TagKey.create(Registries.ITEM, Nycto.id("vampire_hunter_armor"));
	public static final TagKey<Item> WEREWOLF_HUNTER_ARMOR = TagKey.create(Registries.ITEM, Nycto.id("werewolf_hunter_armor"));
	public static final TagKey<Item> REPAIRS_VAMPIRE_ARMOR = TagKey.create(Registries.ITEM, Nycto.id("repairs_vampire_armor"));
	public static final TagKey<Item> REPAIRS_HUNTER_ARMOR = TagKey.create(Registries.ITEM, Nycto.id("repairs_hunter_armor"));

	public static final TagKey<Item> HALBERD_TOOL_MATERIALS = TagKey.create(Registries.ITEM, Nycto.id("halberd_tool_materials"));

	public static final TagKey<Item> WEAK_VAMPIRE_ALTAR_UPGRADES = TagKey.create(Registries.ITEM, Nycto.id("weak_vampire_altar_upgrades"));
	public static final TagKey<Item> AVERAGE_VAMPIRE_ALTAR_UPGRADES = TagKey.create(Registries.ITEM, Nycto.id("average_vampire_altar_upgrades"));
	public static final TagKey<Item> STRONG_VAMPIRE_ALTAR_UPGRADES = TagKey.create(Registries.ITEM, Nycto.id("strong_vampire_altar_upgrades"));
}
