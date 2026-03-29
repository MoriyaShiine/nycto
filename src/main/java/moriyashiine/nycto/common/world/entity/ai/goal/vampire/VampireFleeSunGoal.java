/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.entity.ai.goal.vampire;

import moriyashiine.nycto.common.component.entity.SunExposureComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class VampireFleeSunGoal extends FleeSunGoal {
	public VampireFleeSunGoal(PathfinderMob mob, double speedModifier) {
		super(mob, speedModifier);
	}

	@Override
	public boolean canUse() {
		return ModEntityComponents.SUN_EXPOSURE.get(mob).isExposed() && setWantedPos();
	}

	@Override
	public void start() {
		super.start();
		mob.setTarget(null);
		mob.setLastHurtByMob(null);
		mob.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
		mob.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
	}

	@Override
	protected @Nullable Vec3 getHidePos() {
		Level level = mob.level();
		BlockPos origin = mob.blockPosition();
		RandomSource random = mob.getRandom();

		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (int i = 0; i < 10; i++) {
			mutable.set(origin.getX() + random.nextInt(20) - 10, origin.getY() + random.nextInt(6) - 3, origin.getZ() + random.nextInt(20) - 10);
			while (Math.abs(mutable.getY() - mob.getY()) < 5 && !level.getBlockState(mutable).getCollisionShape(level, mutable).isEmpty()) {
				mutable.move(Direction.UP);
			}
			if (!SunExposureComponent.exposedAtPos(mob, mutable)) {
				return Vec3.atBottomCenterOf(mutable);
			}
		}

		return null;
	}
}
