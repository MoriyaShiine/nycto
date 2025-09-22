/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class CannotUseEquipmentEvent implements PreventEquipmentUsageEvent {
	@Override
	public TriState preventsUsage(LivingEntity entity, ItemStack stack) {
		if (cannotUseEquipment(entity)) {
			if (stack.isIn(ModItemTags.BEAST_UNEQUIPPABLE) || stack.contains(DataComponentTypes.EQUIPPABLE)) {
				return TriState.TRUE;
			}
		}
		return TriState.DEFAULT;
	}

	private static boolean cannotUseEquipment(Entity entity) {
		return NyctoAPI.isBeastForm(entity);
	}
}
