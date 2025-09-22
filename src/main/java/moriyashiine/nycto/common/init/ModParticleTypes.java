/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerParticleType;

public class ModParticleTypes {
	public static final int BLOOD_PARTICLE_COUNT = 8;

	public static final SimpleParticleType BLOOD = registerParticleType("blood", FabricParticleTypes.simple());

	public static final SimpleParticleType BAT_SWARM_CENTER = registerParticleType("bat_swarm_center", FabricParticleTypes.simple());
	public static final SimpleParticleType BAT_SWARM_LEFT = registerParticleType("bat_swarm_left", FabricParticleTypes.simple());
	public static final SimpleParticleType BAT_SWARM_RIGHT = registerParticleType("bat_swarm_right", FabricParticleTypes.simple());

	public static final SimpleParticleType BATSTEP_CENTER = registerParticleType("batstep_center", FabricParticleTypes.simple());
	public static final SimpleParticleType BATSTEP_LEFT = registerParticleType("batstep_left", FabricParticleTypes.simple());
	public static final SimpleParticleType BATSTEP_RIGHT = registerParticleType("batstep_right", FabricParticleTypes.simple());

	public static final SimpleParticleType HYPNOSIS_INDICATOR = registerParticleType("hypnosis_indicator", FabricParticleTypes.simple());
	public static final SimpleParticleType HYPNOSIS_SMALL = registerParticleType("hypnosis_small", FabricParticleTypes.simple());
	public static final SimpleParticleType HYPNOSIS_STAR = registerParticleType("hypnosis_star", FabricParticleTypes.simple());
	public static final SimpleParticleType HYPNOTIZED = registerParticleType("hypnotized", FabricParticleTypes.simple());

	public static final SimpleParticleType THRALLED = registerParticleType("thralled", FabricParticleTypes.simple());

	public static void init() {
	}
}
