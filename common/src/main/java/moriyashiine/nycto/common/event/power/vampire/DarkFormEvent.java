package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.tag.NyctoPowerTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.power.vampire.DarkFormPower;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class DarkFormEvent implements ModifyDamageTakenEvent {
	public static void init() {
		ModifyDamageTakenEvent.MULTIPLY_BASE.register(new DarkFormEvent());
	}

	@Override
	public float modify(Phase phase, LivingEntity victim, ServerLevel level, DamageSource source) {
		if (phase == Phase.FINAL && DarkFormPower.isDarkFormActive(victim) && !NyctoUtil.bypassesBloodVeil(source) && !NyctoUtil.haltsVampireRegeneration(source)) {
			return 1 - (0.32F / 3) * NyctoAPI.getWeaknesses(victim, NyctoPowerTags.VAMPIRE_CHOOSABLE);
		}
		return 1;
	}
}
