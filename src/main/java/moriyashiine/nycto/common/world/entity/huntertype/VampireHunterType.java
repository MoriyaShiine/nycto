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

public class VampireHunterType extends HunterType {
	public VampireHunterType() {
		super(Nycto.id("textures/entity/hunter/vampire_hunter.png"), ResourceKey.create(EquipmentAssets.ROOT_ID, Nycto.id("vampire_hunter")), NyctoItemTags.VAMPIRE_HUNTER_ARMOR);
	}

	@Override
	public boolean shouldTarget(LivingEntity entity) {
		return NyctoAPI.isVampire(entity);
	}

	@Override
	public void equipItems(Hunter hunter, boolean hasHorse) {
		super.equipItems(hunter, hasHorse);
		hunter.setItemSlot(EquipmentSlot.HEAD, NyctoItems.VAMPIRE_HUNTER_HELMET.getDefaultInstance());
		hunter.setItemSlot(EquipmentSlot.CHEST, NyctoItems.VAMPIRE_HUNTER_CHESTPLATE.getDefaultInstance());
		hunter.setItemSlot(EquipmentSlot.LEGS, NyctoItems.VAMPIRE_HUNTER_LEGGINGS.getDefaultInstance());
		hunter.setItemSlot(EquipmentSlot.FEET, NyctoItems.VAMPIRE_HUNTER_BOOTS.getDefaultInstance());
		if (hasHorse) {
			hunter.setItemSlot(EquipmentSlot.MAINHAND, NyctoItems.GARLIC_COATED_HALBERD.getDefaultInstance());
		} else {
			hunter.setItemSlot(EquipmentSlot.MAINHAND, NyctoItems.WOODEN_STAKE.getDefaultInstance());
			hunter.setItemSlot(EquipmentSlot.OFFHAND, Items.CROSSBOW.getDefaultInstance());
		}
	}
}
