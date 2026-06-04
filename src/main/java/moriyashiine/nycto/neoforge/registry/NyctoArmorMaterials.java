/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.registry;

import moriyashiine.nycto.common.NyctoNeoForge;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public final class NyctoArmorMaterials {
	private static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, NyctoNeoForge.MOD_ID);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> VAMPIRE = ARMOR_MATERIALS.register("vampire", () -> ironLike("vampire"));
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> VAMPIRE_HUNTER = ARMOR_MATERIALS.register("vampire_hunter", () -> ironLike("vampire_hunter"));

	private NyctoArmorMaterials() {
	}

	public static void register(IEventBus bus) {
		ARMOR_MATERIALS.register(bus);
	}

	private static ArmorMaterial ironLike(String layer) {
		EnumMap<ArmorItem.Type, Integer> defense = Util.make(new EnumMap<>(ArmorItem.Type.class), values -> {
			values.put(ArmorItem.Type.BOOTS, 2);
			values.put(ArmorItem.Type.LEGGINGS, 5);
			values.put(ArmorItem.Type.CHESTPLATE, 6);
			values.put(ArmorItem.Type.HELMET, 2);
			values.put(ArmorItem.Type.BODY, 5);
		});
		return new ArmorMaterial(defense, 10, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.IRON_INGOT), List.of(new ArmorMaterial.Layer(NyctoNeoForge.id(layer))), 0, 0);
	}
}
