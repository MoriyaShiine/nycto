/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.block;

import moriyashiine.nycto.api.block.WildCropBlock;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WildGarlicBlock extends WildCropBlock {
	public WildGarlicBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler, boolean bl) {
		if (entity instanceof LivingEntity living && living.hurtTime == 0 && NyctoUtil.affectedByHurtsVampiresTag(entity)) {
			NyctoUtil.damageWithToxicTouch(living, 1);
		}
	}
}
