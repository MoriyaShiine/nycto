package moriyashiine.nycto.common.world.inventory;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.inventory.AltarMenu;
import moriyashiine.nycto.common.init.NyctoBlocks;
import moriyashiine.nycto.common.init.NyctoMenuTypes;
import moriyashiine.nycto.common.tag.NyctoItemTags;
import moriyashiine.nycto.common.tag.NyctoPowerTags;
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
		super(NyctoMenuTypes.VAMPIRE_ALTAR, syncId, playerInventory, context, NyctoPowerTags.VAMPIRE_CHOOSABLE);
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(access, player, NyctoBlocks.VAMPIRE_ALTAR) && NyctoAPI.isVampire(player);
	}

	@Override
	protected TagKey<Item> getWeakMaterials() {
		return NyctoItemTags.WEAK_VAMPIRE_ALTAR_UPGRADES;
	}

	@Override
	protected TagKey<Item> getAverageMaterials() {
		return NyctoItemTags.AVERAGE_VAMPIRE_ALTAR_UPGRADES;
	}

	@Override
	protected TagKey<Item> getStrongMaterials() {
		return NyctoItemTags.STRONG_VAMPIRE_ALTAR_UPGRADES;
	}

	@Override
	protected boolean isAlternateMaterial(ItemStack stack) {
		return stack.is(NyctoItemTags.USABLE_BLOOD_BOTTLES);
	}
}
