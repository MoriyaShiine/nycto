/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.RegistryOps;

public final class NyctoValueOutput {
	private final CompoundTag tag;
	private final HolderLookup.Provider registries;

	public NyctoValueOutput(CompoundTag tag, HolderLookup.Provider registries) {
		this.tag = tag;
		this.registries = registries;
	}

	public CompoundTag tag() {
		return tag;
	}

	public void putBoolean(String key, boolean value) {
		tag.putBoolean(key, value);
	}

	public void putInt(String key, int value) {
		tag.putInt(key, value);
	}

	public void putLong(String key, long value) {
		tag.putLong(key, value);
	}

	public void putString(String key, String value) {
		tag.putString(key, value);
	}

	public <T> void store(String key, Codec<T> codec, T value) {
		codec.encodeStart(RegistryOps.create(NbtOps.INSTANCE, registries), value).result().ifPresent(encoded -> tag.put(key, encoded));
	}

	public <T> void storeNullable(String key, Codec<T> codec, T value) {
		if (value == null) {
			tag.remove(key);
		} else {
			store(key, codec, value);
		}
	}
}
