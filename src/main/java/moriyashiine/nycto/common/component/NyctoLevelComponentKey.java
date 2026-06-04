/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.WeakHashMap;
import java.util.function.Function;

public final class NyctoLevelComponentKey<T> {
	private final ResourceLocation id;
	private final Class<T> componentClass;
	private final Function<Level, T> factory;
	private final WeakHashMap<Level, T> values = new WeakHashMap<>();

	public NyctoLevelComponentKey(ResourceLocation id, Class<T> componentClass, Function<Level, T> factory) {
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

	public T get(Level level) {
		return values.computeIfAbsent(level, factory);
	}

	public void sync(Level level) {
		NyctoStateSync.syncLevel(level, this);
	}

	public void load(Level level, CompoundTag tag, HolderLookup.Provider registries) {
		T component = get(level);
		if (component instanceof NyctoPersistedComponent persisted) {
			persisted.readData(new NyctoValueInput(tag, registries));
		}
	}

	public CompoundTag save(Level level, HolderLookup.Provider registries) {
		CompoundTag tag = new CompoundTag();
		T component = get(level);
		if (component instanceof NyctoPersistedComponent persisted) {
			persisted.writeData(new NyctoValueOutput(tag, registries));
		}
		return tag;
	}

	public Iterable<T> attachedValues() {
		return Collections.unmodifiableCollection(values.values());
	}
}
