package moriyashiine.nycto.mixin.power.vampire.vampiricthrall;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PathNavigation.class)
public class PathNavigationMixin {
	@Shadow
	@Final
	protected Mob mob;

	@ModifyReturnValue(method = "isDone", at = @At("RETURN"))
	private boolean nycto$vampiricThrall(boolean original) {
		if (!original) {
			VampiricThrallComponent vampiricThrall = NyctoEntityComponents.VAMPIRIC_THRALL.get(mob);
			if (vampiricThrall.isFeeding() || vampiricThrall.getFollowMode() == VampiricThrallComponent.FollowMode.STAY) {
				return mob.getTarget() == null;
			}
		}
		return original;
	}
}
