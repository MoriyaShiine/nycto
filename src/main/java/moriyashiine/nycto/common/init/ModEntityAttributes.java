/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.entry.RegistryEntry;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerEntityAttribute;

public class ModEntityAttributes {
	public static final RegistryEntry<EntityAttribute> VAMPIRE_RESISTANCE = registerEntityAttribute(Nycto.id("vampire_resistance"), new ClampedEntityAttribute("attribute.name.nycto.vampire_resistance", 0, 0, 10));
	public static final RegistryEntry<EntityAttribute> WEREWOLF_RESISTANCE = registerEntityAttribute(Nycto.id("werewolf_resistance"), new ClampedEntityAttribute("attribute.name.nycto.werewolf_resistance", 0, 0, 10));

	public static void init() {
	}
}
