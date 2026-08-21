package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.NyctoDamageTypes;
import moriyashiine.nycto.common.init.NyctoMobEffects;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class MobEffectEvent {
	public static void init() {
		AfterDamageIncludingDeathEvent.EVENT.register(new VampireWard());
		AfterDamageIncludingDeathEvent.EVENT.register(new Stunned());
	}

	private static class VampireWard implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
			if (!blocked && !source.is(NyctoDamageTypes.TOXIC_TOUCH) && source.getDirectEntity() instanceof LivingEntity attacker && victim != attacker) {
				if (victim.hasEffect(NyctoMobEffects.VAMPIRE_WARD) && NyctoAPI.isVampire(attacker)) {
					attacker.hurt(attacker.damageSources().source(NyctoDamageTypes.TOXIC_TOUCH, victim), Mth.nextFloat(victim.getRandom(), 1, 3));
				}
			}
		}
	}

	private static class Stunned implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
			if (!blocked) {
				victim.removeEffect(NyctoMobEffects.STUNNED);
			}
		}
	}
}
