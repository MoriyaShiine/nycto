package moriyashiine.nycto.common.world.power.vampire;

import moriyashiine.nycto.common.component.entity.power.vampire.KeenSensesComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class KeenSensesPower extends VampireActivePower {
	public KeenSensesPower() {
		super(20);
	}

	@Override
	public void onRemoved(ServerPlayer player) {
		KeenSensesComponent keenSenses = NyctoEntityComponents.KEEN_SENSES.get(player);
		if (keenSenses.isEnabled()) {
			keenSenses.toggle();
		}
	}

	@Override
	protected int getBaseCost(LivingEntity entity) {
		if (entity instanceof Player player && NyctoEntityComponents.KEEN_SENSES.get(player).isEnabled()) {
			return 0;
		}
		return 1;
	}

	@Override
	public boolean shouldBroadcastUseSound() {
		return false;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return NyctoEntityComponents.KEEN_SENSES.get(player).isEnabled() ? NyctoSoundEvents.KEEN_SENSES_OFF : NyctoSoundEvents.KEEN_SENSES_ON;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		NyctoEntityComponents.KEEN_SENSES.get(player).toggle();
	}
}
