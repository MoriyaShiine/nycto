/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.util.VampireFormChangeComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import moriyashiine.strawberrylib.api.objects.records.ParticleVelocity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;

public class MistFormComponent extends VampireFormChangeComponent implements ClientTickingComponent {
	private static final AttributeModifier WAYPOINT_TRANSMIT_RANGE_MODIFIER = new AttributeModifier(Nycto.id("mist_form_waypoint_transmit_range"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

	private static final ParticleVelocity PARTICLE_VELOCITY = ParticleVelocity.of(0.2);

	public MistFormComponent(Player obj) {
		super(obj);
	}

	@Override
	public void clientTick() {
		if (enabled && obj.slib$exists()) {
			if (obj.getRandom().nextInt(3) == 0) {
				SLibClientUtils.addParticles(obj, ParticleTypes.SMOKE, 1, ParticleAnchor.BODY);
			}
			if (obj.getRandom().nextInt(9) == 0) {
				SLibClientUtils.addParticles(obj, ParticleTypes.WHITE_SMOKE, 1, ParticleAnchor.BODY);
			}
		}
	}

	public void sync() {
		NyctoEntityComponents.MIST_FORM.sync(obj);
	}

	@Override
	public void toggle() {
		SLibUtils.addParticles(obj, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY, PARTICLE_VELOCITY);
		SLibUtils.addParticles(obj, ParticleTypes.WHITE_SMOKE, 16, ParticleAnchor.BODY, PARTICLE_VELOCITY);
		if (enabled) {
			drainTicks = 0;
		} else {
			NyctoEntityComponents.BLOOD.get(obj).drain(NyctoPowers.MIST_FORM.getCost(obj));
			drainTicks = POWER_DRAIN_TICKS;
		}
		enabled = !enabled;
		SLibUtils.applyAttributeModifier(obj, Attributes.WAYPOINT_TRANSMIT_RANGE, WAYPOINT_TRANSMIT_RANGE_MODIFIER, enabled);
		sync();
	}
}
