/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.common.init.NyctoDamageTypes;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoParticleTypes;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class BloodComponent implements AutoSyncedComponent, ServerTickingComponent {
	public static final int MAX_BLOOD = 100;
	// keep as a multiple of 20
	public static final int REGEN_TIME = 100;

	private final LivingEntity obj;
	private boolean regeneratesNaturally = true;
	private int blood = MAX_BLOOD, bleedTicks = 0, regenerationBlockTicks = 0;
	private long lastGameTime = 0, lastFillTime = 0;

	public BloodComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		regeneratesNaturally = input.getBooleanOr("RegeneratesNaturally", true);
		blood = input.getIntOr("Blood", MAX_BLOOD);
		bleedTicks = input.getIntOr("BleedTicks", 0);
		regenerationBlockTicks = input.getIntOr("RegenerationBlockTicks", 0);
		lastGameTime = input.getLongOr("LastGameTime", 0);
		lastFillTime = input.getLongOr("LastFillTime", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putBoolean("RegeneratesNaturally", regeneratesNaturally);
		output.putInt("Blood", blood);
		output.putInt("BleedTicks", bleedTicks);
		output.putInt("RegenerationBlockTicks", regenerationBlockTicks);
		output.putLong("LastGameTime", lastGameTime);
		output.putLong("LastFillTime", lastFillTime);
	}

	@Override
	public void serverTick() {
		tickUnloaded();
		lastGameTime = obj.level().getGameTime();
		if (obj.level().getGameTime() % 20 == 0 && obj.level().getDifficulty() == Difficulty.PEACEFUL) {
			fill(5);
		}
		if (bleedTicks > 0 && --bleedTicks % 20 == 0) {
			NyctoUtil.spawnBloodParticles(obj);
			obj.hurt(obj.damageSources().source(NyctoDamageTypes.BLEED), 1);
		}
		if (regenerationBlockTicks > 0) {
			regenerationBlockTicks--;
		}
		if (!regeneratesNaturally) {
			return;
		}
		if (canFill() && obj.level().getGameTime() % 20 == 0 && obj.isAlive()) {
			if (lowBlood()) {
				obj.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 1, true, false));
				obj.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, true, false));
				if (criticalBlood()) {
					SLibUtils.addParticles(obj, NyctoParticleTypes.BLOOD, NyctoParticleTypes.BLOOD_PARTICLE_COUNT, ParticleAnchor.BODY);
					obj.hurt(obj.damageSources().source(NyctoDamageTypes.BLEED), 2);
				}
			}
			if (criticalBlood()) {
				obj.removeEffect(MobEffects.REGENERATION);
			}
			if (regenerationBlockTicks <= 0 && obj.level().getGameTime() % REGEN_TIME == 0) {
				fill(obj.isSleeping() ? 5 : 1);
			}
		}
	}

	public void sync() {
		NyctoEntityComponents.BLOOD.sync(obj);
	}

	private void tickUnloaded() {
		int ticksSinceLastLoad = (int) (obj.level().getGameTime() - lastGameTime - 1);
		if (ticksSinceLastLoad > 0) {
			regenerationBlockTicks = Math.max(0, regenerationBlockTicks - ticksSinceLastLoad);
			if (regeneratesNaturally) {
				fill(((ticksSinceLastLoad - regenerationBlockTicks) / REGEN_TIME) * (obj.isSleeping() ? 5 : 1));
			}
		}
	}

	public boolean regeneratesNaturally() {
		return regeneratesNaturally;
	}

	public void setRegeneratesNaturally(boolean regeneratesNaturally) {
		this.regeneratesNaturally = regeneratesNaturally;
	}

	public int getBlood() {
		return blood;
	}

	public void setBleedTicks(int bleedTicks) {
		this.bleedTicks = bleedTicks;
	}

	public void setRegenerationBlockTicks(int regenerationBlockTicks) {
		this.regenerationBlockTicks = regenerationBlockTicks;
	}

	public boolean canFill() {
		return blood < MAX_BLOOD;
	}

	public boolean aboveHalfBlood() {
		return blood > MAX_BLOOD / 2;
	}

	public boolean lowBlood() {
		return blood < MAX_BLOOD / 5;
	}

	public boolean criticalBlood() {
		return blood < MAX_BLOOD / 10;
	}

	public long getTicksSinceLastFill() {
		return obj.level().getGameTime() - lastFillTime;
	}

	public boolean fill(int amount) {
		if (canFill()) {
			blood = Math.min(MAX_BLOOD, blood + amount);
			lastFillTime = obj.level().getGameTime();
			sync();
			return true;
		}
		return false;
	}

	public boolean drainAttack(int amount) {
		if (amount == 0 || blood == 0 || obj.isInvulnerable()) {
			return false;
		}
		if (obj.getRandom().nextFloat() <= 2 / 3F && NyctoUtil.hasBloodDrainResistance(obj)) {
			SLibUtils.playSound(obj, NyctoSoundEvents.GENERIC_BLOOD_DRAIN_BLOCKED, 1, Mth.nextFloat(obj.getRandom(), 0.95F, 1.05F));
			return false;
		}
		boolean drain = drain(amount, true);
		if (drain) {
			NyctoUtil.spawnBloodParticles(obj);
			setRegenerationBlockTicks(200);
		}
		return drain;
	}

	public boolean drain(int amount) {
		return drain(amount, false);
	}

	private boolean drain(int amount, boolean alwaysAllow) {
		if (amount <= 0) {
			return true;
		}
		if (alwaysAllow || blood - amount >= 0) {
			if (obj.hasInfiniteMaterials()) {
				return true;
			}
			blood = Math.max(0, blood - amount);
			sync();
			return true;
		}
		return false;
	}
}
