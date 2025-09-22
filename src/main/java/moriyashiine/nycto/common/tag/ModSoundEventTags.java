/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.tag;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;

public class ModSoundEventTags {
	public static final TagKey<SoundEvent> NOT_MUFFLED = TagKey.of(RegistryKeys.SOUND_EVENT, Nycto.id("not_muffled"));
	public static final TagKey<SoundEvent> POWER = TagKey.of(RegistryKeys.SOUND_EVENT, Nycto.id("power"));

	public static boolean isIn(SoundEvent sound, TagKey<SoundEvent> tag) {
		return Registries.SOUND_EVENT.getEntry(sound).isIn(tag);
	}
}
