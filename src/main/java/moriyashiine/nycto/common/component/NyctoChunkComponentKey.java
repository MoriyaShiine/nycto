/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.Collections;
import java.util.WeakHashMap;
import java.util.function.Function;

public final class NyctoChunkComponentKey<T> {
	private final ResourceLocation id;
	private final Class<T> componentClass;
	private final Function<ChunkAccess, T> factory;
	private final WeakHashMap<ChunkAccess, T> values = new WeakHashMap<>();

	public NyctoChunkComponentKey(ResourceLocation id, Class<T> componentClass, Function<ChunkAccess, T> factory) {
		this.id = id;
		this.componentClass = componentClass;
		this.factory = factory;
	}

	public ResourceLocation id() {
		return id;
	}

	public Class<T> componentClass() {
		return componentClass;
	}

	public T get(ChunkAccess chunk) {
		return values.computeIfAbsent(chunk, factory);
	}

	public void sync(ChunkAccess chunk) {
		NyctoStateSync.syncChunk(chunk, this);
	}

	public void load(ChunkAccess chunk, CompoundTag tag, HolderLookup.Provider registries) {
		T component = get(chunk);
		if (component instanceof NyctoPersistedComponent persisted) {
			persisted.readData(new NyctoValueInput(tag, registries));
		}
	}

	public CompoundTag save(ChunkAccess chunk, HolderLookup.Provider registries) {
		CompoundTag tag = new CompoundTag();
		T component = get(chunk);
		if (component instanceof NyctoPersistedComponent persisted) {
			persisted.writeData(new NyctoValueOutput(tag, registries));
		}
		return tag;
	}

	public Iterable<T> attachedValues() {
		return Collections.unmodifiableCollection(values.values());
	}
}
