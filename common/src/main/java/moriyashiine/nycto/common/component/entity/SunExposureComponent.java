package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoGameRules;
import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.tag.NyctoBlockTags;
import moriyashiine.nycto.common.tag.NyctoPowerTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.util.VampireSunExposureMode;
import moriyashiine.nycto.common.world.effect.VampireWardMobEffect;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
	public static final int MAX_EXPOSURE_TIME = 320, MIN_DEBUFF_EXPOSURE_TIME = 40;

	private final LivingEntity obj;
	private VampireSunExposureMode vampireSunExposureMode = VampireSunExposureMode.NORMAL;
	private boolean shouldTick, exposed = false;
	private int exposureTime = 0;

	public SunExposureComponent(LivingEntity obj, boolean shouldTick) {
		this.obj = obj;
		this.shouldTick = shouldTick;
	}

	@Override
	public void readData(ValueInput input) {
		vampireSunExposureMode = input.read("VampireSunExposureMode", VampireSunExposureMode.CODEC).orElse(VampireSunExposureMode.NORMAL);
		shouldTick = input.getBooleanOr("ShouldTick", false);
		exposed = input.getBooleanOr("Exposed", false);
		exposureTime = input.getIntOr("ExposureTime", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.store("VampireSunExposureMode", VampireSunExposureMode.CODEC, vampireSunExposureMode);
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
				boolean pyrophobia = obj instanceof Player player && NyctoAPI.hasPower(player, NyctoPowers.PYROPHOBIA);
				boolean cappedBurnTime = !vampireSunExposureMode.burn && sunResistance && !pyrophobia;
				max = cappedBurnTime ? MIN_DEBUFF_EXPOSURE_TIME : MAX_EXPOSURE_TIME;
				if (exposureTime < max) {
					exposureTime = Math.min(max, exposureTime + getExposureTicks(sunResistance, pyrophobia, cappedBurnTime));
				} else if (exposureTime >= MAX_EXPOSURE_TIME) {
					obj.igniteForSeconds(4);
				}
				if (vampireSunExposureMode.debuff && obj instanceof Player player) {
					NyctoUtil.blockPowers(player, power -> power.is(NyctoPowerTags.VAMPIRE_CHOOSABLE));
				}
			}
			if (exposureTime > max) {
				exposureTime = Math.max(0, exposureTime - 8);
			}
		}
	}

	@Override
	public void serverTick() {
		tick();
		if (shouldTick) {
			if (tickGameRule() || tickExposed()) {
				sync();
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
		NyctoEntityComponents.SUN_EXPOSURE.sync(obj);
	}

	public void reset() {
		exposed = false;
		exposureTime = 0;
	}

	public boolean hasVampireSunDebuff() {
		return vampireSunExposureMode.debuff;
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

	private int getExposureTicks(boolean sunResistance, boolean pyrophobia, boolean cappedBurnTime) {
		if (cappedBurnTime) {
			return 2;
		}
		int ticks = pyrophobia ? 8 : 2;
		if (sunResistance) {
			ticks /= 2;
		}
		return ticks;
	}

	private boolean tickGameRule() {
		boolean changed = false;
		VampireSunExposureMode mode = ((ServerLevel) obj.level()).getGameRules().get(NyctoGameRules.VAMPIRE_SUN_EXPOSURE_MODE);
		if (vampireSunExposureMode != mode) {
			vampireSunExposureMode = mode;
			changed = true;
		}
		return changed;
	}

	private boolean tickExposed() {
		boolean changed = false;
		boolean isExposed = updateExposed();
		if (exposed != isExposed) {
			exposed = isExposed;
			changed = true;
		}
		if (vampireSunExposureMode.debuff) {
			if (exposed) {
				if (obj instanceof ServerPlayer player) {
					NyctoUtil.disableFormChangePowers(player.level(), player, null);
				}
				if (NyctoEntityComponents.HEAL_BLOCK.get(obj).getTicks() < -BLOCKED_COOLDOWN) {
					NyctoAPI.applyHealBlock(obj, -BLOCKED_COOLDOWN);
				}
			}
			VampireWardMobEffect.applyAttributes(obj, exposureTime >= MIN_DEBUFF_EXPOSURE_TIME);
		}
		return changed;
	}

	private boolean updateExposed() {
		if (NyctoAPI.hasRespawnLeniency(obj) || !obj.level().isBrightOutside() || !obj.slib$isSurvival() || obj.isInRain()) {
			return false;
		}
		if (obj.getSleepingPos().isPresent() && obj.level().getBlockState(obj.getSleepingPos().get()).is(NyctoBlockTags.COFFINS)) {
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
