/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.power.FormChanger;
import moriyashiine.nycto.api.world.power.PowerInstance;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.strawberrylib.api.event.ModifyMovementEvents;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class BloodrushComponent implements AutoSyncedComponent, CommonTickingComponent {
	private final Player obj;
	private int ticks = 0, leniencyTicks = 0;

	public BloodrushComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		ticks = input.getIntOr("Ticks", 0);
		leniencyTicks = input.getIntOr("LeniencyTicks", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putInt("Ticks", ticks);
		output.putInt("LeniencyTicks", leniencyTicks);
	}

	@Override
	public void tick() {
		if (isActive(false)) {
			ticks--;
			leniencyTicks = 20;
			obj.setDeltaMovement(ModifyMovementEvents.MOVEMENT_DELTA.invoker().modify(obj.getLookAngle(), obj).scale(2 / 3F));
			obj.resetFallDistance();
			if (obj.isOnFire()) {
				obj.extinguishFire();
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
			boolean disable = obj.horizontalCollision || obj.isShiftKeyDown();
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
				obj.startAutoSpinAttack(1, 0, null);
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
		obj.removeVehicle();
		obj.startAutoSpinAttack(ticks, 0, null);
		this.ticks = ticks;
		sync();
	}

	public boolean isActive(boolean useLeniency) {
		return (useLeniency ? leniencyTicks : ticks) > 0;
	}
}
