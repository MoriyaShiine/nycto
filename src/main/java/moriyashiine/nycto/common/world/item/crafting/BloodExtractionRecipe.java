/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.item.crafting;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.init.ModRecipeSerializers;
import moriyashiine.nycto.common.world.item.VampiricDaggerItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.stream.Collectors;

public class BloodExtractionRecipe extends CustomRecipe {
	public static final BloodExtractionRecipe INSTANCE = new BloodExtractionRecipe();
	public static final MapCodec<BloodExtractionRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, BloodExtractionRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public boolean matches(CraftingInput input, Level level) {
		boolean foundDagger = false, foundBottle = false;
		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getItem(i);
			if (stack.is(ModItems.VAMPIRIC_DAGGER) && VampiricDaggerItem.isFull(stack)) {
				foundDagger = true;
			}
			if (stack.is(Items.GLASS_BOTTLE)) {
				foundBottle = true;
			}
		}
		return foundDagger && foundBottle && input.items().stream().filter(stack -> !stack.isEmpty()).collect(Collectors.toSet()).size() == 2;
	}

	@Override
	public ItemStack assemble(CraftingInput input) {
		for (ItemStack stack : input.items()) {
			if (stack.is(ModItems.VAMPIRIC_DAGGER)) {
				return getCraftingResult(stack);
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
		NonNullList<ItemStack> remainingItems = super.getRemainingItems(input);
		for (int i = 0; i < remainingItems.size(); i++) {
			ItemStack stack = input.getItem(i);
			if (stack.is(ModItems.VAMPIRIC_DAGGER)) {
				VampiricDaggerItem.setBloodTypes(stack, false, false);
				VampiricDaggerItem.setBloodCharge(stack, 0);
				remainingItems.set(i, stack.copy());
			}
		}
		return remainingItems;
	}

	@Override
	public RecipeSerializer<BloodExtractionRecipe> getSerializer() {
		return ModRecipeSerializers.BLOOD_EXTRACTION;
	}

	public static ItemStack getCraftingResult(ItemStack stack) {
		Item result = ModItems.BLOOD_BOTTLE;
		boolean vampire = stack.getOrDefault(ModComponentTypes.VAMPIRE_BLOOD, false);
		if (stack.getOrDefault(ModComponentTypes.PLAYER_BLOOD, false)) {
			if (vampire) {
				result = ModItems.PLAYER_VAMPIRE_BLOOD_BOTTLE;
			} else {
				result = ModItems.PLAYER_BLOOD_BOTTLE;
			}
		} else if (vampire) {
			result = ModItems.VAMPIRE_BLOOD_BOTTLE;
		}
		return result.getDefaultInstance();
	}
}
