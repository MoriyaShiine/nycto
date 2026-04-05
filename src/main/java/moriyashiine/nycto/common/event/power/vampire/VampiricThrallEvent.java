/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.event.power.util.HasOwnerEvent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.tag.ModEntityTypeTags;
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
	public static class Revenge implements ServerLivingEntityEvents.AfterDamage {
		private static final HasOwnerEvent.RevengeFunction REVENGE = new HasOwnerEvent.RevengeFunction() {
			@Override
			public boolean shouldHelp(Mob mob, LivingEntity attacker, LivingEntity victim) {
				if (SLibUtils.shouldHurt(attacker, victim) && !NyctoUtil.isSurvivalNullable(mob.getTarget())) {
					VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.get(mob);
					return vampiricThrallComponent.isOwner(attacker) && vampiricThrallComponent.hasFollowModes() && vampiricThrallComponent.getFollowMode() != VampiricThrallComponent.FollowMode.STAY;
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

	public static class Defend implements TickEntityEvent {
		@Override
		public void tick(Level level, Entity entity) {
			if (!level.isClientSide() && (entity.tickCount + entity.getId()) % 20 == 0 && entity instanceof Mob mob && !NyctoUtil.isSurvivalNullable(mob.getTarget())) {
				VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.get(mob);
				if (vampiricThrallComponent.hasOwner() && vampiricThrallComponent.getFollowMode() == VampiricThrallComponent.FollowMode.DEFEND) {
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
			if (target.slib$isSurvival() && SLibUtils.shouldHurt(mob, target) && !NyctoAPI.isVampire(target) && !target.is(ModEntityTypeTags.CANNOT_BE_TARGETED_BY_THRALLS) && mob.hasLineOfSight(target)) {
				return target.slib$isPlayer() || target instanceof Enemy;
			}
			return false;
		}
	}

	public static class RightClickOverride implements UseEntityCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
			if (player.isShiftKeyDown() && player.slib$exists()) {
				@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
				if (vampiricThrallComponent != null) {
					if (vampiricThrallComponent.hasOwner()) {
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
							BloodComponent bloodComponent = ModEntityComponents.BLOOD.get(entity);
							if (bloodComponent.canFill()) {
								if (!level.isClientSide()) {
									bloodComponent.fill(fillAmount);
									NyctoUtil.spawnBloodParticles(entity);
									SLibUtils.playSound(entity, ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value());
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
						} else if (vampiricThrallComponent.isOwner(player) && vampiricThrallComponent.hasFollowModes()) {
							if (!level.isClientSide()) {
								vampiricThrallComponent.cycleFollowMode();
								player.sendOverlayMessage(Component.translatable("message.nycto.cycle_follow_mode." + vampiricThrallComponent.getFollowMode().name().toLowerCase(Locale.ROOT), entity.getName()));
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
