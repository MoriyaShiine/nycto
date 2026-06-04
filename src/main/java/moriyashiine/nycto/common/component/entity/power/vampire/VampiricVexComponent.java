/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.monster.Vex;
import moriyashiine.nycto.common.component.NyctoValueInput;
import moriyashiine.nycto.common.component.NyctoValueOutput;

public class VampiricVexComponent implements moriyashiine.nycto.common.component.NyctoServerTickingComponent {
	private final Vex obj;
	private boolean hasOwner = false;
	private int despawnTimer = 0;

	public VampiricVexComponent(Vex obj) {
		this.obj = obj;
	}

	@Override
	public void readData(NyctoValueInput input) {
		hasOwner = input.getBooleanOr("HasOwner", false);
		despawnTimer = input.getIntOr("DespawnTimer", 0);
	}

	@Override
	public void writeData(NyctoValueOutput output) {
		output.putBoolean("HasOwner", hasOwner);
		output.putInt("DespawnTimer", despawnTimer);
	}

	@Override
	public void serverTick() {
		if (hasOwner) {
			if (obj.getOwner() == null || obj.getOwner().getTarget() == null || !ModEntityComponents.VAMPIRIC_THRALL.get(obj.getOwner()).hasOwner() || ++despawnTimer == 600) {
				kill();
			} else {
				obj.setTarget(obj.getOwner().getTarget());
			}
		}
	}

	public boolean hasOwner() {
		return hasOwner;
	}

	public void setOwned() {
		hasOwner = true;
		ModEntityComponents.VAMPIRIC_VEX.sync(obj);
	}

	private void kill() {
		SLibUtils.addParticles(obj, ParticleTypes.SMOKE, 8, ParticleAnchor.BODY);
		SLibUtils.playSound(obj, SoundEvents.VEX_DEATH);
		obj.discard();
	}
}
