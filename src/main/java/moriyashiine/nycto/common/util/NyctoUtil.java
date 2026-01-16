/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.util;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.power.ActivePower;
import moriyashiine.nycto.api.power.FormChanger;
import moriyashiine.nycto.api.power.PowerInstance;
import moriyashiine.nycto.common.init.ModDamageTypes;
import moriyashiine.nycto.common.init.ModParticleTypes;
import moriyashiine.nycto.common.init.ModStatusEffects;
import moriyashiine.nycto.common.power.vampire.DarkFormPower;
import moriyashiine.nycto.common.tag.ModDamageTypeTags;
import moriyashiine.nycto.common.tag.ModEnchantmentTags;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.VillagerGossipType;
import org.jetbrains.annotations.Nullable;

public class NyctoUtil {
	public static int truncatedWorldSeed = 0;

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean bypassesBloodVeil(DamageSource source) {
		if (source.getSource() instanceof LivingEntity attacker) {
			if (attacker.getType().isIn(ModEntityTypeTags.BYPASSES_BLOOD_VEIL) || EnchantmentHelper.hasAnyEnchantmentsIn(attacker.getMainHandStack(), ModEnchantmentTags.BYPASSES_BLOOD_VEIL)) {
				return true;
			}
		}
		return source.isIn(ModDamageTypeTags.BYPASSES_BLOOD_VEIL) || isVampireWeaknessItem(source) || NyctoAPI.isBeastForm(source);
	}

	public static boolean haltsVampireRegeneration(DamageSource source) {
		return source.isIn(ModDamageTypeTags.HALTS_VAMPIRE_REGENERATION) || isVampireWeaknessItem(source);
	}

	public static boolean isVampireWeaknessItem(DamageSource source) {
		if (SLibUtils.isAttackingPlayerCooldownWithinThreshold(0.7F)) {
			if (source.getSource() instanceof LivingEntity attacker && attacker.getMainHandStack().isIn(ModItemTags.VAMPIRE_WEAKNESSES)) {
				return true;
			}
			return source.getSource() instanceof PersistentProjectileEntity projectile && projectile.getItemStack().isIn(ModItemTags.VAMPIRE_WEAKNESSES);
		}
		return false;
	}

	public static boolean affectedByHurtsVampiresTag(Entity entity) {
		return NyctoAPI.isVampire(entity) && !DarkFormPower.isDarkFormActive(entity);
	}

	public static boolean getsMoreBlood(LivingEntity entity) {
		return getEquippedArmorPieces(entity, ModItemTags.VAMPIRE_ARMOR) >= 1;
	}

	public static boolean hasSunResistance(LivingEntity entity) {
		return getEquippedArmorPieces(entity, ModItemTags.VAMPIRE_ARMOR) >= 2;
	}

	public static boolean hasReducedPowerCost(LivingEntity entity) {
		return getEquippedArmorPieces(entity, ModItemTags.VAMPIRE_ARMOR) >= 3;
	}

	public static boolean hasHealBlockResistance(LivingEntity entity) {
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

	public static boolean isTargetable(@Nullable Entity entity) {
		return entity instanceof LivingEntity living && living.canTakeDamage();
	}

	public static boolean isVillager(Entity entity) {
		return entity instanceof VillagerEntity || entity instanceof WanderingTraderEntity;
	}

	public static int getEquippedArmorPieces(LivingEntity entity, TagKey<Item> tagKey) {
		int count = 0;
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot.isArmorSlot()) {
				if (entity.getEquippedStack(slot).isIn(tagKey)) {
					count++;
				}
			}
		}
		return count;
	}

	public static void notifyNearbyVillagers(LivingEntity living, PlayerEntity player, VillagerGossipType gossipType, int value) {
		living.getEntityWorld().getEntitiesByClass(VillagerEntity.class, new Box(living.getBlockPos()).expand(16), foundVillager -> living != foundVillager && !foundVillager.isSleeping() && !foundVillager.hasStatusEffect(ModStatusEffects.HYPNOTIZED) && foundVillager.canSee(player)).forEach(foundVillager -> foundVillager.getGossip().startGossip(player.getUuid(), gossipType, value));
	}

	public static void spawnBloodParticles(Entity entity) {
		((ServerWorld) entity.getEntityWorld()).spawnParticles(ModParticleTypes.BLOOD, entity.getX(), entity.getEyeY(), entity.getZ(), ModParticleTypes.BLOOD_PARTICLE_COUNT, entity.getWidth() / 2F, MathHelper.nextFloat(entity.getEntityWorld().getRandom(), -0.1F, 0.1F), entity.getWidth() / 2F, 0);
	}

	public static void damageWithToxicTouch(LivingEntity living, float amount) {
		if (living.getEntityWorld() instanceof ServerWorld world && living.damage(world, world.getDamageSources().create(ModDamageTypes.TOXIC_TOUCH), amount)) {
			living.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 110, 1));
		}
	}

	public static void disableFormChangePowers(ServerWorld world, ServerPlayerEntity player, @Nullable PowerInstance currentPower) {
		for (PowerInstance instance : NyctoAPI.getPowers(player)) {
			if (currentPower != instance && instance.getPower() instanceof FormChanger formChanger && formChanger.isFormActive(player)) {
				formChanger.disable(world, player);
			}
		}
	}

	public static void usePower(ServerWorld world, ServerPlayerEntity player, PowerInstance powerInstance) {
		if (canUsePower(player, powerInstance)) {
			ActivePower activePower = (ActivePower) powerInstance.getPower();
			if (activePower instanceof FormChanger formChanger && !formChanger.isFormActive(player)) {
				disableFormChangePowers(world, player, powerInstance);
			}
			activePower.playUseSound(player);
			if (activePower.shouldApplyCooldown(player)) {
				NyctoAPI.setPowerCooldown(player, activePower, activePower.getCooldown());
			}
			activePower.use(world, player);
			player.swingHand(Hand.MAIN_HAND, true);
		}
	}

	public static boolean canUsePower(PlayerEntity player, PowerInstance powerInstance) {
		return powerInstance.getCooldown() == 0 && powerInstance.getPower() instanceof ActivePower activePower && activePower.canUse(player);
	}
}
