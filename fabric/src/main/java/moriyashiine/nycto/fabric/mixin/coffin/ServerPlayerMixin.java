package moriyashiine.nycto.fabric.mixin.coffin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.nycto.common.tag.NyctoBlockTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
	@ModifyExpressionValue(method = "startSleepInBed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/BedRule;canSleep(Lnet/minecraft/world/level/Level;)Z"))
	private boolean nycto$coffin(boolean original, @Local(argsOnly = true) BlockState bedBlockState) {
		return original || bedBlockState.is(NyctoBlockTags.COFFINS);
	}
}
