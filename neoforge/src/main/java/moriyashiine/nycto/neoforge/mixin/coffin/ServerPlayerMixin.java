package moriyashiine.nycto.neoforge.mixin.coffin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import moriyashiine.nycto.common.tag.NyctoBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
	public ServerPlayerMixin(Level level, GameProfile gameProfile) {
		super(level, gameProfile);
	}

	@ModifyExpressionValue(method = "lambda$startSleepInBed$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/BedRule;canSleep(Lnet/minecraft/world/level/Level;)Z"))
	private boolean nycto$coffin(boolean original, BlockPos pos) {
		if (level().getBlockState(pos).is(NyctoBlockTags.COFFINS)) {
			return true;
		}
		return original;
	}
}
