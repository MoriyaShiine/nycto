/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.particle;

import moriyashiine.nycto.common.init.ModParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class BatSwarmParticle extends SingleQuadParticle {
	private final SpriteSet sprites;
	private final boolean hasBlood;
	private int spriteIndex = 0;

	public BatSwarmParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites, boolean hasBlood) {
		super(level, x, y, z, 0, 0, 0, sprites.first());
		this.sprites = sprites;
		this.hasBlood = hasBlood;
		init();
		setSpriteFromAge(sprites);
	}

	@Override
	protected Layer getLayer() {
		return Layer.OPAQUE;
	}

	@Override
	public void tick() {
		super.tick();
		setSprite(sprites.get(spriteIndex, lifetime));
		setParticleSpeed(random.nextGaussian() / 4, random.nextGaussian() / 4, random.nextGaussian() / 4);
		if (age % 2 == 0) {
			spriteIndex = spriteIndex == 0 ? lifetime : 0;
			if (hasBlood) {
				level.addParticle(ModParticleTypes.BLOOD, x, y, z, 0, 0, 0);
			}
		}
	}

	protected void init() {
		scale(Mth.nextFloat(random, 1.5F, 2)).setLifetime(24);
	}

	public record BatSwarmProvider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
			return new BatSwarmParticle(level, x, y, z, sprites(), xAux != 0);
		}
	}

	public record BatstepProvider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
			return new BatSwarmParticle(level, x, y, z, sprites(), xAux != 0) {
				@Override
				protected void init() {
					scale(Mth.nextFloat(random, 0.5F, 1)).setLifetime(8);
				}
			};
		}
	}
}
