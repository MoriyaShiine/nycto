/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.event;

import moriyashiine.nycto.common.NyctoNeoForge;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import moriyashiine.nycto.neoforge.registry.NyctoTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = NyctoNeoForge.MOD_ID)
public final class BeastFormEvents {
	private BeastFormEvents() {
	}

	@SubscribeEvent
	public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
		if (isDarkForm(event.getEntity()) && event.getState().is(NyctoTags.BEAST_MINEABLE) && !event.getState().is(BlockTags.INCORRECT_FOR_WOODEN_TOOL)) {
			event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 4));
		}
	}

	@SubscribeEvent
	public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
		cancelRestrictedUse(event, event.getEntity().getItemInHand(event.getHand()));
	}

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		cancelRestrictedUse(event, event.getEntity().getItemInHand(event.getHand()));
	}

	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		cancelRestrictedUse(event, event.getEntity().getItemInHand(event.getHand()));
	}

	@SubscribeEvent
	public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
		if (event.getEntity() instanceof Player player && isDarkForm(player) && isRestrictedEquipment(event.getTo(), event.getSlot())) {
			unequip(player, event.getSlot(), event.getTo());
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		if (!isDarkForm(event.getEntity())) {
			return;
		}
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack stack = event.getEntity().getItemBySlot(slot);
			if (isRestrictedEquipment(stack, slot)) {
				unequip(event.getEntity(), slot, stack);
			}
		}
	}

	private static void cancelRestrictedUse(PlayerInteractEvent event, ItemStack stack) {
		if (isDarkForm(event.getEntity()) && isRestrictedUse(stack)) {
			if (event instanceof PlayerInteractEvent.RightClickItem rightClickItem) {
				rightClickItem.setCancellationResult(InteractionResult.FAIL);
				rightClickItem.setCanceled(true);
			} else if (event instanceof PlayerInteractEvent.RightClickBlock rightClickBlock) {
				rightClickBlock.setCancellationResult(InteractionResult.FAIL);
				rightClickBlock.setCanceled(true);
			} else if (event instanceof PlayerInteractEvent.EntityInteract entityInteract) {
				entityInteract.setCancellationResult(InteractionResult.FAIL);
				entityInteract.setCanceled(true);
			}
		}
	}

	private static boolean isRestrictedUse(ItemStack stack) {
		return !stack.isEmpty() && (stack.getItem() instanceof ArmorItem || stack.getItem() instanceof ShieldItem || stack.has(DataComponents.TOOL) || stack.is(NyctoTags.BEAST_UNEQUIPPABLE));
	}

	private static boolean isRestrictedEquipment(ItemStack stack, EquipmentSlot slot) {
		if (stack.isEmpty()) {
			return false;
		}
		return stack.getItem() instanceof ShieldItem || stack.is(NyctoTags.BEAST_UNEQUIPPABLE) || slot.isArmor() && stack.getItem() instanceof ArmorItem;
	}

	private static void unequip(Player player, EquipmentSlot slot, ItemStack stack) {
		ItemStack copy = stack.copy();
		player.setItemSlot(slot, ItemStack.EMPTY);
		if (!player.getInventory().add(copy)) {
			player.drop(copy, false);
		}
	}

	private static boolean isDarkForm(Player player) {
		return player instanceof ServerPlayer serverPlayer && NyctoPowers.isDarkFormActive(serverPlayer);
	}
}
