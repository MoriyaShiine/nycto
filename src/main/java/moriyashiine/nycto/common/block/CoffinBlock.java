/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.block;

import moriyashiine.nycto.common.block.entity.CoffinBlockEntity;
import moriyashiine.nycto.common.init.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CoffinBlock extends BedBlock {
	private static final VoxelShape SHAPE = createCuboidShape(0, 0, 0, 16, 10, 16);

	public CoffinBlock(Settings settings) {
		super(DyeColor.RED, settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CoffinBlockEntity(pos, state);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onEntityLand(BlockView world, Entity entity) {
		Blocks.OAK_PLANKS.onEntityLand(world, entity);
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (stack.isOf(ModItems.WOODEN_STAKE) && state.get(Properties.BED_PART) == BedPart.HEAD) {
			for (PlayerEntity otherPlayer : world.getPlayers()) {
				if (pos.equals(otherPlayer.getSleepingPosition().orElse(null))) {
					if (world instanceof ServerWorld serverWorld && otherPlayer.damage(serverWorld, otherPlayer.getDamageSources().playerAttack(player), Integer.MAX_VALUE)) {
						stack.postHit(otherPlayer, player);
					}
					return ActionResult.SUCCESS;
				}
			}
		}
		return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
	}
}
