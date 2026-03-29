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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class VampiricVexComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final Vex obj;
	private boolean hasOwner = false;
	private int despawnTimer = 0;

	public VampiricVexComponent(Vex obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		hasOwner = input.getBooleanOr("HasOwner", false);
		despawnTimer = input.getIntOr("DespawnTimer", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putBoolean("HasOwner", hasOwner);
		output.putInt("DespawnTimer", despawnTimer);
	}

	@Override
	public void serverTick() {
		if (hasOwner) {
			if (obj.getOwner() == null || obj.getOwner().isDeadOrDying() || obj.getOwner().getTarget() == null || obj.getOwner().getTarget().isDeadOrDying() || !ModEntityComponents.VAMPIRIC_THRALL.get(obj.getOwner()).hasOwner() || ++despawnTimer == 600) {
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
