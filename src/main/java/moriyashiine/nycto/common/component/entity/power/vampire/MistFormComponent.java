/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.common.component.entity.power.util.VampireFormChangeComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import moriyashiine.strawberrylib.api.objects.records.ParticleVelocity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;

public class MistFormComponent extends VampireFormChangeComponent implements ClientTickingComponent {
	private static final ParticleVelocity PARTICLE_VELOCITY = ParticleVelocity.of(0.2);

	public MistFormComponent(PlayerEntity obj) {
		super(obj);
	}

	@Override
	public void clientTick() {
		if (enabled && obj.isPartOfGame()) {
			if (obj.getRandom().nextInt(3) == 0) {
				SLibClientUtils.addParticles(obj, ParticleTypes.SMOKE, 1, ParticleAnchor.BODY);
			}
			if (obj.getRandom().nextInt(9) == 0) {
				SLibClientUtils.addParticles(obj, ParticleTypes.WHITE_SMOKE, 1, ParticleAnchor.BODY);
			}
		}
	}

	public void sync() {
		ModEntityComponents.MIST_FORM.sync(obj);
	}

	@Override
	public void toggle() {
		SLibUtils.addParticles(obj, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY, PARTICLE_VELOCITY);
		SLibUtils.addParticles(obj, ParticleTypes.WHITE_SMOKE, 16, ParticleAnchor.BODY, PARTICLE_VELOCITY);
		if (enabled) {
			drainTicks = 0;
		} else {
			ModEntityComponents.BLOOD.get(obj).drain(ModPowers.MIST_FORM.getCost(obj));
			drainTicks = FORM_DRAIN_TICKS;
		}
		enabled = !enabled;
		sync();
	}
}
