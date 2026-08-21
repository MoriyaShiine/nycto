package moriyashiine.nycto.client.event;

import moriyashiine.nycto.api.world.power.ActivePower;
import moriyashiine.nycto.api.world.power.PowerInstance;
import moriyashiine.nycto.client.NyctoClient;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.payload.SyncPowerIndexPayload;
import moriyashiine.nycto.common.payload.UsePowerPayload;
import moriyashiine.nycto.common.util.NyctoUtil;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class PowerClientEvent {
	public static void init() {
		UseBlockCallback.EVENT.register(new UseBlock());
		UseEntityCallback.EVENT.register(new UseEntity());
		UseItemCallback.EVENT.register(new UseItem());
		ClientTickEvents.END_LEVEL_TICK.register(new Tick());
	}

	private static class UseBlock implements UseBlockCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand interactionHand, BlockHitResult blockHitResult) {
			if (use(player)) {
				return InteractionResult.FAIL;
			}
			return InteractionResult.PASS;
		}
	}

	private static class UseEntity implements UseEntityCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand interactionHand, Entity entity, @Nullable EntityHitResult entityHitResult) {
			if (use(player)) {
				return InteractionResult.FAIL;
			}
			return InteractionResult.PASS;
		}
	}

	private static class UseItem implements UseItemCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand interactionHand) {
			if (use(player)) {
				return InteractionResult.FAIL;
			}
			return InteractionResult.PASS;
		}
	}

	private static class Tick implements ClientTickEvents.EndLevelTick {
		private static int cooldown = 0;

		@Override
		public void onEndTick(ClientLevel level) {
			if (cooldown > 0) {
				cooldown--;
			}
		}
	}

	public static boolean scrollPowerIndex(Player player, double wheel) {
		if (wheel != 0) {
			TransformationComponent transformation = NyctoEntityComponents.TRANSFORMATION.get(player);
			List<PowerInstance> powers = transformation.getPowers();
			if (!powers.isEmpty()) {
				while (wheel != 0) {
					int nextIndex = transformation.getPowerIndex() + (int) Math.signum(wheel);
					if (nextIndex < 0) {
						nextIndex += powers.size();
					}
					transformation.setPowerIndex(nextIndex % powers.size());
					if (transformation.getPowers().get(transformation.getPowerIndex()).getPower() instanceof ActivePower) {
						wheel -= Math.signum(wheel);
					}
				}
				player.sendOverlayMessage(Component.translatable(transformation.getPowers().get(transformation.getPowerIndex()).getPower().getOrCreateDescriptionId()));
				SyncPowerIndexPayload.send(transformation.getPowerIndex());
				return true;
			}
		}
		return false;
	}

	public static boolean isActive(Player player, TransformationComponent transformation) {
		return player != null && player.slib$exists() && NyctoClient.POWER_HOTBAR_KEYMAPPING.isDown() && transformation.hasActivePower();
	}

	public static int getActivePowersIndex(TransformationComponent transformation) {
		int index = 0;
		for (int i = 0; i < transformation.getPowers().size(); i++) {
			if (i == transformation.getPowerIndex()) {
				break;
			}
			if (transformation.getPowers().get(i).getPower() instanceof ActivePower) {
				index++;
			}
		}
		return index;
	}

	private static boolean use(Player player) {
		TransformationComponent transformation = NyctoEntityComponents.TRANSFORMATION.get(player);
		if (isActive(player, transformation)) {
			if (Tick.cooldown == 0 && SLibClientUtils.isHost(player) && NyctoUtil.canUsePower(player, transformation.getPowers().get(transformation.getPowerIndex()))) {
				Tick.cooldown = 5;
				UsePowerPayload.send(transformation.getPowerIndex());
			}
			return true;
		}
		return false;
	}
}
