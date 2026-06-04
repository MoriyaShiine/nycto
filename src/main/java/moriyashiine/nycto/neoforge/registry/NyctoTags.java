/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

public final class NyctoTags {
	public static final TagKey<Item> WEAK_VAMPIRE_ALTAR_UPGRADES = TagKey.create(Registries.ITEM, id("weak_vampire_altar_upgrades"));
	public static final TagKey<Item> AVERAGE_VAMPIRE_ALTAR_UPGRADES = TagKey.create(Registries.ITEM, id("average_vampire_altar_upgrades"));
	public static final TagKey<Item> STRONG_VAMPIRE_ALTAR_UPGRADES = TagKey.create(Registries.ITEM, id("strong_vampire_altar_upgrades"));
	public static final TagKey<Item> USABLE_BLOOD_BOTTLES = TagKey.create(Registries.ITEM, id("usable_blood_bottles"));
	public static final TagKey<Block> MIST_FORM_UNPASSABLE = TagKey.create(Registries.BLOCK, id("mist_form_unpassable"));
	public static final TagKey<Block> BEAST_MINEABLE = TagKey.create(Registries.BLOCK, id("beast_mineable"));
	public static final TagKey<Item> BEAST_UNEQUIPPABLE = TagKey.create(Registries.ITEM, id("beast_unequippable"));
	public static final TagKey<EntityType<?>> CAN_BE_THRALLED = TagKey.create(Registries.ENTITY_TYPE, id("can_be_thralled"));
	public static final TagKey<EntityType<?>> CANNOT_BE_TARGETED_BY_THRALLS = TagKey.create(Registries.ENTITY_TYPE, id("cannot_be_targeted_by_thralls"));
	public static final TagKey<EntityType<?>> HAS_NO_BLOOD = TagKey.create(Registries.ENTITY_TYPE, id("has_no_blood"));
	public static final TagKey<EntityType<?>> HAS_QUALITY_BLOOD = TagKey.create(Registries.ENTITY_TYPE, id("has_quality_blood"));
	public static final TagKey<SoundEvent> NOT_MUFFLED = TagKey.create(Registries.SOUND_EVENT, id("not_muffled"));
	public static final TagKey<Biome> VAMPIRE_SPAWN_BIOMES = TagKey.create(Registries.BIOME, id("vampire_spawn_biomes"));

	private NyctoTags() {
	}

	private static ResourceLocation id(String path) {
		return NyctoNeoForge.id(path);
	}
}
