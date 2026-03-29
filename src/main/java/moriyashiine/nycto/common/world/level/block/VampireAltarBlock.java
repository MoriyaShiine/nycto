/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.level.block;

import com.mojang.serialization.MapCodec;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.api.world.level.block.AltarBlock;
import moriyashiine.nycto.common.world.inventory.VampireAltarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;

public class VampireAltarBlock extends AltarBlock {
	public static final MapCodec<VampireAltarBlock> CODEC = simpleCodec(VampireAltarBlock::new);

	public VampireAltarBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<VampireAltarBlock> codec() {
		return CODEC;
	}

	@Override
	protected AbstractContainerMenu getScreenHandler(Level level, BlockPos pos, Inventory inventory, int containerId) {
		return new VampireAltarMenu(containerId, inventory, ContainerLevelAccess.create(level, pos));
	}

	@Override
	protected boolean canUse(Player player) {
		return NyctoAPI.isVampire(player);
	}
}
