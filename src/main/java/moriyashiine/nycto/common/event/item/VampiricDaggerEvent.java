/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.item;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.event.entity.VampireEvent;
import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.world.item.VampiricDaggerItem;
import moriyashiine.nycto.common.world.item.crafting.BloodExtractionRecipe;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;

public class VampiricDaggerEvent implements AfterDamageIncludingDeathEvent {
	private static final int DAMAGE_THRESHOLD = 2;

	@Override
	public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
		if (!blocked && modifiedDamage >= DAMAGE_THRESHOLD && victim.is(ModEntityTypeTags.HAS_QUALITY_BLOOD) && source.getDirectEntity() instanceof LivingEntity attacker) {
			ItemStack stack = attacker.getMainHandItem();
			if (stack.has(ModComponentTypes.BLOOD_CHARGE)) {
				int bloodCharge = VampiricDaggerItem.getBloodCharge(stack);
				if (!VampiricDaggerItem.isFull(bloodCharge)) {
					int drainAmount = Mth.ceil(modifiedDamage * VampireEvent.DrinkBlood.getArmorMultiplier(victim));
					int fillAmount = 0;
					if (drainAmount >= DAMAGE_THRESHOLD && ModEntityComponents.BLOOD.get(victim).drainAttack(drainAmount)) {
						fillAmount = drainAmount;
					}
					if (fillAmount > 0) {
						boolean player = stack.getOrDefault(ModComponentTypes.PLAYER_BLOOD, false), vampire = stack.getOrDefault(ModComponentTypes.VAMPIRE_BLOOD, false);
						if (victim.slib$isPlayer()) {
							player = true;
						}
						if (NyctoAPI.isVampire(victim)) {
							if (bloodCharge == 0) {
								vampire = true;
							}
						} else {
							vampire = false;
						}
						VampiricDaggerItem.setBloodTypes(stack, player, vampire);
						VampiricDaggerItem.setBloodCharge(stack, Math.min(20, bloodCharge + fillAmount));
						if (attacker instanceof Player playerAttacker && VampiricDaggerItem.isFull(stack)) {
							ItemStack bottle = playerAttacker.getOffhandItem();
							if (bottle.is(Items.GLASS_BOTTLE)) {
								ItemStack bloodBottle = BloodExtractionRecipe.getCraftingResult(stack);
								ItemStack filled = ItemUtils.createFilledResult(bottle, playerAttacker, bloodBottle);
								playerAttacker.setItemInHand(InteractionHand.OFF_HAND, filled);
								VampiricDaggerItem.extractBlood(attacker, stack, bloodBottle);
								SLibUtils.playSound(playerAttacker, ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value(), 0.8F, 1);
							}
						}
					}
				}
			}
		}
	}
}
