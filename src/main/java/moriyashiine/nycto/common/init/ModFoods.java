/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;


import net.minecraft.world.food.FoodProperties;

public class ModFoods {
	public static final FoodProperties AMBROSIA_BOTTLE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.1F).alwaysEdible().build();
	public static final FoodProperties GARLIC = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).build();
	public static final FoodProperties GRILLED_GARLIC = new FoodProperties.Builder().nutrition(3).saturationModifier(0.6F).build();
	public static final FoodProperties GARLIC_BREAD = new FoodProperties.Builder().nutrition(8).saturationModifier(0.6F).build();
}
