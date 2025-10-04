/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.particle;

import moriyashiine.strawberrylib.api.registry.client.particle.AnchoredParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class HypnotizedParticle extends AnchoredParticle {
	private final SpriteProvider spriteProvider;

	public HypnotizedParticle(ClientWorld world, double x, double z, int entityId, double yOffset, double speed, double intensity, SpriteProvider spriteProvider) {
		super(world, x, z, entityId, yOffset, speed, intensity, spriteProvider.getFirst());
		this.spriteProvider = spriteProvider;
		setMaxAge(12);
		updateSprite(spriteProvider);
	}

	@Override
	public void tick() {
		super.tick();
		updateSprite(spriteProvider);
		if (entity != null) {
			Vec3d rotation = Vec3d.fromPolar(new Vec2f(entity.getPitch(), entity.getHeadYaw())).multiply(entity.getWidth());
			x += rotation.getX();
			y += rotation.getY();
			z += rotation.getZ();
		}
	}

	public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double z, double entityId, double yOffset, double speed, double intensity, Random random) {
			return new HypnotizedParticle(world, x, z, MathHelper.floor(entityId), yOffset, speed, intensity, spriteProvider());
		}
	}
}
