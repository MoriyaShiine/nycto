/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class NyctoSpriteParticle extends TextureSheetParticle {
	private final SpriteSet sprites;
	private final boolean animated;

	private NyctoSpriteParticle(ClientLevel level, double x, double y, double z, double xa, double ya, double za, SpriteSet sprites, ParticleStyle style) {
		super(level, x, y, z, xa, ya, za);
		this.sprites = sprites;
		this.animated = style.animated;
		gravity = style.gravity;
		friction = style.friction;
		lifetime = Mth.nextInt(random, style.minLifetime, style.maxLifetime);
		quadSize = Mth.nextFloat(random, style.minSize, style.maxSize);
		alpha = style.alpha;
		rCol = style.red;
		gCol = style.green;
		bCol = style.blue;
		setSpriteFromAge(sprites);
	}

	@Override
	public void tick() {
		super.tick();
		if (!removed) {
			if (animated) {
				setSpriteFromAge(sprites);
			} else if (age == 1) {
				pickSprite(sprites);
			}
		}
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	public record Provider(SpriteSet sprites, ParticleStyle style) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xa, double ya, double za) {
			return new NyctoSpriteParticle(level, x, y, z, xa, ya, za, sprites, style);
		}
	}

	public static final class ParticleStyle {
		private final int minLifetime;
		private final int maxLifetime;
		private final float minSize;
		private final float maxSize;
		private final float gravity;
		private final float friction;
		private final float alpha;
		private final float red;
		private final float green;
		private final float blue;
		private final boolean animated;

		private ParticleStyle(int minLifetime, int maxLifetime, float minSize, float maxSize, float gravity, float friction, float alpha, float red, float green, float blue, boolean animated) {
			this.minLifetime = minLifetime;
			this.maxLifetime = maxLifetime;
			this.minSize = minSize;
			this.maxSize = maxSize;
			this.gravity = gravity;
			this.friction = friction;
			this.alpha = alpha;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.animated = animated;
		}

		public static ParticleStyle blood() {
			return new ParticleStyle(18, 28, 0.035F, 0.07F, 0.08F, 0.94F, 1.0F, 0.75F, 0.02F, 0.03F, true);
		}

		public static ParticleStyle ambrosia() {
			return new ParticleStyle(20, 34, 0.04F, 0.09F, 0.02F, 0.95F, 0.9F, 1.0F, 0.85F, 0.35F, true);
		}

		public static ParticleStyle batSwarm() {
			return new ParticleStyle(18, 28, 0.22F, 0.36F, 0.0F, 0.82F, 1.0F, 1.0F, 1.0F, 1.0F, true);
		}

		public static ParticleStyle batstep() {
			return new ParticleStyle(6, 10, 0.12F, 0.22F, 0.0F, 0.75F, 0.85F, 1.0F, 1.0F, 1.0F, true);
		}

		public static ParticleStyle hypnosis() {
			return new ParticleStyle(18, 30, 0.12F, 0.22F, -0.01F, 0.88F, 0.9F, 0.85F, 0.55F, 1.0F, true);
		}

		public static ParticleStyle hypnosisSmall() {
			return new ParticleStyle(14, 22, 0.05F, 0.1F, -0.005F, 0.9F, 0.9F, 0.85F, 0.55F, 1.0F, true);
		}
	}
}
