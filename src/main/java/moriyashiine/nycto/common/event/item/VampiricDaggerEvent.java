/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.item;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.NyctoDataComponents;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoItems;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.item.VampiricDaggerItem;
import moriyashiine.nycto.common.world.item.crafting.BloodExtractionRecipe;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class VampiricDaggerEvent {
	public static void init() {
		AfterDamageIncludingDeathEvent.EVENT.register(new Damage());
		EnchantmentEvents.ALLOW_ENCHANTING.register(new Enchant());
	}

	private static class Damage implements AfterDamageIncludingDeathEvent {
		private static final int DAMAGE_THRESHOLD = 2;

		@Override
		public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
			if (!blocked && modifiedDamage >= DAMAGE_THRESHOLD && NyctoAPI.hasQualityBlood(victim) && source.getDirectEntity() instanceof LivingEntity attacker) {
				ItemStack stack = attacker.getMainHandItem();
				if (stack.has(NyctoDataComponents.BLOOD_CHARGE)) {
					int bloodCharge = VampiricDaggerItem.getBloodCharge(stack);
					if (!VampiricDaggerItem.isFull(bloodCharge)) {
						int drainAmount = Mth.ceil(modifiedDamage * NyctoUtil.getArmorMultiplier(victim));
						int fillAmount = 0;
						if (drainAmount >= DAMAGE_THRESHOLD && NyctoEntityComponents.BLOOD.get(victim).drainAttack(drainAmount)) {
							fillAmount = drainAmount;
						}
						if (fillAmount > 0) {
							boolean player = stack.getOrDefault(NyctoDataComponents.PLAYER_BLOOD, false), vampire = stack.getOrDefault(NyctoDataComponents.VAMPIRE_BLOOD, false);
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
									SLibUtils.playSound(playerAttacker, NyctoSoundEvents.BLOOD_BOTTLE_DRINK.value(), 0.8F, 1);
								}
							}
						}
					}
				}
			}
		}
	}

	private static class Enchant implements EnchantmentEvents.AllowEnchanting {
		@Override
		public TriState allowEnchanting(Holder<Enchantment> enchantment, ItemStack target, EnchantingContext enchantingContext) {
			if (target.is(NyctoItems.VAMPIRIC_DAGGER)) {
				if (enchantment.is(Enchantments.MENDING) || enchantment.is(Enchantments.UNBREAKING)) {
					return TriState.FALSE;
				}
			}
			return TriState.DEFAULT;
		}
	}
}
