package moriyashiine.nycto.fabric.mixin.coffin;

import moriyashiine.nycto.common.tag.NyctoBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.clock.ClockTimeMarker;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.Optional;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {
	@Shadow
	public abstract List<ServerPlayer> players();

	protected ServerLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
		super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
	}

	@ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/clock/ServerClockManager;moveToTimeMarker(Lnet/minecraft/core/Holder;Lnet/minecraft/resources/ResourceKey;)Z"))
	private ResourceKey<ClockTimeMarker> nycto$coffin(ResourceKey<ClockTimeMarker> timeMarkerId) {
		boolean allInCoffins = true;
		for (ServerPlayer player : players()) {
			Optional<BlockPos> sleepingPos = player.getSleepingPos();
			if (sleepingPos.isPresent() && !getBlockState(sleepingPos.get()).is(NyctoBlockTags.COFFINS)) {
				allInCoffins = false;
				break;
			}
		}
		if (allInCoffins) {
			return ClockTimeMarkers.NIGHT;
		}
		return timeMarkerId;
	}
}
