/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModEntityTypeTags {
	public static final TagKey<EntityType<?>> HAS_QUALITY_BLOOD = TagKey.of(RegistryKeys.ENTITY_TYPE, Nycto.id("has_quality_blood"));
	public static final TagKey<EntityType<?>> HAS_NO_BLOOD = TagKey.of(RegistryKeys.ENTITY_TYPE, Nycto.id("has_no_blood"));

	public static final TagKey<EntityType<?>> BYPASSES_BLOOD_VEIL = TagKey.of(RegistryKeys.ENTITY_TYPE, Nycto.id("bypasses_blood_veil"));
	public static final TagKey<EntityType<?>> VAMPIRES = TagKey.of(RegistryKeys.ENTITY_TYPE, Nycto.id("vampires"));

	public static final TagKey<EntityType<?>> CAN_BE_THRALLED = TagKey.of(RegistryKeys.ENTITY_TYPE, Nycto.id("can_be_thralled"));
	public static final TagKey<EntityType<?>> CANNOT_BE_HYPNOTIZED = TagKey.of(RegistryKeys.ENTITY_TYPE, Nycto.id("cannot_be_hypnotized"));
	public static final TagKey<EntityType<?>> VILE_PRESENCE_IMMUNE = TagKey.of(RegistryKeys.ENTITY_TYPE, Nycto.id("vile_presence_immune"));
}
