package moriyashiine.nycto.client.event;

import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.strawberrylib.api.event.client.ReplaceContextualInfoEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public class VampireClientEvent implements ReplaceContextualInfoEvent {
	public static void init() {
		ReplaceContextualInfoEvent.EVENT.register(new VampireClientEvent());
	}

	private static final Identifier BACKGROUND_TEXTURE = Nycto.id("hud/vampire_charge_jump/background");
	private static final Identifier PROGRESS_TEXTURE = Nycto.id("hud/vampire_charge_jump/progress");

	@Override
	public ContextualInfo getInfo(Player player) {
		float progress = NyctoEntityComponents.VAMPIRE.get(player).getChargeJumpBoostProgress();
		if (progress > 0) {
			return new ContextualInfo(BACKGROUND_TEXTURE, PROGRESS_TEXTURE, progress);
		}
		return null;
	}

	@Override
	public int getPriority() {
		return 900;
	}
}
