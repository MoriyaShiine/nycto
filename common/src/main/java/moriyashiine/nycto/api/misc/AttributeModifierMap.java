package moriyashiine.nycto.api.misc;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record AttributeModifierMap(Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
	public AttributeModifierMap() {
		this(HashMultimap.create());
	}

	public void addModifier(Holder<Attribute> attribute, AttributeModifier modifier) {
		modifiers().put(attribute, modifier);
	}

	public boolean isEmpty() {
		return modifiers().isEmpty();
	}
}
