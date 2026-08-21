package moriyashiine.nycto.common.world.level.block.entity;

import moriyashiine.nycto.common.init.NyctoBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CoffinBlockEntity extends BedBlockEntity {
	public CoffinBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return NyctoBlockEntityTypes.COFFIN;
	}

	@Override
	public boolean isValidBlockState(BlockState state) {
		return NyctoBlockEntityTypes.COFFIN.isValid(state);
	}
}
