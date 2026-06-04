/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.function.Function;

public final class NyctoEntityComponentKey<T> {
	private final ResourceLocation id;
	private final Class<T> componentClass;
	private final Class<? extends Entity> entityClass;
	private final Function<Entity, T> factory;
	private final WeakHashMap<Entity, T> values = new WeakHashMap<>();

	@SuppressWarnings("unchecked")
	public <E extends Entity> NyctoEntityComponentKey(ResourceLocation id, Class<T> componentClass, Class<E> entityClass, Function<E, T> factory) {
		this.id = id;
		this.componentClass = componentClass;
		this.entityClass = entityClass;
		this.factory = entity -> factory.apply((E) entity);
	}

	public ResourceLocation id() {
		return id;
	}

	public Class<T> componentClass() {
		return componentClass;
	}

	public boolean appliesTo(Entity entity) {
		return entityClass.isInstance(entity);
	}

	public T get(Entity entity) {
		if (!appliesTo(entity)) {
			throw new IllegalArgumentException("Component " + id + " does not apply to " + entity.getType());
		}
		return values.computeIfAbsent(entity, factory);
	}

	public @Nullable T getNullable(@Nullable Entity entity) {
		if (entity == null || !appliesTo(entity)) {
			return null;
		}
		return get(entity);
	}

	public Optional<T> maybeGet(@Nullable Entity entity) {
		return Optional.ofNullable(getNullable(entity));
	}

	public void sync(Entity entity) {
		if (appliesTo(entity)) {
			NyctoStateSync.syncEntity(entity, this);
		}
	}

	public void clear(Entity entity) {
		values.remove(entity);
	}

	public void load(Entity entity, CompoundTag tag, HolderLookup.Provider registries) {
		T component = get(entity);
		if (component instanceof NyctoPersistedComponent persisted) {
			persisted.readData(new NyctoValueInput(tag, registries));
		}
	}

	public CompoundTag save(Entity entity, HolderLookup.Provider registries) {
		CompoundTag tag = new CompoundTag();
		T component = get(entity);
		if (component instanceof NyctoPersistedComponent persisted) {
			persisted.writeData(new NyctoValueOutput(tag, registries));
		}
		return tag;
	}

	public Iterable<T> attachedValues() {
		return Collections.unmodifiableCollection(values.values());
	}
}
