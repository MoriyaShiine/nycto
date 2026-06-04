/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.block;

import moriyashiine.nycto.neoforge.NyctoData;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CoffinBlock extends BedBlock {
	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 10, 16);
	private static final int CLOSED_TICKS = 40;

	public CoffinBlock(Properties properties) {
		super(DyeColor.RED, properties);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return null;
	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
		Blocks.OAK_PLANKS.updateEntityAfterFallOn(level, entity);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		}
		if (!NyctoData.isVampire(player)) {
			player.sendSystemMessage(Component.translatable("block.nycto.coffin.vampire_only"));
			return InteractionResult.CONSUME;
		}
		if (!level.isDay()) {
			player.sendSystemMessage(Component.translatable("block.nycto.coffin.no_sleep"));
			return InteractionResult.CONSUME;
		}
		if (level instanceof ServerLevel serverLevel) {
			setClosed(serverLevel, pos, state, true);
			serverLevel.scheduleTick(pos, this, CLOSED_TICKS);
			long dayTime = serverLevel.getDayTime();
			long target = dayTime - dayTime % 24000L + 13000L;
			if (target <= dayTime) {
				target += 24000L;
			}
			serverLevel.setDayTime(target);
			if (player instanceof ServerPlayer serverPlayer) {
				CriteriaTriggers.SLEPT_IN_BED.trigger(serverPlayer);
			}
			player.sendSystemMessage(Component.translatable("sleep.skipping_day"));
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, net.minecraft.util.RandomSource random) {
		setClosed(level, pos, state, false);
	}

	private static void setClosed(ServerLevel level, BlockPos pos, BlockState state, boolean closed) {
		BlockPos footPos = state.getValue(PART) == BedPart.FOOT ? pos : pos.relative(state.getValue(FACING).getOpposite());
		BlockState footState = level.getBlockState(footPos);
		if (!(footState.getBlock() instanceof CoffinBlock)) {
			return;
		}
		BlockPos headPos = footPos.relative(footState.getValue(FACING));
		BlockState headState = level.getBlockState(headPos);
		level.setBlock(footPos, footState.setValue(OCCUPIED, closed), 3);
		if (headState.getBlock() instanceof CoffinBlock) {
			level.setBlock(headPos, headState.setValue(OCCUPIED, closed), 3);
		}
	}
}
