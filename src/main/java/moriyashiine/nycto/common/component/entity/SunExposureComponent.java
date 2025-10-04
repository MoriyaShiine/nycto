/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.tag.ModBlockTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class SunExposureComponent implements AutoSyncedComponent, CommonTickingComponent {
	public static final int MAX_EXPOSURE_TIME = 320;

	private final LivingEntity obj;
	private boolean shouldTick, exposed = false;
	private int exposureTime = 0;

	public SunExposureComponent(LivingEntity obj, boolean shouldTick) {
		this.obj = obj;
		this.shouldTick = shouldTick;
	}

	@Override
	public void readData(ReadView readView) {
		shouldTick = readView.getBoolean("ShouldTick", false);
		exposed = readView.getBoolean("Exposed", false);
		exposureTime = readView.getInt("ExposureTime", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putBoolean("ShouldTick", shouldTick);
		writeView.putBoolean("Exposed", exposed);
		writeView.putInt("ExposureTime", exposureTime);
	}

	@Override
	public void tick() {
		if (shouldTick) {
			if (exposed) {
				if (exposureTime < MAX_EXPOSURE_TIME) {
					exposureTime = Math.min(MAX_EXPOSURE_TIME, exposureTime + getExposureTicks());
				} else {
					obj.setOnFireFor(4);
				}
			} else if (exposureTime > 0) {
				exposureTime = Math.max(0, exposureTime - 8);
			}
		}
	}

	@Override
	public void serverTick() {
		tick();
		if (shouldTick) {
			boolean isExposed = updateExposed();
			if (exposed != isExposed) {
				exposed = isExposed;
				sync();
			}
		}
	}

	@Override
	public void clientTick() {
		tick();
		if (exposureTime > 0 && exposureTime % 8 == 0) {
			SLibClientUtils.addParticles(obj, ParticleTypes.SMOKE, 1, ParticleAnchor.BODY);
		}
	}

	public void sync() {
		ModEntityComponents.SUN_EXPOSURE.sync(obj);
	}

	public void reset() {
		exposed = false;
		exposureTime = 0;
	}

	public boolean shouldTick() {
		return shouldTick;
	}

	public void setShouldTick(boolean shouldTick) {
		this.shouldTick = shouldTick;
	}

	public boolean isExposed() {
		return exposed;
	}

	public int getExposureTime() {
		return exposureTime;
	}

	private int getExposureTicks() {
		int ticks = obj instanceof PlayerEntity player && NyctoAPI.hasPower(player, ModPowers.PHOTOPHOBIA) ? 16 : 2;
		if (NyctoUtil.hasSunResistance(obj)) {
			ticks /= 2;
		}
		return ticks;
	}

	private boolean updateExposed() {
		if (NyctoAPI.hasRespawnLeniency(obj) || !obj.getEntityWorld().isDay() || !obj.canTakeDamage() || obj.isBeingRainedOn()) {
			return false;
		}
		if (obj.getSleepingPosition().isPresent() && obj.getEntityWorld().getBlockState(obj.getSleepingPosition().get()).isIn(ModBlockTags.COFFINS)) {
			return false;
		}
		return exposedAtPos(obj, obj.getBlockPos());
	}

	public static boolean exposedAtPos(Entity entity, BlockPos pos) {
		for (int i = MathHelper.ceil(entity.getHeight()) - 1; i >= 0; i--) {
			if (entity.getEntityWorld().isSkyVisible(pos.up(i))) {
				return true;
			}
		}
		return false;
	}
}
