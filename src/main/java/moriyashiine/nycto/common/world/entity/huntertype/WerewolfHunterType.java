/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.huntertype;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.entity.huntertype.HunterType;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.NyctoItems;
import moriyashiine.nycto.common.tag.NyctoItemTags;
import moriyashiine.nycto.common.world.entity.monster.Hunter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class WerewolfHunterType extends HunterType {
	public WerewolfHunterType() {
		super(Nycto.id("textures/entity/hunter/werewolf_hunter.png"), ResourceKey.create(EquipmentAssets.ROOT_ID, Nycto.id("werewolf_hunter")), NyctoItemTags.WEREWOLF_HUNTER_ARMOR);
	}

	@Override
	public boolean shouldTarget(LivingEntity entity) {
		return NyctoAPI.isWerewolf(entity);
	}

	@Override
	public void equipItems(Hunter hunter, boolean hasHorse) {
		super.equipItems(hunter, hasHorse);
		hunter.setItemSlot(EquipmentSlot.HEAD, NyctoItems.WEREWOLF_HUNTER_HELMET.getDefaultInstance());
		hunter.setItemSlot(EquipmentSlot.CHEST, NyctoItems.WEREWOLF_HUNTER_CHESTPLATE.getDefaultInstance());
		hunter.setItemSlot(EquipmentSlot.LEGS, NyctoItems.WEREWOLF_HUNTER_LEGGINGS.getDefaultInstance());
		hunter.setItemSlot(EquipmentSlot.FEET, NyctoItems.WEREWOLF_HUNTER_BOOTS.getDefaultInstance());
		if (hasHorse) {
			hunter.setItemSlot(EquipmentSlot.MAINHAND, NyctoItems.ACONITE_COATED_HALBERD.getDefaultInstance());
		} else {
			hunter.setItemSlot(EquipmentSlot.MAINHAND, Items.IRON_SWORD.getDefaultInstance());
		}
	}
}
