/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.entity.subpredicate.VampirePredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntitySubPredicates {
	public static void init() {
		Registry.register(Registries.ENTITY_SUB_PREDICATE_TYPE, Nycto.id("vampire"), VampirePredicate.CODEC);
	}
}
