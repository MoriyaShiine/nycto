package moriyashiine.nycto.mixin.coffin;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {
	@Unique
	private static final Component COFFIN_SLEEP_TEXT = Component.translatable("sleep.skipping_day");

	protected ServerLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
		super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
	}

	@ModifyArg(method = "announceSleepStatus", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;sendOverlayMessage(Lnet/minecraft/network/chat/Component;)V"))
	private Component nycto$coffin(Component value) {
		return isBrightOutside() ? COFFIN_SLEEP_TEXT : value;
	}
}
