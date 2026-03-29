/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.init;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.tag.ModItemTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class ModArmorMaterials {
	public static final ArmorMaterial VAMPIRE = new ArmorMaterial(ArmorMaterials.IRON.durability(), ArmorMaterials.IRON.defense(), ArmorMaterials.DIAMOND.enchantmentValue(), ArmorMaterials.LEATHER.equipSound(), ArmorMaterials.IRON.toughness(), ArmorMaterials.LEATHER.knockbackResistance(), ModItemTags.REPAIRS_VAMPIRE_ARMOR, ResourceKey.create(EquipmentAssets.ROOT_ID, Nycto.id("vampire")));
	public static final ArmorMaterial HUNTER = new ArmorMaterial(ArmorMaterials.IRON.durability(), ArmorMaterials.IRON.defense(), ArmorMaterials.DIAMOND.enchantmentValue(), ArmorMaterials.LEATHER.equipSound(), ArmorMaterials.IRON.toughness(), ArmorMaterials.LEATHER.knockbackResistance(), ModItemTags.REPAIRS_HUNTER_ARMOR, ResourceKey.create(EquipmentAssets.ROOT_ID, Nycto.id("hunter")));
}
