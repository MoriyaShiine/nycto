/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.item.crafting;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.common.init.ModComponentTypes;
import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.init.ModRecipeSerializers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.stream.Collectors;

public class FoodPoisoningRecipe extends CustomRecipe {
	public static final FoodPoisoningRecipe INSTANCE = new FoodPoisoningRecipe();
	public static final MapCodec<FoodPoisoningRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, FoodPoisoningRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public boolean matches(CraftingInput input, Level level) {
		boolean foundFood = false, foundAconite = false;
		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getItem(i);
			if (stack.has(DataComponents.FOOD) && !stack.getOrDefault(ModComponentTypes.POISONED, false)) {
				foundFood = true;
			}
			if (stack.is(ModItems.ACONITE)) {
				foundAconite = true;
			}
		}
		return foundFood && foundAconite && input.items().stream().filter(stack -> !stack.isEmpty()).collect(Collectors.toSet()).size() == 2;
	}

	@Override
	public ItemStack assemble(CraftingInput input) {
		for (ItemStack stack : input.items()) {
			if (stack.has(DataComponents.FOOD)) {
				ItemStack copy = stack.copyWithCount(1);
				copy.set(ModComponentTypes.POISONED, true);
				return copy;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public RecipeSerializer<FoodPoisoningRecipe> getSerializer() {
		return ModRecipeSerializers.FOOD_POISONING;
	}
}
