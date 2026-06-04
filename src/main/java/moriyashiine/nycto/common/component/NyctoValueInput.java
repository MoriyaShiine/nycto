/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.RegistryOps;

import java.util.Optional;

public final class NyctoValueInput {
	private final CompoundTag tag;
	private final HolderLookup.Provider registries;

	public NyctoValueInput(CompoundTag tag, HolderLookup.Provider registries) {
		this.tag = tag;
		this.registries = registries;
	}

	public boolean getBooleanOr(String key, boolean fallback) {
		return tag.contains(key) ? tag.getBoolean(key) : fallback;
	}

	public int getIntOr(String key, int fallback) {
		return tag.contains(key) ? tag.getInt(key) : fallback;
	}

	public long getLongOr(String key, long fallback) {
		return tag.contains(key) ? tag.getLong(key) : fallback;
	}

	public String getStringOr(String key, String fallback) {
		return tag.contains(key) ? tag.getString(key) : fallback;
	}

	public <T> Optional<T> read(String key, Codec<T> codec) {
		if (!tag.contains(key)) {
			return Optional.empty();
		}
		return codec.parse(RegistryOps.create(NbtOps.INSTANCE, registries), tag.get(key)).result();
	}
}
