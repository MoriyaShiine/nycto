/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.block;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.block.AltarBlock;
import moriyashiine.nycto.common.screenhandler.VampireAltarScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VampireAltarBlock extends AltarBlock {
	public static final MapCodec<VampireAltarBlock> CODEC = createCodec(VampireAltarBlock::new);

	public VampireAltarBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends Block> getCodec() {
		return CODEC;
	}

	@Override
	protected ScreenHandler getScreenHandler(World world, BlockPos pos, PlayerInventory playerInventory, int syncId) {
		return new VampireAltarScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(world, pos));
	}

	@Override
	protected boolean canUse(PlayerEntity player) {
		return NyctoAPI.isVampire(player);
	}
}
