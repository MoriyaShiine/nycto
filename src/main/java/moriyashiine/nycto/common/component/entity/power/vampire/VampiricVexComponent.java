/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class VampiricVexComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final VexEntity obj;
	private boolean isThralled = false;
	private int despawnTimer = 0;

	public VampiricVexComponent(VexEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		isThralled = readView.getBoolean("IsThralled", false);
		despawnTimer = readView.getInt("DespawnTimer", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putBoolean("IsThralled", isThralled);
		writeView.putInt("DespawnTimer", despawnTimer);
	}

	@Override
	public void serverTick() {
		if (isThralled) {
			if (obj.getOwner() == null || obj.getOwner().isDead() || obj.getOwner().getTarget() == null || obj.getOwner().getTarget().isDead() || ++despawnTimer == 600) {
				kill();
			} else {
				obj.setTarget(obj.getOwner().getTarget());
			}
		}
	}

	public boolean isThralled() {
		return isThralled;
	}

	public void setThralled() {
		isThralled = true;
		ModEntityComponents.VAMPIRIC_VEX.sync(obj);
	}

	private void kill() {
		SLibUtils.addParticles(obj, ParticleTypes.SMOKE, 8, ParticleAnchor.BODY);
		SLibUtils.playSound(obj, SoundEvents.ENTITY_VEX_DEATH);
		obj.discard();
	}
}
