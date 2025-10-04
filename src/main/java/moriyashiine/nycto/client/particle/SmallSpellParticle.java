/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;

public class SmallSpellParticle extends SpellParticle {
	public SmallSpellParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
		super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
		scale *= 0.5F;
	}

	public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
			return new SmallSpellParticle(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider());
		}
	}
}
