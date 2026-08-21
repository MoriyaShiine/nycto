package moriyashiine.nycto.mixin.beastform.integration.anthropophagy;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.anthropophagy.common.component.entity.CannibalComponent;
import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CannibalComponent.class)
public class CannibalLevelComponentMixin {
	@Shadow
	@Final
	private Player obj;

	@ModifyReturnValue(method = "getLevel", at = @At("RETURN"))
	private int nycto$beastForm(int original) {
		if (NyctoAPI.isBeastForm(obj)) {
			return 0;
		}
		return original;
	}
}
