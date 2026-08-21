package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.BloodBarrierComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class BloodBarrierEvent implements ServerLivingEntityEvents.AllowDamage {
	public static void init() {
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(new BloodBarrierEvent());
	}

	@Override
	public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
		if (!source.is(DamageTypeTags.BYPASSES_ARMOR) && SLibUtils.isAttackingPlayerCooldownWithinThreshold(0.7F)) {
			BloodBarrierComponent bloodBarrier = NyctoEntityComponents.BLOOD_BARRIER.get(entity);
			if (bloodBarrier.getBarriers() > 0) {
				entity.hurtTime = entity.hurtDuration = 10;
				if (amount >= 3) {
					bloodBarrier.breakBarrier();
				} else {
					SLibUtils.playSound(entity, NyctoSoundEvents.BLOOD_BARRIER_HIT, 1, Mth.nextFloat(entity.getRandom(), 0.8F, 1.2F));
				}
				return false;
			}
		}
		return true;
	}
}
