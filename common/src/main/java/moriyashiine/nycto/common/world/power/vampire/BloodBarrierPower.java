package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class BloodBarrierPower extends VampireActivePower {
	public BloodBarrierPower() {
		super(600);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return 10;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return NyctoSoundEvents.BLOOD_BARRIER_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		activate(player);
	}

	public static void activate(LivingEntity entity) {
		NyctoEntityComponents.BLOOD_BARRIER.get(entity).use();
		NyctoAPI.drainBlood(entity, NyctoPowers.BLOOD_BARRIER.getCost(entity));
	}
}
