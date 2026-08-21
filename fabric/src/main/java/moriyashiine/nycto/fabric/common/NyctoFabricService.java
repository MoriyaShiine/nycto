package moriyashiine.nycto.fabric.common;

import com.google.auto.service.AutoService;
import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.NyctoService;
import net.minecraft.world.entity.player.Player;

@AutoService(NyctoService.class)
public class NyctoFabricService implements NyctoService {
	private static final AbilitySource SOURCE = Pal.getAbilitySource(Nycto.id("bat_form"));

	@Override
	public void applyBatFormAbilities(Player player, boolean grant) {
		if (grant) {
			SOURCE.grantTo(player, VanillaAbilities.ALLOW_FLYING);
			SOURCE.grantTo(player, VanillaAbilities.FLYING);
		} else {
			SOURCE.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
			SOURCE.revokeFrom(player, VanillaAbilities.FLYING);
		}
	}
}
