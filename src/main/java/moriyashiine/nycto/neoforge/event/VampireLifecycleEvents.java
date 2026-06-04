/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.event;

import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.item.VampireArmorItem;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import moriyashiine.nycto.neoforge.registry.NyctoMobEffects;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import moriyashiine.nycto.neoforge.util.NyctoBloodUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class VampireLifecycleEvents {
	private static final String BLOOD_DRAIN_COOLDOWN = "nycto_blood_drain_cooldown";
	private static final String BLOOD_DRAIN_CHARGE = "nycto_blood_drain_charge";
	private static final String BLOOD_DRAIN_CHARGED_UNTIL = "nycto_blood_drain_charged_until";
	private static final int BLOOD_DRAIN_COOLDOWN_TICKS = 30;
	private static final int BLOOD_DRAIN_CHARGE_TICKS = 20;
	private static final int BLOOD_DRAIN_CHARGED_GRACE_TICKS = 40;

	private VampireLifecycleEvents() {
	}

	public static void onPlayerTick(PlayerTickEvent.Post event) {
		if (!(event.getEntity() instanceof ServerPlayer player)) {
			return;
		}
		if (!canChargeBloodDrain(player)) {
			resetDrainCharge(player);
			return;
		}
		if (lookedAtDrainTarget(player) == null) {
			resetDrainCharge(player);
			return;
		}
		int charge = Math.min(BLOOD_DRAIN_CHARGE_TICKS, player.getPersistentData().getInt(BLOOD_DRAIN_CHARGE) + 1);
		player.getPersistentData().putInt(BLOOD_DRAIN_CHARGE, charge);
		if (charge >= BLOOD_DRAIN_CHARGE_TICKS) {
			player.getPersistentData().putLong(BLOOD_DRAIN_CHARGED_UNTIL, player.level().getGameTime() + BLOOD_DRAIN_CHARGED_GRACE_TICKS);
		}
	}

	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		if (tryCycleThrallMode(event.getEntity(), event.getHand(), event.getTarget())) {
			event.setCancellationResult(InteractionResult.sidedSuccess(event.getEntity().level().isClientSide()));
			event.setCanceled(true);
			return;
		}
		if (tryDrainBlood(event.getEntity(), event.getHand(), event.getTarget())) {
			event.setCancellationResult(InteractionResult.sidedSuccess(event.getEntity().level().isClientSide()));
			event.setCanceled(true);
		}
	}

	public static void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
		if (tryCycleThrallMode(event.getEntity(), event.getHand(), event.getTarget())) {
			event.setCancellationResult(InteractionResult.sidedSuccess(event.getEntity().level().isClientSide()));
			event.setCanceled(true);
			return;
		}
		if (tryDrainBlood(event.getEntity(), event.getHand(), event.getTarget())) {
			event.setCancellationResult(InteractionResult.sidedSuccess(event.getEntity().level().isClientSide()));
			event.setCanceled(true);
		}
	}

	private static boolean tryCycleThrallMode(Player player, InteractionHand hand, Object target) {
		if (hand != InteractionHand.MAIN_HAND || !player.isShiftKeyDown() || !(target instanceof Mob mob) || player.level().isClientSide()) {
			return false;
		}
		if (!NyctoPowers.isThrallOf(mob, player.getUUID())) {
			return false;
		}
		ItemStack stack = player.getMainHandItem();
		if (stack.is(NyctoItems.BLOOD_BOTTLE.get())) {
			if (mob.getHealth() >= mob.getMaxHealth()) {
				return false;
			}
			mob.heal(6);
			NyctoPowers.addThrallBlood(mob, 10);
			mob.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0));
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
				player.addItem(new ItemStack(Items.GLASS_BOTTLE));
			}
			player.displayClientMessage(Component.translatable("message.nycto.thrall.fed"), true);
			return true;
		}
		if (!stack.isEmpty() || !player.getOffhandItem().isEmpty()) {
			return false;
		}
		if (mob instanceof TamableAnimal) {
			player.displayClientMessage(Component.translatable("message.nycto.thrall.tamed_commands"), true);
			return true;
		}
		NyctoPowers.ThrallMode mode = NyctoPowers.cycleThrallMode(mob);
		player.displayClientMessage(Component.translatable("message.nycto.thrall.mode", Component.translatable("message.nycto.thrall.mode." + mode.name().toLowerCase())), true);
		return true;
	}

	private static boolean tryDrainBlood(Player player, InteractionHand hand, Object target) {
		if (hand != InteractionHand.MAIN_HAND || !player.getMainHandItem().isEmpty() || !NyctoData.isVampire(player)) {
			return false;
		}
		if (!(target instanceof LivingEntity living) || !NyctoBloodUtil.hasDrainableBlood(living) || living.hurtTime > 0 || !living.isAlive()) {
			return false;
		}
		if (player.level().isClientSide()) {
			return true;
		}
		if (NyctoData.getBlood(player) >= NyctoData.getMaxBlood(player) || NyctoBloodUtil.getBlood(living) <= 0) {
			return false;
		}
		long gameTime = player.level().getGameTime();
		if (player.getPersistentData().getLong(BLOOD_DRAIN_COOLDOWN) > gameTime) {
			return false;
		}
		if (!isBloodDrainCharged(player)) {
			player.displayClientMessage(Component.translatable("message.nycto.blood_drain_charge"), true);
			return true;
		}
		consumeDrainCharge(player);
		player.getPersistentData().putLong(BLOOD_DRAIN_COOLDOWN, gameTime + BLOOD_DRAIN_COOLDOWN_TICKS);
		player.swing(InteractionHand.MAIN_HAND, true);
		int drainAmount = NyctoBloodUtil.drainAmount(living);
		boolean safeDrain = canSafelyDrain(player, living, drainAmount);
		if (!safeDrain) {
			living.hurt(player.damageSources().playerAttack(player), 2);
		}
		if (!NyctoBloodUtil.drainAttack(living, drainAmount)) {
			return true;
		}
		NyctoData.addBlood(player, NyctoBloodUtil.fillAmount(living, VampireArmorItem.getsMoreBlood(player)));
		player.level().playSound(null, player.getX(), player.getY(), player.getZ(), NyctoSoundEvents.BLOOD_BOTTLE_DRINK.get(), SoundSource.PLAYERS, 1, 1);
		living.hurtTime = living.hurtDuration = 10;
		living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80, 0));
		return true;
	}

	private static boolean canSafelyDrain(Player player, LivingEntity target, int amount) {
		if (target.hasEffect(NyctoMobEffects.HYPNOTIZED)) {
			return NyctoBloodUtil.getBlood(target) - amount > 0;
		}
		return NyctoBloodUtil.getBlood(target) > NyctoBloodUtil.MAX_ENTITY_BLOOD / 2 && (target.isSleeping() || player instanceof net.minecraft.server.level.ServerPlayer serverPlayer && NyctoPowers.isMistFormActive(serverPlayer));
	}

	private static boolean canChargeBloodDrain(Player player) {
		return NyctoData.isVampire(player) && player.isShiftKeyDown() && player.getMainHandItem().isEmpty();
	}

	private static boolean isBloodDrainCharged(Player player) {
		return player.getPersistentData().getInt(BLOOD_DRAIN_CHARGE) >= BLOOD_DRAIN_CHARGE_TICKS
				|| player.getPersistentData().getLong(BLOOD_DRAIN_CHARGED_UNTIL) >= player.level().getGameTime();
	}

	private static LivingEntity lookedAtDrainTarget(ServerPlayer player) {
		if (ProjectileUtil.getHitResultOnViewVector(player, entity -> entity instanceof LivingEntity living && NyctoBloodUtil.hasDrainableBlood(living), player.entityInteractionRange()) instanceof EntityHitResult hitResult && hitResult.getEntity() instanceof LivingEntity living) {
			return living;
		}
		return null;
	}

	private static void resetDrainCharge(Player player) {
		player.getPersistentData().remove(BLOOD_DRAIN_CHARGE);
	}

	private static void consumeDrainCharge(Player player) {
		resetDrainCharge(player);
		player.getPersistentData().remove(BLOOD_DRAIN_CHARGED_UNTIL);
	}
}
