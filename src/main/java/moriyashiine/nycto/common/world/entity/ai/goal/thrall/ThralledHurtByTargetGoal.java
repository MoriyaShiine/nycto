package moriyashiine.nycto.common.world.entity.ai.goal.thrall;

import moriyashiine.nycto.common.init.NyctoEntityComponents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class ThralledHurtByTargetGoal extends HurtByTargetGoal {
	public ThralledHurtByTargetGoal(PathfinderMob mob, Class<?>... ignoreDamageFromTheseTypes) {
		super(mob, ignoreDamageFromTheseTypes);
	}

	@Override
	public boolean canUse() {
		return NyctoEntityComponents.VAMPIRIC_THRALL.get(mob).hasOwner() && super.canUse();
	}
}
