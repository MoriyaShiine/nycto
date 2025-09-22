/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.tag.ModItemTags;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;

public class ModArmorMaterials {
	public static final ArmorMaterial VAMPIRE = new ArmorMaterial(ArmorMaterials.IRON.durability(), ArmorMaterials.IRON.defense(), ArmorMaterials.DIAMOND.enchantmentValue(), ArmorMaterials.LEATHER.equipSound(), ArmorMaterials.IRON.toughness(), ArmorMaterials.LEATHER.knockbackResistance(), ModItemTags.REPAIRS_VAMPIRE_ARMOR, RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Nycto.id("vampire")));
	public static final ArmorMaterial HUNTER = new ArmorMaterial(ArmorMaterials.IRON.durability(), ArmorMaterials.IRON.defense(), ArmorMaterials.DIAMOND.enchantmentValue(), ArmorMaterials.LEATHER.equipSound(), ArmorMaterials.IRON.toughness(), ArmorMaterials.LEATHER.knockbackResistance(), ModItemTags.REPAIRS_HUNTER_ARMOR, RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Nycto.id("hunter")));
}
