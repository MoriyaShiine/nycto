/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEntityAttributes {
	public static final RegistryEntry<EntityAttribute> VAMPIRE_RESISTANCE = registerEntityAttribute("vampire_resistance", new ClampedEntityAttribute("attribute.name.nycto.vampire_resistance", 0, 0, 10));
	public static final RegistryEntry<EntityAttribute> WEREWOLF_RESISTANCE = registerEntityAttribute("werewolf_resistance", new ClampedEntityAttribute("attribute.name.nycto.werewolf_resistance", 0, 0, 10));

	public static RegistryEntry<EntityAttribute> registerEntityAttribute(String name, EntityAttribute attribute) {
		return Registry.registerReference(Registries.ATTRIBUTE, Nycto.id(name), attribute);
	}

	public static void init() {
	}
}
