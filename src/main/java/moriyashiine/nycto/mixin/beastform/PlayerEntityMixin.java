/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.mixin.beastform;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.event.entity.BeastFormEvent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected float nycto$beastForm(float original) {
		return Math.max(super.nycto$beastForm(original), NyctoAPI.isBeastForm(this) ? 5 : 0);
	}

	@ModifyReturnValue(method = "canHarvest", at = @At("RETURN"))
	private boolean nycto$beastForm(boolean original, BlockState state) {
		return original || BeastFormEvent.canHarvestAsBeast((PlayerEntity) (Object) this, state);
	}
}
