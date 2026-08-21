package moriyashiine.nycto.common.world.power;

import moriyashiine.nycto.api.world.power.ActivePower;
import moriyashiine.nycto.common.component.entity.power.NightVisionComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;

public class NightVisionPower extends ActivePower {
	public NightVisionPower() {
		super(20);
	}

	@Override
	public void onAdded(ServerPlayer player) {
		NightVisionComponent nightVision = NyctoEntityComponents.NIGHT_VISION.get(player);
		if (!nightVision.isEnabled()) {
			nightVision.toggle();
		}
	}

	@Override
	public void onRemoved(ServerPlayer player) {
		NightVisionComponent nightVision = NyctoEntityComponents.NIGHT_VISION.get(player);
		if (nightVision.isEnabled()) {
			nightVision.toggle();
		}
	}

	@Override
	public boolean shouldBroadcastUseSound() {
		return false;
	}

	@Override
	public SoundEvent getUseSound(Player player) {
		return NyctoEntityComponents.NIGHT_VISION.get(player).isEnabled() ? NyctoSoundEvents.NIGHT_VISION_OFF : NyctoSoundEvents.NIGHT_VISION_ON;
	}

	@Override
	public void use(ServerLevel level, ServerPlayer player) {
		NyctoEntityComponents.NIGHT_VISION.get(player).toggle();
	}
}
