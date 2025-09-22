/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.power.FormChanger;
import moriyashiine.nycto.api.power.PowerInstance;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.strawberrylib.api.event.ModifyMovementEvents;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class BloodrushComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final PlayerEntity obj;
	private int ticks = 0, leniencyTicks = 0;

	public BloodrushComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		ticks = readView.getInt("Ticks", 0);
		leniencyTicks = readView.getInt("LeniencyTicks", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putInt("Ticks", ticks);
		writeView.putInt("LeniencyTicks", leniencyTicks);
	}

	@Override
	public void tick() {
		if (isActive(false)) {
			ticks--;
			leniencyTicks = 20;
			obj.setVelocity(ModifyMovementEvents.MOVEMENT_VELOCITY.invoker().modify(obj.getRotationVector(), obj).multiply(2 / 3F));
			obj.onLanding();
			if (obj.isOnFire()) {
				obj.extinguishWithSound();
			}
		}
		if (leniencyTicks > 0) {
			leniencyTicks--;
		}
	}

	@Override
	public void serverTick() {
		tick();
		if (isActive(false)) {
			boolean disable = obj.horizontalCollision || obj.isSneaking();
			if (!disable) {
				for (PowerInstance power : NyctoAPI.getPowers(obj)) {
					if (power.getPower() instanceof FormChanger formChanger && formChanger != ModPowers.BLOODRUSH && formChanger.isFormActive(obj)) {
						disable = true;
						break;
					}
				}
			}
			if (disable) {
				ticks = 0;
				obj.useRiptide(1, 0, null);
				sync();
			}
		}
	}

	@Override
	public void clientTick() {
		tick();
		if (isActive(false)) {
			SLibClientUtils.addParticles(obj, ModParticleTypes.BLOOD, ModParticleTypes.BLOOD_PARTICLE_COUNT, ParticleAnchor.EYES);
		}
	}

	public void sync() {
		ModEntityComponents.BLOODRUSH.sync(obj);
	}

	public void use(int ticks) {
		obj.dismountVehicle();
		obj.useRiptide(ticks, 0, null);
		this.ticks = ticks;
		sync();
	}

	public boolean isActive(boolean useLeniency) {
		return (useLeniency ? leniencyTicks : ticks) > 0;
	}
}
