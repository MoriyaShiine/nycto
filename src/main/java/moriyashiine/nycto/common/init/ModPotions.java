/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.entry.RegistryEntry;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerPotion;

public class ModPotions {
	public static final RegistryEntry<Potion> GARLIC = registerPotion("garlic", new Potion("nycto.garlic", new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1), new StatusEffectInstance(ModStatusEffects.VAMPIRE_WARD, 400)));
	public static final RegistryEntry<Potion> LONG_GARLIC = registerPotion("long_garlic", new Potion("nycto.garlic", new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1), new StatusEffectInstance(ModStatusEffects.VAMPIRE_WARD, 800)));
	public static final RegistryEntry<Potion> STRONG_GARLIC = registerPotion("strong_garlic", new Potion("nycto.garlic", new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 1), new StatusEffectInstance(ModStatusEffects.VAMPIRE_WARD, 400)));

	public static final RegistryEntry<Potion> WITHER = registerPotion("wither", new Potion("nycto.wither", new StatusEffectInstance(StatusEffects.WITHER, 400)));
	public static final RegistryEntry<Potion> LONG_WITHER = registerPotion("long_wither", new Potion("nycto.wither", new StatusEffectInstance(StatusEffects.WITHER, 800)));
	public static final RegistryEntry<Potion> STRONG_WITHER = registerPotion("strong_wither", new Potion("nycto.wither", new StatusEffectInstance(StatusEffects.WITHER, 200, 1)));

	public static void init() {
		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(Potions.HEALING, ModItems.GARLIC, GARLIC);
			builder.registerPotionRecipe(Potions.STRONG_HEALING, ModItems.GARLIC, STRONG_GARLIC);
			builder.registerPotionRecipe(GARLIC, Items.REDSTONE, LONG_GARLIC);
			builder.registerPotionRecipe(GARLIC, Items.GLOWSTONE_DUST, STRONG_GARLIC);

			builder.registerPotionRecipe(Potions.POISON, ModItems.ACONITE, WITHER);
			builder.registerPotionRecipe(WITHER, Items.REDSTONE, LONG_WITHER);
			builder.registerPotionRecipe(WITHER, Items.GLOWSTONE_DUST, STRONG_WITHER);
		});
	}
}
