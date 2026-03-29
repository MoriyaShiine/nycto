/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerAttribute;

public class ModAttributes {
	public static final Holder<Attribute> VAMPIRE_RESISTANCE = registerAttribute(Nycto.id("vampire_resistance"), new RangedAttribute("attribute.name.nycto.vampire_resistance", 0, 0, 10));
	public static final Holder<Attribute> WEREWOLF_RESISTANCE = registerAttribute(Nycto.id("werewolf_resistance"), new RangedAttribute("attribute.name.nycto.werewolf_resistance", 0, 0, 10));

	public static void init() {
	}
}
