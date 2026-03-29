/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class BloodParticle extends SingleQuadParticle {
	private final SpriteSet sprites;

	public BloodParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
		super(level, x, y, z, sprites.first());
		this.sprites = sprites;
		setSpriteFromAge(sprites);
		setSize(0.01F, 0.01F);
		gravity = 0.06F;
		lifetime = 8;
	}

	@Override
	protected Layer getLayer() {
		return Layer.OPAQUE;
	}

	@Override
	public void tick() {
		xo = x;
		yo = y;
		zo = z;
		if (++age >= lifetime) {
			remove();
		} else {
			setSpriteFromAge(sprites);
			yd = yd - gravity;
			move(xd, yd, zd);
			if (onGround) {
				remove();
			} else {
				xd *= 0.98F;
				yd *= 0.98F;
				zd *= 0.98F;
			}
		}
	}

	public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
			return new BloodParticle(level, x, y, z, sprites());
		}
	}
}
