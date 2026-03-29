/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.item;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.init.ModTriggers;
import moriyashiine.nycto.common.world.item.crafting.BloodExtractionRecipe;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.records.ModifierTrio;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.editModifiers;

public class VampiricDaggerItem extends Item {
	public static final int MAX_CHARGE = 20;

	private static final AttributeModifier ENTITY_INTERACTION_RANGE_MODIFIER = new AttributeModifier(Nycto.id("vampiric_dagger_entity_interaction_range"), -0.5, AttributeModifier.Operation.ADD_VALUE);
	private static final ModifierTrio MODIFIER = new ModifierTrio(Attributes.ENTITY_INTERACTION_RANGE, ENTITY_INTERACTION_RANGE_MODIFIER, EquipmentSlotGroup.MAINHAND);

	private static final Component HOLDING_PLAYER_BLOOD_TEXT = Component.translatable("tooltip.nycto.holding_player_blood").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY);
	private static final Component HOLDING_VAMPIRE_BLOOD_TEXT = Component.translatable("tooltip.nycto.holding_vampire_blood").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY);

	public VampiricDaggerItem(Properties properties) {
		super(editModifiers(() -> properties.sword(ToolMaterial.IRON, 2, -2), MODIFIER));
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (player.hurtTime == 0 && player.isShiftKeyDown()) {
			SLibUtils.runWithPvpBypass(() -> player.attack(player));
			return InteractionResult.SUCCESS;
		}
		return super.use(level, player, hand);
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack self, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess carriedItem) {
		if (clickAction == ClickAction.SECONDARY && isFull(self) && other.is(Items.GLASS_BOTTLE)) {
			ItemStack bloodBottle = BloodExtractionRecipe.getCraftingResult(self);
			if (other.getCount() == 1) {
				carriedItem.set(bloodBottle);
			} else {
				if (player.getInventory().add(bloodBottle)) {
					carriedItem.get().consume(1, player);
				} else {
					player.playSound(ModSoundEvents.ITEM_VAMPIRIC_DAGGER_EXTRACT_FAIL, 1, 1);
					return true;
				}
			}
			if (!player.isCreative()) {
				VampiricDaggerItem.setBloodTypes(self, false, false);
				VampiricDaggerItem.setBloodCharge(self, 0);
			}
			player.playSound(ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value(), 0.8F, 1);
			player.containerMenu.slotsChanged(player.getInventory());
			if (player instanceof ServerPlayer serverPlayer) {
				ModTriggers.EXTRACT_BLOOD.trigger(serverPlayer, bloodBottle);
			}
			return true;
		}
		return super.overrideOtherStackedOnMe(self, other, slot, clickAction, player, carriedItem);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
		if (itemStack.getOrDefault(ModComponentTypes.PLAYER_BLOOD, false)) {
			builder.accept(HOLDING_PLAYER_BLOOD_TEXT);
		}
		if (itemStack.getOrDefault(ModComponentTypes.VAMPIRE_BLOOD, false)) {
			builder.accept(HOLDING_VAMPIRE_BLOOD_TEXT);
		}
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return true;
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
		return isFull(getBloodCharge(stack));
	}

	public static boolean isFull(int charge) {
		return charge == MAX_CHARGE;
	}

	public static void setBloodTypes(ItemStack stack, boolean player, boolean vampire) {
		stack.set(ModComponentTypes.PLAYER_BLOOD, player);
		stack.set(ModComponentTypes.VAMPIRE_BLOOD, vampire);
	}

	public static int getBloodCharge(ItemStack stack) {
		return stack.getOrDefault(ModComponentTypes.BLOOD_CHARGE, 0);
	}

	public static void setBloodCharge(ItemStack stack, int charge) {
		stack.set(ModComponentTypes.BLOOD_CHARGE, charge);
	}
}
