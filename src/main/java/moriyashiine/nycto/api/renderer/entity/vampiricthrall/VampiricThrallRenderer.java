/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.renderer.entity.vampiricthrall;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
public interface VampiricThrallRenderer<T extends LivingEntity> {
	Map<EntityType<?>, VampiricThrallRenderer<?>> CUSTOM_RENDERERS = new HashMap<>();

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Nullable
	static Identifier getTexture(LivingEntity entity) {
		VampiricThrallRenderer renderer = CUSTOM_RENDERERS.get(entity.getType());
		if (renderer != null) {
			return renderer.getCustomTexture(entity);
		}
		return null;
	}

	Identifier getCustomTexture(T entity);
}
