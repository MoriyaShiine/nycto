/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.misc;

import net.minecraft.core.Holder;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Set;

public record AttributeModifierSet(
		Set<Tuple<Holder<Attribute>, AttributeModifier>> attributeModifiers) {
	public void addModifier(Holder<Attribute> attribute, AttributeModifier modifier) {
		attributeModifiers().add(new Tuple<>(attribute, modifier));
	}
}
