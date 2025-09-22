/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.api.init.NyctoRegistries;
import moriyashiine.nycto.api.transformation.Transformation;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.transformation.VampireTransformation;
import net.minecraft.registry.Registry;

public class ModTransformations {
	public static final Transformation HUMAN = registerTransformation("human", new Transformation());
	public static final Transformation VAMPIRE = registerTransformation("vampire", new VampireTransformation());

	private static Transformation registerTransformation(String name, Transformation transformation) {
		return Registry.register(NyctoRegistries.TRANSFORMATION, Nycto.id(name), transformation);
	}

	public static void init() {
	}
}
