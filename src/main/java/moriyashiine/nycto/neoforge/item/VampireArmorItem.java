/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.item;

import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.registry.NyctoArmorMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class VampireArmorItem extends ArmorItem {
	private static final int MAX_BLOOD_BONUS_PER_PIECE = 5;

	public VampireArmorItem(Type type, Properties properties) {
		super(NyctoArmorMaterials.VAMPIRE, type, properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		if (level.isClientSide() || !(entity instanceof Player player) || !NyctoData.isVampire(player) || player.getItemBySlot(getEquipmentSlot()) != stack) {
			return;
		}
		if (player.tickCount % 400 == 0) {
			NyctoData.addBlood(player, 1);
		}
		if (hasFullSet(player) && player.tickCount % 200 == 0 && player.getHealth() < player.getMaxHealth() && NyctoData.getBlood(player) > 0) {
			NyctoData.addBlood(player, -1);
			player.heal(1);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		tooltip.add(Component.translatable("tooltip.nycto.vampire_armor.recover_blood").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.nycto.vampire_armor.full_set_heal").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.nycto.vampire_armor.no_sunlight_required").withStyle(ChatFormatting.DARK_GRAY));
	}

	public static boolean hasFullSet(Player player) {
		return getEquippedPieces(player) >= 4;
	}

	public static boolean hasHealBlockResistance(Player player) {
		return getEquippedPieces(player) >= 1;
	}

	public static boolean getsMoreBlood(Player player) {
		return getEquippedPieces(player) >= 2;
	}

	public static boolean hasReducedPowerCost(Player player) {
		return getEquippedPieces(player) >= 3;
	}

	public static int getMaxBloodBonus(Player player) {
		return getEquippedPieces(player) * MAX_BLOOD_BONUS_PER_PIECE;
	}

	public static int adjustPowerCost(Player player, int baseCost) {
		if (baseCost <= 0 || !hasReducedPowerCost(player)) {
			return baseCost;
		}
		return Math.max(1, (int) (baseCost * 0.75F));
	}

	private static int getEquippedPieces(Player player) {
		return (int) player.getInventory().armor.stream().filter(stack -> stack.getItem() instanceof VampireArmorItem).count();
	}
}
