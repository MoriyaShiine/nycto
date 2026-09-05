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

public class HaemogenesisPower extends VampireActivePower {
	public HaemogenesisPower() {
		super(400);
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		return 10;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return NyctoSoundEvents.HAEMOGENESIS_USE;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		startHealing(player);
	}

	public static void startHealing(LivingEntity entity) {
		NyctoEntityComponents.HAEMOGENESIS.get(entity).startHealing();
		NyctoAPI.drainBlood(entity, NyctoPowers.HAEMOGENESIS.getCost(entity));
	}
}
