package moriyashiine.nycto.neoforge.common;

import com.google.auto.service.AutoService;
import moriyashiine.nycto.common.Nycto;
import moriyashiine.nycto.common.NyctoService;
import moriyashiine.nycto.common.tag.NyctoBlockTags;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.util.ClockAdjustment;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;

import java.util.Optional;

@AutoService(NyctoService.class)
public class NyctoNeoForgeService implements NyctoService {
	private static final AttributeModifier CREATIVE_FLIGHT_MODIFIER = new AttributeModifier(Nycto.id("bat_form_creative_flight"), 1, AttributeModifier.Operation.ADD_VALUE);

	@Override
	public void initNeoForge() {
		NeoForge.EVENT_BUS.addListener(NyctoNeoForgeService::coffinWakeTime);
	}

	@Override
	public void applyBatFormAbilities(Player player, boolean grant) {
		SLibUtils.applyAttributeModifier(player, NeoForgeMod.CREATIVE_FLIGHT, CREATIVE_FLIGHT_MODIFIER, grant);
	}

	private static void coffinWakeTime(SleepFinishedTimeEvent event) {
		boolean allInCoffins = true;
		for (Player player : event.getLevel().players()) {
			Optional<BlockPos> sleepingPos = player.getSleepingPos();
			if (sleepingPos.isPresent() && !event.getLevel().getBlockState(sleepingPos.get()).is(NyctoBlockTags.COFFINS)) {
				allInCoffins = false;
				break;
			}
		}
		if (allInCoffins) {
			event.setAdjustment(new ClockAdjustment.Marker(ClockTimeMarkers.NIGHT));
		}
	}
}
