/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event;

import moriyashiine.nycto.api.world.power.ActivePower;
import moriyashiine.nycto.api.world.power.PowerInstance;
import moriyashiine.nycto.client.NyctoClient;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
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
	public static class UseBlock implements UseBlockCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand interactionHand, BlockHitResult blockHitResult) {
			if (use(player)) {
				return InteractionResult.FAIL;
			}
			return InteractionResult.PASS;
		}
	}

	public static class UseEntity implements UseEntityCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand interactionHand, Entity entity, @Nullable EntityHitResult entityHitResult) {
			if (use(player)) {
				return InteractionResult.FAIL;
			}
			return InteractionResult.PASS;
		}
	}

	public static class UseItem implements UseItemCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand interactionHand) {
			if (use(player)) {
				return InteractionResult.FAIL;
			}
			return InteractionResult.PASS;
		}
	}

	public static class Tick implements ClientTickEvents.EndLevelTick {
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
			TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(player);
			List<PowerInstance> powers = transformationComponent.getPowers();
			if (!powers.isEmpty()) {
				while (wheel != 0) {
					int nextIndex = transformationComponent.getPowerIndex() + (int) Math.signum(wheel);
					if (nextIndex < 0) {
						nextIndex += powers.size();
					}
					transformationComponent.setPowerIndex(nextIndex % powers.size());
					if (transformationComponent.getPowers().get(transformationComponent.getPowerIndex()).getPower() instanceof ActivePower) {
						wheel -= Math.signum(wheel);
					}
				}
				player.sendOverlayMessage(Component.translatable(transformationComponent.getPowers().get(transformationComponent.getPowerIndex()).getPower().getOrCreateDescriptionId()));
				SyncPowerIndexPayload.send(transformationComponent.getPowerIndex());
				return true;
			}
		}
		return false;
	}

	public static boolean isActive(Player player, TransformationComponent transformationComponent) {
		return player != null && player.slib$exists() && NyctoClient.POWER_HOTBAR_KEYMAPPING.isDown() && transformationComponent.hasActivePower();
	}

	public static int getActivePowersIndex(TransformationComponent transformationComponent) {
		int index = 0;
		for (int i = 0; i < transformationComponent.getPowers().size(); i++) {
			if (i == transformationComponent.getPowerIndex()) {
				break;
			}
			if (transformationComponent.getPowers().get(i).getPower() instanceof ActivePower) {
				index++;
			}
		}
		return index;
	}

	private static boolean use(Player player) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(player);
		if (isActive(player, transformationComponent)) {
			if (Tick.cooldown == 0 && SLibClientUtils.isHost(player) && NyctoUtil.canUsePower(player, transformationComponent.getPowers().get(transformationComponent.getPowerIndex()))) {
				Tick.cooldown = 5;
				UsePowerPayload.send(transformationComponent.getPowerIndex());
			}
			return true;
		}
		return false;
	}
}
