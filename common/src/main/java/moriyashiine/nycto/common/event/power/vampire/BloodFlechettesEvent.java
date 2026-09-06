package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class BloodFlechettesEvent implements AfterDamageIncludingDeathEvent {
	public static void init() {
		AfterDamageIncludingDeathEvent.EVENT.register(new BloodFlechettesEvent());
	}

	@Override
	public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
		if (!blocked && NyctoAPI.hasBlood(victim) && source.getDirectEntity() instanceof LivingEntity attacker && attacker.getHealth() < attacker.getMaxHealth() && NyctoEntityComponents.HEAL_BLOCK.get(victim).canStealLife(attacker)) {
			int drainAmount = Mth.floor(Math.min(modifiedDamage * 0.2, NyctoAPI.getBlood(victim)));
			NyctoAPI.drainBloodAttack(victim, drainAmount);
			attacker.heal(drainAmount);
			SLibUtils.playSound(attacker, NyctoSoundEvents.BLOOD_FLECHETTES_LIFE_DRAIN);
		}
	}
}
