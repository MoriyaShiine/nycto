/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.item;

import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.NyctoItems;
import moriyashiine.nycto.neoforge.registry.NyctoParticleTypes;
import moriyashiine.nycto.neoforge.registry.NyctoSoundEvents;
import moriyashiine.nycto.neoforge.util.NyctoBloodUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.List;

public class VampiricDaggerItem extends SwordItem {
	public static final int MAX_CHARGE = 20;

	private static final String BLOOD_CHARGE = "BloodCharge";
	private static final String VAMPIRE_BLOOD = "VampireBlood";

	public VampiricDaggerItem(Properties properties) {
		super(Tiers.IRON, properties.attributes(SwordItem.createAttributes(Tiers.IRON, 2, -2.0F)));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack dagger = player.getItemInHand(hand);
		InteractionHand bottleHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
		ItemStack bottles = player.getItemInHand(bottleHand);
		if (bottles.is(Items.GLASS_BOTTLE) && isFull(dagger)) {
			if (!level.isClientSide()) {
				extractBlood(player, dagger, bottles, bottleHand);
			}
			return InteractionResultHolder.sidedSuccess(dagger, level.isClientSide());
		}
		return super.use(level, player, hand);
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack self, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess carriedItem) {
		if (clickAction == ClickAction.SECONDARY && other.is(Items.GLASS_BOTTLE) && isFull(self)) {
			extractBlood(player, self, other, null);
			player.containerMenu.slotsChanged(player.getInventory());
			return true;
		}
		return super.overrideOtherStackedOnMe(self, other, slot, clickAction, player, carriedItem);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		boolean result = super.hurtEnemy(stack, target, attacker);
		if (!attacker.level().isClientSide() && NyctoBloodUtil.hasQualityBlood(target)) {
			int drainAmount = Math.max(2, NyctoBloodUtil.drainAmount(target) / 2);
			if (!NyctoBloodUtil.drainAttack(target, drainAmount)) {
				return result;
			}
			addBloodCharge(stack, drainAmount, target instanceof Player player && NyctoData.isVampire(player));
			if (target.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(NyctoParticleTypes.BLOOD.get(), target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(), Math.max(4, drainAmount), 0.18, 0.25, 0.18, 0.02);
				target.level().playSound(null, target.getX(), target.getY(), target.getZ(), NyctoSoundEvents.BLOOD_BOTTLE_DRINK.get(), SoundSource.PLAYERS, 0.55F, Mth.nextFloat(target.getRandom(), 0.9F, 1.15F));
			}
			if (attacker instanceof Player player) {
				player.displayClientMessage(Component.translatable("message.nycto.vampiric_dagger.charge", getBloodCharge(stack), MAX_CHARGE), true);
			}
		}
		return result;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		tooltip.add(Component.translatable("tooltip.nycto.vampiric_dagger.blood", getBloodCharge(stack), MAX_CHARGE).withStyle(ChatFormatting.DARK_RED));
		if (hasVampireBlood(stack)) {
			tooltip.add(Component.translatable("tooltip.nycto.holding_vampire_blood").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
		}
		tooltip.add(Component.translatable("tooltip.nycto.vampiric_dagger.extract").withStyle(ChatFormatting.GRAY));
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getBloodCharge(stack) > 0;
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return Math.round(getBloodCharge(stack) * 13F / MAX_CHARGE);
	}

	@Override
	public int getBarColor(ItemStack stack) {
		return Mth.hsvToRgb(0, 1, 1);
	}

	public static boolean isFull(ItemStack stack) {
		return getBloodCharge(stack) >= MAX_CHARGE;
	}

	private static void addBloodCharge(ItemStack stack, int amount, boolean vampireBlood) {
		int nextCharge = Math.min(MAX_CHARGE, getBloodCharge(stack) + Math.max(1, amount));
		CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
			tag.putInt(BLOOD_CHARGE, nextCharge);
			if (vampireBlood) {
				tag.putBoolean(VAMPIRE_BLOOD, true);
			}
		});
	}

	public static int getBloodCharge(ItemStack stack) {
		return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getInt(BLOOD_CHARGE);
	}

	public static boolean hasVampireBlood(ItemStack stack) {
		return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean(VAMPIRE_BLOOD);
	}

	private static void extractBlood(Player player, ItemStack dagger, ItemStack bottles, InteractionHand bottleHand) {
		ItemStack result = new ItemStack(hasVampireBlood(dagger) ? NyctoItems.VAMPIRE_BLOOD_BOTTLE.get() : NyctoItems.BLOOD_BOTTLE.get());
		if (!player.getAbilities().instabuild) {
			bottles.shrink(1);
		}
		if (bottleHand != null && bottles.isEmpty()) {
			player.setItemInHand(bottleHand, result);
		} else if (!player.getInventory().add(result)) {
			player.drop(result, false);
		}
		if (!player.getAbilities().instabuild) {
			resetBlood(dagger);
		}
		player.level().playSound(null, player.getX(), player.getY(), player.getZ(), NyctoSoundEvents.BLOOD_BOTTLE_DRINK.get(), SoundSource.PLAYERS, 0.7F, 1.0F);
	}

	public static void resetBlood(ItemStack dagger) {
		CompoundTag tag = dagger.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		tag.remove(BLOOD_CHARGE);
		tag.remove(VAMPIRE_BLOOD);
		CustomData.set(DataComponents.CUSTOM_DATA, dagger, tag);
	}
}
