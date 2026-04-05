/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTags {
	public static final TagKey<EntityType<?>> HAS_QUALITY_BLOOD = TagKey.create(Registries.ENTITY_TYPE, Nycto.id("has_quality_blood"));
	public static final TagKey<EntityType<?>> HAS_NO_BLOOD = TagKey.create(Registries.ENTITY_TYPE, Nycto.id("has_no_blood"));

	public static final TagKey<EntityType<?>> BYPASSES_BLOOD_VEIL = TagKey.create(Registries.ENTITY_TYPE, Nycto.id("bypasses_blood_veil"));
	public static final TagKey<EntityType<?>> CALLS_HUNTERS = TagKey.create(Registries.ENTITY_TYPE, Nycto.id("calls_hunters"));
	public static final TagKey<EntityType<?>> VAMPIRES = TagKey.create(Registries.ENTITY_TYPE, Nycto.id("vampires"));

	public static final TagKey<EntityType<?>> CAN_BE_THRALLED = TagKey.create(Registries.ENTITY_TYPE, Nycto.id("can_be_thralled"));
	public static final TagKey<EntityType<?>> CANNOT_BE_HYPNOTIZED = TagKey.create(Registries.ENTITY_TYPE, Nycto.id("cannot_be_hypnotized"));
	public static final TagKey<EntityType<?>> CANNOT_BE_TARGETED_BY_THRALLS = TagKey.create(Registries.ENTITY_TYPE, Nycto.id("cannot_be_targeted_by_thralls"));
	public static final TagKey<EntityType<?>> VILE_PRESENCE_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Nycto.id("vile_presence_immune"));
}
