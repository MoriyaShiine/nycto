/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.power.vampire;

import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.event.power.util.HasOwnerEvent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.item.consume.FillBloodConsumeEffect;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class VampiricThrallEvent {
	public static class Revenge implements ServerLivingEntityEvents.AfterDamage {
		private static final HasOwnerEvent.RevengeFunction REVENGE = new HasOwnerEvent.RevengeFunction() {
			@Override
			public boolean shouldHelp(MobEntity mob, LivingEntity attacker, LivingEntity victim) {
				if (SLibUtils.shouldHurt(attacker, victim) && !NyctoUtil.isSurvival(mob.getTarget())) {
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
		public void tick(ServerWorld world, Entity entity) {
			if ((entity.age + entity.getId()) % 20 == 0 && entity instanceof MobEntity mob && !NyctoUtil.isSurvival(mob.getTarget())) {
				VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.get(mob);
				if (vampiricThrallComponent.hasOwner() && vampiricThrallComponent.getFollowMode() == VampiricThrallComponent.FollowMode.DEFEND) {
					List<MobEntity> targets = mob.getEntityWorld().getEntitiesByClass(MobEntity.class, mob.getBoundingBox().expand(16), foundEntity ->
							foundEntity instanceof Monster && SLibUtils.shouldHurt(mob, foundEntity) && !ModEntityComponents.VAMPIRIC_THRALL.get(foundEntity).hasOwner());
					MobEntity closest = null;
					for (MobEntity target : targets) {
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
	}

	public static class RightClickOverride implements UseEntityCallback {
		@Override
		public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
			if (player.isPartOfGame() && player.isSneaking()) {
				@Nullable VampiricThrallComponent vampiricThrallComponent = ModEntityComponents.VAMPIRIC_THRALL.getNullable(entity);
				if (vampiricThrallComponent != null) {
					if (vampiricThrallComponent.hasOwner()) {
						Set<ApplyEffectsConsumeEffect> effects = new HashSet<>();
						int fillAmount = 0;
						ItemStack stack = player.getStackInHand(hand);
						if (stack.contains(DataComponentTypes.CONSUMABLE)) {
							for (ConsumeEffect effect : stack.get(DataComponentTypes.CONSUMABLE).onConsumeEffects()) {
								if (effect instanceof ApplyEffectsConsumeEffect applyEffectsConsumeEffect) {
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
								if (!world.isClient()) {
									bloodComponent.fill(fillAmount);
									NyctoUtil.spawnBloodParticles(entity);
									SLibUtils.playSound(entity, ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value());
									entity.emitGameEvent(GameEvent.DRINK);
									if (entity instanceof LivingEntity living) {
										effects.forEach(effect -> effect.onConsume(world, stack, living));
									}
									ItemStack copy = stack.copy();
									stack.decrementUnlessCreative(1, player);
									if (!player.isCreative() && copy.contains(DataComponentTypes.USE_REMAINDER)) {
										player.giveOrDropStack(copy.get(DataComponentTypes.USE_REMAINDER).convertInto().copy());
									}
								}
								return ActionResult.SUCCESS;
							}
						} else if (vampiricThrallComponent.isOwner(player) && vampiricThrallComponent.hasFollowModes()) {
							if (!world.isClient()) {
								vampiricThrallComponent.cycleFollowMode();
								player.sendMessage(Text.translatable("message.nycto.cycle_follow_mode." + vampiricThrallComponent.getFollowMode().name().toLowerCase(Locale.ROOT), entity.getName()), true);
							}
							return ActionResult.SUCCESS;
						}
					}
				}
			}
			return ActionResult.PASS;
		}
	}
}
