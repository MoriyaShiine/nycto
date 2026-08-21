package moriyashiine.nycto.api.misc;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Set;

public record AttributeModifierSet(
		Set<Pair<Holder<Attribute>, AttributeModifier>> attributeModifiers) {
	public void addModifier(Holder<Attribute> attribute, AttributeModifier modifier) {
		attributeModifiers().add(Pair.of(attribute, modifier));
	}
}
