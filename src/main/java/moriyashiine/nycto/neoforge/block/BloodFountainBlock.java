/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.neoforge.block;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.neoforge.NyctoData;
import moriyashiine.nycto.neoforge.NyctoItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Locale;

public class BloodFountainBlock extends HorizontalDirectionalBlock {
	public static final MapCodec<BloodFountainBlock> CODEC = simpleCodec(BloodFountainBlock::new);
	public static final EnumProperty<FillState> FILL_STATE = EnumProperty.create("fill_state", FillState.class);

	private static final VoxelShape SHAPE = Shapes.or(Block.box(1, 0, 1, 15, 6, 15), Block.box(3, 6, 3, 13, 14, 13), Block.box(5, 14, 5, 11, 16, 11));

	public BloodFountainBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState()
				.setValue(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH)
				.setValue(BlockStateProperties.LOCKED, false)
				.setValue(FILL_STATE, FillState.EMPTY));
	}

	@Override
	protected MapCodec<BloodFountainBlock> codec() {
		return CODEC;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return false;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (!player.isShiftKeyDown()) {
			return InteractionResult.PASS;
		}
		if (!level.isClientSide()) {
			level.setBlockAndUpdate(pos, state.cycle(BlockStateProperties.LOCKED));
			player.sendSystemMessage(Component.translatable(state.getValue(BlockStateProperties.LOCKED) ? "message.nycto.blood_fountain.unlocked" : "message.nycto.blood_fountain.locked"));
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		FillState fillState = state.getValue(FILL_STATE);
		if (fillState == FillState.EMPTY && isBloodBottle(stack) && !state.getValue(BlockStateProperties.LOCKED)) {
			if (!level.isClientSide()) {
				if (!player.getAbilities().instabuild) {
					stack.consume(1, player);
				}
				level.setBlockAndUpdate(pos, state.setValue(FILL_STATE, FillState.BLOOD));
				player.sendSystemMessage(Component.translatable("message.nycto.blood_fountain.stored_blood"));
			}
			return ItemInteractionResult.SUCCESS;
		}
		if (stack.is(Items.GLASS_BOTTLE)) {
			if (fillState == FillState.BLOOD) {
				if (!level.isClientSide()) {
					consumeBottleAndGiveBlood(stack, player, hand);
					level.setBlockAndUpdate(pos, state.setValue(FILL_STATE, FillState.EMPTY));
					player.sendSystemMessage(Component.translatable("message.nycto.blood_fountain.draw_blood"));
				}
				return ItemInteractionResult.SUCCESS;
			}
			if (fillState == FillState.EMPTY && NyctoData.isVampire(player) && NyctoData.getBlood(player) >= 8 && !state.getValue(BlockStateProperties.LOCKED)) {
				if (!level.isClientSide()) {
					NyctoData.addBlood(player, -8);
					consumeBottleAndGiveBlood(stack, player, hand);
					player.sendSystemMessage(Component.translatable("message.nycto.blood_fountain.condense_blood"));
				}
				return ItemInteractionResult.SUCCESS;
			}
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LOCKED, FILL_STATE);
	}

	private static boolean isBloodBottle(ItemStack stack) {
		return stack.is(NyctoItems.BLOOD_BOTTLE.get()) || stack.is(NyctoItems.VAMPIRE_BLOOD_BOTTLE.get());
	}

	private static void consumeBottleAndGiveBlood(ItemStack stack, Player player, InteractionHand hand) {
		if (player.getAbilities().instabuild) {
			if (!player.getInventory().add(new ItemStack(NyctoItems.BLOOD_BOTTLE.get()))) {
				player.drop(new ItemStack(NyctoItems.BLOOD_BOTTLE.get()), false);
			}
			return;
		}
		stack.consume(1, player);
		ItemStack bloodBottle = new ItemStack(NyctoItems.BLOOD_BOTTLE.get());
		if (stack.isEmpty()) {
			player.setItemInHand(hand, bloodBottle);
		} else if (!player.getInventory().add(bloodBottle)) {
			player.drop(bloodBottle, false);
		}
	}

	public enum FillState implements StringRepresentable {
		EMPTY, BLOOD, AMBROSIA;

		@Override
		public String getSerializedName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
}
