/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.nycto.client.event.entity;

import moriyashiine.nycto.common.init.NyctoMobEffects;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.Nullable;

public class MobEffectClientEvent {
	public static void init() {
		ClientPreAttackCallback.EVENT.register(new StunnedAttack());
		UseBlockCallback.EVENT.register(new StunnedUseBlock());
		UseEntityCallback.EVENT.register(new StunnedUseEntity());
		UseItemCallback.EVENT.register(new StunnedUseItem());
	}

	private static class StunnedAttack implements ClientPreAttackCallback {
		@Override
		public boolean onClientPlayerPreAttack(Minecraft client, LocalPlayer player, int clickCount) {
			return player.hasEffect(NyctoMobEffects.STUNNED);
		}
	}

	private static class StunnedUseBlock implements UseBlockCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand interactionHand, BlockHitResult blockHitResult) {
			if (player.hasEffect(NyctoMobEffects.STUNNED)) {
				return InteractionResult.FAIL;
			}
			return InteractionResult.PASS;
		}
	}

	private static class StunnedUseEntity implements UseEntityCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand interactionHand, Entity entity, @Nullable EntityHitResult entityHitResult) {
			if (player.hasEffect(NyctoMobEffects.STUNNED)) {
				return InteractionResult.FAIL;
			}
			return InteractionResult.PASS;
		}
	}

	private static class StunnedUseItem implements UseItemCallback {
		@Override
		public InteractionResult interact(Player player, Level level, InteractionHand interactionHand) {
			if (player.hasEffect(NyctoMobEffects.STUNNED)) {
				return InteractionResult.FAIL;
			}
			return InteractionResult.PASS;
		}
	}
}
