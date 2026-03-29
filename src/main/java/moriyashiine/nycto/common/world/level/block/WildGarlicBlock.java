/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.level.block;

import moriyashiine.nycto.api.world.level.block.WildVegetationBlock;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WildGarlicBlock extends WildVegetationBlock {
	public WildGarlicBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
		if (entity instanceof LivingEntity living && living.hurtTime == 0 && NyctoUtil.affectedByHurtsVampiresTag(entity)) {
			NyctoUtil.hurtWithToxicTouch(living, 1);
		}
	}
}
