/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.item;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.event.entity.VampireEvent;
import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.item.VampiricDaggerItem;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class VampiricDaggerEvent implements AfterDamageIncludingDeathEvent {
	private static final int DAMAGE_THRESHOLD = 2;

	@Override
	public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
		if (!blocked && damageTaken >= DAMAGE_THRESHOLD && entity.getType().isIn(ModEntityTypeTags.HAS_QUALITY_BLOOD) && source.getSource() instanceof LivingEntity attacker) {
			ItemStack stack = attacker.getMainHandStack();
			if (stack.contains(ModComponentTypes.BLOOD_CHARGE)) {
				int bloodCharge = VampiricDaggerItem.getBloodCharge(stack);
				if (!VampiricDaggerItem.isFull(bloodCharge)) {
					int drainAmount = MathHelper.ceil(damageTaken * VampireEvent.DrinkBlood.getArmorMultiplier(entity));
					int fillAmount = 0;
					if (drainAmount >= DAMAGE_THRESHOLD && ModEntityComponents.BLOOD.get(entity).drainAttack(drainAmount)) {
						fillAmount = drainAmount;
					}
					if (fillAmount > 0) {
						boolean player = stack.getOrDefault(ModComponentTypes.PLAYER_BLOOD, false), vampire = stack.getOrDefault(ModComponentTypes.VAMPIRE_BLOOD, false);
						if (entity.isPlayer()) {
							player = true;
						}
						if (NyctoAPI.isVampire(entity)) {
							if (bloodCharge == 0) {
								vampire = true;
							}
						} else {
							vampire = false;
						}
						VampiricDaggerItem.setBloodTypes(stack, player, vampire);
						VampiricDaggerItem.setBloodCharge(stack, Math.min(20, bloodCharge + fillAmount));
					}
				}
			}
		}
	}
}
