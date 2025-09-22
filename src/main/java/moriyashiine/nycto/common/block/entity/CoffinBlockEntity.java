/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.block.entity;

import moriyashiine.nycto.common.init.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CoffinBlockEntity extends BedBlockEntity {
	public CoffinBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return ModBlockEntityTypes.COFFIN;
	}

	@Override
	public boolean supports(BlockState state) {
		return ModBlockEntityTypes.COFFIN.supports(state);
	}
}
