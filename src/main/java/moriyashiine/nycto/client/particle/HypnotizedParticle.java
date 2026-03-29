/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.particle;

import moriyashiine.strawberrylib.api.registry.client.particle.AnchoredParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class HypnotizedParticle extends AnchoredParticle {
	private final SpriteSet sprites;

	public HypnotizedParticle(ClientLevel level, double x, double z, int entityId, double yOffset, double speed, double intensity, SpriteSet sprites) {
		super(level, x, z, entityId, yOffset, speed, intensity, sprites.first());
		this.sprites = sprites;
		setLifetime(12);
		setSpriteFromAge(sprites);
	}

	@Override
	public void tick() {
		super.tick();
		setSpriteFromAge(sprites);
		if (entity != null) {
			Vec3 rotation = Vec3.directionFromRotation(new Vec2(entity.getXRot(), entity.getYHeadRot())).scale(entity.getBbWidth());
			x += rotation.x();
			y += rotation.y();
			z += rotation.z();
		}
	}

	public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double z, double entityId, double yOffset, double speed, double intensity, RandomSource random) {
			return new HypnotizedParticle(level, x, z, Mth.floor(entityId), yOffset, speed, intensity, sprites());
		}
	}
}
