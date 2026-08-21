package moriyashiine.nycto.common.world.entity.ai.goal.vampire;

import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.nycto.common.world.entity.monster.Vampire;
import moriyashiine.nycto.common.world.power.vampire.BloodFlechettesPower;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.ai.goal.Goal;

public class BloodFlechettesGoal extends Goal {
	private final Vampire mob;

	public BloodFlechettesGoal(Vampire mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		return mob.canUsePower(NyctoPowers.BLOOD_FLECHETTES) && mob.getTarget() != null && mob.hasLineOfSight(mob.getTarget());
	}

	@Override
	public void start() {
		mob.useAbility(NyctoPowers.BLOOD_FLECHETTES);
		SLibUtils.playAnchoredSound(mob, NyctoSoundEvents.BLOOD_FLECHETTES_USE);
		mob.lookAt(EntityAnchorArgument.Anchor.EYES, mob.getTarget().getEyePosition());
		BloodFlechettesPower.spawnProjectiles(mob.level(), mob);
	}
}
