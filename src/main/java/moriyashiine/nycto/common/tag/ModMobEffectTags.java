/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

public class ModMobEffectTags {
	public static final TagKey<MobEffect> INFECTION = TagKey.create(Registries.MOB_EFFECT, Nycto.id("infection"));
}
