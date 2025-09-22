/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.particle;

import moriyashiine.nycto.common.init.ModParticleTypes;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;

public class BatSwarmParticle extends SpriteBillboardParticle {
	private final SpriteProvider spriteProvider;
	private final boolean hasBlood;
	private int spriteIndex = 0;

	public BatSwarmParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, boolean hasBlood) {
		super(world, x, y, z, 0, 0, 0);
		this.spriteProvider = spriteProvider;
		this.hasBlood = hasBlood;
		init();
		setSpriteForAge(spriteProvider);
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		super.tick();
		setSprite(spriteProvider.getSprite(spriteIndex, maxAge));
		setVelocity(random.nextGaussian() / 4, random.nextGaussian() / 4, random.nextGaussian() / 4);
		if (age % 2 == 0) {
			spriteIndex = spriteIndex == 0 ? maxAge : 0;
			if (hasBlood) {
				world.addParticleClient(ModParticleTypes.BLOOD,
						x, y, z,
						0, 0, 0);
			}
		}
	}

	protected void init() {
		scale(MathHelper.nextFloat(random, 1.5F, 2)).setMaxAge(24);
	}

	public record BatSwarmFactory(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			return new BatSwarmParticle(world, x, y, z, spriteProvider(), velocityX != 0);
		}
	}

	public record BatstepFactory(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			return new BatSwarmParticle(world, x, y, z, spriteProvider(), velocityX != 0) {
				@Override
				protected void init() {
					scale(MathHelper.nextFloat(random, 0.5F, 1)).setMaxAge(8);
				}
			};
		}
	}
}
