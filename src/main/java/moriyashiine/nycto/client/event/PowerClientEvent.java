/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.nycto.client.event;

import moriyashiine.nycto.api.power.ActivePower;
import moriyashiine.nycto.api.power.PowerInstance;
import moriyashiine.nycto.client.NyctoClient;
import moriyashiine.nycto.common.component.entity.TransformationComponent;
import moriyashiine.nycto.common.init.ModEntityComponents;
import moriyashiine.nycto.common.payload.SyncPowerIndexPayload;
import moriyashiine.nycto.common.payload.UsePowerPayload;
import moriyashiine.nycto.common.util.NyctoUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PowerClientEvent {
	public static class UseBlock implements UseBlockCallback {
		@Override
		public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
			if (use(player)) {
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		}
	}

	public static class UseEntity implements UseEntityCallback {
		@Override
		public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
			if (use(player)) {
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		}
	}

	public static class UseItem implements UseItemCallback {
		@Override
		public ActionResult interact(PlayerEntity player, World world, Hand hand) {
			if (use(player)) {
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		}
	}

	public static class Tick implements ClientTickEvents.EndWorldTick {
		private static int cooldown = 0;

		@Override
		public void onEndTick(ClientWorld world) {
			if (cooldown > 0) {
				cooldown--;
			}
		}
	}

	public static boolean scrollPowerIndex(PlayerEntity player, double scrollAmount) {
		if (scrollAmount != 0) {
			TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(player);
			List<PowerInstance> powers = transformationComponent.getPowers();
			if (!powers.isEmpty()) {
				while (scrollAmount != 0) {
					int nextIndex = transformationComponent.getPowerIndex() + (int) Math.signum(scrollAmount);
					if (nextIndex < 0) {
						nextIndex += powers.size();
					}
					transformationComponent.setPowerIndex(nextIndex % powers.size());
					if (transformationComponent.getPowers().get(transformationComponent.getPowerIndex()).getPower() instanceof ActivePower) {
						scrollAmount -= Math.signum(scrollAmount);
					}
				}
				player.sendMessage(Text.translatable(transformationComponent.getPowers().get(transformationComponent.getPowerIndex()).getPower().getTranslationKey()), true);
				SyncPowerIndexPayload.send(transformationComponent.getPowerIndex());
				return true;
			}
		}
		return false;
	}

	public static boolean isActive(PlayerEntity player, TransformationComponent transformationComponent) {
		return player != null && player.isPartOfGame() && NyctoClient.POWER_HOTBAR_KEYBINDING.isPressed() && transformationComponent.hasActivePower();
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

	private static boolean use(PlayerEntity player) {
		TransformationComponent transformationComponent = ModEntityComponents.TRANSFORMATION.get(player);
		if (isActive(player, transformationComponent)) {
			if (Tick.cooldown == 0 && NyctoUtil.canUsePower(player, transformationComponent.getPowers().get(transformationComponent.getPowerIndex()))) {
				Tick.cooldown = 5;
				UsePowerPayload.send(transformationComponent.getPowerIndex());
			}
			return true;
		}
		return false;
	}
}
