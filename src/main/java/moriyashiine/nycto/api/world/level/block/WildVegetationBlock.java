/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.api.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WildVegetationBlock extends VegetationBlock {
	private static final MapCodec<WildVegetationBlock> CODEC = simpleCodec(WildVegetationBlock::new);

	private static final VoxelShape SHAPE = box(1, 0, 1, 15, 15, 15);

	public WildVegetationBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<WildVegetationBlock> codec() {
		return CODEC;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
