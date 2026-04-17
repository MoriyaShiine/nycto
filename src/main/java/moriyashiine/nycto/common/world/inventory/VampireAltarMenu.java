/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.inventory;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.inventory.AltarMenu;
import moriyashiine.nycto.common.init.ModBlocks;
import moriyashiine.nycto.common.init.ModMenuTypes;
import moriyashiine.nycto.common.tag.ModItemTags;
import moriyashiine.nycto.common.tag.ModPowerTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class VampireAltarMenu extends AltarMenu {
	public VampireAltarMenu(int syncId, Inventory playerInventory) {
		this(syncId, playerInventory, ContainerLevelAccess.NULL);
	}

	public VampireAltarMenu(int syncId, Inventory playerInventory, ContainerLevelAccess context) {
		super(ModMenuTypes.VAMPIRE_ALTAR, syncId, playerInventory, context, ModPowerTags.VAMPIRE_CHOOSABLE);
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(access, player, ModBlocks.VAMPIRE_ALTAR) && NyctoAPI.isVampire(player);
	}

	@Override
	protected TagKey<Item> getWeakMaterials() {
		return ModItemTags.WEAK_VAMPIRE_ALTAR_UPGRADES;
	}

	@Override
	protected TagKey<Item> getAverageMaterials() {
		return ModItemTags.AVERAGE_VAMPIRE_ALTAR_UPGRADES;
	}

	@Override
	protected TagKey<Item> getStrongMaterials() {
		return ModItemTags.STRONG_VAMPIRE_ALTAR_UPGRADES;
	}

	@Override
	protected boolean isAlternateMaterial(ItemStack stack) {
		return stack.is(ModItemTags.USABLE_BLOOD_BOTTLES);
	}
}
