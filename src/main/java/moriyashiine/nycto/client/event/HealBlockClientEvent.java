package moriyashiine.nycto.client.event;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.strawberrylib.api.event.client.ReplaceHeartTexturesEvent;
import net.minecraft.world.entity.player.Player;

public class HealBlockClientEvent implements ReplaceHeartTexturesEvent {
	public static void init() {
		ReplaceHeartTexturesEvent.EVENT.register(new HealBlockClientEvent());
	}

	private static final ReplaceHeartTexturesEvent.TextureSet TEXTURES = new TextureSet(
			Nycto.id("hud/heal_block/full"), Nycto.id("hud/heal_block/full_blinking"),
			Nycto.id("hud/heal_block/half"), Nycto.id("hud/heal_block/half_blinking"),
			Nycto.id("hud/heal_block/hardcore_full"), Nycto.id("hud/heal_block/hardcore_full_blinking"),
			Nycto.id("hud/heal_block/hardcore_half"), Nycto.id("hud/heal_block/hardcore_half_blinking")
	);

	@Override
	public TextureSet getTextureSet(Player player) {
		if (NyctoAPI.isHealingBlocked(player)) {
			return TEXTURES;
		}
		return null;
	}
}
