/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.level.block;

import moriyashiine.nycto.common.init.ModItems;
import moriyashiine.nycto.common.world.level.block.entity.CoffinBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoffinBlock extends BedBlock {
	private static final VoxelShape SHAPE = box(0, 0, 0, 16, 10, 16);

	public CoffinBlock(Properties properties) {
		super(DyeColor.RED, properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CoffinBlockEntity(pos, state);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public void updateEntityMovementAfterFallOn(BlockGetter level, Entity entity) {
		Blocks.OAK_PLANKS.updateEntityMovementAfterFallOn(level, entity);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (stack.is(ModItems.WOODEN_STAKE)) {
			BlockPos[] coffinPoses = {pos, pos.relative(state.getValue(PART) == BedPart.FOOT ? state.getValue(FACING) : state.getValue(FACING).getOpposite())};
			for (BlockPos coffinPos : coffinPoses) {
				state = level.getBlockState(coffinPos);
				if (!state.is(this)) {
					return InteractionResult.CONSUME;
				}
			}
			for (BlockPos coffinPos : coffinPoses) {
				for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, new AABB(coffinPos), LivingEntity::isSleeping)) {
					if (level instanceof ServerLevel serverLevel && entity.hurtServer(serverLevel, entity.damageSources().playerAttack(player), Integer.MAX_VALUE)) {
						stack.hurtEnemy(entity, player);
					}
					return InteractionResult.SUCCESS;
				}
			}
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}
}
