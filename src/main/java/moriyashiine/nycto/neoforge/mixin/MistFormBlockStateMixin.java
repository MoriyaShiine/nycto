/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.mixin;

import moriyashiine.nycto.neoforge.network.MistFormClientState;
import moriyashiine.nycto.neoforge.power.NyctoPowers;
import moriyashiine.nycto.neoforge.registry.NyctoTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MistFormBlockStateMixin {
	@Inject(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
	private void nycto$mistFormCollision(BlockGetter level, net.minecraft.core.BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
		VoxelShape original = cir.getReturnValue();
		if (original == Shapes.empty() || original == Shapes.block() || !(context instanceof EntityCollisionContext entityContext)) {
			return;
		}
		Entity entity = entityContext.getEntity();
		if (!(entity instanceof Player player) || ((BlockBehaviour.BlockStateBase) (Object) this).is(NyctoTags.MIST_FORM_UNPASSABLE)) {
			return;
		}
		if (player instanceof ServerPlayer serverPlayer ? NyctoPowers.isMistFormActive(serverPlayer) : MistFormClientState.isActive(player)) {
			cir.setReturnValue(Shapes.empty());
		}
	}
}
