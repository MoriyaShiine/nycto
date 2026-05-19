/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.references;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.key;

public class NyctoEntityTypeIds {
	public static final ResourceKey<EntityType<?>> WOODEN_STAKE = key(Registries.ENTITY_TYPE, "wooden_stake");
	public static final ResourceKey<EntityType<?>> FIREBOMB = key(Registries.ENTITY_TYPE, "firebomb");
	public static final ResourceKey<EntityType<?>> BLOOD_FLECHETTE = key(Registries.ENTITY_TYPE, "blood_flechette");

	public static final ResourceKey<EntityType<?>> VAMPIRE = key(Registries.ENTITY_TYPE, "vampire");
	public static final ResourceKey<EntityType<?>> HUNTER = key(Registries.ENTITY_TYPE, "hunter");

	public static final ResourceKey<EntityType<?>> DARK_FORM = key(Registries.ENTITY_TYPE, "dark_form");
}
