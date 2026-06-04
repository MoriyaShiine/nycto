/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.mixin.client;

import moriyashiine.nycto.neoforge.client.NyctoClientInputEvents;
import moriyashiine.nycto.neoforge.client.PowerHotbarClientState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.objectweb.asm.Opcodes;

@Mixin(Minecraft.class)
public class PowerHotbarMinecraftMixin {
	@Shadow
	public LocalPlayer player;

	@Redirect(method = "handleKeybinds", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Inventory;selected:I", opcode = Opcodes.PUTFIELD))
	private void nycto$selectPowerInsteadOfHotbarSlot(Inventory inventory, int selected) {
		if (PowerHotbarClientState.isActive(player)) {
			NyctoClientInputEvents.selectPower(player, PowerHotbarClientState.visiblePagePowerIndex(player, selected));
			return;
		}
		inventory.selected = selected;
	}
}
