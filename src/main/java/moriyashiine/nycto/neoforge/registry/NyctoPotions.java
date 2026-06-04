/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NyctoPotions {
	private static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, NyctoNeoForge.MOD_ID);

	public static final DeferredHolder<Potion, Potion> GARLIC = POTIONS.register("garlic", () -> new Potion("nycto.garlic", new MobEffectInstance(MobEffects.HEAL, 1), new MobEffectInstance(NyctoHunterContent.VAMPIRE_WARD, 400)));
	public static final DeferredHolder<Potion, Potion> LONG_GARLIC = POTIONS.register("long_garlic", () -> new Potion("nycto.garlic", new MobEffectInstance(MobEffects.HEAL, 1), new MobEffectInstance(NyctoHunterContent.VAMPIRE_WARD, 800)));
	public static final DeferredHolder<Potion, Potion> STRONG_GARLIC = POTIONS.register("strong_garlic", () -> new Potion("nycto.garlic", new MobEffectInstance(MobEffects.HEAL, 1, 1), new MobEffectInstance(NyctoHunterContent.VAMPIRE_WARD, 400)));

	public static final DeferredHolder<Potion, Potion> WITHER = POTIONS.register("wither", () -> new Potion("nycto.wither", new MobEffectInstance(MobEffects.WITHER, 400)));
	public static final DeferredHolder<Potion, Potion> LONG_WITHER = POTIONS.register("long_wither", () -> new Potion("nycto.wither", new MobEffectInstance(MobEffects.WITHER, 800)));
	public static final DeferredHolder<Potion, Potion> STRONG_WITHER = POTIONS.register("strong_wither", () -> new Potion("nycto.wither", new MobEffectInstance(MobEffects.WITHER, 200, 1)));

	private NyctoPotions() {
	}

	public static void register(IEventBus bus) {
		POTIONS.register(bus);
		NeoForge.EVENT_BUS.addListener(NyctoPotions::registerBrewingRecipes);
	}

	private static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
		PotionBrewing.Builder builder = event.getBuilder();
		builder.addMix(Potions.HEALING, NyctoHunterContent.GARLIC_ITEM.get(), GARLIC);
		builder.addMix(Potions.STRONG_HEALING, NyctoHunterContent.GARLIC_ITEM.get(), STRONG_GARLIC);
		builder.addMix(GARLIC, Items.REDSTONE, LONG_GARLIC);
		builder.addMix(GARLIC, Items.GLOWSTONE_DUST, STRONG_GARLIC);
		builder.addMix(Potions.POISON, NyctoHunterContent.ACONITE_ITEM.get(), WITHER);
		builder.addMix(WITHER, Items.REDSTONE, LONG_WITHER);
		builder.addMix(WITHER, Items.GLOWSTONE_DUST, STRONG_WITHER);
	}
}
