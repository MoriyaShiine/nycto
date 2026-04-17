/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.client.payload.AddBloodBarrierParticlesPayload;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class BloodBarrierComponent implements AutoSyncedComponent, CommonTickingComponent {
	private static final int MAX_BARRIERS = 3, MAX_TICKS = 300;

	private final LivingEntity obj;
	private int barriers = 0, ticks = 0;

	public BloodBarrierComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		barriers = input.getIntOr("Barriers", 0);
		ticks = input.getIntOr("Ticks", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putInt("Barriers", barriers);
		output.putInt("Ticks", ticks);
	}

	@Override
	public void tick() {
		if (ticks > 0) {
			if (ticks > 1 && NyctoAPI.isSunExposed(obj)) {
				ticks = 1;
			}
			if (--ticks == 0) {
				if (!obj.level().isClientSide()) {
					for (int i = 0; i < barriers; i++) {
						addParticles(i);
					}
				}
				barriers = 0;
			}
		}
	}

	public void sync() {
		ModEntityComponents.BLOOD_BARRIER.sync(obj);
	}

	public int getBarriers() {
		return barriers;
	}

	public void breakBarrier() {
		SLibUtils.playSound(obj, ModSoundEvents.POWER_BLOOD_BARRIER_BREAK, 1, Mth.nextFloat(obj.getRandom(), 0.8F, 1.2F));
		barriers--;
		addParticles(barriers);
		if (barriers == 0) {
			ticks = 0;
		}
		sync();
	}

	public void use() {
		for (int i = 0; i < MAX_BARRIERS; i++) {
			addParticles(i);
		}
		barriers = MAX_BARRIERS;
		ticks = MAX_TICKS;
		sync();
	}

	private void addParticles(int barrier) {
		PlayerLookup.tracking(obj).forEach(receiver -> AddBloodBarrierParticlesPayload.send(receiver, obj, barrier));
		if (obj instanceof ServerPlayer player) {
			AddBloodBarrierParticlesPayload.send(player, obj, barrier);
		}
	}

	public static float getHeightOffset(int barrier, float height) {
		return switch (barrier) {
			case 0 -> height * 0.5F;
			case 1 -> height * 0.2F;
			default -> height * 0.8F;
		};
	}
}
