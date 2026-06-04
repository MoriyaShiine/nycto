/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NyctoMobEffects {
	private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, NyctoNeoForge.MOD_ID);

	public static final DeferredHolder<MobEffect, MobEffect> NIGHT_VISION = MOB_EFFECTS.register("night_vision", () -> new NyctoMobEffect(MobEffectCategory.BENEFICIAL, 0x3D5EA8));
	public static final DeferredHolder<MobEffect, MobEffect> VAMPIRISM = MOB_EFFECTS.register("vampirism", () -> new NyctoMobEffect(MobEffectCategory.NEUTRAL, 0xA01424));
	public static final DeferredHolder<MobEffect, MobEffect> HYPNOTIZED = MOB_EFFECTS.register("hypnotized", () -> new NyctoMobEffect(MobEffectCategory.HARMFUL, 0x9ABBB7));
	public static final DeferredHolder<MobEffect, MobEffect> STUNNED = MOB_EFFECTS.register("stunned", () -> new NyctoMobEffect(MobEffectCategory.HARMFUL, 0x43372F));

	private NyctoMobEffects() {
	}

	public static void register(IEventBus bus) {
		MOB_EFFECTS.register(bus);
	}

	private static final class NyctoMobEffect extends MobEffect {
		private NyctoMobEffect(MobEffectCategory category, int color) {
			super(category, color);
		}
	}
}
