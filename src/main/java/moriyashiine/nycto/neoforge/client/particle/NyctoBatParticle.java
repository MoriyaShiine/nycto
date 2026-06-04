/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.client.particle;

import moriyashiine.nycto.neoforge.registry.NyctoParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public final class NyctoBatParticle extends TextureSheetParticle {
	private final SpriteSet sprites;
	private final boolean hasBlood;
	private final int animationPeriod;
	private int spriteIndex;

	private NyctoBatParticle(ClientLevel level, double x, double y, double z, double xa, double ya, double za, SpriteSet sprites, boolean hasBlood, boolean batstep) {
		super(level, x, y, z, xa, ya, za);
		this.sprites = sprites;
		this.hasBlood = hasBlood;
		animationPeriod = batstep ? 1 : 2;
		lifetime = batstep ? Mth.nextInt(random, 7, 10) : Mth.nextInt(random, 22, 28);
		quadSize = batstep ? Mth.nextFloat(random, 0.12F, 0.2F) : Mth.nextFloat(random, 0.34F, 0.48F);
		friction = batstep ? 0.72F : 0.9F;
		alpha = batstep ? 0.9F : 1.0F;
		rCol = 0.88F;
		gCol = batstep ? 0.78F : 0.72F;
		bCol = 1.0F;
		setSprite(sprites.get(0, 1));
	}

	@Override
	public void tick() {
		super.tick();
		if (removed) {
			return;
		}
		if (age % animationPeriod == 0) {
			spriteIndex = spriteIndex == 0 ? 1 : 0;
			setSprite(sprites.get(spriteIndex, 1));
			if (hasBlood) {
				level.addParticle(NyctoParticleTypes.BLOOD.get(), x, y, z, 0, 0, 0);
			}
		}
		setParticleSpeed(random.nextGaussian() * 0.24, random.nextGaussian() * 0.24, random.nextGaussian() * 0.24);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	public record BatSwarmProvider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xa, double ya, double za) {
			return new NyctoBatParticle(level, x, y, z, xa, ya, za, sprites, false, false);
		}
	}

	public record BatstepProvider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xa, double ya, double za) {
			return new NyctoBatParticle(level, x, y, z, xa, ya, za, sprites, false, true);
		}
	}
}
