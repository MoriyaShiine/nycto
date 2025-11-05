/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.item;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.init.ModSoundEvents;
import moriyashiine.nycto.common.recipe.BloodExtractionRecipe;
import moriyashiine.strawberrylib.api.objects.records.ModifierTrio;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.function.Consumer;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.editModifiers;

public class VampiricDaggerItem extends Item {
	public static final int MAX_CHARGE = 20;

	private static final EntityAttributeModifier ENTITY_INTERACTION_RANGE_MODIFIER = new EntityAttributeModifier(Nycto.id("vampiric_dagger_entity_interaction_range"), -0.5, EntityAttributeModifier.Operation.ADD_VALUE);
	private static final ModifierTrio MODIFIER = new ModifierTrio(EntityAttributes.ENTITY_INTERACTION_RANGE, ENTITY_INTERACTION_RANGE_MODIFIER, AttributeModifierSlot.MAINHAND);

	private static final Text HOLDING_PLAYER_BLOOD_TEXT = Text.translatable("tooltip.nycto.holding_player_blood").formatted(Formatting.ITALIC, Formatting.GRAY);
	private static final Text HOLDING_VAMPIRE_BLOOD_TEXT = Text.translatable("tooltip.nycto.holding_vampire_blood").formatted(Formatting.ITALIC, Formatting.GRAY);

	public VampiricDaggerItem(Settings settings) {
		super(editModifiers(() -> settings.sword(ToolMaterial.IRON, 2, -2), MODIFIER));
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		if (user.hurtTime == 0 && user.isSneaking()) {
			user.attack(user);
			return ActionResult.SUCCESS;
		}
		return super.use(world, user, hand);
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT && isFull(stack) && otherStack.isOf(Items.GLASS_BOTTLE)) {
			ItemStack bloodBottle = BloodExtractionRecipe.getCraftingResult(stack);
			if (otherStack.getCount() == 1) {
				cursorStackReference.set(bloodBottle);
			} else {
				if (player.getInventory().insertStack(bloodBottle)) {
					cursorStackReference.get().decrementUnlessCreative(1, player);
				} else {
					player.playSound(ModSoundEvents.ITEM_VAMPIRIC_DAGGER_EXTRACT_FAIL, 1, 1);
					return true;
				}
			}
			if (!player.isCreative()) {
				VampiricDaggerItem.setBloodTypes(stack, false, false);
				VampiricDaggerItem.setBloodCharge(stack, 0);
			}
			player.playSound(ModSoundEvents.ITEM_BLOOD_BOTTLE_DRINK.value(), 0.8F, 1);
			ScreenHandler screenHandler = player.currentScreenHandler;
			if (screenHandler != null) {
				screenHandler.onContentChanged(player.getInventory());
			}
			return true;
		}
		return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
		if (stack.getOrDefault(ModComponentTypes.PLAYER_BLOOD, false)) {
			textConsumer.accept(HOLDING_PLAYER_BLOOD_TEXT);
		}
		if (stack.getOrDefault(ModComponentTypes.VAMPIRE_BLOOD, false)) {
			textConsumer.accept(HOLDING_VAMPIRE_BLOOD_TEXT);
		}
	}

	@Override
	public boolean isItemBarVisible(ItemStack stack) {
		return true;
	}

	@Override
	public int getItemBarStep(ItemStack stack) {
		return Math.round(getBloodCharge(stack) * 13F / MAX_CHARGE);
	}

	@Override
	public int getItemBarColor(ItemStack stack) {
		return MathHelper.hsvToRgb(0, 1, 1);
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
