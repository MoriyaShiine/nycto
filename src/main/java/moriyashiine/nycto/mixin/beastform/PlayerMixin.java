/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.mixin.beastform;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.event.entity.BeastFormEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntityMixin {
	public PlayerMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Override
	protected float nycto$beastForm(float original) {
		return Math.max(super.nycto$beastForm(original), NyctoAPI.isBeastForm(this) ? 5 : 0);
	}

	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "hasCorrectToolForDrops", at = @At("RETURN"))
	private boolean nycto$beastForm(boolean original, BlockState state) {
		return original || BeastFormEvent.DestroySpeed.canHarvestAsBeast((Player) (Object) this, state);
	}
}
