/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import net.minecraft.core.particles.SimpleParticleType;

public class ModParticleTypes {
	public static final int BLOOD_PARTICLE_COUNT = 8;

	public static final SimpleParticleType AMBROSIA = registerParticleType("ambrosia");
	public static final SimpleParticleType BLOOD = registerParticleType("blood");

	public static final SimpleParticleType BAT_SWARM_CENTER = registerParticleType("bat_swarm_center");
	public static final SimpleParticleType BAT_SWARM_LEFT = registerParticleType("bat_swarm_left");
	public static final SimpleParticleType BAT_SWARM_RIGHT = registerParticleType("bat_swarm_right");

	public static final SimpleParticleType BATSTEP_CENTER = registerParticleType("batstep_center");
	public static final SimpleParticleType BATSTEP_LEFT = registerParticleType("batstep_left");
	public static final SimpleParticleType BATSTEP_RIGHT = registerParticleType("batstep_right");

	public static final SimpleParticleType HYPNOSIS_INDICATOR = registerParticleType("hypnosis_indicator");
	public static final SimpleParticleType HYPNOSIS_INDICATOR_INVERSE = registerParticleType("hypnosis_indicator_inverse");
	public static final SimpleParticleType HYPNOSIS_SMALL = registerParticleType("hypnosis_small");
	public static final SimpleParticleType HYPNOSIS_STAR = registerParticleType("hypnosis_star");
	public static final SimpleParticleType HYPNOTIZED = registerParticleType("hypnotized");

	public static final SimpleParticleType THRALLED = registerParticleType("thralled");

	private static SimpleParticleType registerParticleType(String name) {
		SimpleParticleType type = new SimpleParticleType(false);
		ModRegistration.register(ModRegistration.PARTICLE_TYPES, name, type);
		return type;
	}

	public static void init() {
	}
}
