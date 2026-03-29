/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.world.entity.subpredicate.VampirePredicate;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerEntitySubPredicateType;

public class ModEntitySubPredicateTypes {
	public static void init() {
		registerEntitySubPredicateType("vampire", VampirePredicate.CODEC);
	}
}
