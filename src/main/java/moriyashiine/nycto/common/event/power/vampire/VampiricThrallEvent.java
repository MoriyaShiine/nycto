/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.event.power.util.HasOwnerEvent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.nycto.common.tag.NyctoEntityTypeTags;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.nycto.common.world.item.consumeeffects.FillBloodConsumeEffect;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class VampiricThrallEvent {
	public static void init() {
		ServerLivingEntityEvents.AFTER_DAMAGE.register(new Revenge());
		TickEntityEvent.EVENT.register(new Defend());
		UseEntityCallback.EVENT.register(new RightClickOverride());
	}

	private static class Revenge implements ServerLivingEntityEvents.AfterDamage {
		private static final HasOwnerEvent.RevengeFunction REVENGE = new HasOwnerEvent.RevengeFunction() {
			@Override
			public boolean shouldHelp(Mob mob, LivingEntity attacker, LivingEntity victim) {
				if (SLibUtils.shouldHurt(attacker, victim) && mob.getTarget() == null) {
					VampiricThrallComponent vampiricThrall = NyctoEntityComponents.VAMPIRIC_THRALL.get(mob);
					return vampiricThrall.isOwner(attacker) && vampiricThrall.hasFollowModes() && vampiricThrall.getFollowMode() != VampiricThrallComponent.FollowMode.STAY;
				}
				return false;
			}

			@Override
			public boolean targetAttacker() {
				return false;
			}
		};

		@Override
		public void afterDamage(LivingEntity entity, DamageSource source, float baseDamageTaken, float damageTaken, boolean blocked) {
			HasOwnerEvent.revenge(entity, source, REVENGE);
		}
	}

	private static class Defend implements TickEntityEvent {
		@Override
		public void tick(Level level, Entity entity) {
			if (!level.isClientSide() && (entity.tickCount + entity.getId()) % 20 == 0 && entity instanceof Mob mob && mob.getTarget() == null) {
				VampiricThrallComponent vampiricThrall = NyctoEntityComponents.VAMPIRIC_THRALL.get(mob);
				if (vampiricThrall.hasOwner() && vampiricThrall.getFollowMode() == VampiricThrallComponent.FollowMode.DEFEND) {
					List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(16), foundEntity -> shouldTarget(mob, foundEntity));
					LivingEntity closest = null;
					for (LivingEntity target : targets) {
						if (closest == null || target.distanceTo(mob) < closest.distanceTo(mob)) {
							closest = target;
						}
					}
					if (closest != null) {
						HasOwnerEvent.setTarget(mob, closest);
					}
				}
			}
		}

		private static boolean shouldTarget(Mob mob, LivingEntity target) {
			if (target.slib$isSurvival() && SLibUtils.shouldHurt(mob, target) && !NyctoAPI.isVampire(target) && !target.is(NyctoEntityTypeTags.CANNOT_BE_TARGETED_BY_THRALLS) && mob.hasLineOfSight(target)) {
				if (target.slib$isPlayer() || target instanceof Enemy) {
					VampiricThrallComponent vampiricThrall = NyctoEntityComponents.VAMPIRIC_THRALL.get(mob);
					return vampiricThrall.getWanderHome() == null || Math.sqrt(vampiricThrall.getWanderHome().distToCenterSqr(target.position())) <= mob.getNavigation().getMaxPathLength();
				}
			}
			return false;
		}
	}

	private static class RightClickOverride implements UseEntityCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
			if (player.isShiftKeyDown() && player.slib$exists()) {
				VampiricThrallComponent vampiricThrall = NyctoEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
				if (vampiricThrall != null) {
					if (vampiricThrall.hasOwner()) {
						Set<ApplyStatusEffectsConsumeEffect> effects = new HashSet<>();
						int fillAmount = 0;
						ItemStack stack = player.getItemInHand(hand);
						if (stack.has(DataComponents.CONSUMABLE)) {
							for (ConsumeEffect effect : stack.get(DataComponents.CONSUMABLE).onConsumeEffects()) {
								if (effect instanceof ApplyStatusEffectsConsumeEffect applyEffectsConsumeEffect) {
									effects.add(applyEffectsConsumeEffect);
								}
								if (effect instanceof FillBloodConsumeEffect fillBloodConsumeEffect) {
									fillAmount += fillBloodConsumeEffect.fillAmount();
								}
							}
						}
						if (fillAmount > 0) {
							BloodComponent blood = NyctoEntityComponents.BLOOD.get(entity);
							if (blood.canFill()) {
								if (!level.isClientSide()) {
									blood.fill(fillAmount);
									NyctoUtil.spawnBloodParticles(entity);
									SLibUtils.playSound(entity, NyctoSoundEvents.BLOOD_BOTTLE_DRINK.value());
									entity.gameEvent(GameEvent.DRINK);
									if (entity instanceof LivingEntity living) {
										effects.forEach(effect -> effect.apply(level, stack, living));
									}
									ItemStack copy = stack.copy();
									stack.consume(1, player);
									if (!player.isCreative() && copy.has(DataComponents.USE_REMAINDER)) {
										player.handleExtraItemsCreatedOnUse(copy.get(DataComponents.USE_REMAINDER).convertInto().create());
									}
								}
								return InteractionResult.SUCCESS;
							}
						} else if (vampiricThrall.isOwner(player) && vampiricThrall.hasFollowModes()) {
							if (!level.isClientSide()) {
								vampiricThrall.cycleFollowMode();
								player.sendOverlayMessage(Component.translatable("message.nycto.cycle_follow_mode." + vampiricThrall.getFollowMode().name().toLowerCase(Locale.ROOT), entity.getName()));
							}
							return InteractionResult.SUCCESS;
						}
					}
				}
			}
			return InteractionResult.PASS;
		}
	}
}
