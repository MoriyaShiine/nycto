/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.power.PowerInstance;
import moriyashiine.nycto.common.NyctoAPIImpl;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModPowers;
import moriyashiine.nycto.common.tag.ModBlockTags;
import moriyashiine.nycto.common.tag.ModPowerTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.effect.VampireWardMobEffect;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import static moriyashiine.nycto.api.world.power.ActivePower.BLOCKED_COOLDOWN;

public class SunExposureComponent implements AutoSyncedComponent, CommonTickingComponent {
	public static final int MAX_EXPOSURE_TIME = 160;

	private final LivingEntity obj;
	private boolean shouldTick, exposed = false;
	private int exposureTime = 0;

	public SunExposureComponent(LivingEntity obj, boolean shouldTick) {
		this.obj = obj;
		this.shouldTick = shouldTick;
	}

	@Override
	public void readData(ValueInput input) {
		shouldTick = input.getBooleanOr("ShouldTick", false);
		exposed = input.getBooleanOr("Exposed", false);
		exposureTime = input.getIntOr("ExposureTime", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putBoolean("ShouldTick", shouldTick);
		output.putBoolean("Exposed", exposed);
		output.putInt("ExposureTime", exposureTime);
	}

	@Override
	public void tick() {
		if (shouldTick) {
			int max = 0;
			if (exposed) {
				boolean sunResistance = NyctoUtil.hasSunResistance(obj);
				boolean photophobia = obj instanceof Player player && NyctoAPI.hasPower(player, ModPowers.PHOTOPHOBIA);
				max = sunResistance && !photophobia ? 20 : MAX_EXPOSURE_TIME;
				if (exposureTime < max) {
					exposureTime = Math.min(max, exposureTime + getExposureTicks(sunResistance, photophobia));
				} else if (exposureTime >= MAX_EXPOSURE_TIME) {
					obj.igniteForSeconds(4);
				}
				if (obj instanceof Player player) {
					for (PowerInstance power : NyctoAPI.getPowers(player)) {
						if (power.getCooldown() <= 0 && power.is(ModPowerTags.VAMPIRE_CHOOSABLE)) {
							NyctoAPIImpl.setPowerCooldown(player, power.getPower(), BLOCKED_COOLDOWN);
						}
					}
				}
			}
			if (exposureTime > max) {
				exposureTime = Math.max(0, exposureTime - 4);
			}
		}
	}

	@Override
	public void serverTick() {
		tick();
		if (shouldTick) {
			boolean changed = false;
			boolean isExposed = updateExposed();
			if (exposed != isExposed) {
				exposed = isExposed;
				changed = true;
				sync();
			}
			if (exposed) {
				if (obj instanceof ServerPlayer player) {
					NyctoUtil.disableFormChangePowers(player.level(), player, null);
				}
				if (ModEntityComponents.HEAL_BLOCK.get(obj).getTicksToBlock() < -BLOCKED_COOLDOWN) {
					NyctoAPI.applyHealBlock(obj, -BLOCKED_COOLDOWN);
				}
				VampireWardMobEffect.applyAttributes(obj, true);
			} else if (changed) {
				VampireWardMobEffect.applyAttributes(obj, false);
			}
		}
	}

	@Override
	public void clientTick() {
		tick();
		if (isExposed() && (obj.getId() + obj.tickCount) % 8 == 0) {
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

	private int getExposureTicks(boolean sunResistance, boolean photophobia) {
		return !sunResistance && photophobia ? 8 : 1;
	}

	private boolean updateExposed() {
		if (NyctoAPI.hasRespawnLeniency(obj) || !obj.level().isBrightOutside() || !obj.slib$isSurvival() || obj.isInRain()) {
			return false;
		}
		if (obj.getSleepingPos().isPresent() && obj.level().getBlockState(obj.getSleepingPos().get()).is(ModBlockTags.COFFINS)) {
			return false;
		}
		return exposedAtPos(obj, obj.blockPosition());
	}

	public static boolean exposedAtPos(Entity entity, BlockPos pos) {
		for (int i = Mth.ceil(entity.getBbHeight()) - 1; i >= 0; i--) {
			if (entity.level().canSeeSky(pos.above(i))) {
				return true;
			}
		}
		return false;
	}
}
