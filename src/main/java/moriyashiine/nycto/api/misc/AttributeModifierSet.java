/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.api.misc;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Pair;

import java.util.Set;

public record AttributeModifierSet(
		Set<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> attributeModifiers) {
	public void addModifier(RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier modifier) {
		attributeModifiers().add(new Pair<>(attribute, modifier));
	}
}
