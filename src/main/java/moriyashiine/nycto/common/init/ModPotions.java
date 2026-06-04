/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

public class ModPotions {
	public static final Holder<Potion> GARLIC = ModRegistration.POTIONS.register("garlic", () -> new Potion("nycto.garlic", new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1), new MobEffectInstance(ModMobEffects.VAMPIRE_WARD, 400)));
	public static final Holder<Potion> LONG_GARLIC = ModRegistration.POTIONS.register("long_garlic", () -> new Potion("nycto.garlic", new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1), new MobEffectInstance(ModMobEffects.VAMPIRE_WARD, 800)));
	public static final Holder<Potion> STRONG_GARLIC = ModRegistration.POTIONS.register("strong_garlic", () -> new Potion("nycto.garlic", new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1, 1), new MobEffectInstance(ModMobEffects.VAMPIRE_WARD, 400)));

	public static final Holder<Potion> WITHER = ModRegistration.POTIONS.register("wither", () -> new Potion("nycto.wither", new MobEffectInstance(MobEffects.WITHER, 400)));
	public static final Holder<Potion> LONG_WITHER = ModRegistration.POTIONS.register("long_wither", () -> new Potion("nycto.wither", new MobEffectInstance(MobEffects.WITHER, 800)));
	public static final Holder<Potion> STRONG_WITHER = ModRegistration.POTIONS.register("strong_wither", () -> new Potion("nycto.wither", new MobEffectInstance(MobEffects.WITHER, 200, 1)));

	public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
		PotionBrewing.Builder builder = event.getBuilder();
		builder.addMix(Potions.HEALING, ModItems.GARLIC, GARLIC);
		builder.addMix(Potions.STRONG_HEALING, ModItems.GARLIC, STRONG_GARLIC);
		builder.addMix(GARLIC, Items.REDSTONE, LONG_GARLIC);
		builder.addMix(GARLIC, Items.GLOWSTONE_DUST, STRONG_GARLIC);

		builder.addMix(Potions.POISON, ModItems.ACONITE, WITHER);
		builder.addMix(WITHER, Items.REDSTONE, LONG_WITHER);
		builder.addMix(WITHER, Items.GLOWSTONE_DUST, STRONG_WITHER);
	}

	public static void init() {
	}
}
