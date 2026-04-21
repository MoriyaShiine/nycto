/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.tag.ModBlockTags;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.strawberrylib.api.event.ModifyDestroySpeedEvent;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import moriyashiine.strawberrylib.api.objects.enums.PreventionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class BeastFormEvent {
	public static class DestroySpeed implements ModifyDestroySpeedEvent {
		@Override
		public float modify(Player player, ItemStack stack, Level level, BlockState state, @Nullable BlockPos pos) {
			return canHarvestAsBeast(player, state) ? 4 : 1;
		}

		public static boolean canHarvestAsBeast(Player player, BlockState state) {
			if (state.is(ModBlockTags.BEAST_MINEABLE) && !state.is(BlockTags.INCORRECT_FOR_WOODEN_TOOL)) {
				return NyctoAPI.isBeastForm(player);
			}
			return false;
		}
	}

	public static class PreventEquipmentUsage implements PreventEquipmentUsageEvent {
		@Override
		public PreventionResult getPreventionResult(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
			if (NyctoAPI.isBeastForm(entity)) {
				boolean equippable = stack.has(DataComponents.EQUIPPABLE);
				if (equippable || stack.is(ModItemTags.BEAST_UNEQUIPPABLE)) {
					if (equippable) {
						if (slot.isArmor()) {
							return PreventionResult.PREVENT_AND_STORE;
						}
					} else {
						return PreventionResult.PREVENT_AND_STORE;
					}
				}
			}
			return PreventionResult.PASS;
		}
	}
}
