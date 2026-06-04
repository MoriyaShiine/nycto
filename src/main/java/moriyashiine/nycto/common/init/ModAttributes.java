/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ModAttributes {
	public static final Holder<Attribute> VAMPIRE_RESISTANCE = ModRegistration.ATTRIBUTES.register("vampire_resistance", () -> new RangedAttribute("attribute.name.nycto.vampire_resistance", 0, 0, 10));
	public static final Holder<Attribute> WEREWOLF_RESISTANCE = ModRegistration.ATTRIBUTES.register("werewolf_resistance", () -> new RangedAttribute("attribute.name.nycto.werewolf_resistance", 0, 0, 10));

	public static void init() {
	}
}
