/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeTypes;

public class ModEnvironmentAttributes {
	public static final EnvironmentAttribute<Activity> VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY = register("gameplay/vampire_villager_activity", EnvironmentAttribute.builder(EnvironmentAttributeTypes.ACTIVITY).defaultValue(Activity.IDLE));
	public static final EnvironmentAttribute<Activity> BABY_VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY = register("gameplay/baby_vampire_villager_activity", EnvironmentAttribute.builder(EnvironmentAttributeTypes.ACTIVITY).defaultValue(Activity.IDLE));

	private static <T> EnvironmentAttribute<T> register(String name, EnvironmentAttribute.Builder<T> builder) {
		EnvironmentAttribute<T> environmentAttribute = builder.build();
		Registry.register(Registries.ENVIRONMENTAL_ATTRIBUTE, Nycto.id(name), environmentAttribute);
		return environmentAttribute;
	}

	public static void init() {
	}
}
