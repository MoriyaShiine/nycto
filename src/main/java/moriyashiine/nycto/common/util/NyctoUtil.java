/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.util;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.power.ActivePower;
import moriyashiine.nycto.api.world.power.FormChanger;
import moriyashiine.nycto.api.world.power.PowerInstance;
import moriyashiine.nycto.common.init.ModDamageTypes;
import moriyashiine.nycto.common.init.ModMobEffects;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.tag.ModDamageTypeTags;
import moriyashiine.nycto.common.tag.ModEnchantmentTags;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.nycto.common.world.power.vampire.DarkFormPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

public class NyctoUtil {
	public static int truncatedWorldSeed = 0;

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean bypassesBloodVeil(DamageSource source) {
		if (source.getDirectEntity() instanceof LivingEntity attacker) {
			if (attacker.is(ModEntityTypeTags.BYPASSES_BLOOD_VEIL) || EnchantmentHelper.hasTag(attacker.getMainHandItem(), ModEnchantmentTags.BYPASSES_BLOOD_VEIL)) {
				return true;
			}
		}
		return source.is(ModDamageTypeTags.BYPASSES_BLOOD_VEIL) || isVampireWeaknessItem(source) || NyctoAPI.isBeastForm(source);
	}

	public static boolean haltsVampireRegeneration(DamageSource source) {
		return source.is(ModDamageTypeTags.HALTS_VAMPIRE_REGENERATION) || isVampireWeaknessItem(source);
	}

	public static boolean isVampireWeaknessItem(DamageSource source) {
		if (SLibUtils.isAttackingPlayerCooldownWithinThreshold(0.7F)) {
			if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.getMainHandItem().is(ModItemTags.VAMPIRE_WEAKNESSES)) {
				return true;
			}
			return source.getDirectEntity() instanceof AbstractArrow arrow && arrow.getPickupItemStackOrigin().is(ModItemTags.VAMPIRE_WEAKNESSES);
		}
		return false;
	}

	public static boolean affectedByHurtsVampiresTag(Entity entity) {
		return NyctoAPI.isVampire(entity) && !DarkFormPower.isDarkFormActive(entity);
	}

	public static boolean hasHealBlockResistance(LivingEntity entity) {
		return getEquippedArmorPieces(entity, ModItemTags.VAMPIRE_ARMOR) >= 1 || DarkFormPower.isDarkFormActive(entity);
	}

	public static boolean getsMoreBlood(LivingEntity entity) {
		return getEquippedArmorPieces(entity, ModItemTags.VAMPIRE_ARMOR) >= 2;
	}

	public static boolean hasReducedPowerCost(LivingEntity entity) {
		return getEquippedArmorPieces(entity, ModItemTags.VAMPIRE_ARMOR) >= 3;
	}

	public static boolean hasSunResistance(LivingEntity entity) {
		return getEquippedArmorPieces(entity, ModItemTags.VAMPIRE_ARMOR) >= 4;
	}

	public static boolean hasBloodDrainResistance(LivingEntity entity) {
		return getEquippedArmorPieces(entity, ModItemTags.VAMPIRE_HUNTER_ARMOR) >= 1;
	}

	public static boolean hasGarlicAura(LivingEntity entity) {
		return getEquippedArmorPieces(entity, ModItemTags.VAMPIRE_HUNTER_ARMOR) >= 2;
	}

	public static boolean hasReducedWoodenStakeCooldown(LivingEntity entity) {
		return getEquippedArmorPieces(entity, ModItemTags.VAMPIRE_HUNTER_ARMOR) >= 3;
	}

	public static boolean hasVampireCriticalHitImmunity(LivingEntity entity) {
		return getEquippedArmorPieces(entity, ModItemTags.VAMPIRE_HUNTER_ARMOR) >= 4;
	}

	public static boolean isSurvivalNullable(@Nullable Entity entity) {
		return entity instanceof LivingEntity living && living.slib$isSurvival();
	}

	public static boolean isVillager(Entity entity) {
		return entity instanceof Villager || entity instanceof WanderingTrader;
	}

	public static int getEquippedArmorPieces(LivingEntity entity, TagKey<Item> tagKey) {
		int count = 0;
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot.isArmor()) {
				if (entity.getItemBySlot(slot).is(tagKey)) {
					count++;
				}
			}
		}
		return count;
	}

	public static void notifyNearbyVillagers(LivingEntity living, Player player, GossipType type, int value) {
		living.level().getEntitiesOfClass(Villager.class, new AABB(living.blockPosition()).inflate(16), foundVillager -> living != foundVillager && !foundVillager.isSleeping() && !foundVillager.hasEffect(ModMobEffects.HYPNOTIZED) && foundVillager.hasLineOfSight(player)).forEach(foundVillager -> foundVillager.getGossips().add(player.getUUID(), type, value));
	}

	public static void spawnBloodParticles(Entity entity) {
		((ServerLevel) entity.level()).sendParticles(ModParticleTypes.BLOOD, entity.getX(), entity.getEyeY(), entity.getZ(), ModParticleTypes.BLOOD_PARTICLE_COUNT, entity.getBbWidth() / 2F, Mth.nextFloat(entity.level().getRandom(), -0.1F, 0.1F), entity.getBbWidth() / 2F, 0);
	}

	public static void hurtWithToxicTouch(LivingEntity living, float amount) {
		if (living.level() instanceof ServerLevel level && living.hurtServer(level, level.damageSources().source(ModDamageTypes.TOXIC_TOUCH), amount)) {
			living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 110, 1));
		}
	}

	public static void disableFormChangePowers(ServerLevel level, ServerPlayer player, @Nullable PowerInstance currentPower) {
		for (PowerInstance instance : NyctoAPI.getPowers(player)) {
			if (currentPower != instance && instance.getPower() instanceof FormChanger formChanger && formChanger.isFormActive(player)) {
				formChanger.disable(level, player);
			}
		}
	}

	public static void usePower(ServerLevel level, ServerPlayer player, PowerInstance powerInstance) {
		if (canUsePower(player, powerInstance)) {
			ActivePower activePower = (ActivePower) powerInstance.getPower();
			if (activePower instanceof FormChanger formChanger && !formChanger.isFormActive(player)) {
				disableFormChangePowers(level, player, powerInstance);
			}
			activePower.playUseSound(player);
			if (activePower.shouldApplyCooldown(player)) {
				NyctoAPI.setPowerCooldown(player, activePower, activePower.getCooldown());
			}
			activePower.use(level, player);
			player.swing(InteractionHand.MAIN_HAND, true);
		}
	}

	public static boolean canUsePower(Player player, PowerInstance powerInstance) {
		return powerInstance.getCooldown() == 0 && powerInstance.getPower() instanceof ActivePower activePower && activePower.canUse(player);
	}
}
