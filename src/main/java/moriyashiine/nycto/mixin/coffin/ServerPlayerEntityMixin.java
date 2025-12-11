/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.coffin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import moriyashiine.nycto.common.tag.ModBlockTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	public ServerPlayerEntityMixin(World world, GameProfile profile) {
		super(world, profile);
	}

	@ModifyExpressionValue(method = "trySleep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/BedRule;canSleep(Lnet/minecraft/world/World;)Z"))
	private boolean nycto$coffin(boolean original, BlockPos pos) {
		if (getEntityWorld().getBlockState(pos).isIn(ModBlockTags.COFFINS)) {
			return true;
		}
		return original;
	}
}
