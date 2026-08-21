package moriyashiine.nycto.client.gui.hud.power;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.component.entity.power.vampire.CarnageComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public class CarnageHudElement implements HudElement {
	private static final Identifier CARNAGE_OVERLAY = Nycto.id("textures/misc/carnage_overlay.png");

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		Minecraft client = Minecraft.getInstance();
		Player player = client.player;
		if (player != null) {
			CarnageComponent carnage = NyctoEntityComponents.CARNAGE.get(player);
			if (carnage.isActive()) {
				client.gui.extractTextureOverlay(graphics, CARNAGE_OVERLAY, carnage.getOverlayOpacity(2 / 3F));
			}
		}
	}
}
