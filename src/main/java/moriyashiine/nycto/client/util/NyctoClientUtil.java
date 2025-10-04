/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.util;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.impl.client.rendering.ArmorRendererRegistryImpl;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class NyctoClientUtil {
	public static boolean isArmorRenderer(BipedEntityRenderState state, EquipmentSlot slot, Predicate<ArmorRenderer> rendererPredicate) {
		ItemStack stack = switch (slot) {
			case HEAD -> state.equippedHeadStack;
			case CHEST -> state.equippedChestStack;
			case LEGS -> state.equippedLegsStack;
			case FEET -> state.equippedFeetStack;
			default -> ItemStack.EMPTY;
		};
		return rendererPredicate.test(ArmorRendererRegistryImpl.get(stack.getItem()));
	}
}
