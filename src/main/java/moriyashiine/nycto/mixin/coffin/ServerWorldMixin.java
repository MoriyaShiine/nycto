/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.coffin;

import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.Optional;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {
	@Unique
	private static final Text COFFIN_SLEEP_TEXT = Text.translatable("sleep.skipping_day");

	@Shadow
	@Final
	List<ServerPlayerEntity> players;

	protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
		super(properties, registryRef, registryManager, dimensionEntry, isClient, debugWorld, seed, maxChainedNeighborUpdates);
	}

	@ModifyArg(method = "sendSleepingStatus", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;sendMessage(Lnet/minecraft/text/Text;Z)V"))
	private Text nycto$coffin(Text value) {
		return isDay() ? COFFIN_SLEEP_TEXT : value;
	}

	@ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setTimeOfDay(J)V"))
	private long nycto$coffin(long value) {
		boolean allInCoffins = true;
		for (ServerPlayerEntity player : players) {
			Optional<BlockPos> sleepingPos = player.getSleepingPosition();
			if (sleepingPos.isPresent() && !getBlockState(sleepingPos.get()).isIn(ModBlockTags.COFFINS)) {
				allInCoffins = false;
				break;
			}
		}
		if (allInCoffins) {
			return value - 11000L;
		}
		return value;
	}
}
