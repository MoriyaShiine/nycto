/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;


import net.minecraft.component.type.FoodComponent;

public class ModFoodComponents {
	public static final FoodComponent AMBROSIA_BOTTLE = new FoodComponent.Builder().nutrition(6).saturationModifier(0.1F).alwaysEdible().build();
	public static final FoodComponent GARLIC = new FoodComponent.Builder().nutrition(1).saturationModifier(0.3F).build();
	public static final FoodComponent GRILLED_GARLIC = new FoodComponent.Builder().nutrition(3).saturationModifier(0.6F).build();
	public static final FoodComponent GARLIC_BREAD = new FoodComponent.Builder().nutrition(8).saturationModifier(0.6F).build();
}
