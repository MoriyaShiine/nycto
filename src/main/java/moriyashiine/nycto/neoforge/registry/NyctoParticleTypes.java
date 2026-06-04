/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NyctoParticleTypes {
	private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, NyctoNeoForge.MOD_ID);

	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AMBROSIA = register("ambrosia");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLOOD = register("blood");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BAT_SWARM_CENTER = register("bat_swarm_center");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BAT_SWARM_LEFT = register("bat_swarm_left");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BAT_SWARM_RIGHT = register("bat_swarm_right");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BATSTEP_CENTER = register("batstep_center");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BATSTEP_LEFT = register("batstep_left");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BATSTEP_RIGHT = register("batstep_right");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HYPNOSIS_INDICATOR = register("hypnosis_indicator");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HYPNOSIS_INDICATOR_INVERSE = register("hypnosis_indicator_inverse");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HYPNOSIS_SMALL = register("hypnosis_small");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HYPNOSIS_STAR = register("hypnosis_star");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HYPNOTIZED = register("hypnotized");
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> THRALLED = register("thralled");

	private NyctoParticleTypes() {
	}

	public static void register(IEventBus bus) {
		PARTICLE_TYPES.register(bus);
	}

	private static DeferredHolder<ParticleType<?>, SimpleParticleType> register(String name) {
		return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(false));
	}
}
