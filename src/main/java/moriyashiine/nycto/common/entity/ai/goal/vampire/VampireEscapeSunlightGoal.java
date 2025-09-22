/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.common.entity.ai.goal.vampire;

import moriyashiine.nycto.common.component.entity.SunExposureComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.goal.EscapeSunlightGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class VampireEscapeSunlightGoal extends EscapeSunlightGoal {
	public VampireEscapeSunlightGoal(PathAwareEntity mob, double speed) {
		super(mob, speed);
	}

	@Override
	public boolean canStart() {
		return ModEntityComponents.SUN_EXPOSURE.get(mob).isExposed() && targetShadedPos();
	}

	@Override
	public void start() {
		super.start();
		mob.setTarget(null);
		mob.setAttacker(null);
		mob.getBrain().forget(MemoryModuleType.ANGRY_AT);
		mob.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
	}

	@Override
	protected @Nullable Vec3d locateShadedPos() {
		World world = mob.getWorld();
		BlockPos origin = mob.getBlockPos();
		Random random = mob.getRandom();

		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for (int i = 0; i < 10; i++) {
			mutable.set(origin.getX() + random.nextInt(20) - 10, origin.getY() + random.nextInt(6) - 3, origin.getZ() + random.nextInt(20) - 10);
			while (Math.abs(mutable.getY() - mob.getY()) < 5 && !world.getBlockState(mutable).getCollisionShape(world, mutable).isEmpty()) {
				mutable.move(Direction.UP);
			}
			if (!SunExposureComponent.exposedAtPos(mob, mutable)) {
				return Vec3d.ofBottomCenter(mutable);
			}
		}

		return null;
	}
}
