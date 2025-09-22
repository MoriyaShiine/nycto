/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.block;

import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GarlicBlock extends CropBlock {
	private static final VoxelShape[] AGE_TO_SHAPE = {createCuboidShape(0, 0, 0, 16, 4, 16), createCuboidShape(0, 0, 0, 16, 7, 16), createCuboidShape(0, 0, 0, 16, 13, 16), createCuboidShape(0, 0, 0, 16, 14, 16)};

	public GarlicBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return AGE_TO_SHAPE[state.get(getAgeProperty())];
	}

	@Override
	protected ItemConvertible getSeedsItem() {
		return ModItems.GARLIC;
	}

	@Override
	public IntProperty getAgeProperty() {
		return Properties.AGE_3;
	}

	@Override
	public int getMaxAge() {
		return 3;
	}

	@Override
	protected int getGrowthAmount(World world) {
		return super.getGrowthAmount(world) / 3;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt(3) != 0) {
			super.randomTick(state, world, pos, random);
		}
	}

	@Override
	protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
		if (entity instanceof LivingEntity living && living.hurtTime == 0 && NyctoUtil.affectedByHurtsVampiresTag(entity)) {
			NyctoUtil.damageWithToxicTouch(living, 1);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(getAgeProperty());
	}
}
