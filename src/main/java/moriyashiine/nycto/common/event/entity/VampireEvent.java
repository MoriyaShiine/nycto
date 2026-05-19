/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.tag.NyctoBlockTags;
import moriyashiine.nycto.common.tag.NyctoItemTags;
import moriyashiine.nycto.common.tag.NyctoMobEffectTags;
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
	public static void init() {
		ServerLivingEntityEvents.ALLOW_DEATH.register(new BloodVeil());
		ModifyMovementEvents.JUMP_DELTA.register(new ChargeJump());
		UseEntityCallback.EVENT.register(new DrinkBlood());
		EatFoodEvent.EVENT.register(new EatFood());
		ServerMobEffectEvents.ALLOW_ADD.register(new EffectImmunity());
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(new FreezeImmunity());
		ServerLivingEntityEvents.AFTER_DAMAGE.register(new HealBlock());
		AfterDamageIncludingDeathEvent.EVENT.register(new WeaknessCrit());
		PlayerBlockBreakEvents.AFTER.register(new BreakHarming());
		TickEntityEvent.EVENT.register(new TickHarming());
	}

	private static class BloodVeil implements ServerLivingEntityEvents.AllowDeath {
		@Override
		public boolean allowDeath(LivingEntity entity, DamageSource damageSource, float damageAmount) {
			if (entity.getHealth() - damageAmount <= 0 && NyctoAPI.isVampire(entity) && !NyctoUtil.bypassesBloodVeil(damageSource) && !hasThinBlood(entity)) {
				BloodComponent blood = NyctoEntityComponents.BLOOD.get(entity);
				if (blood.drain(Mth.floor(damageAmount * NyctoUtil.getArmorMultiplier(entity)))) {
					NyctoAPI.applyHealBlock(entity, 60);
					entity.setHealth(1);
					return false;
				}
			}
			return true;
		}

		private static boolean hasThinBlood(LivingEntity living) {
			return living instanceof Player player && NyctoAPI.hasPower(player, NyctoPowers.THIN_BLOOD);
		}
	}

	private static class ChargeJump implements ModifyMovementEvents.JumpDelta {
		@Override
		public Vec3 modify(Vec3 delta, LivingEntity entity) {
			if (entity instanceof Player player && NyctoAPI.isVampire(player)) {
				float progress = NyctoEntityComponents.VAMPIRE.get(entity).getChargeJumpBoostProgress();
				if (progress > 0) {
					if (progress > 0.25F && entity.level() instanceof ServerLevel level) {
						level.sendParticles(NyctoParticleTypes.BLOOD, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 8, entity.getBbWidth() / 2, 0, entity.getBbWidth() / 2, 0.15);
						SLibUtils.playSound(entity, SoundEvents.SLIME_BLOCK_FALL, 1, 0.75F);
						entity.gameEvent(GameEvent.ENTITY_ACTION);
					}
					return delta.add(0, progress * 0.34F, 0);
				}
			}
			return delta;
		}
	}

	private static class DrinkBlood implements UseEntityCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
			if (hand == InteractionHand.MAIN_HAND && player.isShiftKeyDown() && player.slib$exists() && player.getItemInHand(hand).isEmpty() && NyctoAPI.hasBlood(entity) && entity instanceof LivingEntity living && living.hurtTime == 0 && living.isAlive() && !living.hasInfiniteMaterials() && NyctoAPI.isVampire(player)) {
				boolean qualityBlood = NyctoAPI.hasQualityBlood(entity);
				if (!qualityBlood && NyctoAPI.hasPower(player, NyctoPowers.RICH_TASTES)) {
					return InteractionResult.PASS;
				}
				VampiricThrallComponent vampiricThrall = NyctoEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
				if (vampiricThrall != null && vampiricThrall.isOwner(player)) {
					return InteractionResult.PASS;
				}
				BloodComponent playerBlood = NyctoEntityComponents.BLOOD.get(player);
				BloodComponent livingBlood = NyctoEntityComponents.BLOOD.get(living);
				int fillAmount = (qualityBlood ? 5 : 1) + (NyctoUtil.getsMoreBlood(player) ? 2 : 0);
				int drainAmount = qualityBlood ? 10 : 25;
				double armorMultiplier = NyctoUtil.getArmorMultiplier(living);
				fillAmount = Mth.ceil(fillAmount * armorMultiplier);
				drainAmount = Mth.ceil(drainAmount * armorMultiplier);
				if (fillAmount > 0 && playerBlood.canFill() && livingBlood.getBlood() > 0) {
					if (level instanceof ServerLevel serverWorld) {
						player.swing(InteractionHand.MAIN_HAND, true);
						if (canSafelyDrain(player, living, livingBlood, drainAmount)) {
							living.hurtTime = living.hurtDuration = 10;
							if (NyctoUtil.isVillager(living)) {
								NyctoUtil.notifyNearbyVillagers(living, player, GossipType.MINOR_NEGATIVE, 10);
							}
						} else {
							living.hurtServer(serverWorld, level.damageSources().source(NyctoDamageTypes.BLEED, player), 2);
						}
						if (livingBlood.drainAttack(drainAmount)) {
							SLibUtils.playSound(entity, NyctoSoundEvents.BLOOD_BOTTLE_DRINK.value());
							fillAmount = getModifiedFillAmount(fillAmount, qualityBlood, living.getRandom());
							if (fillAmount > 0) {
								playerBlood.fill(fillAmount);
							}
						}
					}
					return InteractionResult.CONSUME;
				}
			}
			return InteractionResult.PASS;
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
			if (target.hasEffect(NyctoMobEffects.HYPNOTIZED)) {
				return targetBloodComponent.getBlood() - toDrain > 0;
			}
			if (targetBloodComponent.aboveHalfBlood()) {
				return target.isSleeping() || NyctoEntityComponents.MIST_FORM.get(attacker).isEnabled();
			}
			return false;
		}
	}

	private static class EatFood implements EatFoodEvent {
		@Override
		public void eat(Level level, LivingEntity user, ItemStack stack, FoodProperties properties) {
			if (level instanceof ServerLevel serverLevel && NyctoAPI.isVampire(user) && !stack.is(NyctoItemTags.SAFE_EDIBLES)) {
				user.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 2));
				user.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, 1));
				user.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1));
				user.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1));
				if (stack.is(NyctoItemTags.HURTS_VAMPIRES)) {
					user.hurtServer(serverLevel, level.damageSources().source(NyctoDamageTypes.TOXIC_TOUCH), Float.MAX_VALUE);
				}
			}
		}
	}

	private static class EffectImmunity implements ServerMobEffectEvents.AllowAdd {
		@Override
		public boolean allowAdd(MobEffectInstance effectInstance, LivingEntity entity, EffectEventContext ctx) {
			return !(effectInstance.getEffect().is(NyctoMobEffectTags.INFECTION) && NyctoAPI.isVampire(entity));
		}
	}

	private static class FreezeImmunity implements ServerLivingEntityEvents.AllowDamage {
		@Override
		public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
			return !source.is(DamageTypeTags.IS_FREEZING) || !NyctoAPI.isVampire(entity);
		}
	}

	private static class HealBlock implements ServerLivingEntityEvents.AfterDamage {
		@Override
		public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
			if (!blocked && NyctoAPI.isVampire(entity) && NyctoUtil.haltsVampireRegeneration(source)) {
				NyctoAPI.applyHealBlock(entity, NyctoUtil.isVampireWeakness(source) ? 60 : 100);
			}
		}
	}

	private static class WeaknessCrit implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
			if (!blocked && NyctoAPI.isVampire(victim) && NyctoUtil.isVampireWeakness(source)) {
				SLibUtils.playSound(victim, SoundEvents.PLAYER_ATTACK_CRIT);
				SLibUtils.addTrackingEmitter(victim, ParticleTypes.ENCHANTED_HIT);
			}
		}
	}

	private static class BreakHarming implements PlayerBlockBreakEvents.After {
		@Override
		public void afterBlockBreak(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
			if (state.is(NyctoBlockTags.HURTS_VAMPIRES) && !player.getMainHandItem().is(ConventionalItemTags.SHEAR_TOOLS) && NyctoUtil.affectedByHurtsVampiresTag(player)) {
				NyctoUtil.hurtWithToxicTouch(player, player.getMaxHealth() / 5);
			}
		}
	}

	private static class TickHarming implements TickEntityEvent {
		@Override
		public void tick(Level level, Entity entity) {
			if (!level.isClientSide() && entity.tickCount % 10 == 0 && entity instanceof LivingEntity living && living.hurtTime == 0 && living.slib$isSurvival() && !NyctoAPI.hasRespawnLeniency(living)) {
				int count = 0;
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					if (living.getItemBySlot(slot).is(NyctoItemTags.HURTS_VAMPIRES)) {
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
