/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;

public class ModSoundEventTags {
	public static final TagKey<SoundEvent> NOT_MUFFLED = TagKey.create(Registries.SOUND_EVENT, Nycto.id("not_muffled"));
	public static final TagKey<SoundEvent> POWER = TagKey.create(Registries.SOUND_EVENT, Nycto.id("power"));

	public static boolean is(SoundEvent sound, TagKey<SoundEvent> tagKey) {
		return BuiltInRegistries.SOUND_EVENT.wrapAsHolder(sound).is(tagKey);
	}
}
