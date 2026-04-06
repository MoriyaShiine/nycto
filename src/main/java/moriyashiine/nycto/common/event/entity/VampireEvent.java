/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.tag.ModBlockTags;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.nycto.common.tag.ModMobEffectTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.event.EatFoodEvent;
import moriyashiine.strawberrylib.api.event.ModifyMovementEvents;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.effect.EffectEventContext;
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class VampireEvent {
	public static class BloodVeil implements ServerLivingEntityEvents.AllowDeath {
		@Override
		public boolean allowDeath(LivingEntity entity, DamageSource damageSource, float damageAmount) {
			if (entity.getHealth() - damageAmount <= 0 && NyctoAPI.isVampire(entity) && !NyctoUtil.bypassesBloodVeil(damageSource) && !hasThinBlood(entity)) {
				BloodComponent bloodComponent = ModEntityComponents.BLOOD.get(entity);
				if (bloodComponent.drain(Mth.floor(damageAmount * DrinkBlood.getArmorMultiplier(entity)))) {
					NyctoAPI.applyHealBlock(entity, 60);
					entity.setHealth(1);
					return false;
				}
			}
			return true;
		}

		private static boolean hasThinBlood(LivingEntity living) {
			return living instanceof Player player && NyctoAPI.hasPower(player, ModPowers.THIN_BLOOD);
		}
	}

	public static class ChargeJump implements ModifyMovementEvents.JumpDelta {
		@Override
		public Vec3 modify(Vec3 delta, LivingEntity entity) {
			if (entity instanceof Player player && NyctoAPI.isVampire(player)) {
				float boostProgress = ModEntityComponents.VAMPIRE_CHARGE_JUMP.get(entity).getBoostProgress();
				if (boostProgress > 0) {
					if (boostProgress > 0.25F && entity.level() instanceof ServerLevel level) {
						level.sendParticles(ModParticleTypes.BLOOD, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 8, entity.getBbWidth() / 2, 0, entity.getBbWidth() / 2, 0.15);
						SLibUtils.playSound(entity, SoundEvents.SLIME_BLOCK_FALL, 1, 0.75F);
						entity.gameEvent(GameEvent.ENTITY_ACTION);
					}
					return delta.add(0, boostProgress * 0.34F, 0);
				}
			}
			return delta;
		}
	}

	public static class DrinkBlood implements UseEntityCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
			if (hand == InteractionHand.MAIN_HAND && player.isShiftKeyDown() && player.slib$exists() && player.getItemInHand(hand).isEmpty() && !entity.is(ModEntityTypeTags.HAS_NO_BLOOD) && entity instanceof LivingEntity living && living.hurtTime == 0 && living.isAlive() && !living.hasInfiniteMaterials() && NyctoAPI.isVampire(player)) {
				boolean qualityBlood = entity.is(ModEntityTypeTags.HAS_QUALITY_BLOOD);
				if (!qualityBlood && NyctoAPI.hasPower(player, ModPowers.RICH_TASTES)) {
					return InteractionResult.PASS;
				}
				VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
				if (vampiricThrallComponent != null && vampiricThrallComponent.isOwner(player)) {
					return InteractionResult.PASS;
				}
				BloodComponent playerBloodComponent = ModEntityComponents.BLOOD.get(player);
				BloodComponent livingBloodComponent = ModEntityComponents.BLOOD.get(living);
				int fillAmount = (qualityBlood ? 5 : 1) + (NyctoUtil.getsMoreBlood(player) ? 2 : 0);
				int drainAmount = qualityBlood ? 10 : 25;
				double armorMultiplier = getArmorMultiplier(living);
				fillAmount = Mth.ceil(fillAmount * armorMultiplier);
				drainAmount = Mth.ceil(drainAmount * armorMultiplier);
				if (fillAmount > 0 && playerBloodComponent.canFill() && livingBloodComponent.getBlood() > 0) {
					if (level instanceof ServerLevel serverWorld) {
						player.swing(InteractionHand.MAIN_HAND, true);
						if (canSafelyDrain(player, living, livingBloodComponent, drainAmount)) {
							living.hurtTime = living.hurtDuration = 10;
							if (NyctoUtil.isVillager(living)) {
								NyctoUtil.notifyNearbyVillagers(living, player, GossipType.MINOR_NEGATIVE, 10);
							}
						} else {
							living.hurtServer(serverWorld, level.damageSources().source(ModDamageTypes.BLEED, player), 2);
						}
						if (livingBloodComponent.drainAttack(drainAmount)) {
							SLibUtils.playSound(entity, ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value());
							fillAmount = getModifiedFillAmount(fillAmount, qualityBlood, living.getRandom());
							if (fillAmount > 0) {
								playerBloodComponent.fill(fillAmount);
							}
						}
					}
					return InteractionResult.CONSUME;
				}
			}
			return InteractionResult.PASS;
		}

		public static double getArmorMultiplier(LivingEntity living) {
			return Math.max(1 / 3F, Mth.lerp(living.getArmorValue() / ((RangedAttribute) Attributes.ARMOR.value()).getMaxValue(), 1, 0));
		}

		private static int getModifiedFillAmount(int fillAmount, boolean qualityBlood, RandomSource random) {
			if (!qualityBlood) {
				fillAmount /= 2;
				if (fillAmount == 0 && random.nextBoolean()) {
					fillAmount = 1;
				}
			}
			return fillAmount;
		}

		private static boolean canSafelyDrain(Player attacker, LivingEntity target, BloodComponent targetBloodComponent, int toDrain) {
			if (target.hasEffect(ModMobEffects.HYPNOTIZED)) {
				return targetBloodComponent.getBlood() - toDrain > 0;
			}
			if (targetBloodComponent.aboveHalfBlood()) {
				return target.isSleeping() || ModEntityComponents.MIST_FORM.get(attacker).isEnabled();
			}
			return false;
		}
	}

	public static class EatFood implements EatFoodEvent {
		@Override
		public void eat(Level level, LivingEntity user, ItemStack stack, FoodProperties properties) {
			if (level instanceof ServerLevel serverLevel && NyctoAPI.isVampire(user) && !stack.is(ModItemTags.SAFE_EDIBLES)) {
				user.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 2));
				user.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, 1));
				user.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1));
				user.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1));
				if (stack.is(ModItemTags.HURTS_VAMPIRES)) {
					user.hurtServer(serverLevel, level.damageSources().source(ModDamageTypes.TOXIC_TOUCH), Float.MAX_VALUE);
				}
			}
		}
	}

	public static class EffectImmunity implements ServerMobEffectEvents.AllowAdd {
		@Override
		public boolean allowAdd(MobEffectInstance effectInstance, LivingEntity entity, EffectEventContext ctx) {
			return !(effectInstance.getEffect().is(ModMobEffectTags.INFECTION) && NyctoAPI.isVampire(entity));
		}
	}

	public static class FreezeImmunity implements ServerLivingEntityEvents.AllowDamage {
		@Override
		public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
			return !source.is(DamageTypeTags.IS_FREEZING) || !NyctoAPI.isVampire(entity);
		}
	}

	public static class HealBlock implements ServerLivingEntityEvents.AfterDamage {
		@Override
		public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
			if (!blocked && NyctoAPI.isVampire(entity) && NyctoUtil.haltsVampireRegeneration(source)) {
				NyctoAPI.applyHealBlock(entity, NyctoUtil.isVampireWeaknessItem(source) ? 60 : 100);
			}
		}
	}

	public static class WeaknessItem implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
			if (!blocked && NyctoAPI.isVampire(victim) && NyctoUtil.isVampireWeaknessItem(source)) {
				SLibUtils.playSound(victim, SoundEvents.PLAYER_ATTACK_CRIT);
				SLibUtils.addTrackingEmitter(victim, ParticleTypes.ENCHANTED_HIT);
			}
		}
	}

	public static class BreakHarming implements PlayerBlockBreakEvents.After {
		@Override
		public void afterBlockBreak(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
			if (state.is(ModBlockTags.HURTS_VAMPIRES) && !player.getMainHandItem().is(ConventionalItemTags.SHEAR_TOOLS) && NyctoUtil.affectedByHurtsVampiresTag(player)) {
				NyctoUtil.hurtWithToxicTouch(player, player.getMaxHealth() / 5);
			}
		}
	}

	public static class TickHarming implements TickEntityEvent {
		@Override
		public void tick(Level level, Entity entity) {
			if (!level.isClientSide() && entity.tickCount % 10 == 0 && entity instanceof LivingEntity living && living.hurtTime == 0 && living.slib$isSurvival() && !NyctoAPI.hasRespawnLeniency(living)) {
				int count = 0;
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					if (living.getItemBySlot(slot).is(ModItemTags.HURTS_VAMPIRES)) {
						count++;
					}
				}
				if (count > 0 && NyctoUtil.affectedByHurtsVampiresTag(living)) {
					NyctoUtil.hurtWithToxicTouch(living, count);
				}
			}
		}
	}
}
