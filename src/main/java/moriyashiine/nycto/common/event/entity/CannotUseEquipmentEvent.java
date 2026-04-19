/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.event.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import moriyashiine.strawberrylib.api.objects.enums.PreventionResult;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CannotUseEquipmentEvent implements PreventEquipmentUsageEvent {
	@Override
	public PreventionResult getPreventionResult(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		if (cannotUseEquipment(entity)) {
			if (stack.is(ModItemTags.BEAST_UNEQUIPPABLE) || stack.has(DataComponents.EQUIPPABLE)) {
				return PreventionResult.PREVENT_AND_STORE;
			}
		}
		return PreventionResult.PASS;
	}

	private static boolean cannotUseEquipment(Entity entity) {
		return NyctoAPI.isBeastForm(entity);
	}
}
