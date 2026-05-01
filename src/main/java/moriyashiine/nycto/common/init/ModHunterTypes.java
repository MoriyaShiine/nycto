/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.world.entity.huntertype.HunterType;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.world.entity.huntertype.VampireHunterType;
import moriyashiine.nycto.common.world.entity.huntertype.WerewolfHunterType;
import net.minecraft.core.Registry;

public class ModHunterTypes {
	public static final HunterType VAMPIRE = registerHunterType("vampire", new VampireHunterType());
	public static final HunterType WEREWOLF = registerHunterType("werewolf", new WerewolfHunterType());

	private static HunterType registerHunterType(String name, HunterType hunterType) {
		return Registry.register(NyctoRegistries.HUNTER_TYPE, Nycto.id(name), hunterType);
	}

	public static void init() {
	}
}
