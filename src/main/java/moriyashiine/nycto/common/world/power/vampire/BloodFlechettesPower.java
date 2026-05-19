/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import moriyashiine.nycto.common.world.entity.projectile.arrow.BloodFlechette;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BloodFlechettesPower extends VampireActivePower {
	public BloodFlechettesPower() {
		super(40);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return 3;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return NyctoSoundEvents.BLOOD_FLECHETTES_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		spawnProjectiles(level, player);
	}

	public static void spawnProjectiles(Level level, LivingEntity entity) {
		for (int i = 0; i < entity.getRandom().nextIntBetweenInclusive(6, 8); i++) {
			BloodFlechette bloodFlechette = new BloodFlechette(level, entity);
			bloodFlechette.shootFromRotation(entity, entity.getXRot(), entity.getYHeadRot(), 0, 1, i == 0 ? 0 : 24);
			level.addFreshEntity(bloodFlechette);
		}
		NyctoEntityComponents.BLOOD.get(entity).drain(NyctoPowers.BLOOD_FLECHETTES.getCost(entity));
	}
}
