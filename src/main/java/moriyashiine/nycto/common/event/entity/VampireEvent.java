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
import moriyashiine.nycto.common.tag.ModStatusEffectTags;
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
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.VillagerGossipType;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class VampireEvent {
	public static class BloodVeil implements ServerLivingEntityEvents.AllowDeath {
		@Override
		public boolean allowDeath(LivingEntity entity, DamageSource damageSource, float damageAmount) {
			if (entity.getHealth() - damageAmount <= 0 && NyctoAPI.isVampire(entity) && !NyctoUtil.bypassesBloodVeil(damageSource) && !hasThinBlood(entity)) {
				BloodComponent bloodComponent = ModEntityComponents.BLOOD.get(entity);
				if (bloodComponent.drain(MathHelper.floor(damageAmount * DrinkBlood.getArmorMultiplier(entity)))) {
					NyctoAPI.applyHealBlock(entity, 60);
					entity.setHealth(1);
					return false;
				}
			}
			return true;
		}

		private static boolean hasThinBlood(LivingEntity living) {
			return living instanceof PlayerEntity player && NyctoAPI.hasPower(player, ModPowers.THIN_BLOOD);
		}
	}

	public static class ChargeJump implements ModifyMovementEvents.JumpVelocity {
		@Override
		public Vec3d modify(Vec3d velocity, LivingEntity entity) {
			if (entity instanceof PlayerEntity player && NyctoAPI.isVampire(player)) {
				float boostProgress = ModEntityComponents.VAMPIRE_CHARGE_JUMP.get(entity).getBoostProgress();
				if (boostProgress > 0) {
					if (boostProgress > 0.25F && entity.getEntityWorld() instanceof ServerWorld world) {
						world.spawnParticles(ModParticleTypes.BLOOD, entity.getX(), entity.getY() + entity.getHeight() * 0.5, entity.getZ(), 8, entity.getWidth() / 2, 0, entity.getWidth() / 2, 0.15);
						SLibUtils.playSound(entity, SoundEvents.BLOCK_SLIME_BLOCK_FALL, 1, 0.75F);
						entity.emitGameEvent(GameEvent.ENTITY_ACTION);
					}
					return velocity.add(0, boostProgress * 0.34F, 0);
				}
			}
			return velocity;
		}
	}

	public static class DrinkBlood implements UseEntityCallback {
		@Override
		public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
			if (hand == Hand.MAIN_HAND && player.isSneaking() && player.isPartOfGame() && player.getStackInHand(hand).isEmpty() && !entity.getType().isIn(ModEntityTypeTags.HAS_NO_BLOOD) && entity instanceof LivingEntity living && living.hurtTime == 0 && living.isAlive() && !living.isInCreativeMode() && NyctoAPI.isVampire(player)) {
				boolean qualityBlood = entity.getType().isIn(ModEntityTypeTags.HAS_QUALITY_BLOOD);
				if (!qualityBlood && NyctoAPI.hasPower(player, ModPowers.RICH_TASTES)) {
					return ActionResult.PASS;
				}
				@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
				if (vampiricThrallComponent != null && vampiricThrallComponent.isOwner(player)) {
					return ActionResult.PASS;
				}
				BloodComponent playerBloodComponent = ModEntityComponents.BLOOD.get(player);
				BloodComponent livingBloodComponent = ModEntityComponents.BLOOD.get(living);
				int fillAmount = (qualityBlood ? 5 : 1) + (NyctoUtil.getsMoreBlood(player) ? 2 : 0);
				int drainAmount = qualityBlood ? 10 : 25;
				double armorMultiplier = getArmorMultiplier(living);
				fillAmount = MathHelper.ceil(fillAmount * armorMultiplier);
				drainAmount = MathHelper.ceil(drainAmount * armorMultiplier);
				if (fillAmount > 0 && playerBloodComponent.canFill() && livingBloodComponent.getBlood() > 0) {
					if (world instanceof ServerWorld serverWorld) {
						player.swingHand(Hand.MAIN_HAND, true);
						if (canSafelyDrain(player, living, livingBloodComponent, drainAmount)) {
							living.hurtTime = living.maxHurtTime = 10;
							if (NyctoUtil.isVillager(living)) {
								NyctoUtil.notifyNearbyVillagers(living, player, VillagerGossipType.MINOR_NEGATIVE, 10);
							}
						} else {
							living.damage(serverWorld, world.getDamageSources().create(ModDamageTypes.BLEED, player), 2);
						}
						if (livingBloodComponent.drainAttack(drainAmount)) {
							SLibUtils.playSound(entity, ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value());
							fillAmount = getModifiedFillAmount(fillAmount, qualityBlood, living.getRandom());
							if (fillAmount > 0) {
								playerBloodComponent.fill(fillAmount);
							}
						}
					}
					return ActionResult.CONSUME;
				}
			}
			return ActionResult.PASS;
		}

		public static double getArmorMultiplier(LivingEntity living) {
			return Math.max(1 / 3F, MathHelper.lerp(living.getArmor() / ((ClampedEntityAttribute) EntityAttributes.ARMOR.value()).getMaxValue(), 1, 0));
		}

		private static int getModifiedFillAmount(int fillAmount, boolean qualityBlood, Random random) {
			if (!qualityBlood) {
				fillAmount /= 2;
				if (fillAmount == 0 && random.nextBoolean()) {
					fillAmount = 1;
				}
			}
			return fillAmount;
		}

		private static boolean canSafelyDrain(PlayerEntity attacker, LivingEntity target, BloodComponent targetBloodComponent, int toDrain) {
			if (target.hasStatusEffect(ModStatusEffects.HYPNOTIZED)) {
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
		public void eat(World world, LivingEntity entity, ItemStack food, FoodComponent foodComponent) {
			if (world instanceof ServerWorld serverWorld && NyctoAPI.isVampire(entity) && !food.isIn(ModItemTags.SAFE_EDIBLES)) {
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200, 2));
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1));
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 1));
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 1));
				if (food.isIn(ModItemTags.HURTS_VAMPIRES)) {
					entity.damage(serverWorld, world.getDamageSources().create(ModDamageTypes.TOXIC_TOUCH), Float.MAX_VALUE);
				}
			}
		}
	}

	public static class EffectImmunity implements ServerMobEffectEvents.AllowAdd {
		@Override
		public boolean allowAdd(StatusEffectInstance effect, LivingEntity entity, EffectEventContext ctx) {
			return !(effect.getEffectType().isIn(ModStatusEffectTags.INFECTION) && NyctoAPI.isVampire(entity));
		}
	}

	public static class FreezeImmunity implements ServerLivingEntityEvents.AllowDamage {
		@Override
		public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
			return !source.isIn(DamageTypeTags.IS_FREEZING) || !NyctoAPI.isVampire(entity);
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
		public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
			if (!blocked && NyctoAPI.isVampire(entity) && NyctoUtil.isVampireWeaknessItem(source)) {
				SLibUtils.playSound(entity, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT);
				SLibUtils.addEmitterParticle(entity, ParticleTypes.ENCHANTED_HIT);
			}
		}
	}

	public static class BreakHarming implements PlayerBlockBreakEvents.After {
		@Override
		public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
			if (state.isIn(ModBlockTags.HURTS_VAMPIRES) && !player.getMainHandStack().isIn(ConventionalItemTags.SHEAR_TOOLS) && NyctoUtil.affectedByHurtsVampiresTag(player)) {
				NyctoUtil.damageWithToxicTouch(player, player.getMaxHealth() / 5);
			}
		}
	}

	public static class TickHarming implements TickEntityEvent {
		@Override
		public void tick(ServerWorld world, Entity entity) {
			if (entity.age % 10 == 0 && entity instanceof LivingEntity living && living.hurtTime == 0 && living.canTakeDamage() && !NyctoAPI.hasRespawnLeniency(living)) {
				int count = 0;
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					if (living.getEquippedStack(slot).isIn(ModItemTags.HURTS_VAMPIRES)) {
						count++;
					}
				}
				if (count > 0 && NyctoUtil.affectedByHurtsVampiresTag(living)) {
					NyctoUtil.damageWithToxicTouch(living, count);
				}
			}
		}
	}
}
