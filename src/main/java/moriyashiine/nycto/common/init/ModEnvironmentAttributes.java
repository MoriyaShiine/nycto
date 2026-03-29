/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.entity.schedule.Activity;

public class ModEnvironmentAttributes {
	public static final EnvironmentAttribute<Activity> VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY = register("gameplay/vampire_villager_activity", EnvironmentAttribute.builder(AttributeTypes.ACTIVITY).defaultValue(Activity.IDLE));
	public static final EnvironmentAttribute<Activity> BABY_VAMPIRE_VILLAGER_ACTIVITY_GAMEPLAY = register("gameplay/baby_vampire_villager_activity", EnvironmentAttribute.builder(AttributeTypes.ACTIVITY).defaultValue(Activity.IDLE));

	private static <T> EnvironmentAttribute<T> register(String name, EnvironmentAttribute.Builder<T> builder) {
		EnvironmentAttribute<T> environmentAttribute = builder.build();
		Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, Nycto.id(name), environmentAttribute);
		return environmentAttribute;
	}

	public static void init() {
	}
}
