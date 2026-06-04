/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.event;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.UUID;

@EventBusSubscriber(modid = NyctoNeoForge.MOD_ID)
public final class VampiricThrallEvents {
	private VampiricThrallEvents() {
	}

	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Post event) {
		if (event.getEntity() instanceof Vex vex && !vex.level().isClientSide()) {
			NyctoPowers.inheritThrallOwnerFromSummoner(vex);
		}
		if (event.getEntity() instanceof Witch witch && !witch.level().isClientSide() && NyctoPowers.isThrall(witch)) {
			reverseHelpfulWitchDrink(witch);
		}
		if (event.getEntity() instanceof Mob mob && !mob.level().isClientSide() && mob.tickCount % 20 == 0) {
			if (mob instanceof Animal animal && NyctoPowers.isThrall(animal)) {
				animal.resetLove();
			}
			NyctoPowers.tickLoadedThrall(mob);
		}
	}

	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		if (event.getTarget() instanceof Animal animal && NyctoPowers.isThrall(animal)) {
			ItemStack stack = event.getEntity().getItemInHand(event.getHand());
			if (!event.getEntity().isShiftKeyDown() && animal.isFood(stack)) {
				event.setCancellationResult(InteractionResult.sidedSuccess(event.getEntity().level().isClientSide()));
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onBabyEntitySpawn(BabyEntitySpawnEvent event) {
		if (event.getParentA() instanceof Mob parentA && NyctoPowers.isThrall(parentA) || event.getParentB() instanceof Mob parentB && NyctoPowers.isThrall(parentB)) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
		if (event.getLevel().isClientSide()) {
			return;
		}
		if (event.getEntity() instanceof ThrownPotion potion && potion.getOwner() instanceof Witch witch && NyctoPowers.isThrall(witch)) {
			PotionContents contents = potion.getItem().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
			if (isHelpfulWitchPotion(contents)) {
				potion.setItem(PotionContents.createItemStack(Items.SPLASH_POTION, contents.is(Potions.STRONG_HEALING) ? Potions.STRONG_HARMING : Potions.HARMING));
			}
		}
	}

	@SubscribeEvent
	public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
		if (!(event.getEntity() instanceof Mob thrall) || !NyctoPowers.isThrall(thrall)) {
			return;
		}
		ServerPlayer owner = owner(thrall);
		if (owner == null || !NyctoPowers.isValidThrallTarget(owner, thrall, event.getNewAboutToBeSetTarget())) {
			event.setNewAboutToBeSetTarget(null);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
		Entity attacker = event.getSource().getEntity();
		if (!(attacker instanceof LivingEntity livingAttacker)) {
			return;
		}
		if (event.getEntity() instanceof Mob thrall && NyctoPowers.isThrall(thrall) && isFriendOfThrall(thrall, livingAttacker)) {
			event.setCanceled(true);
			return;
		}
		if (event.getEntity() instanceof ServerPlayer owner && livingAttacker instanceof Mob thrall && NyctoPowers.isThrallOf(thrall, owner.getUUID())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onLivingDamagePost(LivingDamageEvent.Post event) {
		if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
			return;
		}
		if (event.getEntity() instanceof Mob hurtThrall && NyctoPowers.isThrall(hurtThrall)) {
			retaliate(hurtThrall, attacker);
			return;
		}
		if (event.getEntity() instanceof ServerPlayer owner) {
			for (Mob thrall : owner.level().getEntitiesOfClass(Mob.class, owner.getBoundingBox().inflate(24), mob -> NyctoPowers.isThrallOf(mob, owner.getUUID()))) {
				retaliate(thrall, attacker);
			}
		}
	}

	private static void retaliate(Mob thrall, LivingEntity attacker) {
		ServerPlayer owner = owner(thrall);
		if (owner != null && NyctoPowers.thrallMode(thrall) != NyctoPowers.ThrallMode.STAY && NyctoPowers.isValidThrallTarget(owner, thrall, attacker)) {
			thrall.setTarget(attacker);
		}
	}

	private static boolean isFriendOfThrall(Mob thrall, LivingEntity other) {
		UUID ownerId = NyctoPowers.thrallOwner(thrall).orElse(null);
		if (ownerId == null) {
			return false;
		}
		if (other instanceof ServerPlayer player) {
			return ownerId.equals(player.getUUID());
		}
		return other instanceof Mob otherMob && NyctoPowers.isThrallOf(otherMob, ownerId);
	}

	private static ServerPlayer owner(Mob thrall) {
		return NyctoPowers.thrallOwner(thrall)
				.map(ownerId -> thrall.level().getPlayerByUUID(ownerId))
				.filter(ServerPlayer.class::isInstance)
				.map(ServerPlayer.class::cast)
				.orElse(null);
	}

	private static void reverseHelpfulWitchDrink(Witch witch) {
		if (!witch.isDrinkingPotion()) {
			return;
		}
		ItemStack stack = witch.getMainHandItem();
		if (!stack.is(Items.POTION)) {
			return;
		}
		PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
		if (isHelpfulWitchPotion(contents)) {
			witch.setItemSlot(EquipmentSlot.MAINHAND, PotionContents.createItemStack(Items.POTION, contents.is(Potions.STRONG_HEALING) ? Potions.STRONG_HARMING : Potions.HARMING));
		}
	}

	private static boolean isHelpfulWitchPotion(PotionContents contents) {
		return contents.is(Potions.HEALING)
				|| contents.is(Potions.STRONG_HEALING)
				|| contents.is(Potions.REGENERATION)
				|| contents.is(Potions.LONG_REGENERATION)
				|| contents.is(Potions.STRONG_REGENERATION);
	}
}
