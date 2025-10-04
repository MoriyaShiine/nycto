/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.particle;

import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;

public class BloodParticle extends BillboardParticle {
	private final SpriteProvider spriteProvider;

	public BloodParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
		super(world, x, y, z, spriteProvider.getFirst());
		this.spriteProvider = spriteProvider;
		updateSprite(spriteProvider);
		setBoundingBoxSpacing(0.01F, 0.01F);
		gravityStrength = 0.06F;
		maxAge = 8;
	}

	@Override
	protected RenderType getRenderType() {
		return RenderType.PARTICLE_ATLAS_OPAQUE;
	}

	@Override
	public void tick() {
		lastX = x;
		lastY = y;
		lastZ = z;
		if (++age >= maxAge) {
			markDead();
		} else {
			updateSprite(spriteProvider);
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
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
			return new BloodParticle(world, x, y, z, spriteProvider());
		}
	}
}
