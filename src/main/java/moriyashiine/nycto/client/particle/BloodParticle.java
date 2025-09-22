/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class BloodParticle extends SpriteBillboardParticle {
	private final SpriteProvider spriteProvider;

	public BloodParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
		super(world, x, y, z);
		this.spriteProvider = spriteProvider;
		setSpriteForAge(spriteProvider);
		setBoundingBoxSpacing(0.01F, 0.01F);
		gravityStrength = 0.06F;
		maxAge = 8;
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		lastX = x;
		lastY = y;
		lastZ = z;
		if (++age >= maxAge) {
			markDead();
		} else {
			setSpriteForAge(spriteProvider);
			velocityY = velocityY - gravityStrength;
			move(velocityX, velocityY, velocityZ);
			if (onGround) {
				markDead();
			} else {
				velocityX *= 0.98F;
				velocityY *= 0.98F;
				velocityZ *= 0.98F;
			}
		}
	}

	public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			return new BloodParticle(world, x, y, z, spriteProvider());
		}
	}
}
